package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class MfaChallengePacket(val unk: Byte, val email: String)

object MfaChallengePacketCodec : PacketCodec<MfaChallengePacket>() {
  override fun CodecScope<MfaChallengePacket>.body(): MfaChallengePacket {
    val unk = field(S8) { it.unk }
    val email = field(Utf16LeNullTerminated) { it.email }
    return MfaChallengePacket(unk, email)
  }
}
