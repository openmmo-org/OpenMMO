package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.GrowthRate
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ExpCurvesTest :
    FunSpec({
      test("the curve totals match the known level 100 values") {
        ExpCurves.totalXpFor(GrowthRate.MEDIUM_FAST, 100) shouldBe 1_000_000
        ExpCurves.totalXpFor(GrowthRate.MEDIUM_SLOW, 100) shouldBe 1_059_860
        ExpCurves.totalXpFor(GrowthRate.FAST, 100) shouldBe 800_000
        ExpCurves.totalXpFor(GrowthRate.SLOW, 100) shouldBe 1_250_000
        ExpCurves.totalXpFor(GrowthRate.ERRATIC, 100) shouldBe 600_000
        ExpCurves.totalXpFor(GrowthRate.FLUCTUATING, 100) shouldBe 1_640_000
      }

      test("piecewise branches match known table entries") {
        ExpCurves.totalXpFor(GrowthRate.MEDIUM_SLOW, 5) shouldBe 135
        ExpCurves.totalXpFor(GrowthRate.ERRATIC, 50) shouldBe 125_000
        ExpCurves.totalXpFor(GrowthRate.FLUCTUATING, 15) shouldBe 1_957
      }

      test("the first two levels are the hardcoded table entries") {
        GrowthRate.entries.forEach { rate ->
          ExpCurves.totalXpFor(rate, 0) shouldBe 0
          ExpCurves.totalXpFor(rate, 1) shouldBe 1
        }
      }

      test("levelFor inverts totalXpFor") {
        GrowthRate.entries.forEach { rate ->
          for (level in 1..100) {
            ExpCurves.levelFor(rate, ExpCurves.totalXpFor(rate, level)) shouldBe level
          }
        }
      }

      test("levelFor caps at 100") {
        ExpCurves.levelFor(GrowthRate.MEDIUM_FAST, Int.MAX_VALUE) shouldBe 100
      }
    })
