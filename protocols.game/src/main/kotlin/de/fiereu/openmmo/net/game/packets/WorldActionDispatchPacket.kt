package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldActionDispatchPacket(
    val action: Byte,
    val subject: Byte,
    val args: List<Short>,
)

object WorldActionDispatchPacketCodec : PacketCodec<WorldActionDispatchPacket>() {
    override fun CodecScope<WorldActionDispatchPacket>.body(): WorldActionDispatchPacket {
        val action = field(S8) { it.action }
        val subject = field(S8) { it.subject }
        val args = field(S16LE.listPrefixed(U8)) { it.args }
        return WorldActionDispatchPacket(action, subject, args)
    }
}
