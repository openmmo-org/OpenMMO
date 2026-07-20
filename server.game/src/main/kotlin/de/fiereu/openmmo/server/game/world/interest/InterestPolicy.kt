package de.fiereu.openmmo.server.game.world.interest

import de.fiereu.network.SessionContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Filters a subject's bucket members down to the sessions that should actually observe it. The
 * default passes the whole bucket through. A radius-based policy would slot in here without
 * touching callers.
 */
fun interface InterestPolicy {
  fun filter(subject: SessionContext, candidates: Collection<SessionContext>): List<SessionContext>
}

@Singleton
class PassThroughInterestPolicy @Inject constructor() : InterestPolicy {
  override fun filter(
      subject: SessionContext,
      candidates: Collection<SessionContext>,
  ): List<SessionContext> = candidates.filter { it !== subject }
}
