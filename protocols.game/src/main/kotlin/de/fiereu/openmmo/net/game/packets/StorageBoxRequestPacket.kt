package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class StorageBoxRequestPacket

object StorageBoxRequestPacketCodec : PacketCodec<StorageBoxRequestPacket>() {
  override fun CodecScope<StorageBoxRequestPacket>.body(): StorageBoxRequestPacket {
    return StorageBoxRequestPacket()
  }
}
