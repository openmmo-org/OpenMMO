package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class CookieNamedValue(val key: Byte, val value: String)

data class ClientCookieUpdatePacket(
    val blob: ByteArray?,
    val namedValues: List<CookieNamedValue>?,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is ClientCookieUpdatePacket) return false
        if (namedValues != other.namedValues) return false
        if (blob == null) return other.blob == null
        return other.blob != null && blob.contentEquals(other.blob)
    }

    override fun hashCode(): Int {
        var r = blob?.contentHashCode() ?: 0
        r = r * 31 + (namedValues?.hashCode() ?: 0)
        return r
    }
}

private val CookieBlob = bytesPrefixed(U8)

private val CookieNamedValueCodec: Codec<CookieNamedValue> =
    (S8.then(Utf16LeNullTerminated)).let { pair ->
        object : Codec<CookieNamedValue> {
            override fun read(buf: de.fiereu.bytecodec.ReadBuffer): CookieNamedValue {
                val (k, v) = pair.read(buf)
                return CookieNamedValue(k, v)
            }

            override fun write(buf: de.fiereu.bytecodec.WriteBuffer, value: CookieNamedValue) {
                pair.write(buf, value.key to value.value)
            }
        }
    }

object ClientCookieUpdatePacketCodec : PacketCodec<ClientCookieUpdatePacket>() {
    override fun CodecScope<ClientCookieUpdatePacket>.body(): ClientCookieUpdatePacket {
        val flags =
            field(S8) {
                var f = 0
                if (it.blob != null) f = f or 1
                if (it.namedValues != null) f = f or 2
                f.toByte()
            }
        val blob: ByteArray? = if (flags.toInt() and 1 != 0) field(CookieBlob) { it.blob!! } else null
        val namedValues: List<CookieNamedValue>? =
            if (flags.toInt() and 2 != 0)
                field(CookieNamedValueCodec.listPrefixed(U8)) { it.namedValues!! }
            else null
        return ClientCookieUpdatePacket(blob, namedValues)
    }
}
