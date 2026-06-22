package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntitySpriteAppearance(
    val name: String,
    val gender: Byte,
    val id: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

data class EntityAppearanceSyncPacket(
    val entityId: Long,
    val appearance: EntitySpriteAppearance,
)

private val EntitySpriteAppearanceCodec: Codec<EntitySpriteAppearance> =
    object : PacketCodec<EntitySpriteAppearance>() {
      override fun CodecScope<EntitySpriteAppearance>.body(): EntitySpriteAppearance {
        val name = field(de.fiereu.bytecodec.Utf16LeNullTerminated) { it.name }
        val gender = field(S8) { it.gender }
        val id = field(S32LE) { it.id }
        val kind = field(S8) { it.kind }
        val palettePack = field(S8) { it.palettePack }
        val slots = field(S16LE.repeat(4)) { it.slots }
        return EntitySpriteAppearance(name, gender, id, kind, palettePack, slots)
      }
    }

object EntityAppearanceSyncPacketCodec : PacketCodec<EntityAppearanceSyncPacket>() {
  override fun CodecScope<EntityAppearanceSyncPacket>.body(): EntityAppearanceSyncPacket {
    val entityId = field(S64LE) { it.entityId }
    val appearance = field(EntitySpriteAppearanceCodec) { it.appearance }
    return EntityAppearanceSyncPacket(entityId, appearance)
  }
}
