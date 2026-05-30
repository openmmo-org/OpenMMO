package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.bytesPrefixed
import de.fiereu.bytecodec.reserved

data class ExistingSessionPacket(
    val sessionId: Long,
    val sessionKey: ByteArray,
    val serverName: String,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ExistingSessionPacket) return false
    return sessionId == other.sessionId &&
        sessionKey.contentEquals(other.sessionKey) &&
        serverName == other.serverName
  }

  override fun hashCode(): Int {
    var result = sessionId.hashCode()
    result = 31 * result + sessionKey.contentHashCode()
    result = 31 * result + serverName.hashCode()
    return result
  }
}

private val SessionKeyCodec = bytesPrefixed(U8)

object ExistingSessionPacketCodec : PacketCodec<ExistingSessionPacket>() {
  override fun CodecScope<ExistingSessionPacket>.body(): ExistingSessionPacket {
    val sessionId = field(S64LE) { it.sessionId }
    val sessionKey = field(SessionKeyCodec) { it.sessionKey }
    reserved(byte = 0)
    val serverName = field(Utf16LeNullTerminated) { it.serverName }
    reserved(byte = 0)
    field(Utf16LeNullTerminated) { "" }
    field(S32LE) { 0 }
    field(S16LE) { 0 }
    field(S16LE) { 0 }
    field(Bool) { false }
    field(U8) { 0 }
    return ExistingSessionPacket(sessionId, sessionKey, serverName)
  }
}
