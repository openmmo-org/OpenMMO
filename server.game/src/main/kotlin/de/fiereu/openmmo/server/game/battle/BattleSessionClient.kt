package de.fiereu.openmmo.server.game.battle

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.charset.StandardCharsets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * Kotlin client for the Node **Battle-Service** sidecar (Track A). Speaks the localhost
 * newline-JSON protocol (see server/src/battle-service/protocol.ts). The sidecar owns wild-battle
 * resolution (pokemon-showdown); this class is the server.game side of that boundary.
 *
 * One in-flight request at a time per connection (a wild battle's actions are sequential), guarded
 * by a Mutex; the connection is opened lazily and reused.
 */
class BattleSessionClient(
    private val host: String = "127.0.0.1",
    private val port: Int = 7801,
    private val connectTimeoutMs: Int = 5_000,
) : AutoCloseable {

  private val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
  }
  private val mutex = Mutex()
  private var socket: Socket? = null
  private var reader: BufferedReader? = null
  private var writer: OutputStream? = null
  private var idSeq = 0

  /** Start a wild battle. Player is p1, wild is p2. */
  suspend fun create(
      playerTeam: List<WirePokemon>,
      wildTeam: List<WirePokemon>,
      battleId: String? = null,
  ): CreateResult {
    val params =
        json.encodeToJsonElement(
            CreateParams.serializer(),
            CreateParams(playerTeam, wildTeam, battleId),
        )
    return decode(call("create", params), CreateResult.serializer())
  }

  /** Submit the player's choice: "move 1".."move 4" or "switch 2".."switch 6". */
  suspend fun choice(battleId: String, choice: String): TurnResult {
    val params = json.encodeToJsonElement(ChoiceParams.serializer(), ChoiceParams(battleId, choice))
    return decode(call("choice", params), ChoiceResult.serializer()).turn
  }

  /** Attempt a catch; on success the battle finishes and `caughtPokemon` echoes the wild mon. */
  suspend fun attemptCatch(
      battleId: String,
      ballItemId: String,
      rateModifier: Double? = null
  ): CatchResult {
    val params =
        json.encodeToJsonElement(
            CatchParams.serializer(),
            CatchParams(battleId, ballItemId, rateModifier),
        )
    return decode(call("catch", params), CatchResult.serializer())
  }

  /** Player flees; battle finishes. */
  suspend fun run(battleId: String): TurnResult {
    val params = json.encodeToJsonElement(IdParams.serializer(), IdParams(battleId))
    return decode(call("run", params), ChoiceResult.serializer()).turn
  }

  /** Dispose a battle on the sidecar. */
  suspend fun end(battleId: String) {
    val params = json.encodeToJsonElement(IdParams.serializer(), IdParams(battleId))
    call("end", params)
  }

  private suspend fun call(method: String, params: JsonElement): JsonElement =
      mutex.withLock {
        withContext(Dispatchers.IO) {
          ensureConnected()
          val id = "k${++idSeq}"
          val request = buildJsonObject {
            put("id", id)
            put("method", method)
            put("params", params)
          }
          val out = writer ?: error("battle-service not connected")
          out.write((request.toString() + "\n").toByteArray(StandardCharsets.UTF_8))
          out.flush()
          val line = reader?.readLine() ?: error("battle-service closed the connection")
          val response = json.decodeFromString(RawResponse.serializer(), line)
          if (!response.ok || response.result == null) {
            error("battle-service error: ${response.error?.message ?: "unknown"}")
          }
          response.result
        }
      }

  private fun <T> decode(
      element: JsonElement,
      deserializer: kotlinx.serialization.DeserializationStrategy<T>
  ): T = json.decodeFromJsonElement(deserializer, element)

  private fun ensureConnected() {
    val s = socket
    if (s != null && s.isConnected && !s.isClosed) return
    val fresh = Socket()
    fresh.connect(InetSocketAddress(host, port), connectTimeoutMs)
    socket = fresh
    reader = BufferedReader(InputStreamReader(fresh.getInputStream(), StandardCharsets.UTF_8))
    writer = fresh.getOutputStream()
  }

  override fun close() {
    runCatching { reader?.close() }
    runCatching { writer?.close() }
    runCatching { socket?.close() }
    socket = null
    reader = null
    writer = null
  }
}

// ---- request params (mirror protocol.ts) ----------------------------------

@Serializable
private data class CreateParams(
    val playerTeam: List<WirePokemon>,
    val wildTeam: List<WirePokemon>,
    val battleId: String? = null,
)

@Serializable private data class ChoiceParams(val battleId: String, val choice: String)

@Serializable
private data class CatchParams(
    val battleId: String,
    val ballItemId: String,
    val rateModifier: Double? = null
)

@Serializable private data class IdParams(val battleId: String)

// ---- responses (mirror protocol.ts) ---------------------------------------

@Serializable
private data class RawResponse(
    val id: String = "",
    val ok: Boolean = false,
    val result: JsonElement? = null,
    val error: ErrorBody? = null,
)

@Serializable private data class ErrorBody(val message: String)

@Serializable data class CreateResult(val battleId: String, val turn: TurnResult)

@Serializable private data class ChoiceResult(val turn: TurnResult)

@Serializable
data class CatchResult(
    @SerialName("catch") val catchOutcome: CatchOutcome,
    val turn: TurnResult,
)

@Serializable
data class CatchOutcome(
    val success: Boolean,
    val shakes: Int = 0,
    val caughtPokemon: WirePokemon? = null,
    val message: String = "",
)

/** The per-action snapshot streamed back to the client (see protocol.ts TurnResult). */
@Serializable
data class TurnResult(
    val battleId: String,
    val log: List<String> = emptyList(),
    /** Both sides' active mon + hp/status; opaque here (translated by the packet layer). */
    val sides: JsonElement? = null,
    /** Showdown request (legal choices this turn); null once finished. */
    val request: JsonElement? = null,
    val finished: Boolean = false,
    val winner: String? = null,
)

// ---- the wire Pokémon shape the sidecar expects (TS OwnedPokemon) ----------
// The adapter from server.game's own Pokémon model to this is gated on Pi's
// party/storage types (T0); WirePokemon is the stable sidecar boundary type.

@Serializable
data class WirePokemon(
    val uid: String,
    val speciesId: Int,
    val level: Int,
    val exp: Int = 0,
    val nature: String,
    val ability: String,
    val gender: String = "genderless",
    val shiny: Boolean = false,
    val ivs: WireStats,
    val evs: WireStats,
    val moves: List<WireMove>,
    val heldItem: String? = null,
    val friendship: Int = 70,
    val otWallet: String = "",
    val otName: String = "",
    val pid: Int = 0,
    val metLevel: Int = 1,
    val metLocation: String = "",
)

/**
 * Sidecar stat convention (Showdown / domain OwnedPokemon): **spa**=Sp.Atk, **spd**=Sp.Def,
 * **spe**=Speed. The domain `OwnedPokemon` uses these same names, so the domain→wire adapter is
 * 1:1.
 *
 * ⚠️ FOOTGUN — do NOT confuse with `common.Pokemon` / `PokemonStats`, whose `spd` means **SPEED**
 * (and Sp.Def is `spDef`). Any conversion touching `common.Pokemon` (e.g. the S2C PokemonContainer
 * emission) must map `spd(wire)←spDef(common)` and `spe(wire)←spd(common)`. See
 * docs/BATTLE-PACKET-MAP.md.
 */
@Serializable
data class WireStats(
    val hp: Int,
    val atk: Int,
    val def: Int,
    val spa: Int,
    val spd: Int,
    val spe: Int
)

@Serializable data class WireMove(val moveId: Int, val ppUp: Int = 0, val ppCurrent: Int = 0)
