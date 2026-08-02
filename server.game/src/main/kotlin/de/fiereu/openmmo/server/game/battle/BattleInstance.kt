package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.server.game.world.interest.BattleInterestKey
import kotlinx.coroutines.CompletableDeferred

enum class BattleResult {
  VICTORY,
  DEFEAT,
  FLED,
  CAUGHT,
  DISCONNECTED,
  FAILED,
}

/** One running wild battle. Trainer battles later add more participants to the same key. */
class BattleInstance(
    val battleId: Long,
    val charId: Long,
    val session: SessionContext,
    val party: List<BattleMonState>,
    val wild: BattleMonState,
    val rng: BattleRng,
    val catchable: Boolean = true,
    val escapable: Boolean = true,
) {
  val key: BattleInterestKey = BattleInterestKey(battleId)
  var turn: Int = 1
  var activeSlot: Int = 0

  // Which party slots have been sent out as active. A monster's first appearance carries its
  // full block, a return only its active detail.
  val seenActive: MutableSet<Int> = mutableSetOf(0)

  /** Completes when the battle leaves the registry. */
  val completion = CompletableDeferred<BattleResult>()

  /** Result held until the client confirms that its battle-to-map transition has finished. */
  var pendingResult: BattleResult? = null

  fun activeMon(): BattleMonState = party[activeSlot]
}
