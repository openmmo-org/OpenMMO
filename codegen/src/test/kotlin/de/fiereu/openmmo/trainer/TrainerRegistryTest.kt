package de.fiereu.openmmo.trainer

import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import de.fiereu.openmmo.common.MAX_PARTY_SIZE
import de.fiereu.openmmo.common.enums.MAX_IV
import de.fiereu.openmmo.common.enums.Region
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

// Every [TRAINER_*] the two decomps declare, less TRAINER_NONE in each, which has no party.
private const val HOENN_TRAINERS = 854
private const val KANTO_TRAINERS = 742

class TrainerRegistryTest :
    FunSpec({
      val trainers = TrainerRegistry()

      test("every decomp trainer with a party is registered") {
        // An exact count, so a decomp update that breaks the party parser cannot pass unnoticed.
        trainers.size() shouldBe HOENN_TRAINERS + KANTO_TRAINERS
        trainers.get(Region.KANTO, BROCK).shouldNotBeNull()
        trainers.get(Region.HOENN, 1).shouldNotBeNull()
      }

      test("a gym leader keeps its decomp party") {
        val brock = trainers.get(Region.KANTO, BROCK).shouldNotBeNull()
        brock.name shouldBe "BROCK"
        brock.doubleBattle shouldBe false
        brock.party shouldBe
            listOf(
                TrainerMon(GEODUDE, 12, 0, 0, listOf(TACKLE, DEFENSE_CURL)),
                TrainerMon(ONIX, 14, 0, 0, listOf(TACKLE, BIND, ROCK_TOMB)),
            )
      }

      test("the same id in the other region is a different trainer") {
        trainers.get(Region.HOENN, BROCK) shouldNotBe trainers.get(Region.KANTO, BROCK)
      }

      test("every trainer has a usable party") {
        for (region in Region.entries) {
          for (id in 0..1000) {
            val def = trainers.get(region, id) ?: continue
            withClue("$region/$id") {
              def.party.size shouldBeGreaterThan 0
              def.party.size shouldBeLessThanOrEqual MAX_PARTY_SIZE
              def.party.forEach {
                it.level shouldBeGreaterThan 0
                it.dexId shouldBeGreaterThan 0
                it.moveIds.size shouldBeLessThanOrEqual MAX_MOVE_SLOTS
                // The decomp field is a 0 to 255 difficulty, scaled to an IV when parsed.
                it.iv shouldBeLessThanOrEqual MAX_IV
              }
            }
          }
        }
      }
    })
