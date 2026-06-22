package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class NamedCategoryEntry(
    val category: Byte,
    val name: String,
    val kind: Byte,
    val payload: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NamedCategoryEntry) return false
        return category == other.category &&
                name == other.name &&
                kind == other.kind &&
                payload.contentEquals(other.payload)
    }

    override fun hashCode(): Int {
        var result = category.toInt()
        result = 31 * result + name.hashCode()
        result = 31 * result + kind.toInt()
        result = 31 * result + payload.contentHashCode()
        return result
    }
}

data class NamedCategoryEntriesPacket(
    val entries: List<NamedCategoryEntry>,
)

private object NamedCategoryEntryCodec : PacketCodec<NamedCategoryEntry>() {
    override fun CodecScope<NamedCategoryEntry>.body(): NamedCategoryEntry {
        val category = field(S8, NamedCategoryEntry::category)
        val name = field(Utf16LeNullTerminated, NamedCategoryEntry::name)
        val kind = field(S8, NamedCategoryEntry::kind)
        val payload = field(bytesPrefixed(U8), NamedCategoryEntry::payload)
        return NamedCategoryEntry(category, name, kind, payload)
    }
}

private val NamedCategoryEntryListPrefixedU8: Codec<List<NamedCategoryEntry>> =
    object : Codec<List<NamedCategoryEntry>> {
        override fun read(buf: ReadBuffer): List<NamedCategoryEntry> {
            val n = U8.read(buf)
            return List(n) { NamedCategoryEntryCodec.read(buf) }
        }

        override fun write(buf: WriteBuffer, value: List<NamedCategoryEntry>) {
            U8.write(buf, value.size)
            value.forEach { NamedCategoryEntryCodec.write(buf, it) }
        }
    }

object NamedCategoryEntriesPacketCodec : PacketCodec<NamedCategoryEntriesPacket>() {
    override fun CodecScope<NamedCategoryEntriesPacket>.body(): NamedCategoryEntriesPacket {
        val entries = field(NamedCategoryEntryListPrefixedU8, NamedCategoryEntriesPacket::entries)
        return NamedCategoryEntriesPacket(entries)
    }
}
