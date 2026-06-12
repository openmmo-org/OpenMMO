package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GmCharacterSession(
    val entityId: Long,
    val name: String,
    val secondaryName: String,
    val intA: Int,
    val byteA: Byte,
    val intB: Int,
    val longA: Long,
    val intC: Int,
    val intD: Int,
    val intE: Int,
    val shortA: Short,
    val intF: Int,
    val byteB: Byte,
    val byteC: Byte,
    val byteD: Byte,
    val shortB: Short,
    val byteE: Byte,
    val intG: Int,
    val byteF: Byte,
    val byteG: Byte,
    val byteH: Byte,
    val byteI: Byte,
    val byteJ: Byte,
    val shortC: Short,
    val shortD: Short,
    val shortE: Short,
    val statusByte: Byte,
    val shortF: Short,
    val shortG: Short,
    val statusList: List<Byte>,
)

private val GmCharacterSessionCodec: Codec<GmCharacterSession> =
    object : PacketCodec<GmCharacterSession>() {
        override fun CodecScope<GmCharacterSession>.body(): GmCharacterSession {
            val entityId = field(S64LE) { it.entityId }
            val name = field(Utf16LeNullTerminated) { it.name }
            val secondaryName = field(Utf16LeNullTerminated) { it.secondaryName }
            val intA = field(S32LE) { it.intA }
            val byteA = field(S8) { it.byteA }
            val intB = field(S32LE) { it.intB }
            val longA = field(S64LE) { it.longA }
            val intC = field(S32LE) { it.intC }
            field(S32LE) { 0 }
            field(S8) { 0 }
            val intD = field(S32LE) { it.intD }
            val intE = field(S32LE) { it.intE }
            val shortA = field(S16LE) { it.shortA }
            val intF = field(S32LE) { it.intF }
            val byteB = field(S8) { it.byteB }
            val byteC = field(S8) { it.byteC }
            field(S8) { 0 }
            val byteD = field(S8) { it.byteD }
            field(S32LE) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            val shortB = field(S16LE) { it.shortB }
            val byteE = field(S8) { it.byteE }
            val intG = field(S32LE) { it.intG }
            val byteF = field(S8) { it.byteF }
            val byteG = field(S8) { it.byteG }
            val byteH = field(S8) { it.byteH }
            field(S8) { 0 }
            field(S8) { 0 }
            field(S8) { 0 }
            val byteI = field(S8) { it.byteI }
            val byteJ = field(S8) { it.byteJ }
            field(S8) { 0 }
            val shortC = field(S16LE) { it.shortC }
            val shortD = field(S16LE) { it.shortD }
            field(S8) { 0 }
            field(S8) { 0 }
            val shortE = field(S16LE) { it.shortE }
            val statusByte = field(S8) { it.statusByte }
            val shortF = field(S16LE) { it.shortF }
            val shortG = field(S16LE) { it.shortG }
            val statusCount = field(U8) { it.statusList.size }
            val statusList = (0 until statusCount).map { i -> field(S8) { it.statusList[i] } }
            return GmCharacterSession(
                entityId, name, secondaryName, intA, byteA, intB, longA, intC, intD, intE, shortA, intF,
                byteB, byteC, byteD, shortB, byteE, intG, byteF, byteG, byteH, byteI, byteJ, shortC,
                shortD, shortE, statusByte, shortF, shortG, statusList
            )
        }
    }

data class GmCharacterListing(
    val entityId: Long,
    val value: Int,
    val name: String,
)

private val GmCharacterListingCodec: Codec<GmCharacterListing> =
    object : PacketCodec<GmCharacterListing>() {
        override fun CodecScope<GmCharacterListing>.body(): GmCharacterListing {
            val entityId = field(S64LE) { it.entityId }
            val value = field(S32LE) { it.value }
            val name = field(Utf16LeNullTerminated) { it.name }
            return GmCharacterListing(entityId, value, name)
        }
    }

data class GmAccountInfo(
    val rankExtraA: Byte,
    val rankExtraB: Byte,
    val rankExtraC: Byte,
    val ipAddress: String,
    val playtime: Long,
    val accountId: Int,
    val stringA: String,
    val stringB: String,
    val stringC: String,
)

data class GmExtraDetail(
    val value: Int,
    val text: String,
)

data class GmPlayerLookupPacket(
    val found: Boolean,
    val session: GmCharacterSession?,
    val rank: Byte,
    val account: GmAccountInfo?,
    val extra: GmExtraDetail?,
    val characters: List<GmCharacterListing>,
)

object GmPlayerLookupPacketCodec : PacketCodec<GmPlayerLookupPacket>() {
    override fun CodecScope<GmPlayerLookupPacket>.body(): GmPlayerLookupPacket {
        val foundByte = field(U8) { if (it.found) 1 else 0 }
        if (foundByte != 1) {
            return GmPlayerLookupPacket(false, null, 0, null, null, emptyList())
        }
        val session = field(GmCharacterSessionCodec) { it.session!! }
        val rank = field(S8) { it.rank }
        val account =
            if (rank > 0) {
                val rankExtraA = field(S8) { it.account!!.rankExtraA }
                val rankExtraB = field(S8) { it.account!!.rankExtraB }
                val rankExtraC = field(S8) { it.account!!.rankExtraC }
                val ipAddress = field(Utf16LeNullTerminated) { it.account!!.ipAddress }
                val playtime = field(S64LE) { it.account!!.playtime }
                val accountId = field(S32LE) { it.account!!.accountId }
                val stringA = field(Utf16LeNullTerminated) { it.account!!.stringA }
                val stringB = field(Utf16LeNullTerminated) { it.account!!.stringB }
                val stringC = field(Utf16LeNullTerminated) { it.account!!.stringC }
                GmAccountInfo(
                    rankExtraA, rankExtraB, rankExtraC, ipAddress, playtime, accountId, stringA, stringB,
                    stringC
                )
            } else null
        val extraByte = field(U8) { if (it.extra != null) 1 else 0 }
        val extra =
            if (extraByte == 1) {
                val value = field(S32LE) { it.extra!!.value }
                val text = field(Utf16LeNullTerminated) { it.extra!!.text }
                GmExtraDetail(value, text)
            } else null
        val charCount = field(U16LE) { it.characters.size }
        val characters = (0 until charCount).map { i -> field(GmCharacterListingCodec) { it.characters[i] } }
        return GmPlayerLookupPacket(true, session, rank, account, extra, characters)
    }
}
