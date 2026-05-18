package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

class MapTransitionPacket

class MapTransitionSerializer : PacketSerializer<MapTransitionPacket> {
  override fun serialize(packet: MapTransitionPacket, buffer: ByteBuf) {}
}

class MapTransitionDeserializer : PacketDeserializer<MapTransitionPacket> {
  override fun deserialize(buffer: ByteBuf): MapTransitionPacket = MapTransitionPacket()
}
