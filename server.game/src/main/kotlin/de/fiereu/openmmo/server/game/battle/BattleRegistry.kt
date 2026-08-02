package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

/** The running battles, one per character. */
@Singleton
class BattleRegistry @Inject constructor() {

  private val byChar = ConcurrentHashMap<Long, BattleInstance>()
  private val ids = AtomicLong(1)

  fun create(
      charId: Long,
      session: SessionContext,
      party: List<BattleMonState>,
      wild: BattleMonState,
      rng: BattleRng,
      catchable: Boolean = true,
      escapable: Boolean = true,
  ): BattleInstance {
    val battle =
        BattleInstance(
            ids.getAndIncrement(),
            charId,
            session,
            party,
            wild,
            rng,
            catchable,
            escapable,
        )
    byChar[charId] = battle
    return battle
  }

  fun byChar(charId: Long): BattleInstance? = byChar[charId]

  fun remove(charId: Long): BattleInstance? = byChar.remove(charId)
}
