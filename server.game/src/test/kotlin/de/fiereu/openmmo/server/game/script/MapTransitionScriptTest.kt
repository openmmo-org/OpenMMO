package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.DialogDataPacket
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.NpcSpawnPacket
import de.fiereu.openmmo.net.game.packets.NpcUpdatePacket
import de.fiereu.openmmo.net.game.packets.StoryFlagUpdatePacket
import de.fiereu.openmmo.server.game.script.generated.hoenn.LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall
import de.fiereu.openmmo.server.game.script.generated.hoenn.LittlerootTown_BrendansHouse_2F_OnTransition
import de.fiereu.openmmo.server.game.script.generated.hoenn.LittlerootTown_MaysHouse_2F_OnTransition
import de.fiereu.openmmo.server.game.script.generated.hoenn.LittlerootTown_OnTransition
import de.fiereu.openmmo.server.game.script.generated.hoenn.Route101_EventScript_HideMapNamePopup
import de.fiereu.openmmo.server.game.script.generated.hoenn.Route101_EventScript_StartBirchRescue
import de.fiereu.openmmo.server.game.script.generated.hoenn.setWallClock
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.NpcService
import de.fiereu.openmmo.server.game.services.ScriptMovementService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.session.PENDING_DIALOG
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class MapTransitionScriptTest :
    FunSpec({
      test("Littleroot on-transition sets the visited flag on the player") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          val story = StoryService(store)

          val session = FakeSession(characterId = charId)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)

          LittlerootTown_OnTransition.run(ctx)

          story.isFlagSet(charId, HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN) shouldBe true
          session.sent.filterIsInstance<StoryFlagUpdatePacket>() shouldBe
              listOf(StoryFlagUpdatePacket(regionId = 1, flagId = 0x086f, enabled = true))
        }
      }

      test("female bedroom transition advances the intro to the clock step") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          val story = StoryService(store)
          story.setVar(
              charId,
              de.fiereu.openmmo.story.generated.hoenn.HoennVars.VAR_LITTLEROOT_INTRO_STATE,
              4)

          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)

          LittlerootTown_MaysHouse_2F_OnTransition.run(ctx)

          story.getVar(
              charId,
              de.fiereu.openmmo.story.generated.hoenn.HoennVars.VAR_LITTLEROOT_INTRO_STATE) shouldBe
              5
        }
      }

      test("the wall-clock cutscene removes Mom and her collision from the bedroom stairs") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          store.updatePosition(charId, 3, 3, 51, 3)
          val story = StoryService(store)
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val npcs = NpcService(mapManager, store)
          val movement = ScriptMovementService(mapManager, npcs, store)
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)
          val momEntityId = npcs.entityIdFor(1, 51, 3, 13)

          val cutscene = launch { setWallClock(ctx, female = true) }
          advanceUntilIdle()
          session.attributes[PENDING_DIALOG]!!.complete(Unit)
          advanceUntilIdle()
          session.attributes[PENDING_DIALOG]!!.complete(Unit)
          advanceUntilIdle()
          cutscene.join()

          val momMovement =
              session.sent
                  .filterIsInstance<DialogDataPacket>()
                  .filter { it.entityId == momEntityId }
                  .map { it.data.toList() }
          momMovement shouldBe
              listOf(
                  listOf(0x1B, 0x10, 0x03, 0x1C, 0x1B, 0x13).map(Int::toByte),
                  listOf(0x12, 0x11, 0x1B).map(Int::toByte),
              )
          session.sent.filterIsInstance<EntityLeavePacket>() shouldBe
              listOf(EntityLeavePacket(momEntityId))
        }
      }

      test("the generated registry exposes the female truck path") {
        (ScriptRegistry.generated().forLabel("LittlerootTown_EventScript_StepOffTruckFemale") !=
            null) shouldBe true
      }

      test("the mandatory Hoenn path through Rustboro Gym has no missing trigger scripts") {
        val registry = ScriptRegistry.generated()
        val mandatoryLabels =
            listOf(
                "Route101_EventScript_StartBirchRescue",
                "Route101_EventScript_BirchsBag",
                "LittlerootTown_ProfessorBirchsLab_EventScript_GiveStarterEvent",
                "Route103_EventScript_Rival",
                "OldaleTown_EventScript_RivalTrigger1",
                "LittlerootTown_ProfessorBirchsLab_EventScript_GivePokedexEvent",
                "PetalburgCity_EventScript_ShowGymToPlayer0",
                "PetalburgCity_EventScript_ShowGymToPlayer1",
                "PetalburgCity_EventScript_ShowGymToPlayer2",
                "PetalburgCity_EventScript_ShowGymToPlayer3",
                "PetalburgCity_Gym_EventScript_Norman",
                "PetalburgCity_Gym_EventScript_ReturnFromWallyTutorial",
                "PetalburgWoods_EventScript_DevonResearcherLeft",
                "PetalburgWoods_EventScript_DevonResearcherRight",
                "RustboroCity_OnTransition",
                "RustboroCity_Gym_EventScript_Roxanne",
            )

        mandatoryLabels.forEach { label -> (registry.forLabel(label) != null) shouldBe true }
      }

      test("female players become ready to meet Brendan on entering his bedroom") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          val story = StoryService(store)
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 1)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)

          LittlerootTown_BrendansHouse_2F_OnTransition.run(ctx)

          story.getVar(charId, HoennVars.VAR_LITTLEROOT_RIVAL_STATE) shouldBe 2
        }
      }

      test("male players become ready to meet May on entering her bedroom") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId =
              store.createCharacter(1, "Brendan", CharacterGender.MALE, Region.HOENN).info.id
          val story = StoryService(store)
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)

          LittlerootTown_MaysHouse_2F_OnTransition.run(ctx)

          story.getVar(charId, HoennVars.VAR_LITTLEROOT_RIVAL_STATE) shouldBe 2
        }
      }

      test("completed rival meetings repair both bedroom stair-tile hide flags") {
        data class RivalRoom(
            val playerName: String,
            val gender: CharacterGender,
            val mapId: Byte,
            val hideFlag: String,
            val transition: Script,
            val expectedX: Int,
            val expectedY: Int,
        )

        val rooms =
            listOf(
                RivalRoom(
                    "May",
                    CharacterGender.FEMALE,
                    1,
                    HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_RIVAL_BEDROOM,
                    LittlerootTown_BrendansHouse_2F_OnTransition,
                    0,
                    2,
                ),
                RivalRoom(
                    "Brendan",
                    CharacterGender.MALE,
                    3,
                    HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MAYS_HOUSE_RIVAL_BEDROOM,
                    LittlerootTown_MaysHouse_2F_OnTransition,
                    8,
                    2,
                ),
            )

        runTest {
          rooms.forEach { room ->
            val store =
                CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
            val charId =
                store.createCharacter(1, room.playerName, room.gender, Region.HOENN).info.id
            store.updatePosition(charId, 3, 3, 51, room.mapId)
            val story = StoryService(store)
            story.clearFlag(charId, room.hideFlag)
            story.setVar(charId, HoennVars.VAR_LITTLEROOT_RIVAL_STATE, 3)
            val session = FakeSession(characterId = charId, bankId = 51, mapId = room.mapId.toInt())
            val state = session.attributes[PLAYER_STATE]!!
            val mapManager = MapManager()
            val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
            val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)

            room.transition.run(ctx)

            story.isFlagSet(charId, room.hideFlag) shouldBe true
            val spawn = session.sent.filterIsInstance<NpcSpawnPacket>().single()
            spawn.x shouldBe room.expectedX
            spawn.y shouldBe room.expectedY
          }
        }
      }

      test("Route 101 entry arms the Birch rescue coordinate triggers") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          val story = StoryService(store)
          val session = FakeSession(characterId = charId, bankId = 50, mapId = 16)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)

          Route101_EventScript_HideMapNamePopup.run(ctx)

          story.getVar(charId, HoennVars.VAR_ROUTE101_STATE) shouldBe 1
          val route = mapManager.getMap(1, 50, 16)!!
          route.coordScripts.count { it.script == "Route101_EventScript_StartBirchRescue" } shouldBe
              2
          (ScriptRegistry.generated().forLabel("Route101_EventScript_StartBirchRescue") !=
              null) shouldBe true
        }
      }

      test("Birch rescue repositions existing npcs and keeps Zigzagoon one tile away") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          store.updatePosition(charId, 10, 19, 50, 16)
          val story = StoryService(store)
          val session = FakeSession(characterId = charId, bankId = 50, mapId = 16)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val npcs = NpcService(mapManager, store)
          val movement = ScriptMovementService(mapManager, npcs, store)
          val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)
          val birchId = npcs.entityIdFor(1, 50, 16, 1)
          val zigzagoonId = npcs.entityIdFor(1, 50, 16, 3)

          val cutscene = launch { Route101_EventScript_StartBirchRescue.run(ctx) }
          advanceUntilIdle()
          session.attributes[PENDING_DIALOG]!!.complete(Unit)
          advanceUntilIdle()
          session.attributes[PENDING_DIALOG]!!.complete(Unit)
          advanceUntilIdle()
          cutscene.join()

          session.sent.filterIsInstance<NpcUpdatePacket>() shouldBe
              listOf(
                  NpcUpdatePacket(birchId, 1, 50, 16, 0, 15, 0xF6, 3),
                  NpcUpdatePacket(zigzagoonId, 1, 50, 16, 0, 16, 0xF6, 2),
              )
          val chase =
              session.sent
                  .filterIsInstance<DialogDataPacket>()
                  .single { it.entityId == zigzagoonId && it.data.size > 20 }
                  .data
          // Birch and Zigzagoon finish one tile apart.
          chase.takeLast(2) shouldBe listOf(0x1F.toByte(), 0x1F.toByte())
          chase.size shouldBe 30
        }
      }

      test("Brendan meeting completes the rival state and opens the north intro path") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          store.updatePosition(charId, 3, 3, 51, 1)
          val story = StoryService(store)
          story.setVar(charId, HoennVars.VAR_LITTLEROOT_RIVAL_STATE, 2)
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 1)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val npcs = NpcService(mapManager, store)
          val movement = ScriptMovementService(mapManager, npcs, store)
          val dialog = DialogService()
          val ctx =
              ScriptContext(
                  session,
                  state,
                  entityId = npcs.entityIdFor(1, 51, 1, 14),
                  dialog,
                  story,
                  movement,
              )

          val cutscene = launch {
            LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall.run(ctx)
          }
          advanceUntilIdle()
          session.attributes[PENDING_DIALOG]!!.complete(Unit)
          advanceUntilIdle()
          cutscene.join()

          story.getVar(charId, HoennVars.VAR_LITTLEROOT_RIVAL_STATE) shouldBe 3
          story.getVar(charId, HoennVars.VAR_LITTLEROOT_TOWN_STATE) shouldBe 1
          story.isFlagSet(
              charId, HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_2F_POKE_BALL) shouldBe
              true
          story.isFlagSet(
              charId, HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_RIVAL_BEDROOM) shouldBe
              true
        }
      }
    })
