package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.storage.EntityIdService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class WildMonFactoryTest :
    FunSpec({
      val factory = WildMonFactory(SpeciesRegistry(), MoveRegistry(), EntityIdService())

      test("the same seed rolls the same monster") {
        val a = factory.create(19, 3, BattleRng(seed = 42))!!
        val b = factory.create(19, 3, BattleRng(seed = 42))!!
        a.seed shouldBe b.seed
        a.iVs shouldBe b.iVs
        a.nature shouldBe b.nature
      }

      test("IVs stay in the legal range and hp equals the computed maximum") {
        val species = SpeciesRegistry()
        repeat(50) { i ->
          val mon = factory.create(19, 5, BattleRng(seed = i.toLong()))!!
          mon.iVs.values.forEach { it.toInt() shouldBeInRange 0..31 }
          val stats = StatCalculator.computeAll(species.get(19)!!, mon)
          mon.hp shouldBe stats.hp.toShort()
        }
      }

      test("the moveset is Tackle with its registry pp") {
        val mon = factory.create(1, 5, BattleRng(seed = 7))!!
        mon.moves[0].id shouldBe 33.toShort()
        mon.moves[0].pp shouldBe MoveRegistry().get(33)!!.pp.toByte()
        mon.moves.drop(1).all { it.id == 0.toShort() } shouldBe true
      }

      test("xp matches the species growth curve at the rolled level") {
        val mon = factory.create(19, 7, BattleRng(seed = 1))!!
        mon.xp shouldBe ExpCurves.totalXpFor(SpeciesRegistry().get(19)!!.growthRate, 7)
      }

      test("an unknown species returns null") {
        factory.create(9999, 5, BattleRng(seed = 1)).shouldBeNull()
        factory.create(495, 5, BattleRng(seed = 1)).shouldBeNull()
      }

      test("the wild id carries the monster tag") {
        val mon = factory.create(19, 3, BattleRng(seed = 3))!!
        mon.shouldNotBeNull()
        (mon.id and 0xFFFF) shouldBe 0xC000L
      }
    })
