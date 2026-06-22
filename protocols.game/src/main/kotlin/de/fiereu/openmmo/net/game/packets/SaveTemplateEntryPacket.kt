package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SaveTemplateEntryPacket(
    val templateSlot: Byte,
    val name: String,
    val entryKindId: Byte,
    val data: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is SaveTemplateEntryPacket &&
          templateSlot == other.templateSlot &&
          name == other.name &&
          entryKindId == other.entryKindId &&
          data.contentEquals(other.data)

  override fun hashCode(): Int {
    var r = templateSlot.toInt()
    r = r * 31 + name.hashCode()
    r = r * 31 + entryKindId
    r = r * 31 + data.contentHashCode()
    return r
  }
}

object SaveTemplateEntryPacketCodec : PacketCodec<SaveTemplateEntryPacket>() {
  override fun CodecScope<SaveTemplateEntryPacket>.body(): SaveTemplateEntryPacket {
    val templateSlot = field(S8) { it.templateSlot }
    val name = field(Utf16LeNullTerminated) { it.name }
    val entryKindId = field(S8) { it.entryKindId }
    val data = field(bytesPrefixed(U8)) { it.data }
    return SaveTemplateEntryPacket(templateSlot, name, entryKindId, data)
  }
}
