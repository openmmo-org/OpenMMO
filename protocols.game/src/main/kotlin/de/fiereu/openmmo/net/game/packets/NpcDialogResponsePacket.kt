package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class NpcDialogResponsePacket(
    val dialogType: Byte,
    val responseText: String,
)

object NpcDialogResponsePacketCodec : PacketCodec<NpcDialogResponsePacket>() {
    override fun CodecScope<NpcDialogResponsePacket>.body(): NpcDialogResponsePacket {
        val dialogType = field(S8) { it.dialogType }
        val responseText = field(Utf16LeNullTerminated) { it.responseText }
        return NpcDialogResponsePacket(dialogType, responseText)
    }
}
