package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ContactInsertAppearance(
    val name: String,
    val valueA: Byte,
    val valueB: Int,
    val kind: Byte,
    val packedSlots: Byte,
    val spriteData: List<Short>,
)

data class ContactInsertPacket(
    val player: Long,
    val value: Int,
    val online: Boolean,
    val appearance: ContactInsertAppearance,
)

private val ContactInsertAppearanceCodec: Codec<ContactInsertAppearance> =
    object : PacketCodec<ContactInsertAppearance>() {
        override fun CodecScope<ContactInsertAppearance>.body(): ContactInsertAppearance {
            val name = field(Utf16LeNullTerminated) { it.name }
            val valueA = field(S8) { it.valueA }
            val valueB = field(S32LE) { it.valueB }
            val kind = field(S8) { it.kind }
            val packedSlots = field(S8) { it.packedSlots }
            val spriteData = field(S16LE.repeat(12)) { it.spriteData }
            return ContactInsertAppearance(name, valueA, valueB, kind, packedSlots, spriteData)
        }
    }

object ContactInsertPacketCodec : PacketCodec<ContactInsertPacket>() {
    override fun CodecScope<ContactInsertPacket>.body(): ContactInsertPacket {
        val player = field(S64LE) { it.player }
        val value = field(S32LE) { it.value }
        val online = field(U8) { if (it.online) 1 else 0 } == 1
        val appearance = field(ContactInsertAppearanceCodec) { it.appearance }
        return ContactInsertPacket(player, value, online, appearance)
    }
}
