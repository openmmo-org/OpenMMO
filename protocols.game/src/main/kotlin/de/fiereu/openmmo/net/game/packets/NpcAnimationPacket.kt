package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8

data class NpcAnimationPacket(val entityId: Long, val animation: Int)

object NpcAnimationPacketCodec : PacketCodec<NpcAnimationPacket>() {
    override fun CodecScope<NpcAnimationPacket>.body(): NpcAnimationPacket {
        val entityId = field(S64LE) { it.entityId }
        val animation = field(U8) { it.animation }
        return NpcAnimationPacket(entityId, animation)
    }
}
