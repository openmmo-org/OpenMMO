package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class NamedEntityChoice(val entityId: Long, val name: String)

data class EntityNameChoicesPacket(
    val kind: Byte,
    val flag: Boolean,
    val choices: List<NamedEntityChoice>,
)

private val NamedEntityChoiceCodec: Codec<NamedEntityChoice> =
    object : PacketCodec<NamedEntityChoice>() {
      override fun CodecScope<NamedEntityChoice>.body(): NamedEntityChoice {
        val entityId = field(S64LE) { it.entityId }
        val name = field(Utf16LeNullTerminated) { it.name }
        return NamedEntityChoice(entityId, name)
      }
    }

object EntityNameChoicesPacketCodec : PacketCodec<EntityNameChoicesPacket>() {
  override fun CodecScope<EntityNameChoicesPacket>.body(): EntityNameChoicesPacket {
    val kind = field(S8) { it.kind }
    val flag = field(U8) { if (it.flag) 1 else 0 } == 1
    val choices = field(NamedEntityChoiceCodec.listPrefixed(U8)) { it.choices }
    return EntityNameChoicesPacket(kind, flag, choices)
  }
}
