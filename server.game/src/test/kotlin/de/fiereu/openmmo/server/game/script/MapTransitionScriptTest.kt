package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.server.game.script.generated.hoenn.LittlerootTown_OnTransition
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class MapTransitionScriptTest :
    FunSpec({
      test("Littleroot on-transition sets the visited flag on the player") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Ash").info.id
          val story = StoryService(store)

          val session = FakeSession(characterId = charId)
          val state = session.attributes[PLAYER_STATE]!!
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story)

          LittlerootTown_OnTransition.run(ctx)

          story.isFlagSet(charId, HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN) shouldBe true
        }
      }
    })
