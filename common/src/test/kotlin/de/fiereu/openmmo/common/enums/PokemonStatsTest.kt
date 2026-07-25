package de.fiereu.openmmo.common.enums

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PokemonStatsTest :
    FunSpec({
      test("zero is a valid stat value") {
        val evs = EVs()
        evs.atk = 10
        evs.atk = 0
        evs.atk shouldBe 0
      }

      test("the individual cap still rejects") {
        shouldThrowAny { EVs().apply { hp = 253 } }
        shouldThrowAny { IVs().apply { hp = 32 } }
      }

      test("the total cap counts the replaced value, not the old one") {
        val evs = EVs()
        evs.hp = 252
        evs.atk = 252
        shouldThrowAny { evs.def = 7 }
        evs.def = 6
        evs.total shouldBe 510
        evs.hp = 100
        evs.def = 158
        evs.total shouldBe 510
      }

      test("a full set of perfect IVs fits the total cap") {
        val ivs = IVs()
        ivs.hp = 31
        ivs.atk = 31
        ivs.def = 31
        ivs.spAtk = 31
        ivs.spDef = 31
        ivs.spd = 31
        ivs.total shouldBe 186
      }

      test("an all-zero IV word decompresses and compresses back") {
        val ivs = decompressIVs(0)
        ivs.total shouldBe 0
        ivs.compress() shouldBe 0
      }
    })
