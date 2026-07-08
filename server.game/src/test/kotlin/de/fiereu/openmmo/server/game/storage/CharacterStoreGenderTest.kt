package de.fiereu.openmmo.server.game.storage

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CharacterStoreGenderTest :
    FunSpec({
      test("createCharacter preserves decoded player gender for LocalPlayerState emission") {
        val store = CharacterStore()
        val character = store.createCharacter(userId = 99, name = "GenderOne", gender = 1)

        character.info.rivalSex shouldBe 1
      }
    })
