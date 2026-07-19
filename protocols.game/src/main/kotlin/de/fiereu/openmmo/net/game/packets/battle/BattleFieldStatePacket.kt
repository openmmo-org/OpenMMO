package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleFieldMon(
    val entityId: Long,
    val species: Short,
    val hp: Short,
)

/**
 * The field state for a wild encounter (opcode 0x30). Each side sends a monster count that sizes
 * the client's slot array followed by that many monster blocks. A player block carries a
 * per-species stat and move block with its hp and party slot filled in, so a benched monster goes
 * in its own slot instead of overwriting the active one.
 */
data class BattleFieldStatePacket(
    val playerName: String,
    val playerId: Long,
    val playerParty: List<BattleFieldMon>,
    val wildMon: BattleFieldMon,
)

private fun seg(hex: String): ByteArray =
    ByteArray(hex.length / 2) { hex.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

private val PREAMBLE = seg("02000000000000000000000000ff000000000016000000ff00200006")
private val AFTER_NAME = seg("00")
private val APPEARANCE = seg("ff00024c031aac0f00038001a400040000000000")
private val SIDE_HEADER = seg("01")
private val SIDE_MID = seg("00")
// A monster block starts with its slot index then a constant 01.
private val MON_CONST = seg("01")
private val ACTIVE_DETAIL = seg("0000ef010600000000000003ff000000006666666601060000000000")
private val WILD_DATA_A = seg("020000010000000e00")
private val WILD_DATA_B = seg("000000ff0300010000f8010200000000010003ff000000006666666600000000")

private const val WILD_COUNT = 1

// Per-species blocks captured from a real field state. The 9-byte stat segment is
// [prefix (7)][max hp][00] and the 17-byte move segment is [move body (16)][party slot]. Hp and
// slot are spliced in per monster.
private class SpeciesBlock(val statPrefix: ByteArray, val moveBody: ByteArray)

private val SPECIES_BLOCKS =
    mapOf(
        495 to SpeciesBlock(seg("06000000000000"), seg("000000ff0301410021002b0000000000")),
        504 to SpeciesBlock(seg("02000001000000"), seg("000000ff030132002100000000000000")),
    )
private val DEFAULT_SPECIES_BLOCK = SPECIES_BLOCKS.getValue(504)

private fun speciesBlock(species: Short): SpeciesBlock =
    SPECIES_BLOCKS[species.toInt()] ?: DEFAULT_SPECIES_BLOCK

private val BattleFieldStateBody: Codec<BattleFieldStatePacket> =
    object : Codec<BattleFieldStatePacket> {
      override fun read(buf: ReadBuffer): BattleFieldStatePacket {
        buf.readBytes(ByteArray(PREAMBLE.size))
        val playerName = Utf16LeNullTerminated.read(buf)
        buf.readBytes(ByteArray(AFTER_NAME.size))
        val playerId = S64LE.read(buf)
        buf.readBytes(ByteArray(APPEARANCE.size))
        buf.readBytes(ByteArray(SIDE_HEADER.size))
        val playerCount = U8.read(buf)
        buf.readBytes(ByteArray(SIDE_MID.size))
        val party = List(playerCount) { readMon(buf) }
        buf.readBytes(ByteArray(ACTIVE_DETAIL.size))
        buf.readBytes(ByteArray(SIDE_HEADER.size))
        U8.read(buf) // opponent count
        buf.readBytes(ByteArray(SIDE_MID.size))
        val wild = readWild(buf)
        return BattleFieldStatePacket(playerName, playerId, party, wild)
      }

      override fun write(buf: WriteBuffer, value: BattleFieldStatePacket) {
        buf.writeBytes(PREAMBLE)
        Utf16LeNullTerminated.write(buf, value.playerName)
        buf.writeBytes(AFTER_NAME)
        S64LE.write(buf, value.playerId)
        buf.writeBytes(APPEARANCE)
        buf.writeBytes(SIDE_HEADER)
        U8.write(buf, value.playerParty.size)
        buf.writeBytes(SIDE_MID)
        value.playerParty.forEachIndexed { slot, mon -> writeMon(buf, slot, mon) }
        buf.writeBytes(ACTIVE_DETAIL)
        buf.writeBytes(SIDE_HEADER)
        U8.write(buf, WILD_COUNT)
        buf.writeBytes(SIDE_MID)
        writeWild(buf, value.wildMon)
      }

      private fun readMon(buf: ReadBuffer): BattleFieldMon {
        S8.read(buf) // slot index
        buf.readBytes(ByteArray(MON_CONST.size))
        val entityId = S64LE.read(buf)
        val species = S16LE.read(buf)
        val block = speciesBlock(species)
        buf.readBytes(ByteArray(block.statPrefix.size))
        S8.read(buf) // max hp
        S8.read(buf) // stat tail
        val hp = S16LE.read(buf)
        buf.readBytes(ByteArray(block.moveBody.size))
        S8.read(buf) // slot trailer
        return BattleFieldMon(entityId, species, hp)
      }

      private fun writeMon(buf: WriteBuffer, slot: Int, mon: BattleFieldMon) {
        S8.write(buf, slot.toByte())
        buf.writeBytes(MON_CONST)
        S64LE.write(buf, mon.entityId)
        S16LE.write(buf, mon.species)
        val block = speciesBlock(mon.species)
        buf.writeBytes(block.statPrefix)
        S8.write(buf, mon.hp.toByte()) // max hp
        S8.write(buf, 0)
        S16LE.write(buf, mon.hp)
        buf.writeBytes(block.moveBody)
        S8.write(buf, slot.toByte()) // slot trailer
      }

      private fun readWild(buf: ReadBuffer): BattleFieldMon {
        S8.read(buf) // slot index
        buf.readBytes(ByteArray(MON_CONST.size))
        val entityId = S64LE.read(buf)
        val species = S16LE.read(buf)
        buf.readBytes(ByteArray(WILD_DATA_A.size))
        val hp = S16LE.read(buf)
        buf.readBytes(ByteArray(WILD_DATA_B.size))
        return BattleFieldMon(entityId, species, hp)
      }

      private fun writeWild(buf: WriteBuffer, mon: BattleFieldMon) {
        S8.write(buf, 0)
        buf.writeBytes(MON_CONST)
        S64LE.write(buf, mon.entityId)
        S16LE.write(buf, mon.species)
        buf.writeBytes(WILD_DATA_A)
        S16LE.write(buf, mon.hp)
        buf.writeBytes(WILD_DATA_B)
      }
    }

object BattleFieldStatePacketCodec : PacketCodec<BattleFieldStatePacket>() {
  override fun CodecScope<BattleFieldStatePacket>.body(): BattleFieldStatePacket {
    return field(BattleFieldStateBody) { it }
  }
}
