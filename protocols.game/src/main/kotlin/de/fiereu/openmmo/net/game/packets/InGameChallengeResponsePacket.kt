package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class InGameChallengeResponsePacket(val accepted: Boolean, val responseText: String)

object InGameChallengeResponsePacketCodec : PacketCodec<InGameChallengeResponsePacket>() {
  override fun CodecScope<InGameChallengeResponsePacket>.body(): InGameChallengeResponsePacket {
    val declined = field(U8) { if (it.accepted) 0 else 1 }
    val responseText = field(Utf16LeNullTerminated) { it.responseText }
    return InGameChallengeResponsePacket(declined == 0, responseText)
  }
}
