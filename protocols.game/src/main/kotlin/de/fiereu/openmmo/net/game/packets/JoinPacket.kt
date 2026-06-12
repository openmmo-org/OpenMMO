package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.enums.Arch
import de.fiereu.openmmo.common.enums.Bitness
import de.fiereu.openmmo.common.enums.Platform

data class RomInfo(val code: String, val id: Byte, val type: Byte)

sealed class GameAuthenticationData

data class ReconnectAuthData(val sessionId: Long, val sessionKey: ByteArray) :
    GameAuthenticationData() {
    override fun equals(other: Any?): Boolean =
        other is ReconnectAuthData &&
                sessionId == other.sessionId &&
                sessionKey.contentEquals(other.sessionKey)

    override fun hashCode(): Int = sessionId.hashCode() * 31 + sessionKey.contentHashCode()
}

data class NewAuthData(val userId: Int, val sessionKey: ByteArray) : GameAuthenticationData() {
    override fun equals(other: Any?): Boolean =
        other is NewAuthData && userId == other.userId && sessionKey.contentEquals(other.sessionKey)

    override fun hashCode(): Int = userId * 31 + sessionKey.contentHashCode()
}

data class JoinPacket(
    val authData: GameAuthenticationData,
    val mac: ByteArray,
    val clientRevision: Int,
    val installationRevision: Int,
    val currentChatLanguage: Byte,
    val chatLanguages: Short,
    val matchmakingLanguages: Short,
    val romMask: Byte,
    val roms: List<RomInfo>,
    val clientInfo: Map<Byte, String>,
    val platform: Platform,
    val arch: Arch,
    val bitness: Bitness,
    val unk1: ByteArray,
    val unk2: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is JoinPacket &&
                authData == other.authData &&
                mac.contentEquals(other.mac) &&
                clientRevision == other.clientRevision &&
                installationRevision == other.installationRevision &&
                currentChatLanguage == other.currentChatLanguage &&
                chatLanguages == other.chatLanguages &&
                matchmakingLanguages == other.matchmakingLanguages &&
                romMask == other.romMask &&
                roms == other.roms &&
                clientInfo == other.clientInfo &&
                platform == other.platform &&
                arch == other.arch &&
                bitness == other.bitness &&
                unk1.contentEquals(other.unk1) &&
                unk2.contentEquals(other.unk2)

    override fun hashCode(): Int {
        var r = authData.hashCode()
        r = r * 31 + mac.contentHashCode()
        r = r * 31 + clientRevision
        r = r * 31 + installationRevision
        r = r * 31 + currentChatLanguage
        r = r * 31 + chatLanguages
        r = r * 31 + matchmakingLanguages
        r = r * 31 + romMask
        r = r * 31 + roms.hashCode()
        r = r * 31 + clientInfo.hashCode()
        r = r * 31 + platform.hashCode()
        r = r * 31 + arch.hashCode()
        r = r * 31 + bitness.hashCode()
        r = r * 31 + unk1.contentHashCode()
        r = r * 31 + unk2.contentHashCode()
        return r
    }
}

private val SessionKey = bytesPrefixed(U8)

private object NewAuthDataCodec : PacketCodec<NewAuthData>() {
    override fun CodecScope<NewAuthData>.body(): NewAuthData {
        val userId = field(S32LE) { it.userId }
        val sessionKey = field(SessionKey) { it.sessionKey }
        return NewAuthData(userId, sessionKey)
    }
}

private object ReconnectAuthDataCodec : PacketCodec<ReconnectAuthData>() {
    override fun CodecScope<ReconnectAuthData>.body(): ReconnectAuthData {
        val sessionId = field(S64LE) { it.sessionId }
        val sessionKey = field(SessionKey) { it.sessionKey }
        return ReconnectAuthData(sessionId, sessionKey)
    }
}

private val AuthDataCodec: Codec<GameAuthenticationData> =
    choose(
        tag = U8,
        identify = { data ->
            when (data) {
                is NewAuthData -> 0x00
                is ReconnectAuthData -> 0x01
            }
        },
        0x00 to NewAuthDataCodec,
        0x01 to ReconnectAuthDataCodec,
    )

private val PlatformCodec: Codec<Platform> =
    U8.imap(
        decode = { Platform.from(it) },
        encode = { p ->
            when (p) {
                Platform.WINDOWS -> 0x00
                Platform.LINUX -> 0x01
                Platform.MACOS -> 0x02
                Platform.IOS -> 0x03
                Platform.ANDROID -> 0x04
                Platform.UNKNOWN -> 0xFF
            }
        },
    )

private val ArchCodec: Codec<Arch> = enumByOrdinalByte<Arch>()
private val BitnessCodec: Codec<Bitness> = enumByOrdinalByte<Bitness>()

private val RomInfoCodec: Codec<RomInfo> =
    object : PacketCodec<RomInfo>() {
        override fun CodecScope<RomInfo>.body(): RomInfo {
            val code = field(Utf16LeNullTerminated) { it.code }
            val id = field(S8) { it.id }
            val type = field(S8) { it.type }
            return RomInfo(code, id, type)
        }
    }

private val ClientInfoEntryCodec: Codec<Pair<Byte, String>> = S8.then(Utf16LeNullTerminated)

private val ClientInfoMapCodec: Codec<Map<Byte, String>> =
    ClientInfoEntryCodec.listPrefixed(U8)
        .imap(decode = { it.toMap() }, encode = { it.entries.map { e -> e.key to e.value } })

private val MacBytes = fixedBytes(6)
private val Unk1Bytes = bytesPrefixed(U8)
private val Unk2Bytes = fixedBytes(32)

object JoinPacketCodec : PacketCodec<JoinPacket>() {
    override fun CodecScope<JoinPacket>.body(): JoinPacket {
        val authData = field(AuthDataCodec) { it.authData }
        val mac = field(MacBytes) { it.mac }
        val clientRevision = field(S32LE) { it.clientRevision }
        val installationRevision = field(S32LE) { it.installationRevision }
        val currentChatLanguage = field(S8) { it.currentChatLanguage }
        val chatLanguages = field(S16LE) { it.chatLanguages }
        val matchmakingLanguages = field(S16LE) { it.matchmakingLanguages }
        val romMask = field(S8) { it.romMask }
        val roms = field(RomInfoCodec.listPrefixed(U8)) { it.roms }
        val clientInfo = field(ClientInfoMapCodec) { it.clientInfo }
        val platform = field(PlatformCodec) { it.platform }
        val arch = field(ArchCodec) { it.arch }
        val bitness = field(BitnessCodec) { it.bitness }
        val unk1 = field(Unk1Bytes) { it.unk1 }
        val unk2 = field(Unk2Bytes) { it.unk2 }
        return JoinPacket(
            authData = authData,
            mac = mac,
            clientRevision = clientRevision,
            installationRevision = installationRevision,
            currentChatLanguage = currentChatLanguage,
            chatLanguages = chatLanguages,
            matchmakingLanguages = matchmakingLanguages,
            romMask = romMask,
            roms = roms,
            clientInfo = clientInfo,
            platform = platform,
            arch = arch,
            bitness = bitness,
            unk1 = unk1,
            unk2 = unk2,
        )
    }
}
