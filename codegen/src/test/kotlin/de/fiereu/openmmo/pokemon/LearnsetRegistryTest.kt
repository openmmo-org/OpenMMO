package de.fiereu.openmmo.pokemon

import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import de.fiereu.openmmo.moves.MoveRegistry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val TACKLE = 33
private const val GROWL = 45
private const val LEECH_SEED = 73
private const val VINE_WHIP = 22
private const val POISON_POWDER = 77
private const val SLEEP_POWDER = 79
private const val SWEET_SCENT = 230
private const val GROWTH = 74
private const val SYNTHESIS = 235
private const val SOLAR_BEAM = 76

class LearnsetRegistryTest :
    FunSpec({
      val learnsets = LearnsetRegistry()

      test("every species with stats has a learnset") {
        learnsets.size() shouldBe SpeciesRegistry().size()
      }

      test("a learnset is sorted by level") {
        for (dexId in 1..learnsets.size()) {
          val levels = learnsets.get(dexId).map { it.level }
          levels shouldBe levels.sorted()
        }
      }

      test("Bulbasaur learns the moves it does in the games") {
        learnsets.get(1).take(4) shouldBe
            listOf(
                LevelUpMove(1, TACKLE),
                LevelUpMove(4, GROWL),
                LevelUpMove(7, LEECH_SEED),
                LevelUpMove(10, VINE_WHIP),
            )
      }

      test("two moves can share a level") {
        learnsets.movesAt(1, 15) shouldBe listOf(POISON_POWDER, SLEEP_POWDER)
      }

      test("a level with no move learns nothing") { learnsets.movesAt(1, 2).shouldBeEmpty() }

      test("the initial moveset stops at the given level") {
        learnsets.initialMoveset(1, 1) shouldBe listOf(TACKLE)
        learnsets.initialMoveset(1, 10) shouldBe listOf(TACKLE, GROWL, LEECH_SEED, VINE_WHIP)
      }

      test("a fifth move pushes out the oldest one") {
        learnsets.initialMoveset(1, 100) shouldBe listOf(SWEET_SCENT, GROWTH, SYNTHESIS, SOLAR_BEAM)
      }

      test("no moveset is longer than four moves") {
        for (dexId in 1..learnsets.size()) {
          learnsets.initialMoveset(dexId, 100).size shouldBe
              minOf(MAX_MOVE_SLOTS, learnsets.get(dexId).map { it.moveId }.distinct().size)
        }
      }

      test("an unknown species has no learnset") {
        learnsets.get(9999).shouldBeEmpty()
        learnsets.initialMoveset(9999, 50).shouldBeEmpty()
      }

      test("every learned move is in the move registry") {
        val moves = MoveRegistry()
        val learned = (1..learnsets.size()).flatMap { learnsets.get(it) }
        learned.size shouldBeGreaterThan 0
        learned.filter { moves.get(it.moveId) == null }.shouldBeEmpty()
      }
    })
