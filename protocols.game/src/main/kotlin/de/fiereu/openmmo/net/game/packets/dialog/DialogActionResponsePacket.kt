package de.fiereu.openmmo.net.game.packets.dialog

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U8

data class DialogActionResponsePacket(val id: Int, val unk: Int)

object DialogActionResponsePacketCodec : PacketCodec<DialogActionResponsePacket>() {
  override fun CodecScope<DialogActionResponsePacket>.body(): DialogActionResponsePacket {
    val id = field(U8) { it.id }
    val unk = field(U8) { it.unk }
    return DialogActionResponsePacket(id, unk)
  }
}
