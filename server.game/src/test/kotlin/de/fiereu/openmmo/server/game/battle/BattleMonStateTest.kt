package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

private fun rattata(level: Byte = 3, hp: Short = 14): Pokemon =
    Pokemon(
        id = 1L,
        ownerId = 2L,
        container = PokemonContainer.PARTY,
        containerSlot = 0,
        dexId = 19,
        seed = 0,
        ot = "Ash",
        nickname = "",
        level = level,
        hp = hp,
        xp = 27,
        eVs = EVs(),
        iVs = IVs(),
        moves =
            listOf(PokemonMove(33, 35), PokemonMove(0, 0), PokemonMove(0, 0), PokemonMove(0, 0)),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.now(),
    )

private fun state(): BattleMonState {
  val species = SpeciesRegistry().get(19)!!
  val mon = rattata()
  return BattleMonState(mon.id, species, 0, mon, StatCalculator.computeAll(species, mon))
}

class BattleMonStateTest :
    FunSpec({
      test("stages clamp at plus and minus six and report the applied delta") {
        val mon = state()
        mon.changeStage(BattleStat.ATTACK, -2) shouldBe -2
        mon.changeStage(BattleStat.ATTACK, -5) shouldBe -4
        mon.stage(BattleStat.ATTACK) shouldBe -6
        mon.changeStage(BattleStat.ATTACK, -1) shouldBe 0

        mon.changeStage(BattleStat.SPEED, 7) shouldBe 6
        mon.stage(BattleStat.SPEED) shouldBe 6
      }

      test("effective stats follow the gen 3 stage ratios") {
        val mon = state()
        val base = mon.unstaged(BattleStat.ATTACK)
        mon.effective(BattleStat.ATTACK) shouldBe base

        mon.changeStage(BattleStat.ATTACK, 2)
        mon.effective(BattleStat.ATTACK) shouldBe base * 2

        mon.changeStage(BattleStat.ATTACK, -4)
        mon.effective(BattleStat.ATTACK) shouldBe base * 10 / 20
      }

      test("current hp starts clamped to the computed maximum") {
        val species = SpeciesRegistry().get(19)!!
        val mon = rattata(hp = 999)
        val stats = StatCalculator.computeAll(species, mon)
        BattleMonState(1L, species, 0, mon, stats).currentHp shouldBe stats.hp
      }

      test("the block carries the computed stats and the live hp") {
        val mon = state()
        mon.currentHp = 9
        val block = mon.toBlock(slot = 2, movesPresent = true)
        block.slot shouldBe 2
        block.species shouldBe 19.toShort()
        block.maxHp shouldBe mon.stats.hp.toShort()
        block.currentHp shouldBe 9.toShort()
        block.movesPresent shouldBe true
        block.moveIds shouldBe listOf<Short>(33, 0, 0, 0)
      }
    })
