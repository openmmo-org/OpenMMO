package de.fiereu.openmmo.net.login.packets

import com.github.maltalex.ineter.base.IPv4Address
import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.MalformedPacketException
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated

enum class KickReason {
  A,
  IP_RANGE,
  C,
}

data class LoginKickPacket(val reason: KickReason?)

object LoginKickPacketCodec : PacketCodec<LoginKickPacket>() {
  override fun CodecScope<LoginKickPacket>.body(): LoginKickPacket {
    val hasReason = field(Bool) { it.reason != null }
    if (!hasReason) return LoginKickPacket(null)
    val ordinal = field(U8) { it.reason!!.ordinal }
    val reason =
        KickReason.entries.getOrNull(ordinal)
            ?: throw MalformedPacketException("Unknown kick reason: $ordinal")
    when (reason) {
      KickReason.IP_RANGE -> {
        field(S32LE) { 0 }
        field(TaggedIpCodec) { IPv4Address.MIN_ADDR }
        field(TaggedIpCodec) { IPv4Address.MAX_ADDR }
        field(S32LE) { 0 }
        field(S32LE) { 0 }
        field(Utf16LeNullTerminated) { "" }
        field(Utf16LeNullTerminated) { "" }
        field(U8) { 0 }
      }
      KickReason.A,
      KickReason.C -> {
        field(S32LE) { 0 }
        field(S32LE) { 0 }
        field(S32LE) { 0 }
        field(S32LE) { 0 }
        field(Utf16LeNullTerminated) { "" }
        field(Utf16LeNullTerminated) { "" }
        field(U8) { 0 }
      }
    }
    return LoginKickPacket(reason)
  }
}
