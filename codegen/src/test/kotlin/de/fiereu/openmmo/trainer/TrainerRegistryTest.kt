package de.fiereu.openmmo.trainer

import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val BROCK = 414
private const val GEODUDE = 74
private const val ONIX = 95
private const val TACKLE = 33
private const val DEFENSE_CURL = 111
private const val BIND = 20
private const val ROCK_TOMB = 317

class TrainerRegistryTest :
    FunSpec({
      val trainers = TrainerRegistry()

      test("both regions load") {
        trainers.size() shouldBeGreaterThan 1500
        trainers.get("kanto", BROCK).shouldNotBeNull()
        trainers.get("hoenn", 1).shouldNotBeNull()
      }

      test("a gym leader keeps its decomp party") {
        val brock = trainers.get("kanto", BROCK).shouldNotBeNull()
        brock.name shouldBe "BROCK"
        brock.doubleBattle shouldBe false
        brock.party shouldBe
            listOf(
                TrainerMon(GEODUDE, 12, 0, 0, listOf(TACKLE, DEFENSE_CURL)),
                TrainerMon(ONIX, 14, 0, 0, listOf(TACKLE, BIND, ROCK_TOMB)),
            )
      }

      test("the same id in the other region is a different trainer") {
        trainers.get("hoenn", BROCK) shouldNotBe trainers.get("kanto", BROCK)
      }

      test("every trainer has a usable party") {
        for (region in listOf("hoenn", "kanto")) {
          for (id in 0..1000) {
            val def = trainers.get(region, id) ?: continue
            withClue("$region/$id") {
              def.party.size shouldBeGreaterThan 0
              def.party.forEach {
                it.level shouldBeGreaterThan 0
                it.dexId shouldBeGreaterThan 0
                it.moveIds.size shouldBeLessThanOrEqual MAX_MOVE_SLOTS
              }
            }
          }
        }
      }
    })
