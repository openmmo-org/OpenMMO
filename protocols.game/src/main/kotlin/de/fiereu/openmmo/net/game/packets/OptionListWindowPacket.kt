package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class OptionMenuEntry(
    val typeId: Byte,
    val subType: Byte,
    val valueA: Short,
    val valueB: Short,
    val valueC: Short,
    val valueD: Short,
    val valueE: Short,
)

data class OptionListWindowPacket(
    val kind: Byte,
    val options: List<OptionMenuEntry>,
)

private val OptionMenuEntryCodec: Codec<OptionMenuEntry> =
    object : PacketCodec<OptionMenuEntry>() {
        override fun CodecScope<OptionMenuEntry>.body(): OptionMenuEntry {
            field(S64LE) { 0L }
            field(S8) { 0 }
            val typeId = field(S8) { it.typeId }
            val subType = field(S8) { it.subType }
            val valueA = field(S16LE) { it.valueA }
            val valueB = field(S16LE) { it.valueB }
            val valueC = field(S16LE) { it.valueC }
            val valueD = field(S16LE) { it.valueD }
            val valueE = field(S16LE) { it.valueE }
            return OptionMenuEntry(typeId, subType, valueA, valueB, valueC, valueD, valueE)
        }
    }

object OptionListWindowPacketCodec : PacketCodec<OptionListWindowPacket>() {
    override fun CodecScope<OptionListWindowPacket>.body(): OptionListWindowPacket {
        val kind = field(S8) { it.kind }
        val options = field(OptionMenuEntryCodec.listPrefixed(U8)) { it.options }
        return OptionListWindowPacket(kind, options)
    }
}
