package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import io.netty.buffer.ByteBuf

class NullPacketDeserializer : PacketDeserializer<Unit> {
  override fun deserialize(buffer: ByteBuf) {
    buffer.skipBytes(buffer.readableBytes())
  }
}
