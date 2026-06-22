package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityAppearance(
    val name: String,
    val gender: Byte,
    val formId: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

private val EntityAppearanceCodec: Codec<EntityAppearance> =
    object : PacketCodec<EntityAppearance>() {
      override fun CodecScope<EntityAppearance>.body(): EntityAppearance {
        val name = field(Utf16LeNullTerminated) { it.name }
        val gender = field(S8) { it.gender }
        val formId = field(S32LE) { it.formId }
        val kind = field(S8) { it.kind }
        val palettePack = field(S8) { it.palettePack }
        val slots = field(S16LE.repeat(4)) { it.slots }
        return EntityAppearance(name, gender, formId, kind, palettePack, slots)
      }
    }

data class EntityCosmetic(
    val cosmeticId: Short,
    val cosmeticValue: Byte,
)

data class EntityAppearancePatchPacket(
    val entityId: Long,
    val spriteId: Short,
    val direction: Byte?,
    val animation: Short?,
    val appearance: EntityAppearance?,
    val cosmetics: List<EntityCosmetic>?,
)

object EntityAppearancePatchPacketCodec : PacketCodec<EntityAppearancePatchPacket>() {
  override fun CodecScope<EntityAppearancePatchPacket>.body(): EntityAppearancePatchPacket {
    val entityId = field(S64LE) { it.entityId }
    val spriteId = field(S16LE) { it.spriteId }
    val flags =
        field(S8) { v ->
          ((if (v.direction != null) 1 else 0) or
                  (if (v.animation != null) 2 else 0) or
                  (if (v.appearance != null) 4 else 0) or
                  (if (v.cosmetics != null) 8 else 0))
              .toByte()
        }
    val flagsInt = flags.toInt()
    val direction = if (flagsInt and 1 != 0) field(S8) { it.direction!! } else null
    val animation = if (flagsInt and 2 != 0) field(S16LE) { it.animation!! } else null
    val appearance =
        if (flagsInt and 4 != 0) field(EntityAppearanceCodec) { it.appearance!! } else null
    val cosmetics =
        if (flagsInt and 8 != 0) {
          val count = field(S8) { it.cosmetics!!.size.toByte() }.toInt()
          (0 until count).map { i ->
            val cosmeticId = field(S16LE) { it.cosmetics!![i].cosmeticId }
            val cosmeticValue = field(S8) { it.cosmetics!![i].cosmeticValue }
            EntityCosmetic(cosmeticId, cosmeticValue)
          }
        } else null
    return EntityAppearancePatchPacket(
        entityId, spriteId, direction, animation, appearance, cosmetics)
  }
}
