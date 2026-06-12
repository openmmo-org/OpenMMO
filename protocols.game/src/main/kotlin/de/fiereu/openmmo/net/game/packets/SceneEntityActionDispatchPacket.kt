package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class SceneEntityActionDispatchPacket(
    val entityId: Long,
    val action: Byte,
)

object SceneEntityActionDispatchPacketCodec : PacketCodec<SceneEntityActionDispatchPacket>() {
    override fun CodecScope<SceneEntityActionDispatchPacket>.body(): SceneEntityActionDispatchPacket {
        val entityId = field(S64LE) { it.entityId }
        val action = field(S8) { it.action }
        return SceneEntityActionDispatchPacket(entityId, action)
    }
}
