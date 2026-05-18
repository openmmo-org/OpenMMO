package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class TokenPayloadPacket(val payload: ByteArray)

class TokenPayloadSerializer : PacketSerializer<TokenPayloadPacket> {
  override fun serialize(packet: TokenPayloadPacket, buffer: ByteBuf) {
    buffer.writeBytes(packet.payload)
  }
}

class TokenPayloadDeserializer : PacketDeserializer<TokenPayloadPacket> {
  override fun deserialize(buffer: ByteBuf): TokenPayloadPacket {
    val data = ByteArray(buffer.readableBytes())
    buffer.readBytes(data)
    return TokenPayloadPacket(data)
  }
}
