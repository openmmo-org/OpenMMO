package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class DuelInvitePacket(
    val flags: Byte,
    val requestType: Byte,
    val name: String,
)

object DuelInvitePacketCodec : PacketCodec<DuelInvitePacket>() {
    override fun CodecScope<DuelInvitePacket>.body(): DuelInvitePacket {
        val flags = field(S8) { it.flags }
        val requestType = field(S8) { it.requestType }
        val name = field(Utf16LeNullTerminated) { it.name }
        return DuelInvitePacket(flags, requestType, name)
    }
}
