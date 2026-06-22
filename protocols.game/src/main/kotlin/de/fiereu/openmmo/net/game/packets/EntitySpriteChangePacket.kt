package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.net.game.codecs.DefaultSkinSetCodec
import de.fiereu.openmmo.net.game.codecs.SkinSet

data class EntitySpriteChangePacket(
    val entityId: Long,
    val facingFront: Boolean,
    val appearance: SkinSet,
    val direction: Byte,
)

object EntitySpriteChangePacketCodec : PacketCodec<EntitySpriteChangePacket>() {
  override fun CodecScope<EntitySpriteChangePacket>.body(): EntitySpriteChangePacket {
    val entityId = field(S64LE) { it.entityId }
    val facingFront = field(Bool) { it.facingFront }
    val appearance = field(DefaultSkinSetCodec) { it.appearance }
    val direction = field(S8) { it.direction }
    return EntitySpriteChangePacket(entityId, facingFront, appearance, direction)
  }
}
