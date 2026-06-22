package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

sealed class HelloAuthData

data class ReconnectHelloAuth(val entityId: Long, val sessionToken: ByteArray) : HelloAuthData() {
    override fun equals(other: Any?): Boolean =
        other is ReconnectHelloAuth &&
                entityId == other.entityId &&
                sessionToken.contentEquals(other.sessionToken)

    override fun hashCode(): Int = entityId.hashCode() * 31 + sessionToken.contentHashCode()
}

data class NewHelloAuth(val userId: Int, val sessionToken: ByteArray) : HelloAuthData() {
    override fun equals(other: Any?): Boolean =
        other is NewHelloAuth && userId == other.userId && sessionToken.contentEquals(other.sessionToken)

    override fun hashCode(): Int = userId * 31 + sessionToken.contentHashCode()
}

data class LanguagePackEntry(val name: String, val version: Byte, val kind: Byte)

data class GraphicsModeEntry(val id: Byte, val name: String)

data class GameHandshakeHelloPacket(
    val authData: HelloAuthData,
    val localIp: ByteArray,
    val magic: Int,
    val clientRevision: Int,
    val languageId: Byte,
    val chatChannelMask: Short,
    val languageFilterMask: Short,
    val languagePackMask: Byte,
    val languagePacks: List<LanguagePackEntry>,
    val graphicsModes: List<GraphicsModeEntry>,
    val mapDataState: Byte,
    val platformOrdinal: Byte,
    val osOrdinal: Byte,
    val capabilityFlagA: Byte,
    val capabilityFlagB: Byte,
) {
    override fun equals(other: Any?): Boolean =
        other is GameHandshakeHelloPacket &&
                authData == other.authData &&
                localIp.contentEquals(other.localIp) &&
                magic == other.magic &&
                clientRevision == other.clientRevision &&
                languageId == other.languageId &&
                chatChannelMask == other.chatChannelMask &&
                languageFilterMask == other.languageFilterMask &&
                languagePackMask == other.languagePackMask &&
                languagePacks == other.languagePacks &&
                graphicsModes == other.graphicsModes &&
                mapDataState == other.mapDataState &&
                platformOrdinal == other.platformOrdinal &&
                osOrdinal == other.osOrdinal &&
                capabilityFlagA == other.capabilityFlagA &&
                capabilityFlagB == other.capabilityFlagB

    override fun hashCode(): Int {
        var r = authData.hashCode()
        r = r * 31 + localIp.contentHashCode()
        r = r * 31 + magic
        r = r * 31 + clientRevision
        r = r * 31 + languageId
        r = r * 31 + chatChannelMask
        r = r * 31 + languageFilterMask
        r = r * 31 + languagePackMask
        r = r * 31 + languagePacks.hashCode()
        r = r * 31 + graphicsModes.hashCode()
        r = r * 31 + mapDataState
        r = r * 31 + platformOrdinal
        r = r * 31 + osOrdinal
        r = r * 31 + capabilityFlagA
        r = r * 31 + capabilityFlagB
        return r
    }
}

private val HelloToken = bytesPrefixed(U8)
private val HelloLocalIp = fixedBytes(4)

private object NewHelloAuthCodec : PacketCodec<NewHelloAuth>() {
    override fun CodecScope<NewHelloAuth>.body(): NewHelloAuth {
        val userId = field(S32LE) { it.userId }
        val sessionToken = field(HelloToken) { it.sessionToken }
        return NewHelloAuth(userId, sessionToken)
    }
}

private object ReconnectHelloAuthCodec : PacketCodec<ReconnectHelloAuth>() {
    override fun CodecScope<ReconnectHelloAuth>.body(): ReconnectHelloAuth {
        val entityId = field(S64LE) { it.entityId }
        val sessionToken = field(HelloToken) { it.sessionToken }
        return ReconnectHelloAuth(entityId, sessionToken)
    }
}

private val HelloAuthCodec: Codec<HelloAuthData> =
    choose(
        tag = U8,
        identify = { data ->
            when (data) {
                is NewHelloAuth -> 0x00
                is ReconnectHelloAuth -> 0x01
            }
        },
        0x00 to NewHelloAuthCodec,
        0x01 to ReconnectHelloAuthCodec,
    )

private val LanguagePackEntryCodec: Codec<LanguagePackEntry> =
    object : PacketCodec<LanguagePackEntry>() {
        override fun CodecScope<LanguagePackEntry>.body(): LanguagePackEntry {
            val name = field(Utf16LeNullTerminated) { it.name }
            val version = field(S8) { it.version }
            val kind = field(S8) { it.kind }
            return LanguagePackEntry(name, version, kind)
        }
    }

private val GraphicsModeEntryCodec: Codec<GraphicsModeEntry> =
    object : PacketCodec<GraphicsModeEntry>() {
        override fun CodecScope<GraphicsModeEntry>.body(): GraphicsModeEntry {
            val id = field(S8) { it.id }
            val name = field(Utf16LeNullTerminated) { it.name }
            return GraphicsModeEntry(id, name)
        }
    }

object GameHandshakeHelloPacketCodec : PacketCodec<GameHandshakeHelloPacket>() {
    override fun CodecScope<GameHandshakeHelloPacket>.body(): GameHandshakeHelloPacket {
        val authData = field(HelloAuthCodec) { it.authData }
        val localIp = field(HelloLocalIp) { it.localIp }
        val magic = field(S32LE) { it.magic }
        val clientRevision = field(S32LE) { it.clientRevision }
        val languageId = field(S8) { it.languageId }
        val chatChannelMask = field(S16LE) { it.chatChannelMask }
        val languageFilterMask = field(S16LE) { it.languageFilterMask }
        val languagePackMask = field(S8) { it.languagePackMask }
        val languagePacks = field(LanguagePackEntryCodec.listPrefixed(U8)) { it.languagePacks }
        val graphicsModes = field(GraphicsModeEntryCodec.listPrefixed(U8)) { it.graphicsModes }
        val mapDataState = field(S8) { it.mapDataState }
        val platformOrdinal = field(S8) { it.platformOrdinal }
        val osOrdinal = field(S8) { it.osOrdinal }
        val capabilityFlagA = field(S8) { it.capabilityFlagA }
        val capabilityFlagB = field(S8) { it.capabilityFlagB }
        return GameHandshakeHelloPacket(
            authData,
            localIp,
            magic,
            clientRevision,
            languageId,
            chatChannelMask,
            languageFilterMask,
            languagePackMask,
            languagePacks,
            graphicsModes,
            mapDataState,
            platformOrdinal,
            osOrdinal,
            capabilityFlagA,
            capabilityFlagB,
        )
    }
}
