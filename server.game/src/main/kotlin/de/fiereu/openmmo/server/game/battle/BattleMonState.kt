package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.net.game.packets.battle.BattleMonBlock
import de.fiereu.openmmo.net.game.packets.battle.BattleOpponentBlock
import de.fiereu.openmmo.pokemon.SpeciesDef
import java.util.EnumMap

/**
 * One monster's live state inside a battle. [source] is the snapshot the battle started from.
 * [currentHp] and the move PP are the live values written back when the battle ends.
 */
class BattleMonState(
    val entityId: Long,
    val species: SpeciesDef,
    val partyIndex: Int?,
    // Both move on when a reward lands, so a second reward in the same battle builds on the first.
    var source: Pokemon,
    var stats: ComputedStats,
    val gender: Byte = 0,
) {
  var currentHp: Int = source.hp.toInt().coerceIn(0, stats.hp)
  val moves: MutableList<PokemonMove> =
      source.moves.map { PokemonMove(it.id, it.pp) }.toMutableList()

  private val stages = EnumMap<BattleStat, Int>(BattleStat::class.java)

  val level: Int
    get() = source.level.toInt()

  val fainted: Boolean
    get() = currentHp <= 0

  fun stage(stat: BattleStat): Int = stages[stat] ?: 0

  /** Clamp to the stage limits and return the delta that was actually applied. */
  fun changeStage(stat: BattleStat, delta: Int): Int {
    val old = stage(stat)
    val new = (old + delta).coerceIn(StatStages.MIN, StatStages.MAX)
    stages[stat] = new
    return new - old
  }

  fun unstaged(stat: BattleStat): Int =
      when (stat) {
        BattleStat.ATTACK -> stats.atk
        BattleStat.DEFENSE -> stats.def
        BattleStat.SP_ATTACK -> stats.spAtk
        BattleStat.SP_DEFENSE -> stats.spDef
        BattleStat.SPEED -> stats.spd
        BattleStat.ACCURACY,
        BattleStat.EVASION -> error("$this has no base stat to stage")
      }

  fun effective(stat: BattleStat): Int = StatStages.scaleStat(unstaged(stat), stage(stat))

  fun toOpponentBlock(slot: Int): BattleOpponentBlock =
      BattleOpponentBlock(
          slot = slot,
          revealed = true,
          entityId = entityId,
          species = species.id.toShort(),
          level = source.level,
          gender = gender,
          maxHp = stats.hp.toShort(),
          currentHp = currentHp.toShort(),
      )

  fun toBlock(slot: Int, movesPresent: Boolean): BattleMonBlock =
      BattleMonBlock(
          slot = slot,
          entityId = entityId,
          species = species.id.toShort(),
          level = source.level,
          gender = gender,
          abilityId = species.ability1.ordinal.toShort(),
          maxHp = stats.hp.toShort(),
          currentHp = currentHp.toShort(),
          movesPresent = movesPresent,
          moveIds = List(BattleMonBlock.MOVE_SLOTS) { moves.getOrNull(it)?.id ?: 0 },
      )
}
