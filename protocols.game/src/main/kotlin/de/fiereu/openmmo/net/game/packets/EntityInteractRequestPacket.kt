package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityInteractRequestPacket(
    val interactionType: Byte,
    val primaryEntityId: Long,
    val primaryX: Short,
    val primaryY: Short,
    val secondaryEntityId: Long?,
    val secondaryX: Short?,
    val secondaryY: Short?,
)

object EntityInteractRequestPacketCodec : PacketCodec<EntityInteractRequestPacket>() {
  override fun CodecScope<EntityInteractRequestPacket>.body(): EntityInteractRequestPacket {
    val interactionType = field(S8) { it.interactionType }
    val hasSecondEntity = field(Bool) { it.secondaryEntityId != null }
    val primaryEntityId = field(S64LE) { it.primaryEntityId }
    val primaryX = field(S16LE) { it.primaryX }
    val primaryY = field(S16LE) { it.primaryY }
    val secondaryEntityId: Long? =
        if (hasSecondEntity) field(S64LE) { it.secondaryEntityId!! } else null
    val secondaryX: Short? = if (hasSecondEntity) field(S16LE) { it.secondaryX!! } else null
    val secondaryY: Short? = if (hasSecondEntity) field(S16LE) { it.secondaryY!! } else null
    return EntityInteractRequestPacket(
        interactionType,
        primaryEntityId,
        primaryX,
        primaryY,
        secondaryEntityId,
        secondaryX,
        secondaryY,
    )
  }
}
