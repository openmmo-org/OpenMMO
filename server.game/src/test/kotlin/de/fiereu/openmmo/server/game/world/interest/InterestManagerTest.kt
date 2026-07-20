package de.fiereu.openmmo.server.game.world.interest

import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class InterestManagerTest :
    FunSpec({
      val map = MapInterestKey(1, 51, 3)
      val guild = GuildInterestKey(7)

      test("a session can belong to several groups at once and groups stay independent") {
        val manager = InterestManager()
        val a = FakeSession(characterId = 1)
        val b = FakeSession(characterId = 2)
        val c = FakeSession(characterId = 3)

        manager.join(a, map)
        manager.join(b, map)
        manager.join(a, guild)
        manager.join(c, guild)

        manager.members(map) shouldContainExactlyInAnyOrder listOf(a, b)
        manager.members(guild) shouldContainExactlyInAnyOrder listOf(a, c)
        manager.keysOf(a) shouldContainExactlyInAnyOrder listOf(map, guild)
      }

      test("broadcast reaches only the group's members and can exclude the sender") {
        val manager = InterestManager()
        val a = FakeSession(characterId = 1)
        val b = FakeSession(characterId = 2)
        val c = FakeSession(characterId = 3)
        manager.join(a, map)
        manager.join(b, map)
        manager.join(a, guild)
        manager.join(c, guild)

        manager.broadcast(guild, "guild-hello", exclude = a)

        a.sent shouldBe emptyList()
        b.sent shouldBe emptyList() // b is not in the guild group
        c.sent shouldBe listOf("guild-hello")
      }

      test("leaveAll removes the session from every group") {
        val manager = InterestManager()
        val a = FakeSession(characterId = 1)
        val b = FakeSession(characterId = 2)
        manager.join(a, map)
        manager.join(b, map)
        manager.join(a, guild)

        manager.leaveAll(a)

        manager.keysOf(a) shouldBe emptySet()
        manager.members(map) shouldContainExactlyInAnyOrder listOf(b)
        manager.members(guild) shouldBe emptySet()
      }
    })
