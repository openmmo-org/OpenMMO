package de.fiereu.openmmo.server.game.script

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.MapEntryScripts
import de.fiereu.openmmo.server.game.services.ScriptMovementService
import de.fiereu.openmmo.server.game.services.ScriptWarpService
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SCRIPT_SCOPE
import de.fiereu.openmmo.server.game.session.SCRIPT_SNAPSHOT
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
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
    private val warpService: ScriptWarpService,
    private val storyPlayerService: StoryPlayerService,
    private val battleService: BattleService,
    private val characterStore: CharacterStore,
    private val mapManager: MapManager,
    private val entryScripts: MapEntryScripts,
) {
  fun run(session: SessionContext, state: PlayerState, script: Script, entityId: Long) =
      runAll(session, state, listOf(script), entityId)

  /**
   * Undoes what a script wrote when it does not reach its end. Scenes advance their story var last,
   * so leaving the earlier writes in place would let the next login replay the scene on top of
   * them, granting its rewards twice.
   */
  fun rollBack(session: SessionContext, state: PlayerState, entityId: Long) {
    val snapshot = session.attributes.remove(SCRIPT_SNAPSHOT) ?: return
    val charId = state.characterId ?: return
    log.info { "Rolling back the unfinished script for entity $entityId on character $charId" }
    characterStore.restoreProgress(charId, snapshot)
  }

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
    val scope =
        session.attributes.getOrPut(SCRIPT_SCOPE) {
          CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }
    // A cancelled scope cannot launch, so bail before claiming the dialog lock.
    if (!scope.isActive) return
    state.inDialog = true
    val snapshot = state.characterId?.let(characterStore::getCharacter)
    if (snapshot != null) session.attributes[SCRIPT_SNAPSHOT] = snapshot
    val ctx =
        ScriptContext(
            session,
            state,
            entityId,
            dialogService,
            storyService,
            movementService,
            warpService,
            storyPlayerService,
            battleService,
            characterStore,
            mapManager,
            entryScripts,
        )
    scope.launch {
      var finished = false
      var cancelled = false
      try {
        for (script in scripts) script.run(ctx)
        finished = true
      } catch (e: CancellationException) {
        // The disconnect that cancelled this rolls back itself, in order with the battle flush
        // and the unload. Doing it here as well would race that and lose to the eviction.
        cancelled = true
        throw e
      } catch (e: NotImplementedError) {
        log.info { "Script not ported yet for entity $entityId" }
        // A stub wrote nothing, so there is nothing to undo.
        finished = true
      } catch (e: Exception) {
        log.error(e) { "Script failed for entity $entityId" }
      } finally {
        if (!cancelled) {
          if (!finished) rollBack(session, state, entityId)
          session.attributes.remove(SCRIPT_SNAPSHOT)
        }
        dialogService.close(session, state)
      }
    }
  }
}
