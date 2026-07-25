package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.GrowthRate
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

private val species = SpeciesRegistry()
private val rewards = BattleRewards()

private const val BULBASAUR = 1
private const val RATTATA = 19
private const val CHANSEY = 113

private fun winner(level: Int, xp: Int, evs: EVs = EVs(), damage: Int = 0): BattleMonState {
  val def = species.get(BULBASAUR)!!
  val mon =
      Pokemon(
          id = 1L,
          ownerId = 2L,
          container = PokemonContainer.PARTY,
          containerSlot = 0,
          dexId = BULBASAUR,
          seed = 0,
          ot = "Ash",
          nickname = "",
          level = level.toByte(),
          hp = Short.MAX_VALUE,
          xp = xp,
          eVs = evs,
          iVs = IVs(),
          moves = listOf(PokemonMove(33, 35)),
          isShiny = false,
          hasHiddenAbility = false,
          isAlpha = false,
          isSecret = false,
          isFatefulEncounter = false,
          isRaidEncounter = false,
          caughtAt = LocalDateTime.now(),
      )
  val state = BattleMonState(1L, def, 0, mon, StatCalculator.computeAll(def, mon))
  state.currentHp -= damage
  return state
}

class BattleRewardsTest :
    FunSpec({
      test("wild xp follows yield times level over seven") {
        val rattata = species.get(RATTATA)!!
        rewards.wildXp(rattata, 3) shouldBe rattata.expYield * 3 / 7
      }

      test("a big win jumps several levels and recomputes stats") {
        val start = ExpCurves.totalXpFor(GrowthRate.MEDIUM_SLOW, 5)
        val mon = winner(level = 5, xp = start)
        val chansey = species.get(CHANSEY)!!

        val reward = rewards.apply(mon, chansey, 100)

        reward.xpGained shouldBe chansey.expYield * 100 / 7
        reward.newXp shouldBe start + reward.xpGained
        reward.newLevel shouldBe ExpCurves.levelFor(GrowthRate.MEDIUM_SLOW, reward.newXp)
        reward.newLevel shouldBeGreaterThan 6
        reward.leveled.shouldBeTrue()
        reward.newStats.hp shouldBe StatCalculator.maxHp(45, 0, reward.newEvs.hp, reward.newLevel)
      }

      test("the hp gained on level up keeps the missing hp missing") {
        val start = ExpCurves.totalXpFor(GrowthRate.MEDIUM_SLOW, 5)
        val mon = winner(level = 5, xp = start, damage = 9)
        val reward = rewards.apply(mon, species.get(CHANSEY)!!, 100)

        reward.newCurrentHp shouldBe reward.newStats.hp - 9
      }

      test("ev yields clamp at the per stat cap") {
        val evs = EVs().apply { hp = 251 }
        val mon =
            winner(level = 50, xp = ExpCurves.totalXpFor(GrowthRate.MEDIUM_SLOW, 50), evs = evs)
        val reward = rewards.apply(mon, species.get(CHANSEY)!!, 10)

        reward.newEvs.hp shouldBe 252
      }

      test("ev yields respect the total cap") {
        val evs =
            EVs().apply {
              hp = 252
              atk = 252
              def = 6
            }
        val mon =
            winner(level = 50, xp = ExpCurves.totalXpFor(GrowthRate.MEDIUM_SLOW, 50), evs = evs)
        val reward = rewards.apply(mon, species.get(RATTATA)!!, 10)

        reward.newEvs.spd shouldBe 0
        reward.newEvs.total shouldBe 510
      }

      test("xp and level cap at 100") {
        val cap = ExpCurves.totalXpFor(GrowthRate.MEDIUM_SLOW, 100)
        val mon = winner(level = 99, xp = cap - 1)
        val reward = rewards.apply(mon, species.get(CHANSEY)!!, 100)

        reward.newXp shouldBe cap
        reward.newLevel shouldBe 100
      }
    })
