package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class DialogChoicePacket(
    val unk1: Int,
    val unk2: Int,
)

class DialogChoiceSerializer : PacketSerializer<DialogChoicePacket> {
  override fun serialize(packet: DialogChoicePacket, buffer: ByteBuf) {
    buffer.writeByte(packet.unk1)
    buffer.writeShortLE(packet.unk2)
  }
}

class DialogChoiceDeserializer : PacketDeserializer<DialogChoicePacket> {
  override fun deserialize(buffer: ByteBuf): DialogChoicePacket {
    val unk1 = buffer.readByte().toInt()
    val unk2 = buffer.readShortLE().toInt()
    return DialogChoicePacket(unk1, unk2)
  }
}
