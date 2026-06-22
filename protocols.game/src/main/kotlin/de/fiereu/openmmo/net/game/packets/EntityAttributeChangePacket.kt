package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

const val ENTITY_ATTRIBUTE_TYPE_NAME: Int = 0
const val ENTITY_ATTRIBUTE_TYPE_ITEM: Int = 1
const val ENTITY_ATTRIBUTE_TYPE_INT_TEXT_A: Int = 2
const val ENTITY_ATTRIBUTE_TYPE_INT_TEXT_B: Int = 3

data class EntityAttributeChangePacket(
    val entityId: Long,
    val type: Byte,
    val text: String?,
    val item: Byte?,
    val value: Int?,
)

object EntityAttributeChangePacketCodec : PacketCodec<EntityAttributeChangePacket>() {
  override fun CodecScope<EntityAttributeChangePacket>.body(): EntityAttributeChangePacket {
    val entityId = field(S64LE) { it.entityId }
    val type = field(S8) { it.type }
    var text: String? = null
    var item: Byte? = null
    var value: Int? = null
    when (type.toInt()) {
      ENTITY_ATTRIBUTE_TYPE_NAME -> {
        text = field(Utf16LeNullTerminated) { it.text!! }
      }

      ENTITY_ATTRIBUTE_TYPE_ITEM -> {
        item = field(S8) { it.item!! }
      }

      ENTITY_ATTRIBUTE_TYPE_INT_TEXT_A,
      ENTITY_ATTRIBUTE_TYPE_INT_TEXT_B -> {
        value = field(S32LE) { it.value!! }
        text = field(Utf16LeNullTerminated) { it.text!! }
      }
    }
    return EntityAttributeChangePacket(entityId, type, text, item, value)
  }
}
