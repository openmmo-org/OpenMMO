package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8

data class DialogChoicePacket(val unk1: Int, val unk2: Int)

object DialogChoicePacketCodec : PacketCodec<DialogChoicePacket>() {
  override fun CodecScope<DialogChoicePacket>.body(): DialogChoicePacket {
    val unk1 = field(U8) { it.unk1 }
    val unk2 = field(U16LE) { it.unk2 }
    return DialogChoicePacket(unk1, unk2)
  }
}
