package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

private const val BULBASAUR = 1
private const val TACKLE: Short = 33
private const val GROWL: Short = 45
private const val LEECH_SEED: Short = 73
private const val VINE_WHIP: Short = 22
private const val POISON_POWDER: Short = 77

private fun slots(vararg ids: Short) = ids.map { PokemonMove(it, 0) }.toMutableList()

class MoveLearnerTest :
    FunSpec({
      val moves = MoveRegistry()
      val learner = MoveLearner(LearnsetRegistry(), moves)

      test("a move learned at the new level goes into a free slot") {
        val known = slots(TACKLE, 0, 0, 0)
        val learned = learner.learn(known, BULBASAUR, 3, 4)
        learned.map { it.moveId } shouldBe listOf(GROWL.toInt())
        known.map { it.id } shouldBe listOf(TACKLE, GROWL, 0, 0)
      }

      test("the new move gets its registry pp") {
        val known = slots(TACKLE, 0, 0, 0)
        learner.learn(known, BULBASAUR, 3, 4)
        known[1].pp shouldBe moves.get(GROWL.toInt())!!.pp.toByte()
      }

      test("skipping levels learns every move in between") {
        val known = slots(TACKLE, 0, 0, 0)
        val learned = learner.learn(known, BULBASAUR, 1, 10)
        learned.map { it.moveId.toShort() } shouldBe listOf(GROWL, LEECH_SEED, VINE_WHIP)
        known.map { it.id } shouldBe listOf(TACKLE, GROWL, LEECH_SEED, VINE_WHIP)
      }

      test("a full moveset keeps its moves") {
        val known = slots(TACKLE, GROWL, LEECH_SEED, VINE_WHIP)
        learner.learn(known, BULBASAUR, 14, 15).shouldBeEmpty()
        known.map { it.id } shouldBe listOf(TACKLE, GROWL, LEECH_SEED, VINE_WHIP)
      }

      // Bulbasaur learns Poison Powder and Sleep Powder at 15, only the first one fits.
      test("a partly full moveset learns until it runs out of slots") {
        val known = slots(TACKLE, GROWL, LEECH_SEED, 0)
        val learned = learner.learn(known, BULBASAUR, 14, 15)
        learned.map { it.moveId.toShort() } shouldBe listOf(POISON_POWDER)
        known.map { it.id } shouldBe listOf(TACKLE, GROWL, LEECH_SEED, POISON_POWDER)
      }

      test("a known move is not learned twice") {
        val known = slots(GROWL, 0, 0, 0)
        learner.learn(known, BULBASAUR, 3, 4).shouldBeEmpty()
        known.map { it.id } shouldBe listOf(GROWL, 0, 0, 0)
      }

      test("no level up learns nothing") {
        val known = slots(TACKLE, 0, 0, 0)
        learner.learn(known, BULBASAUR, 5, 5).shouldBeEmpty()
      }

      test("an unknown species learns nothing") {
        val known = slots(TACKLE, 0, 0, 0)
        learner.learn(known, 9999, 1, 100).shouldBeEmpty()
      }

      test("a moveset shorter than four slots grows") {
        val known = slots(TACKLE)
        learner.learn(known, BULBASAUR, 3, 4)
        known.map { it.id } shouldBe listOf(TACKLE, GROWL)
      }
    })
