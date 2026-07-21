package de.fiereu.openmmo.server.game.storage

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EntityIdServiceTest :
    FunSpec({
      val service = EntityIdService()

      test("character ids carry the character tag") {
        val id = service.newCharacterId()
        (id and 0xFFFF) shouldBe CHARACTER_ID_TAG
      }

      test("monster ids carry the monster tag") {
        val id = service.newMonsterId()
        (id and 0xFFFF) shouldBe MONSTER_ID_TAG
      }

      test("ids are positive") { repeat(100) { (service.newCharacterId() > 0) shouldBe true } }

      test("a burst of ids has no duplicates") {
        val ids = (1..200).map { service.newMonsterId() }
        ids.toSet().size shouldBe ids.size
      }
    })
