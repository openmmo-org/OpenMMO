package de.fiereu.openmmo.typechart

import de.fiereu.openmmo.common.enums.PokemonType
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TypeChartTest :
    FunSpec({
      val chart = TypeChart()

      test("immunities multiply to zero") {
        chart.multiplier(PokemonType.ELECTRIC, PokemonType.GROUND) shouldBe 0
        chart.multiplier(PokemonType.NORMAL, PokemonType.GHOST) shouldBe 0
        chart.multiplier(PokemonType.FIGHTING, PokemonType.GHOST) shouldBe 0
      }

      test("super effective doubles and resisted halves") {
        chart.multiplier(PokemonType.FIRE, PokemonType.GRASS) shouldBe 20
        chart.multiplier(PokemonType.WATER, PokemonType.FIRE) shouldBe 20
        chart.multiplier(PokemonType.GHOST, PokemonType.STEEL) shouldBe 5
      }

      test("an unlisted matchup is neutral") {
        chart.multiplier(PokemonType.NORMAL, PokemonType.NORMAL) shouldBe 10
      }

      test("dual typing multiplies both matchups") {
        // Grass/Poison versus Fire: 20 * 10 / 10.
        chart.effectiveness(PokemonType.FIRE, PokemonType.GRASS, PokemonType.POISON) shouldBe 20
        // Bug/Grass versus Fire: 20 * 20 / 10.
        chart.effectiveness(PokemonType.FIRE, PokemonType.BUG, PokemonType.GRASS) shouldBe 40
        // Ground immunity wins over the Rock weakness for Electric.
        chart.effectiveness(PokemonType.ELECTRIC, PokemonType.GROUND, PokemonType.FLYING) shouldBe 0
      }

      test("a mono type counts only once") {
        chart.effectiveness(PokemonType.FIRE, PokemonType.GRASS, PokemonType.GRASS) shouldBe 20
      }
    })
