package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.MalformedPacketException
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.bytesPrefixed
import de.fiereu.bytecodec.imap
import de.fiereu.openmmo.common.enums.Language

sealed interface LoginMethod

data class PasswordLogin(val password: String, val stayLoggedIn: Boolean) : LoginMethod

data class TokenLogin(val token: ByteArray) : LoginMethod {
  override fun equals(other: Any?): Boolean =
      other is TokenLogin && token.contentEquals(other.token)

  override fun hashCode(): Int = token.contentHashCode()
}

data class LoginRequestPacket(
    val username: String,
    val manualLogin: Boolean,
    val hwid: ByteArray,
    val method: LoginMethod,
    val language: Language,
    val clientRevision: Int,
    val installationRevision: Int,
    val os: UByte,
    val hardwareInfoCache: ByteArray,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is LoginRequestPacket) return false
    return username == other.username &&
        manualLogin == other.manualLogin &&
        hwid.contentEquals(other.hwid) &&
        method == other.method &&
        language == other.language &&
        clientRevision == other.clientRevision &&
        installationRevision == other.installationRevision &&
        os == other.os &&
        hardwareInfoCache.contentEquals(other.hardwareInfoCache)
  }

  override fun hashCode(): Int {
    var result = username.hashCode()
    result = 31 * result + manualLogin.hashCode()
    result = 31 * result + hwid.contentHashCode()
    result = 31 * result + method.hashCode()
    result = 31 * result + language.hashCode()
    result = 31 * result + clientRevision
    result = 31 * result + installationRevision
    result = 31 * result + os.hashCode()
    result = 31 * result + hardwareInfoCache.contentHashCode()
    return result
  }
}

private val LanguageCodec =
    Utf16LeNullTerminated.imap(
        decode = { Language.fromCode(it) },
        encode = { it.code },
    )

private val LengthPrefixedBytes = bytesPrefixed(U8)

object LoginRequestPacketCodec : PacketCodec<LoginRequestPacket>() {
  override fun CodecScope<LoginRequestPacket>.body(): LoginRequestPacket {
    val username = field(Utf16LeNullTerminated) { it.username }
    val manualLogin = field(Bool) { it.manualLogin }
    val hwid = field(LengthPrefixedBytes) { it.hwid }
    val methodTag =
        field(U8) {
          when (it.method) {
            is PasswordLogin -> 0
            is TokenLogin -> 1
          }
        }
    val method: LoginMethod =
        when (methodTag) {
          0 -> {
            val password = field(Utf16LeNullTerminated) { (it.method as PasswordLogin).password }
            val stayLoggedIn = field(Bool) { (it.method as PasswordLogin).stayLoggedIn }
            PasswordLogin(password, stayLoggedIn)
          }
          1 -> {
            val token = field(LengthPrefixedBytes) { (it.method as TokenLogin).token }
            TokenLogin(token)
          }
          else -> throw MalformedPacketException("Unknown login method type: $methodTag")
        }
    val language = field(LanguageCodec) { it.language }
    val clientRevision = field(S32LE) { it.clientRevision }
    val installationRevision = field(S32LE) { it.installationRevision }
    val os = field(U8) { it.os.toInt() }.toUByte()
    val hardwareInfoCache = field(LengthPrefixedBytes) { it.hardwareInfoCache }
    return LoginRequestPacket(
        username,
        manualLogin,
        hwid,
        method,
        language,
        clientRevision,
        installationRevision,
        os,
        hardwareInfoCache,
    )
  }
}
