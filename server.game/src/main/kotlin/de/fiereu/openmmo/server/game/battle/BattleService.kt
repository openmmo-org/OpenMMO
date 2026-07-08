package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

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
    // TODO(capture): emit BattleStartScene(0xCA)/BattleScreenOpen(0x47) + send-out
    //   packets from created.turn; see docs/BATTLE-PACKET-MAP.md.
    return created
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
