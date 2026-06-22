package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class LearnedMoveAppearance(
    val name: String,
    val gender: Byte,
    val formId: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

data class LearnedMoveSlot(
    val moveId: Short,
    val sourceId: Long?,
    val appearance: LearnedMoveAppearance?,
)

data class LearnedMovesetPacket(
    val moves: List<LearnedMoveSlot>,
)

private object LearnedMoveAppearanceCodec : PacketCodec<LearnedMoveAppearance>() {
  override fun CodecScope<LearnedMoveAppearance>.body(): LearnedMoveAppearance {
    val name = field(Utf16LeNullTerminated, LearnedMoveAppearance::name)
    val gender = field(S8, LearnedMoveAppearance::gender)
    val formId = field(S32LE, LearnedMoveAppearance::formId)
    val kind = field(S8, LearnedMoveAppearance::kind)
    val palettePack = field(S8, LearnedMoveAppearance::palettePack)
    val slots = field(S16LE.repeat(4), LearnedMoveAppearance::slots)
    return LearnedMoveAppearance(name, gender, formId, kind, palettePack, slots)
  }
}

private object LearnedMoveSlotCodec : PacketCodec<LearnedMoveSlot>() {
  override fun CodecScope<LearnedMoveSlot>.body(): LearnedMoveSlot {
    val moveId = field(S16LE, LearnedMoveSlot::moveId)
    if (moveId < 0) {
      return LearnedMoveSlot(moveId, null, null)
    }
    val sourceId = field(S64LE) { it.sourceId!! }
    val appearance = field(LearnedMoveAppearanceCodec) { it.appearance!! }
    return LearnedMoveSlot(moveId, sourceId, appearance)
  }
}

private val LearnedMoveSlotListPrefixedU8: Codec<List<LearnedMoveSlot>> =
    object : Codec<List<LearnedMoveSlot>> {
      override fun read(buf: ReadBuffer): List<LearnedMoveSlot> {
        val n = U8.read(buf)
        return List(n) { LearnedMoveSlotCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<LearnedMoveSlot>) {
        U8.write(buf, value.size)
        value.forEach { LearnedMoveSlotCodec.write(buf, it) }
      }
    }

object LearnedMovesetPacketCodec : PacketCodec<LearnedMovesetPacket>() {
  override fun CodecScope<LearnedMovesetPacket>.body(): LearnedMovesetPacket {
    val moves = field(LearnedMoveSlotListPrefixedU8, LearnedMovesetPacket::moves)
    return LearnedMovesetPacket(moves)
  }
}
