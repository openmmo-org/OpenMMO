package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.BattleOpenPacket
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

/**
 * server.game battle orchestrator (Track B skeleton).
 *
 * Owns the mapping between a live client session and its sidecar battle, and is the single place
 * that translates PokéMMO battle packets ↔ the Battle-Service sidecar (via [BattleSessionClient]).
 * Wild-battle resolution lives in the sidecar (pokemon-showdown); this class never runs battle math
 * or catch math.
 *
 * GATED (needs a real wild-battle capture — see docs/BATTLE-PACKET-MAP.md — and Pi's party/storage
 * types for the player team): the exact C2S handler registrations in GameAppHandler and the S2C
 * event-packet emission. Those TODOs are intentionally not wired yet so we don't guess
 * opcodes/sequencing. What IS proven here: the sidecar boundary (create/choice/catch/run)
 * round-trips.
 */
@Singleton
class BattleService @Inject constructor(private val sidecar: BattleSessionClient) {

  /** session → active sidecar battleId. */
  private val battleBySession = ConcurrentHashMap<SessionContext, String>()

  /**
   * Start a wild battle for [session]. Kotlin's EncounterGenerator (Pi) builds [wildTeam]; the
   * player's party (Pi, T0) builds [playerTeam]. Returns the sidecar's opening snapshot;
   * translating it into the S2C battle-start packet sequence is the capture-gated TODO below.
   */
  suspend fun startWildBattle(
      session: SessionContext,
      playerTeam: List<WirePokemon>,
      wildTeam: List<WirePokemon>,
  ): CreateResult {
    val created = sidecar.create(playerTeam, wildTeam)
    battleBySession[session] = created.battleId
    log.info { "Wild battle ${created.battleId} started (${playerTeam.size}v${wildTeam.size})" }
    // Open the battle on the client with the validated S2C 0x30 battle-init.
    session.send(buildWildOpen(playerTeam, wildTeam, created.turn))
    // TODO(PR#9): translate created.turn.log + subsequent turn logs → the S2C event
    //   codecs (0x33/0x16/0x79/0x31); see docs/BATTLE-PACKET-MAP.md.
    return created
  }

  /**
   * Build the S2C `0x30` battle-open from the sidecar's opening snapshot: species/level come from
   * the team DTOs, current/max HP from the sidecar `sides`. TODO(session): the player name + entity
   * ids are still template-derived in [BattleOpenPacket.wild] — patch them from the live session
   * next.
   */
  fun buildWildOpen(
      playerTeam: List<WirePokemon>,
      wildTeam: List<WirePokemon>,
      turn: TurnResult,
  ): BattleOpenPacket {
    val player = playerTeam.first()
    val wild = wildTeam.first()
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
    )
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

  /** C2S BattleMoveUse(0x0A) → sidecar choice("move N"). */
  suspend fun onMoveUse(session: SessionContext, moveSlotIndex: Int): TurnResult? {
    val battleId = battleBySession[session] ?: return null
    val turn = sidecar.choice(battleId, "move ${moveSlotIndex + 1}")
    // TODO(capture): translate turn.log/sides → S2C battle event packets.
    finishIfEnded(session, turn)
    return turn
  }

  /** C2S BattlePartySwitch(0x0C) → sidecar choice("switch N"). */
  suspend fun onSwitch(session: SessionContext, partySlot: Int): TurnResult? {
    val battleId = battleBySession[session] ?: return null
    val turn = sidecar.choice(battleId, "switch ${partySlot + 1}")
    finishIfEnded(session, turn)
    return turn
  }

  /** C2S BattleUseItem(0x39) with a ball → sidecar catch. Persisting the mon is Pi's. */
  suspend fun onThrowBall(session: SessionContext, ballItemId: String): CatchResult? {
    val battleId = battleBySession[session] ?: return null
    val result = sidecar.attemptCatch(battleId, ballItemId)
    if (result.catchOutcome.success) {
      log.info { "Caught ${result.catchOutcome.caughtPokemon?.speciesId} in battle $battleId" }
      endBattle(session, battleId)
      // TODO(Pi): persist result.catchOutcome.caughtPokemon to party/storage.
    }
    return result
  }

  /** C2S LeaveBattle(0x35) → sidecar run (flee), then free the session. */
  suspend fun onLeave(session: SessionContext): TurnResult? {
    val battleId = battleBySession[session] ?: return null
    val turn = sidecar.run(battleId)
    endBattle(session, battleId)
    return turn
  }

  /** Clean up a battle if the session drops mid-fight. */
  suspend fun onSessionClosed(session: SessionContext) {
    val battleId = battleBySession[session] ?: return
    endBattle(session, battleId)
  }

  private suspend fun finishIfEnded(session: SessionContext, turn: TurnResult) {
    if (turn.finished) {
      endBattle(session, turn.battleId)
      // TODO(capture): emit BattleEndDisconnect(0x96)/outcome(0x9B).
    }
  }

  /**
   * Free a finished/abandoned battle on BOTH sides: drop the session mapping and dispose the
   * sidecar session (the sidecar only frees on explicit `end`). Called from every terminal path —
   * move/switch KO, catch, flee, disconnect.
   */
  private suspend fun endBattle(session: SessionContext, battleId: String) {
    battleBySession.remove(session)
    sidecar.end(battleId)
  }
}
