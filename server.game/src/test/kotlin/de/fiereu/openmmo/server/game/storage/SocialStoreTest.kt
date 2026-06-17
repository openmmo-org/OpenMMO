package de.fiereu.openmmo.server.game.storage

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SocialStoreTest :
    FunSpec({
      test("friends are seeded per user and survive add/remove") {
        val store = SocialStore()
        store.getFriends(1) shouldBe linkedSetOf("Red", "Blue", "Green")

        store.addFriend(1, "Yellow")
        store.getFriends(1).contains("Yellow") shouldBe true

        store.removeFriend(1, "Red") shouldBe true
        store.getFriends(1).contains("Red") shouldBe false
        store.removeFriend(1, "Red") shouldBe false
      }

      test("block list is independent per user") {
        val store = SocialStore()
        store.getBlocked(1) shouldBe emptySet()

        store.block(1, "Troll")
        store.getBlocked(1) shouldBe setOf("Troll")
        store.getBlocked(2) shouldBe emptySet()

        store.unblock(1, "Troll") shouldBe true
        store.getBlocked(1) shouldBe emptySet()
      }
    })
