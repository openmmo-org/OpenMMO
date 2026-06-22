package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class MonsterInspectRequestPacket(
    val entityId: Long,
    val contextType: Byte,
    val viewMode: Byte,
)

object MonsterInspectRequestPacketCodec : PacketCodec<MonsterInspectRequestPacket>() {
    override fun CodecScope<MonsterInspectRequestPacket>.body(): MonsterInspectRequestPacket {
        val entityId = field(S64LE) { it.entityId }
        val contextType = field(S8) { it.contextType }
        val viewMode = field(S8) { it.viewMode }
        return MonsterInspectRequestPacket(entityId, contextType, viewMode)
    }
}
