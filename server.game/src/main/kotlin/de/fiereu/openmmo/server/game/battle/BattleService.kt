package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.BattleOpenPacket
import de.fiereu.openmmo.server.game.domain.OwnedPokemon
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val log = KotlinLogging.logger {}

// C2S 0x32 BattleActionSelect.actionKindId values (validated vs capture 2026-07-07-232143).
private const val ACTION_NAV = 0
private const val ACTION_USE_ITEM = 1
private const val DEFAULT_BALL_ID = "poke-ball"

/**
 * server.game battle orchestrator. Owns the mapping between a live client session and its sidecar
 * battle, and is the single place that translates PokéMMO battle packets ↔ the Battle-Service
 * sidecar (via [BattleSessionClient]). Wild-battle resolution + catch math live in the sidecar
 * (pokemon-showdown); this class never re-runs them — it forwards the sidecar's outcome.
 *
 * Works in domain [OwnedPokemon]; converts to the sidecar [WirePokemon] shape only at the sidecar
 * edge, so a caught mon is persisted as the ORIGINAL domain object Kotlin generated (lossless).
 *
 * STILL GATED (needs capture #2 + live-client validation): the S2C turn-event stream
 * (0x33/0x16/0x79/0x31 from the sidecar log) and the C2S 0x32 move-select variant. Those are
 * intentionally not emitted here so we don't guess byte layouts that could crash the client.
 */
/**
 * Persists a caught mon to the player's party/storage. The DI binding delegates to the merged
 * `PokemonPartyService.addCaughtPokemon(characterId, OwnedPokemon)` (which emits the S2C 0x14
 * delta); this seam keeps BattleService unit-testable without instrumenting the final service
 * class.
 */
fun interface CaughtPokemonSink {
  fun persist(characterId: Long, pokemon: OwnedPokemon)
}

@Singleton
class BattleService
@Inject
constructor(
    private val sidecar: BattleSessionClient,
    private val caughtSink: CaughtPokemonSink,
) {

  private data class BattleState(
      val battleId: String,
      val wildMon: OwnedPokemon,
      val characterId: Long,
      /** The two per-mon entity ids stamped into the 0x30 open; every event reuses them. */
      val playerMonEntityId: Long,
      val wildMonEntityId: Long,
  )

  /** session → active battle. */
  private val battleBySession = ConcurrentHashMap<SessionContext, BattleState>()

  /** Session-global source of battle-mon entity ids (see [BattleEntityIdAllocator]). */
  private val entityIds = BattleEntityIdAllocator()

  /**
   * Start a wild battle for [session]. [playerTeam] comes from the party (PokemonPartyService);
   * [wildMon] from the Kotlin EncounterGenerator. Sends the validated S2C 0x30 battle-open and
   * tracks the wild mon so a successful catch persists the exact generated instance.
   */
  suspend fun startWildBattle(
      session: SessionContext,
      characterId: Long,
      playerTeam: List<OwnedPokemon>,
      wildMon: OwnedPokemon,
  ): CreateResult {
    val created = sidecar.create(playerTeam.map { it.toWire() }, listOf(wildMon.toWire()))
    // Assign the two per-mon entity ids for THIS battle and track them so every S2C event reuses
    // the exact ids the client learns from the 0x30 open below.
    val playerMonEntityId = entityIds.next()
    val wildMonEntityId = entityIds.next()
    battleBySession[session] =
        BattleState(created.battleId, wildMon, characterId, playerMonEntityId, wildMonEntityId)
    log.info { "Wild battle ${created.battleId} started for character $characterId" }
    session.send(
        buildWildOpen(
            playerTeam.first(),
            wildMon,
            created.turn,
            playerCharEntityId = characterId,
            playerMonEntityId = playerMonEntityId,
            wildMonEntityId = wildMonEntityId,
        ),
    )
    // TODO(PR-next, gated on capture #2 + live client): translate created.turn.log + subsequent
    //   turn logs → the S2C event codecs (0x33/0x16/0x79/0x31) via [hpDeltas] + siblings; see
    //   docs/BATTLE-PACKET-MAP.md.
    return created
  }

  /**
   * Build the S2C `0x30` battle-open: species/level from the domain mons, current/max HP from the
   * sidecar `sides`, and the session-assigned entity ids (player character + both active mons). The
   * ids default to the captured template's ids so callers that don't care (unit tests) stay
   * byte-exact vs the capture. TODO(session): player name is still template-derived.
   */
  fun buildWildOpen(
      player: OwnedPokemon,
      wild: OwnedPokemon,
      turn: TurnResult,
      playerCharEntityId: Long = BattleOpenPacket.TEMPLATE_PLAYER_CHAR_ENTITY_ID,
      playerMonEntityId: Long = BattleOpenPacket.TEMPLATE_PLAYER_MON_ENTITY_ID,
      wildMonEntityId: Long = BattleOpenPacket.TEMPLATE_WILD_MON_ENTITY_ID,
  ): BattleOpenPacket {
    val (playerCur, playerMax) = activeHp(turn.sides, "player")
    val (wildCur, wildMax) = activeHp(turn.sides, "wild")
    return BattleOpenPacket.wild(
        playerSpecies = player.speciesId,
        playerLevel = player.level,
        playerCurrentHp = playerCur,
        playerMaxHp = playerMax,
        wildSpecies = wild.speciesId,
        wildLevel = wild.level,
        wildCurrentHp = wildCur,
        wildMaxHp = wildMax,
        playerCharEntityId = playerCharEntityId,
        playerMonEntityId = playerMonEntityId,
        wildMonEntityId = wildMonEntityId,
    )
  }

  /**
   * The validated S2C `0x16` HP deltas for a turn, keyed to THIS session's tracked battle-mon
   * entity ids — i.e. the same ids the client learned from the `0x30` open. This is the seam the
   * full turn-event stream (still gated on capture #2) will emit through; it never guesses an
   * unvalidated layout. Empty if the session has no active battle.
   */
  fun hpDeltas(session: SessionContext, turn: TurnResult): List<BattleEntityDeltaPacket> {
    val state = battleBySession[session] ?: return emptyList()
    return BattleEventTranslator.hpDeltas(
        turn.sides, state.playerMonEntityId, state.wildMonEntityId)
  }

  /**
   * C2S `0x32` BattleActionSelect — the in-battle action menu. `actionKindId` (validated vs
   * capture): 0 = menu nav/confirm, 1 = use item (Poké Ball throw). The move-select variant is
   * UNCONFIRMED (only nav + ball were captured) → stubbed pending capture #2; we do NOT guess it.
   */
  suspend fun onBattleAction(session: SessionContext, packet: BattleActionSelectPacket) {
    when (packet.actionKindId.toInt()) {
      ACTION_USE_ITEM ->
          // TODO(codec): the C2S 0x32 read is a 2-byte stub — parse packet.moveOrItemId for the
          //   actual ball and map it to a ball name; defaulting to a standard Poké Ball for now.
          onThrowBall(session, DEFAULT_BALL_ID)
      ACTION_NAV -> Unit // menu advance/confirm — no sidecar action
      else -> Unit // TODO(capture #2): FIGHT→attack move-select variant
    }
    // TODO(PR-next): translate the resulting turn.log → the S2C event codecs (0x33/0x16/0x79/0x31).
  }

  /** C2S BattleMoveUse(0x0A) → sidecar choice("move N"). */
  suspend fun onMoveUse(session: SessionContext, moveSlotIndex: Int): TurnResult? {
    val state = battleBySession[session] ?: return null
    val turn = sidecar.choice(state.battleId, "move ${moveSlotIndex + 1}")
    // TODO(capture): translate turn.log/sides → S2C battle event packets.
    finishIfEnded(session, turn)
    return turn
  }

  /** C2S BattlePartySwitch(0x0C) → sidecar choice("switch N"). */
  suspend fun onSwitch(session: SessionContext, partySlot: Int): TurnResult? {
    val state = battleBySession[session] ?: return null
    val turn = sidecar.choice(state.battleId, "switch ${partySlot + 1}")
    finishIfEnded(session, turn)
    return turn
  }

  /**
   * The player threw a ball (C2S 0x32, actionKind=item). The sidecar resolves the catch; on success
   * the ORIGINAL generated wild mon is persisted via [PokemonPartyService.addCaughtPokemon] (which
   * also emits the S2C 0x14 party delta) — never re-resolved here.
   */
  suspend fun onThrowBall(session: SessionContext, ballItemId: String): CatchResult? {
    val state = battleBySession[session] ?: return null
    val result = sidecar.attemptCatch(state.battleId, ballItemId)
    if (result.catchOutcome.success) {
      caughtSink.persist(state.characterId, state.wildMon)
      log.info { "Caught species ${state.wildMon.speciesId} for character ${state.characterId}" }
      endBattle(session)
    }
    return result
  }

  /** C2S LeaveBattle → sidecar run (flee), then free the session. */
  suspend fun onLeave(session: SessionContext): TurnResult? {
    val state = battleBySession[session] ?: return null
    val turn = sidecar.run(state.battleId)
    endBattle(session)
    return turn
  }

  /** Clean up a battle if the session drops mid-fight. */
  suspend fun onSessionClosed(session: SessionContext) {
    endBattle(session)
  }

  private suspend fun finishIfEnded(session: SessionContext, turn: TurnResult) {
    if (turn.finished) {
      endBattle(session)
      // TODO(capture): emit the S2C battle-end sequence (return to overworld via 0x0F).
    }
  }

  /**
   * Free a finished/abandoned battle on BOTH sides: drop the session mapping and dispose the
   * sidecar session (the sidecar only frees on explicit `end`). Idempotent; called from every
   * terminal path.
   */
  private suspend fun endBattle(session: SessionContext) {
    val state = battleBySession.remove(session) ?: return
    sidecar.end(state.battleId)
  }

  /**
   * Read (currentHp, maxHp) for the active mon of the given side from the sidecar `sides` snapshot.
   */
  private fun activeHp(sides: JsonElement?, side: String): Pair<Int, Int> {
    val arr = sides as? JsonArray ?: return 0 to 0
    for (entry in arr) {
      val obj = entry.jsonObject
      if (obj["side"]?.jsonPrimitive?.content != side) continue
      val active = obj["active"]?.jsonArray?.firstOrNull()?.jsonObject ?: return 0 to 0
      val cur = active["hpCurrent"]?.jsonPrimitive?.intOrNull ?: 0
      val max = active["hpMax"]?.jsonPrimitive?.intOrNull ?: 0
      return cur to max
    }
    return 0 to 0
  }
}
