package de.fiereu.openmmo.server.game.storage

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CharacterStoreGenderTest :
    FunSpec({
      test("createCharacter preserves decoded player gender and opaque cosmetics") {
        val store = CharacterStore()
        val cosmetics = byteArrayOf(0x00, 0x03, 0x00, 0x11, 0x22, 0x33)
        val character =
            store.createCharacter(
                userId = 99, name = "GenderOne", gender = 1, cosmetics = cosmetics)

        character.info.rivalSex shouldBe 1
        character.info.gender shouldBe 1
        character.info.cosmetics shouldBe cosmetics
      }

      test("createCharacter sanitizes invalid create gender enum for character list") {
        val store = CharacterStore()
        val character = store.createCharacter(userId = 99, name = "GenderThree", gender = 3)

        character.info.rivalSex shouldBe 0
      }
    })
