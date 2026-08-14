package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S8

data class DialogOptionPacket(
    val unk1: Int,
    val unk2: Int,
    val unk3: Int,
    val unk4: Byte,
    val unk5: Byte,
)

object DialogOptionPacketCodec : PacketCodec<DialogOptionPacket>() {
  override fun CodecScope<DialogOptionPacket>.body(): DialogOptionPacket {
    val unk1 = field(S32LE) { it.unk1 }
    val unk2 = field(S32LE) { it.unk2 }
    val unk3 = field(S32LE) { it.unk3 }
    val unk4 = field(S8) { it.unk4 }
    val unk5 = field(S8) { it.unk5 }
    return DialogOptionPacket(unk1, unk2, unk3, unk4, unk5)
  }
}
