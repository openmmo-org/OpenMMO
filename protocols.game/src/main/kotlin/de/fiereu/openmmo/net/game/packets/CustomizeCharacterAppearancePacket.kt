package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class CreatureMoveEntry(
    val packed: Short,
    val extra: Byte?,
)

data class CreatureMoveset(
    val headerByte: Byte,
    val moveMask: Short,
    val moves: List<CreatureMoveEntry>,
)

private val CreatureMovesetCodec: Codec<CreatureMoveset> =
    object : Codec<CreatureMoveset> {
      override fun read(buf: ReadBuffer): CreatureMoveset {
        val headerByte = S8.read(buf)
        val moveMask = S16LE.read(buf)
        val hasExtra = (moveMask.toInt() and 0x8000) != 0
        val moves = ArrayList<CreatureMoveEntry>()
        for (slot in 0 until 15) {
          if ((moveMask.toInt() and (1 shl slot)) != 0) {
            val packed = S16LE.read(buf)
            val extra = if (hasExtra) S8.read(buf) else null
            moves.add(CreatureMoveEntry(packed, extra))
          }
        }
        return CreatureMoveset(headerByte, moveMask, moves)
      }

      override fun write(buf: WriteBuffer, value: CreatureMoveset) {
        S8.write(buf, value.headerByte)
        S16LE.write(buf, value.moveMask)
        val hasExtra = (value.moveMask.toInt() and 0x8000) != 0
        for (move in value.moves) {
          S16LE.write(buf, move.packed)
          if (hasExtra && move.extra != null) S8.write(buf, move.extra)
        }
      }
    }

data class CustomizeCharacterAppearancePacket(
    val entityId: Long,
    val colorCategoryId: Byte,
    val colorSlot: Byte,
    val moveSet: CreatureMoveset,
    val paletteIndex: Byte,
)

object CustomizeCharacterAppearancePacketCodec : PacketCodec<CustomizeCharacterAppearancePacket>() {
  override fun CodecScope<CustomizeCharacterAppearancePacket>.body():
      CustomizeCharacterAppearancePacket {
    val entityId = field(S64LE) { it.entityId }
    val colorCategoryId = field(S8) { it.colorCategoryId }
    val colorSlot = field(S8) { it.colorSlot }
    val moveSet = field(CreatureMovesetCodec) { it.moveSet }
    val paletteIndex = field(S8) { it.paletteIndex }
    return CustomizeCharacterAppearancePacket(
        entityId, colorCategoryId, colorSlot, moveSet, paletteIndex)
  }
}
