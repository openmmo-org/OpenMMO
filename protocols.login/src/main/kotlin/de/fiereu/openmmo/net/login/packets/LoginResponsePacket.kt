package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.MalformedPacketException
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.openmmo.common.enums.LoginState
import java.time.LocalDateTime
import java.time.ZoneOffset

data class LoginResponsePacket(val state: LoginState, val ratelimitEnd: LocalDateTime?) {
  constructor(state: LoginState) : this(state, null)
}

object LoginResponsePacketCodec : PacketCodec<LoginResponsePacket>() {
  override fun CodecScope<LoginResponsePacket>.body(): LoginResponsePacket {
    val stateId = field(U8) { it.state.id }
    val state = LoginState.entries.find { it.id == stateId } ?: LoginState.SYSTEM_ERROR
    var ratelimitEnd: LocalDateTime? = null
    if (state == LoginState.RATE_LIMITED || state == LoginState.RATE_LIMITED_2FA) {
      val epoch =
          field(S64LE) {
            it.ratelimitEnd?.toEpochSecond(ZoneOffset.UTC)
                ?: throw MalformedPacketException(
                    "ratelimitEnd must be provided for RATE_LIMITED states")
          }
      ratelimitEnd = LocalDateTime.ofEpochSecond(epoch, 0, ZoneOffset.UTC)
    }
    if (state == LoginState.AUTHED) {
      field(Utf16LeNullTerminated) { "" }
    }
    return LoginResponsePacket(state, ratelimitEnd)
  }
}
