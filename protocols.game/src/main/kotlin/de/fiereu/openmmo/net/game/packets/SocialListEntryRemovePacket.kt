package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class SocialListEntryRemovePacket(
    val listType: Byte,
    val entityId: Long,
)

object SocialListEntryRemovePacketCodec : PacketCodec<SocialListEntryRemovePacket>() {
    override fun CodecScope<SocialListEntryRemovePacket>.body(): SocialListEntryRemovePacket {
        val listType = field(S8) { it.listType }
        val entityId = field(S64LE) { it.entityId }
        return SocialListEntryRemovePacket(listType, entityId)
    }
}
