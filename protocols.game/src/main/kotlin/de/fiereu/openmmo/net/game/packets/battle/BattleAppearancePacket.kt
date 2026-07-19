package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleAppearancePacket(
    val partySlot: Short,
    val pokemonEntityId: Long,
    val shininessType: Byte,
    val colorPalette: List<Short>,
)

private val ColorPaletteCodec: Codec<List<Short>> =
    object : Codec<List<Short>> {
      override fun read(buf: ReadBuffer): List<Short> {
        val list = ArrayList<Short>()
        while (buf.remaining() >= 2) {
          list.add(S16LE.read(buf))
        }
        return list
      }

      override fun write(buf: WriteBuffer, value: List<Short>) {
        for (entry in value) S16LE.write(buf, entry)
      }
    }

object BattleAppearancePacketCodec : PacketCodec<BattleAppearancePacket>() {
  override fun CodecScope<BattleAppearancePacket>.body(): BattleAppearancePacket {
    val partySlot = field(S16LE) { it.partySlot }
    val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
    val shininessType = field(S8) { it.shininessType }
    val colorPalette = field(ColorPaletteCodec) { it.colorPalette }
    return BattleAppearancePacket(partySlot, pokemonEntityId, shininessType, colorPalette)
  }
}
