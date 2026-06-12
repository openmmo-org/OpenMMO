package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class CreateCharacterPacket(val name: String)

object CreateCharacterPacketCodec : PacketCodec<CreateCharacterPacket>() {
    override fun CodecScope<CreateCharacterPacket>.body(): CreateCharacterPacket {
        val name = field(Utf16LeNullTerminated) { it.name }
        return CreateCharacterPacket(name)
    }
}
