package de.fiereu.openmmo.server.game.battle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * Parses pokemon-showdown SIM-PROTOCOL log lines (the `TurnResult.log` the sidecar returns) into
 * the intermediate [BattleEvent] model. Pure + capture-independent — this is the sidecar→S2C half
 * of the translator BEFORE the (capture-#2-gated) wire emitters. Line formats per
 * https://github.com/smogon/pokemon-showdown/blob/master/sim/SIM-PROTOCOL.md.
 */
class BattleLogParserTest :
    FunSpec({
      val snivy = BattleActor(BattleSide.PLAYER, 0, "Snivy")
      val patrat = BattleActor(BattleSide.WILD, 0, "Patrat")

      test("parses a two-sided attack turn into move + damage events keyed by side") {
        val log =
            listOf(
                "|move|p1a: Snivy|Tackle|p2a: Patrat",
                "|-damage|p2a: Patrat|72/100",
                "|move|p2a: Patrat|Tackle|p1a: Snivy",
                "|-damage|p1a: Snivy|85/100",
            )
        BattleLogParser.parse(log) shouldBe
            listOf(
                MoveUsed(snivy, "Tackle", patrat),
                Damage(patrat, HpStatus(72, 100, fainted = false, status = null)),
                MoveUsed(patrat, "Tackle", snivy),
                Damage(snivy, HpStatus(85, 100, fainted = false, status = null)),
            )
      }

      test("parses a faint (0 fnt) then the win line") {
        val log =
            listOf(
                "|move|p1a: Snivy|Tackle|p2a: Patrat",
                "|-damage|p2a: Patrat|0 fnt",
                "|faint|p2a: Patrat",
                "|win|OtherBag",
            )
        BattleLogParser.parse(log) shouldBe
            listOf(
                MoveUsed(snivy, "Tackle", patrat),
                Damage(patrat, HpStatus(0, null, fainted = true, status = null)),
                Faint(patrat),
                BattleEnded("OtherBag"),
            )
      }

      test("parses boost/unboost, status, heal, and intentional vs forced switches") {
        val log =
            listOf(
                "|-boost|p1a: Snivy|atk|1",
                "|-unboost|p2a: Patrat|spe|2",
                "|-status|p2a: Patrat|par",
                "|-heal|p1a: Snivy|100/100",
                "|switch|p1a: Servine|Servine, L18, M|60/60",
                "|drag|p2a: Lillipup|Lillipup, L4, F|18/18",
            )
        BattleLogParser.parse(log) shouldBe
            listOf(
                StatChange(snivy, "atk", 1),
                StatChange(patrat, "spe", -2),
                StatusInflicted(patrat, "par"),
                Heal(snivy, HpStatus(100, 100, fainted = false, status = null)),
                SwitchIn(
                    BattleActor(BattleSide.PLAYER, 0, "Servine"),
                    forced = false,
                    hp = HpStatus(60, 60, fainted = false, status = null)),
                SwitchIn(
                    BattleActor(BattleSide.WILD, 0, "Lillipup"),
                    forced = true,
                    hp = HpStatus(18, 18, fainted = false, status = null)),
            )
      }

      test("keeps a status suffix on an HP token and ignores move [tags]") {
        val log =
            listOf(
                "|-damage|p1a: Snivy|54/100 brn",
                "|move|p1a: Snivy|Tackle|p2a: Patrat|[miss]",
            )
        BattleLogParser.parse(log) shouldBe
            listOf(
                Damage(snivy, HpStatus(54, 100, fainted = false, status = "brn")),
                MoveUsed(snivy, "Tackle", patrat),
            )
      }

      test("ignores pacing/metadata lines and maps the sidecar flee marker to Fled") {
        val log = listOf("|turn|1", "|upkeep", "", "|-weather|none", "|flee|")
        BattleLogParser.parse(log) shouldBe listOf(Fled)
      }
    })
