package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.readUtf16LE
import io.netty.buffer.ByteBuf

data class CreateCharacterPacket(val name: String)

class CreateCharacterDeserializer : PacketDeserializer<CreateCharacterPacket> {
  override fun deserialize(buffer: ByteBuf): CreateCharacterPacket {
    val name = buffer.readUtf16LE()
    buffer.skipBytes(buffer.readableBytes())
    return CreateCharacterPacket(name)
  }
}
