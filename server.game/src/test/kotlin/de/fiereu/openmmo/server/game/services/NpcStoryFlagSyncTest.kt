package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.NpcSpawnPacket
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class NpcStoryFlagSyncTest :
    FunSpec({
      test("persisted hide flags determine the entities restored for a map") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val character = store.createCharacter(1, "Brendan", CharacterGender.MALE, Region.HOENN)
          val session = FakeSession(characterId = character.info.id, bankId = 50, mapId = 9)
          val npcs = NpcService(MapManager(), store)

          npcs.spawnNpcsForMap(session, bankId = 50, mapId = 9)

          // Only Brendan's truck starts visible.
          (npcs.getNpcEntityId(50, 9, 4) != null) shouldBe true
          npcs.getNpcEntityId(50, 9, 5) shouldBe null
          npcs.getNpcEntityId(50, 9, 3) shouldBe null

          StoryService(store)
              .clearFlag(character.info.id, HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MOM_OUTSIDE)
          npcs.spawnNpcsForMap(session, bankId = 50, mapId = 9)

          (npcs.getNpcEntityId(50, 9, 3) != null) shouldBe true
        }
      }

      test("bedroom decoration placeholders are not spawned as npcs") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val character = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN)
          val session = FakeSession(characterId = character.info.id, bankId = 51, mapId = 3)
          val npcs = NpcService(MapManager(), store)

          npcs.spawnNpcsForMap(session, bankId = 51, mapId = 3)

          // Decoration slots are not normal NPCs.
          (1..12).forEach { localId -> npcs.getNpcEntityId(51, 3, localId) shouldBe null }
        }
      }

      test("the Littleroot twin moves beside the north exit after meeting the rival") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val character = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN)
          val session = FakeSession(characterId = character.info.id, bankId = 50, mapId = 9)
          val story = StoryService(store)
          story.setVar(character.info.id, HoennVars.VAR_LITTLEROOT_TOWN_STATE, 1)
          val npcs = NpcService(MapManager(), store)

          npcs.spawnNpcsForMap(session, bankId = 50, mapId = 9)

          val twinId = npcs.getNpcEntityId(50, 9, 0)
          val twin =
              session.sent.filterIsInstance<NpcSpawnPacket>().single { it.entityId == twinId }
          twin.x shouldBe 10
          twin.y shouldBe 1
        }
      }

      test("dynamic rival graphics resolve to the opposite player gender") {
        runTest {
          val mapManager = MapManager()

          val femaleStore =
              CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val female = femaleStore.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN)
          StoryService(femaleStore).clearFlag(female.info.id, HoennFlags.FLAG_HIDE_ROUTE_103_RIVAL)
          val femaleSession = FakeSession(characterId = female.info.id, bankId = 50, mapId = 18)
          val femaleNpcs = NpcService(mapManager, femaleStore)
          femaleNpcs.spawnNpcsForMap(femaleSession, bankId = 50, mapId = 18)
          val brendanId = femaleNpcs.getNpcEntityId(50, 18, 1)
          femaleSession.sent
              .filterIsInstance<NpcSpawnPacket>()
              .single { it.entityId == brendanId }
              .unk2 shouldBe 100

          val maleStore =
              CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val male = maleStore.createCharacter(2, "Brendan", CharacterGender.MALE, Region.HOENN)
          StoryService(maleStore).clearFlag(male.info.id, HoennFlags.FLAG_HIDE_ROUTE_103_RIVAL)
          val maleSession = FakeSession(characterId = male.info.id, bankId = 50, mapId = 18)
          val maleNpcs = NpcService(mapManager, maleStore)
          maleNpcs.spawnNpcsForMap(maleSession, bankId = 50, mapId = 18)
          val mayId = maleNpcs.getNpcEntityId(50, 18, 1)
          maleSession.sent
              .filterIsInstance<NpcSpawnPacket>()
              .single { it.entityId == mayId }
              .unk2 shouldBe 105
        }
      }

      test("completed rescue saves never restore the Route 101 Zigzagoon") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val character = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN)
          StoryService(store).setFlag(character.info.id, HoennFlags.FLAG_RESCUED_BIRCH)
          val session = FakeSession(characterId = character.info.id, bankId = 50, mapId = 16)
          val npcs = NpcService(MapManager(), store)

          npcs.spawnNpcsForMap(session, bankId = 50, mapId = 16)

          npcs.getNpcEntityId(50, 16, 3) shouldBe null
        }
      }
    })
