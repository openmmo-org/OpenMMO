package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ObjectiveProgressPacket(
    val id: Byte,
    val value: Int,
    val count: Short,
)

object ObjectiveProgressPacketCodec : PacketCodec<ObjectiveProgressPacket>() {
    override fun CodecScope<ObjectiveProgressPacket>.body(): ObjectiveProgressPacket {
        val id = field(S8) { it.id }
        val value = field(S32LE) { it.value }
        val count = field(S16LE) { it.count }
        return ObjectiveProgressPacket(id, value, count)
    }
}
