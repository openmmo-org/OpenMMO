package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class SetPlayerTitlePacket(val titleText: String)

object SetPlayerTitlePacketCodec : PacketCodec<SetPlayerTitlePacket>() {
    override fun CodecScope<SetPlayerTitlePacket>.body(): SetPlayerTitlePacket {
        val titleText = field(Utf16LeNullTerminated) { it.titleText }
        return SetPlayerTitlePacket(titleText)
    }
}
