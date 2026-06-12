package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class StorageBoxClosePacket(val placeholder: Unit = Unit)

object StorageBoxClosePacketCodec : PacketCodec<StorageBoxClosePacket>() {
    override fun CodecScope<StorageBoxClosePacket>.body(): StorageBoxClosePacket {
        return StorageBoxClosePacket()
    }
}
