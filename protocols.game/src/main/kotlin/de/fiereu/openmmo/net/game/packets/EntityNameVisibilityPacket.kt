package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class EntityNameVisibilityPacket(
    val entityId: Long,
    val hidden: Boolean,
    val gmMode: Boolean,
    val marked: Boolean,
)

object EntityNameVisibilityPacketCodec : PacketCodec<EntityNameVisibilityPacket>() {
  override fun CodecScope<EntityNameVisibilityPacket>.body(): EntityNameVisibilityPacket {
    val entityId = field(S64LE) { it.entityId }
    val hidden = field(Bool) { it.hidden }
    val gmMode = field(Bool) { it.gmMode }
    val marked = field(Bool) { it.marked }
    return EntityNameVisibilityPacket(entityId, hidden, gmMode, marked)
  }
}
