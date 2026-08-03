package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.MAX_MOVE_SLOTS

/**
 * One monster as the battle packets carry it, shared by the field state and the switch-in. The
 * values are server computed and the client displays them as sent. A full block carries hp,
 * ability, and moves. The 21-byte active detail names only species, level, and gender.
 *
 * [movesPresent] is the wire flag that guards the ability and move ids. The wild block clears it so
 * the client never learns the enemy moveset.
 */
data class BattleMonBlock(
    val slot: Int,
    val entityId: Long,
    val species: Short,
    val level: Byte,
    val gender: Byte,
    val abilityId: Short,
    val maxHp: Short,
    val currentHp: Short,
    val movesPresent: Boolean,
    val moveIds: List<Short>,
) {
  init {
    require(moveIds.size == MOVE_SLOTS) { "A battle mon block carries exactly $MOVE_SLOTS moves" }
  }

  companion object {
    const val MOVE_SLOTS = MAX_MOVE_SLOTS
  }
}

private fun seg(hex: String): ByteArray =
    ByteArray(hex.length / 2) { hex.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

// Captured constant that precedes the moves-present flag, meaning still unknown.
private val MOVES_HEADER = seg("000000ff03")
// The wild block replaces the ability and move segment with this captured marker.
private val WILD_MOVES_SEGMENT = seg("000000ff030001")
private val ACTIVE_TAIL = seg("03ff0000000066666666")

private val NO_MOVES = List(BattleMonBlock.MOVE_SLOTS) { 0.toShort() }

/** The active monster's detail block, naming only species, level, and gender. */
internal data class BattleActiveDetail(
    val slot: Int,
    val species: Short,
    val level: Byte,
    val gender: Byte,
) {
  companion object {
    fun of(slot: Int, mon: BattleMonBlock): BattleActiveDetail =
        BattleActiveDetail(slot, mon.species, mon.level, mon.gender)
  }
}

internal object BattleActiveDetailCodec : PacketCodec<BattleActiveDetail>() {
  const val WIRE_SIZE = 21

  override fun CodecScope<BattleActiveDetail>.body(): BattleActiveDetail {
    reserved(0)
    val slot = field(S8) { it.slot.toByte() }.toInt()
    val species = field(S16LE) { it.species }
    val level = field(S8) { it.level }
    padding(4)
    val gender = field(S8) { it.gender }
    reserved(0)
    constant(ACTIVE_TAIL)
    return BattleActiveDetail(slot, species, level, gender)
  }
}

internal object BattleFullBlockCodec : PacketCodec<BattleMonBlock>() {
  override fun CodecScope<BattleMonBlock>.body(): BattleMonBlock {
    val slot = field(S8) { it.slot.toByte() }.toInt()
    constant(1)
    val entityId = field(S64LE) { it.entityId }
    val species = field(S16LE) { it.species }
    val level = field(S8) { it.level }
    padding(2)
    val gender = field(S8) { it.gender }
    padding(3)
    val currentHp = field(S16LE) { it.currentHp }
    val maxHp = field(S16LE) { it.maxHp }
    constant(MOVES_HEADER)
    val movesPresent = field(Bool) { it.movesPresent }
    val abilityId = field(S16LE) { it.abilityId }
    val moveIds = field(S16LE.repeat(BattleMonBlock.MOVE_SLOTS)) { it.moveIds }
    return BattleMonBlock(
        slot, entityId, species, level, gender, abilityId, maxHp, currentHp, movesPresent, moveIds)
  }
}

/** The wild side's block. The decode fills ability and moves with zero. */
internal object BattleWildBlockCodec : PacketCodec<BattleMonBlock>() {
  override fun CodecScope<BattleMonBlock>.body(): BattleMonBlock {
    val slot = field(S8) { it.slot.toByte() }.toInt()
    constant(1)
    val entityId = field(S64LE) { it.entityId }
    val species = field(S16LE) { it.species }
    val level = field(S8) { it.level }
    padding(2)
    val gender = field(S8) { it.gender }
    padding(3)
    val currentHp = field(S16LE) { it.currentHp }
    val maxHp = field(S16LE) { it.maxHp }
    constant(WILD_MOVES_SEGMENT)
    field(BattleActiveDetailCodec) { BattleActiveDetail.of(it.slot, it) }
    padding(4)
    return BattleMonBlock(
        slot, entityId, species, level, gender, 0, maxHp, currentHp, false, NO_MOVES)
  }
}
