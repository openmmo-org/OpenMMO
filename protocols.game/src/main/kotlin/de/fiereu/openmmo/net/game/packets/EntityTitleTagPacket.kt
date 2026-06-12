package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class EntityTitleTagPacket(
    val entityId: Long,
    val nameTag: String,
)

object EntityTitleTagPacketCodec : PacketCodec<EntityTitleTagPacket>() {
    override fun CodecScope<EntityTitleTagPacket>.body(): EntityTitleTagPacket {
        val entityId = field(S64LE) { it.entityId }
        val nameTag = field(Utf16LeNullTerminated) { it.nameTag }
        return EntityTitleTagPacket(entityId, nameTag)
    }
}
