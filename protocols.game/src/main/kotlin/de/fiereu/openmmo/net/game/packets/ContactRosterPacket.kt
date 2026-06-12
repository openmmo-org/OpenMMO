package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ContactAppearance(
    val name: String,
    val valueA: Byte,
    val valueB: Int,
    val kind: Byte,
    val packedSlots: Byte,
    val spriteData: List<Short>,
)

data class ContactRosterEntry(
    val player: Long,
    val value: Int,
    val online: Boolean,
    val appearance: ContactAppearance,
)

data class ContactRosterPacket(
    val mode: Int,
    val entries: List<ContactRosterEntry>,
)

private val ContactAppearanceCodec: Codec<ContactAppearance> =
    object : PacketCodec<ContactAppearance>() {
        override fun CodecScope<ContactAppearance>.body(): ContactAppearance {
            val name = field(Utf16LeNullTerminated) { it.name }
            val valueA = field(S8) { it.valueA }
            val valueB = field(S32LE) { it.valueB }
            val kind = field(S8) { it.kind }
            val packedSlots = field(S8) { it.packedSlots }
            val spriteData = field(S16LE.repeat(12)) { it.spriteData }
            return ContactAppearance(name, valueA, valueB, kind, packedSlots, spriteData)
        }
    }

private val ContactRosterEntryCodec: Codec<ContactRosterEntry> =
    object : PacketCodec<ContactRosterEntry>() {
        override fun CodecScope<ContactRosterEntry>.body(): ContactRosterEntry {
            val player = field(S64LE) { it.player }
            val value = field(S32LE) { it.value }
            val online = field(U8) { if (it.online) 1 else 0 } == 1
            val appearance = field(ContactAppearanceCodec) { it.appearance }
            return ContactRosterEntry(player, value, online, appearance)
        }
    }

object ContactRosterPacketCodec : PacketCodec<ContactRosterPacket>() {
    override fun CodecScope<ContactRosterPacket>.body(): ContactRosterPacket {
        val mode = field(U8) { it.mode }
        val entries = field(ContactRosterEntryCodec.listPrefixed(U8)) { it.entries }
        return ContactRosterPacket(mode, entries)
    }
}
