package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.server.game.world.interest.BattleInterestKey
import de.fiereu.openmmo.trainer.TrainerDef
import kotlinx.coroutines.CompletableDeferred

enum class BattleResult {
  VICTORY,
  DEFEAT,
  FLED,
  CAUGHT,
  DISCONNECTED,
  FAILED,
}

/** What kind of battle this is, beyond the two teams. A [trainer] owns the opposing side. */
data class BattleRules(
    val catchable: Boolean = true,
    val escapable: Boolean = true,
    val trainer: TrainerDef? = null,
)

/** One running battle. A wild encounter is the case where [opponent] holds a single monster. */
class BattleInstance(
    val battleId: Long,
    val charId: Long,
    val session: SessionContext,
    val party: List<BattleMonState>,
    val opponent: List<BattleMonState>,
    val rng: BattleRng,
    val catchable: Boolean = true,
    val escapable: Boolean = true,
    /** The trainer who owns [opponent], or null for a wild encounter. */
    val trainer: TrainerDef? = null,
) {
  val key: BattleInterestKey = BattleInterestKey(battleId)
  var turn: Int = 1
  var activeSlot: Int = 0
  var opponentSlot: Int = 0

  // Which slots have been sent out as active. A monster's first appearance carries its full block,
  // a return only its active detail.
  val seenActive: MutableSet<Int> = mutableSetOf(0)
  val opponentSeen: MutableSet<Int> = mutableSetOf(0)

  /** Completes when the battle leaves the registry. */
  val completion = CompletableDeferred<BattleResult>()

  /** Result held until the client confirms that its battle-to-map transition has finished. */
  var pendingResult: BattleResult? = null

  fun activeMon(): BattleMonState = party[activeSlot]

  fun opponentMon(): BattleMonState = opponent[opponentSlot]

  fun isPlayerSide(entityId: Long): Boolean = party.any { it.entityId == entityId }
}
