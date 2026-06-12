package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class StorageBoxFilterPacket(
    val listTypeIndex: Byte?,
    val boxName: String?,
    val cookieData: ByteArray?,
) {
    override fun equals(other: Any?): Boolean =
        other is StorageBoxFilterPacket &&
                listTypeIndex == other.listTypeIndex &&
                boxName == other.boxName &&
                (cookieData?.contentEquals(other.cookieData ?: ByteArray(0)) ?: (other.cookieData == null))

    override fun hashCode(): Int {
        var r = listTypeIndex?.hashCode() ?: 0
        r = r * 31 + (boxName?.hashCode() ?: 0)
        r = r * 31 + (cookieData?.contentHashCode() ?: 0)
        return r
    }
}

private val CookieBytes = bytesPrefixed(U8)

object StorageBoxFilterPacketCodec : PacketCodec<StorageBoxFilterPacket>() {
    override fun CodecScope<StorageBoxFilterPacket>.body(): StorageBoxFilterPacket {
        val typeFlags =
            field(U8) {
                (if (it.boxName != null) 1 else 0) or (if (it.cookieData != null) 2 else 0)
            }
        val listTypeIndex: Byte? =
            if (typeFlags and 1 != 0) field(S8) { it.listTypeIndex ?: 0 } else null
        val boxName: String? =
            if (typeFlags and 1 != 0) field(Utf16LeNullTerminated) { it.boxName ?: "" } else null
        val cookieData: ByteArray? =
            if (typeFlags and 2 != 0) field(CookieBytes) { it.cookieData ?: ByteArray(0) } else null
        return StorageBoxFilterPacket(listTypeIndex, boxName, cookieData)
    }
}
