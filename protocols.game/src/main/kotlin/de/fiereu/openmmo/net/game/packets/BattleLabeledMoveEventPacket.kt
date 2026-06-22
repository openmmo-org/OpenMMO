package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleLabeledMoveEntry(
    val entityId: Long,
    val value: Short,
    val actionCount: Int,
)

private val BattleLabeledMoveEntryCodec: Codec<BattleLabeledMoveEntry> =
    object : PacketCodec<BattleLabeledMoveEntry>() {
        override fun CodecScope<BattleLabeledMoveEntry>.body(): BattleLabeledMoveEntry {
            val entityId = field(S64LE) { it.entityId }
            val value = field(S16LE) { it.value }
            val actionCount = field(U8) { it.actionCount }
            return BattleLabeledMoveEntry(entityId, value, actionCount)
        }
    }

data class BattleLabeledMoveEventPacket(
    val byte1: Byte,
    val byte2: Byte,
    val value: Short,
    val byte3: Byte,
    val short2: Short,
    val label: String,
    val entries: List<BattleLabeledMoveEntry>,
)

object BattleLabeledMoveEventPacketCodec : PacketCodec<BattleLabeledMoveEventPacket>() {
    override fun CodecScope<BattleLabeledMoveEventPacket>.body(): BattleLabeledMoveEventPacket {
        val byte1 = field(S8) { it.byte1 }
        val byte2 = field(S8) { it.byte2 }
        val value = field(S16LE) { it.value }
        val byte3 = field(S8) { it.byte3 }
        val short2 = field(S16LE) { it.short2 }
        val label = field(Utf16LeNullTerminated) { it.label }
        val entries = field(BattleLabeledMoveEntryCodec.listPrefixed(U8)) { it.entries }
        return BattleLabeledMoveEventPacket(byte1, byte2, value, byte3, short2, label, entries)
    }
}
