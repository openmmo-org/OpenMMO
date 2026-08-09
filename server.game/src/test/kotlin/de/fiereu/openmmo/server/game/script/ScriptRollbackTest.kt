package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.session.SCRIPT_SCOPE
import de.fiereu.openmmo.server.game.session.SCRIPT_SNAPSHOT
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.testsupport.scriptRunner
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

private const val FLAG = "kanto/FLAG_SYS_POKEMON_GET"
private const val SCENE = "kanto/VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB"

@OptIn(ExperimentalCoroutinesApi::class)
class ScriptRollbackTest :
    FunSpec({
      /** A scene that writes like the real ones do: rewards first, scene var last. */
      fun grantingScript(blockOn: CompletableDeferred<Unit>?) = Script { ctx ->
        ctx.setFlag(FLAG)
        ctx.setVar(SCENE, 9)
        blockOn?.await()
        ctx.setVar(SCENE, 3)
      }

      fun FakeSession.useScope(scope: CoroutineScope) = also { attributes[SCRIPT_SCOPE] = scope }

      suspend fun CharacterStore.player(): Long =
          createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id

      test("a script that finishes keeps everything it wrote") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session = FakeSession(characterId = charId).useScope(backgroundScope)

          scriptRunner(store).run(session, session.state(), grantingScript(null), entityId = -1)
          runCurrent()

          val stored = store.getCharacter(charId)!!
          stored.storyFlags.contains(FLAG) shouldBe true
          stored.storyVars[SCENE] shouldBe 3
          session.attributes[SCRIPT_SNAPSHOT] shouldBe null
        }
      }

      test("a script cancelled part way through leaves nothing behind") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session = FakeSession(characterId = charId).useScope(backgroundScope)
          val runner = scriptRunner(store)
          val before = store.getCharacter(charId)!!

          runner.run(session, session.state(), grantingScript(CompletableDeferred()), entityId = -1)
          runCurrent()
          store.getCharacter(charId)!!.storyFlags.contains(FLAG) shouldBe true

          // What onInactive does for a player who closed the client mid scene.
          runner.rollBack(session, session.state(), entityId = -1)

          val stored = store.getCharacter(charId)!!
          stored.storyFlags shouldBe before.storyFlags
          stored.storyVars shouldBe before.storyVars
          stored.pokemon shouldBe before.pokemon
          stored.info shouldBe before.info
        }
      }

      test("rolling back twice leaves later writes alone") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session = FakeSession(characterId = charId).useScope(backgroundScope)
          val runner = scriptRunner(store)

          runner.run(session, session.state(), grantingScript(CompletableDeferred()), entityId = -1)
          runCurrent()
          runner.rollBack(session, session.state(), entityId = -1)
          store.updatePosition(charId, 9, 9, facing = Direction.LEFT)
          runner.rollBack(session, session.state(), entityId = -1)

          store.getCharacter(charId)!!.info.positionX shouldBe 9
        }
      }

      test("a script started on a disconnected session never claims the dialog") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val dead = CoroutineScope(backgroundScope.coroutineContext + Job()).also { it.cancel() }
          val session = FakeSession(characterId = charId).useScope(dead)

          scriptRunner(store).run(session, session.state(), grantingScript(null), entityId = -1)
          runCurrent()

          session.state().inDialog shouldBe false
          store.getCharacter(charId)!!.storyFlags.contains(FLAG) shouldBe false
        }
      }
    })
