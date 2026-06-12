package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ContactCategoryGroupPacket(
    val category: Byte,
    val name: String,
    val memberIds: List<Long>,
)

object ContactCategoryGroupPacketCodec : PacketCodec<ContactCategoryGroupPacket>() {
    override fun CodecScope<ContactCategoryGroupPacket>.body(): ContactCategoryGroupPacket {
        val category = field(S8, ContactCategoryGroupPacket::category)
        val name = field(Utf16LeNullTerminated, ContactCategoryGroupPacket::name)
        val memberIds = field(S64LE.repeat(6), ContactCategoryGroupPacket::memberIds)
        return ContactCategoryGroupPacket(category, name, memberIds)
    }
}
