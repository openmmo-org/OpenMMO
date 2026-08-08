package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.typechart.TypeChart
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

private const val PLAYER_ID = 0x1C000L
private const val WILD_ID = 0x3C000L

private const val TACKLE: Short = 33
private const val GROWL: Short = 45
private const val SWIFT: Short = 129
private const val QUICK_ATTACK: Short = 98
private const val SPLASH: Short = 150
private const val PECK: Short = 64

private val speciesRegistry = SpeciesRegistry()

private fun pokemon(dexId: Int, level: Int, moves: List<Short>, id: Long): Pokemon {
  val moveRegistry = MoveRegistry()
  val padded = List(4) { i -> moves.getOrNull(i) ?: 0 }
  return Pokemon(
      id = id,
      ownerId = 0,
      container = PokemonContainer.PARTY,
      containerSlot = 0,
      dexId = dexId,
      seed = 0,
      ot = "",
      nickname = "",
      level = level.toByte(),
      hp = Short.MAX_VALUE,
      xp = 0,
      eVs = EVs(),
      iVs = IVs(),
      moves = padded.map { PokemonMove(it, (moveRegistry.get(it.toInt())?.pp ?: 0).toByte()) },
      isShiny = false,
      hasHiddenAbility = false,
      isAlpha = false,
      isSecret = false,
      isFatefulEncounter = false,
      isRaidEncounter = false,
      caughtAt = LocalDateTime.now(),
  )
}

private fun state(dexId: Int, level: Int, moves: List<Short>, id: Long): BattleMonState {
  val def = speciesRegistry.get(dexId)!!
  val mon = pokemon(dexId, level, moves, id)
  return BattleMonState(
      id, def, if (id == PLAYER_ID) 0 else null, mon, StatCalculator.computeAll(def, mon))
}

private fun battle(
    player: BattleMonState,
    wild: BattleMonState,
    seed: Long,
): BattleInstance =
    BattleInstance(1L, 100L, FakeSession(100L), listOf(player), listOf(wild), BattleRng(seed))

private val engine = TurnEngine(MoveRegistry(), TypeChart())

class TurnEngineTest :
    FunSpec({
      test("the same seed replays the same turn") {
        fun run(): List<BattleEvent> {
          val player = state(1, 5, listOf(TACKLE, GROWL), PLAYER_ID)
          val wild = state(19, 3, listOf(TACKLE), WILD_ID)
          return engine.resolveTurn(battle(player, wild, seed = 7), TACKLE)
        }
        run() shouldBe run()
      }

      test("swift always hits and pp decrements to the event value") {
        val player = state(1, 20, listOf(SWIFT), PLAYER_ID)
        val wild = state(19, 3, listOf(TACKLE), WILD_ID)
        val events = engine.resolveTurn(battle(player, wild, seed = 1), SWIFT)

        val used =
            events.filterIsInstance<BattleEvent.MoveUsed>().first { it.attackerId == PLAYER_ID }
        used.moveSlot shouldBe 0
        used.ppLeft shouldBe 19
        player.moves[0].pp shouldBe 19.toByte()
        events.filterIsInstance<BattleEvent.DamageDealt>().first { it.targetId == WILD_ID }
      }

      test("damage stays inside the gen 3 bounds for the setup") {
        // Bulbasaur level 20 Swift versus Snorlax level 20. Swift is Normal, so the gen 3 type
        // split makes it physical, and the target is bulky enough that no roll can faint it and
        // clamp the damage.
        repeat(30) { i ->
          val player = state(1, 20, listOf(SWIFT), PLAYER_ID)
          val wild = state(143, 20, listOf(TACKLE), WILD_ID)
          val before = wild.currentHp
          val events = engine.resolveTurn(battle(player, wild, seed = i.toLong()), SWIFT)
          val hit =
              events.filterIsInstance<BattleEvent.DamageDealt>().first { it.targetId == WILD_ID }
          val dealt = before - hit.newHp
          val base = (2 * 20 / 5 + 2) * 60 * player.stats.atk / wild.stats.def / 50 + 2
          val bound = if (hit.crit) base * 2 else base
          dealt shouldBeGreaterThan bound * 85 / 100 - 1
          (dealt <= bound).shouldBeTrue()
        }
      }

      test("a critical hit deals more than any normal roll") {
        var critMin = Int.MAX_VALUE
        var normalMax = 0
        for (seed in 0L until 200L) {
          val player = state(1, 50, listOf(SWIFT), PLAYER_ID)
          val wild = state(19, 30, listOf(TACKLE), WILD_ID)
          val before = wild.currentHp
          val events = engine.resolveTurn(battle(player, wild, seed), SWIFT)
          val hit =
              events.filterIsInstance<BattleEvent.DamageDealt>().firstOrNull {
                it.targetId == WILD_ID
              } ?: continue
          val dealt = before - hit.newHp
          if (hit.crit) critMin = minOf(critMin, dealt) else normalMax = maxOf(normalMax, dealt)
        }
        critMin shouldNotBe Int.MAX_VALUE
        normalMax shouldBeGreaterThan 0
        critMin shouldBeGreaterThan normalMax
      }

      test("STAB outdamages an equal power move without it") {
        // Rattata uses Tackle (Normal, 35, STAB) or Peck (Flying, 35, no STAB) into Snorlax.
        fun maxDealt(moveId: Short): Int {
          var best = 0
          for (seed in 0L until 100L) {
            val player = state(19, 30, listOf(moveId), PLAYER_ID)
            val wild = state(143, 30, listOf(SPLASH), WILD_ID)
            val before = wild.currentHp
            val events = engine.resolveTurn(battle(player, wild, seed), moveId)
            val hit =
                events.filterIsInstance<BattleEvent.DamageDealt>().firstOrNull {
                  it.targetId == WILD_ID
                } ?: continue
            best = maxOf(best, before - hit.newHp)
          }
          return best
        }
        maxDealt(TACKLE) shouldBeGreaterThan maxDealt(PECK)
      }

      test("an immune target takes no damage and the move fails") {
        // Tackle into Gastly (Ghost/Poison) is a Normal immunity.
        val player = state(1, 20, listOf(TACKLE), PLAYER_ID)
        val wild = state(92, 5, listOf(SPLASH), WILD_ID)
        val before = wild.currentHp
        val events = engine.resolveTurn(battle(player, wild, seed = 3), TACKLE)

        events.filterIsInstance<BattleEvent.MoveFailed>().first { it.attackerId == PLAYER_ID }
        events
            .filterIsInstance<BattleEvent.DamageDealt>()
            .none { it.targetId == WILD_ID }
            .shouldBeTrue()
        wild.currentHp shouldBe before
      }

      test("growl drops the enemy attack stage") {
        val player = state(1, 5, listOf(GROWL), PLAYER_ID)
        val wild = state(19, 3, listOf(TACKLE), WILD_ID)
        val events = engine.resolveTurn(battle(player, wild, seed = 5), GROWL)

        val change =
            events.filterIsInstance<BattleEvent.StageChanged>().first { it.targetId == WILD_ID }
        change.stat shouldBe BattleStat.ATTACK
        change.stage shouldBe -1
        change.failed shouldBe false
        wild.stage(BattleStat.ATTACK) shouldBe -1
      }

      test("a stage already at the limit reports a failed change") {
        val player = state(1, 5, listOf(GROWL), PLAYER_ID)
        val wild = state(19, 3, listOf(TACKLE), WILD_ID)
        wild.changeStage(BattleStat.ATTACK, -6)
        val events = engine.resolveTurn(battle(player, wild, seed = 5), GROWL)

        val change =
            events.filterIsInstance<BattleEvent.StageChanged>().first { it.targetId == WILD_ID }
        change.failed shouldBe true
        wild.stage(BattleStat.ATTACK) shouldBe -6
      }

      test("priority beats speed") {
        // Level 5 Bulbasaur is far slower than a level 30 Rattata, Quick Attack still goes first.
        val player = state(1, 5, listOf(QUICK_ATTACK), PLAYER_ID)
        val wild = state(19, 30, listOf(TACKLE), WILD_ID)
        val events = engine.resolveTurn(battle(player, wild, seed = 11), QUICK_ATTACK)

        events.filterIsInstance<BattleEvent.MoveUsed>().first().attackerId shouldBe PLAYER_ID
      }

      test("the faster monster acts first without priority") {
        val player = state(1, 5, listOf(TACKLE), PLAYER_ID)
        val wild = state(19, 30, listOf(TACKLE), WILD_ID)
        val events = engine.resolveTurn(battle(player, wild, seed = 11), TACKLE)

        events.filterIsInstance<BattleEvent.MoveUsed>().first().attackerId shouldBe WILD_ID
      }

      test("a speed tie falls to the rng and both orders appear") {
        val leaders = mutableSetOf<Long>()
        for (seed in 0L until 40L) {
          val player = state(19, 10, listOf(TACKLE), PLAYER_ID)
          val wild = state(19, 10, listOf(TACKLE), WILD_ID)
          val events = engine.resolveTurn(battle(player, wild, seed), TACKLE)
          leaders += events.filterIsInstance<BattleEvent.MoveUsed>().first().attackerId
        }
        leaders shouldBe setOf(PLAYER_ID, WILD_ID)
      }

      test("a faint ends the turn before the loser acts") {
        val player = state(1, 50, listOf(SWIFT), PLAYER_ID)
        val wild = state(19, 2, listOf(TACKLE), WILD_ID)
        wild.currentHp = 1
        val events = engine.resolveTurn(battle(player, wild, seed = 2), SWIFT)

        events.filterIsInstance<BattleEvent.Fainted>().first().targetId shouldBe WILD_ID
        events
            .filterIsInstance<BattleEvent.MoveUsed>()
            .none { it.attackerId == WILD_ID }
            .shouldBeTrue()
      }

      test("an effect outside the core fails gracefully") {
        val player = state(1, 5, listOf(SPLASH), PLAYER_ID)
        val wild = state(19, 3, listOf(TACKLE), WILD_ID)
        val before = wild.currentHp
        val events = engine.resolveTurn(battle(player, wild, seed = 9), SPLASH)

        events.filterIsInstance<BattleEvent.MoveFailed>().shouldNotBeEmpty()
        wild.currentHp shouldBe before
        wild.stage(BattleStat.ATTACK) shouldBe 0
      }
    })
