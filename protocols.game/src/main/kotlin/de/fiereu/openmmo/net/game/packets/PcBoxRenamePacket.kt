package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class PcBoxRenamePacket(
    val boxIndex: Byte,
    val newName: String,
)

object PcBoxRenamePacketCodec : PacketCodec<PcBoxRenamePacket>() {
    override fun CodecScope<PcBoxRenamePacket>.body(): PcBoxRenamePacket {
        val boxIndex = field(S8) { it.boxIndex }
        val newName = field(Utf16LeNullTerminated) { it.newName }
        return PcBoxRenamePacket(boxIndex, newName)
    }
}
