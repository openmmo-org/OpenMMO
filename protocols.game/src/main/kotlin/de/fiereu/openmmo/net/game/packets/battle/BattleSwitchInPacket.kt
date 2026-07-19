package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleSwitchInMon(
    val entityId: Long,
    val species: Short,
    val hp: Short,
)

/**
 * Brings a monster onto the field after a switch (opcode 0x35). The header names the new and
 * previous active party slots. A monster coming out for the first time carries its full block, the
 * same layout as the field state. One already seen active is sent as just its 21-byte active
 * detail. When [fullBlock] is false the incoming hp and entity id are left at zero on decode.
 */
data class BattleSwitchInPacket(
    val newSlot: Int,
    val oldSlot: Int,
    val mon: BattleSwitchInMon,
    val fullBlock: Boolean,
)

private fun switchSeg(hex: String): ByteArray =
    ByteArray(hex.length / 2) { hex.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

private val SWITCH_HEADER = switchSeg("0000")
private val SWITCH_MON_CONST = switchSeg("01")

// A species carries a full-block stat prefix and move body (as in the field state) plus a distinct
// active-detail stat used by the switch-in and active-mon segments.
private class SwitchSpeciesBlock(
    val statPrefix: ByteArray,
    val moveBody: ByteArray,
    val activeStat: ByteArray,
)

private val SWITCH_SPECIES_BLOCKS =
    mapOf(
        495 to
            SwitchSpeciesBlock(
                switchSeg("06000000000000"),
                switchSeg("000000ff0301410021002b0000000000"),
                switchSeg("06000000000000"),
            ),
        504 to
            SwitchSpeciesBlock(
                switchSeg("02000001000000"),
                switchSeg("000000ff030132002100000000000000"),
                switchSeg("02000000000100"),
            ),
    )
private val DEFAULT_SWITCH_BLOCK = SWITCH_SPECIES_BLOCKS.getValue(504)

private fun switchBlock(species: Short): SwitchSpeciesBlock =
    SWITCH_SPECIES_BLOCKS[species.toInt()] ?: DEFAULT_SWITCH_BLOCK

// [00][slot][species][active stat (7)] then this constant tail.
private val SWITCH_ACTIVE_TAIL = switchSeg("03ff0000000066666666")
private const val SWITCH_STAT_PREFIX_SIZE = 7
private const val SWITCH_MOVE_BODY_SIZE = 16
private const val SWITCH_ACTIVE_STAT_SIZE = 7
private const val SWITCH_ACTIVE_DETAIL_SIZE = 21

private val BattleSwitchInBody: Codec<BattleSwitchInPacket> =
    object : Codec<BattleSwitchInPacket> {
      override fun read(buf: ReadBuffer): BattleSwitchInPacket {
        buf.readBytes(ByteArray(SWITCH_HEADER.size))
        val newSlot = U8.read(buf)
        val oldSlot = U8.read(buf)
        val fullBlock = buf.remaining() > SWITCH_ACTIVE_DETAIL_SIZE
        var entityId = 0L
        var blockHp: Short = 0
        var blockSpecies: Short = 0
        if (fullBlock) {
          S8.read(buf) // slot index
          buf.readBytes(ByteArray(SWITCH_MON_CONST.size))
          entityId = S64LE.read(buf)
          blockSpecies = S16LE.read(buf)
          buf.readBytes(ByteArray(SWITCH_STAT_PREFIX_SIZE))
          S8.read(buf) // max hp
          S8.read(buf) // stat tail
          blockHp = S16LE.read(buf)
          buf.readBytes(ByteArray(SWITCH_MOVE_BODY_SIZE))
          S8.read(buf) // slot trailer
        }
        // active detail
        S8.read(buf) // constant 00
        S8.read(buf) // slot
        val activeSpecies = S16LE.read(buf)
        buf.readBytes(ByteArray(SWITCH_ACTIVE_STAT_SIZE))
        buf.readBytes(ByteArray(SWITCH_ACTIVE_TAIL.size))
        val species = if (fullBlock) blockSpecies else activeSpecies
        return BattleSwitchInPacket(
            newSlot, oldSlot, BattleSwitchInMon(entityId, species, blockHp), fullBlock)
      }

      override fun write(buf: WriteBuffer, value: BattleSwitchInPacket) {
        buf.writeBytes(SWITCH_HEADER)
        U8.write(buf, value.newSlot)
        U8.write(buf, value.oldSlot)
        val block = switchBlock(value.mon.species)
        if (value.fullBlock) {
          S8.write(buf, value.newSlot.toByte())
          buf.writeBytes(SWITCH_MON_CONST)
          S64LE.write(buf, value.mon.entityId)
          S16LE.write(buf, value.mon.species)
          buf.writeBytes(block.statPrefix)
          S8.write(buf, value.mon.hp.toByte()) // max hp
          S8.write(buf, 0)
          S16LE.write(buf, value.mon.hp)
          buf.writeBytes(block.moveBody)
          S8.write(buf, value.newSlot.toByte()) // slot trailer
        }
        S8.write(buf, 0)
        S8.write(buf, value.newSlot.toByte())
        S16LE.write(buf, value.mon.species)
        buf.writeBytes(block.activeStat)
        buf.writeBytes(SWITCH_ACTIVE_TAIL)
      }
    }

object BattleSwitchInPacketCodec : PacketCodec<BattleSwitchInPacket>() {
  override fun CodecScope<BattleSwitchInPacket>.body(): BattleSwitchInPacket {
    return field(BattleSwitchInBody) { it }
  }
}
