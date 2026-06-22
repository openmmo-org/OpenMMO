package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class HighScoreAppearance(
    val name: String,
    val gender: Byte,
    val id: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

data class HighScoreEntry(
    val entityId: Long,
    val rank: Int,
    val score: Int,
    val appearances: List<HighScoreAppearance>,
)

data class HighScoreBoardPacket(
    val category: Byte,
    val entries: List<HighScoreEntry>,
)

private val HighScoreAppearanceCodec: Codec<HighScoreAppearance> =
    object : PacketCodec<HighScoreAppearance>() {
        override fun CodecScope<HighScoreAppearance>.body(): HighScoreAppearance {
            val name = field(Utf16LeNullTerminated) { it.name }
            val gender = field(S8) { it.gender }
            val id = field(S32LE) { it.id }
            val kind = field(S8) { it.kind }
            val palettePack = field(S8) { it.palettePack }
            val slots = field(S16LE.repeat(4)) { it.slots }
            return HighScoreAppearance(name, gender, id, kind, palettePack, slots)
        }
    }

private val HighScoreEntryCodec: Codec<HighScoreEntry> =
    object : PacketCodec<HighScoreEntry>() {
        override fun CodecScope<HighScoreEntry>.body(): HighScoreEntry {
            val entityId = field(S64LE) { it.entityId }
            val rank = field(S32LE) { it.rank }
            val score = field(S32LE) { it.score }
            val count = field(U8) { it.appearances.size }
            val appearances = (0 until count).map { i -> field(HighScoreAppearanceCodec) { it.appearances[i] } }
            return HighScoreEntry(entityId, rank, score, appearances)
        }
    }

object HighScoreBoardPacketCodec : PacketCodec<HighScoreBoardPacket>() {
    override fun CodecScope<HighScoreBoardPacket>.body(): HighScoreBoardPacket {
        val category = field(S8) { it.category }
        field(S32LE) { 0 }
        val count = field(S32LE) { it.entries.size }
        val entries = (0 until count).map { i -> field(HighScoreEntryCodec) { it.entries[i] } }
        return HighScoreBoardPacket(category, entries)
    }
}
