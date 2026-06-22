package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class EntityRenamePacket(
    val entityId: Long,
    val name: String,
)

object EntityRenamePacketCodec : PacketCodec<EntityRenamePacket>() {
    override fun CodecScope<EntityRenamePacket>.body(): EntityRenamePacket {
        val entityId = field(S64LE) { it.entityId }
        val name = field(Utf16LeNullTerminated) { it.name }
        return EntityRenamePacket(entityId, name)
    }
}
