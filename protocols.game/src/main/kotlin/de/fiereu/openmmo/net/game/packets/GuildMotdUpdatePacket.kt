package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class GuildMotdUpdatePacket(
    val motdText: String,
)

object GuildMotdUpdatePacketCodec : PacketCodec<GuildMotdUpdatePacket>() {
    override fun CodecScope<GuildMotdUpdatePacket>.body(): GuildMotdUpdatePacket {
        val motdText = field(Utf16LeNullTerminated) { it.motdText }
        return GuildMotdUpdatePacket(motdText)
    }
}
