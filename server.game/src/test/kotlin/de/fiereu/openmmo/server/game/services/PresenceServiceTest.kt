package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.EntityMovePacket
import de.fiereu.openmmo.net.game.packets.LoadEntityPacket
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.server.game.world.interest.PassThroughInterestPolicy
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PresenceServiceTest :
    FunSpec({
      val mapManager = MapManager()

      fun freshPresence(): Pair<PresenceService, CharacterStore> {
        val store = CharacterStore()
        val presence =
            PresenceService(
                InterestManager(), PassThroughInterestPolicy(), MapLoadService(mapManager), store)
        return presence to store
      }

      fun CharacterStore.session(name: String, mapId: Int = 3): FakeSession {
        val character = createCharacter(userId = 1, name = name)
        return FakeSession(characterId = character.info.id, mapId = mapId)
      }

      fun FakeSession.spawnedEntityIds(): List<Long> =
          sent.filterIsInstance<LoadEntityPacket>().map { it.entityId }

      fun FakeSession.id(): Long = state().characterId!!

      test("entering exchanges entity snapshots with co-located observers") {
        val (presence, store) = freshPresence()
        val a = store.session("A")
        val b = store.session("B")

        presence.enter(a)
        a.sent shouldBe emptyList() // nobody else here yet

        presence.enter(b)
        a.spawnedEntityIds() shouldBe listOf(b.id())
        b.spawnedEntityIds() shouldBe listOf(a.id())
      }

      test("movement propagates only to observers on the same map") {
        val (presence, store) = freshPresence()
        val a = store.session("A", mapId = 3)
        val b = store.session("B", mapId = 3)
        val c = store.session("C", mapId = 9)
        presence.enter(a)
        presence.enter(b)
        presence.enter(c)
        listOf(a, b, c).forEach { it.sent.clear() }

        val move = EntityMovePacket(entityId = a.id(), x = 1, y = 1, direction = Direction.DOWN)
        presence.broadcastMove(a, move)

        b.sent shouldBe listOf(move)
        c.sent shouldBe emptyList()
        a.sent shouldBe emptyList()
      }

      test("leaving despawns the player from its observers") {
        val (presence, store) = freshPresence()
        val a = store.session("A")
        val b = store.session("B")
        presence.enter(a)
        presence.enter(b)
        listOf(a, b).forEach { it.sent.clear() }

        presence.leave(b)

        a.sent shouldBe listOf(EntityLeavePacket(b.id()))

        val move = EntityMovePacket(entityId = a.id(), x = 1, y = 1, direction = Direction.DOWN)
        presence.broadcastMove(a, move)
        b.sent shouldBe emptyList() // no longer observing
      }

      test("refresh relocates the player between maps") {
        val (presence, store) = freshPresence()
        val a = store.session("A", mapId = 3)
        val d = store.session("D", mapId = 4)
        val b = store.session("B", mapId = 3)
        presence.enter(a)
        presence.enter(d)
        presence.enter(b)
        listOf(a, b, d).forEach { it.sent.clear() }

        b.state().mapId = 4
        presence.refresh(b)

        a.sent shouldBe listOf(EntityLeavePacket(b.id())) // despawned from the old map
        d.spawnedEntityIds() shouldBe listOf(b.id()) // spawned to the new map
        b.spawnedEntityIds() shouldBe listOf(d.id())
      }
    })
