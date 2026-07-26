package de.fiereu.openmmo.server.game.script

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.ScriptMovementService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SCRIPT_SCOPE
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private val log = KotlinLogging.logger {}

/**
 * Launches a [Script] on the connection's own coroutine scope so it can wait on a dialog without
 * blocking packet handling. Shared by every script trigger (npc/sign interactions and map entry).
 * Claiming the dialog first stops a second trigger from starting a parallel script on the same
 * player, and the dialog is always closed when the script ends.
 */
@Singleton
class ScriptRunner
@Inject
constructor(
    private val dialogService: DialogService,
    private val storyService: StoryService,
    private val movementService: ScriptMovementService,
) {
  fun run(session: SessionContext, state: PlayerState, script: Script, entityId: Long) =
      runAll(session, state, listOf(script), entityId)

  /**
   * Runs [scripts] in order on one coroutine so they share the dialog lock, for example a map's
   * on-transition script followed by its matching on-frame cutscene.
   */
  fun runAll(
      session: SessionContext,
      state: PlayerState,
      scripts: List<Script>,
      entityId: Long,
  ) {
    if (scripts.isEmpty()) return
    state.inDialog = true
    val scope =
        session.attributes.getOrPut(SCRIPT_SCOPE) {
          CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }
    val ctx = ScriptContext(session, state, entityId, dialogService, storyService, movementService)
    scope.launch {
      try {
        for (script in scripts) script.run(ctx)
      } catch (e: CancellationException) {
        throw e
      } catch (e: NotImplementedError) {
        log.info { "Script not ported yet for entity $entityId" }
      } catch (e: Exception) {
        log.error(e) { "Script failed for entity $entityId" }
      } finally {
        dialogService.close(session, state)
      }
    }
  }
}
