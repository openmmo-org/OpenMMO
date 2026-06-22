package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class StorageBoxEntriesRequestPacket(
    val listType: Byte,
    val itemIds: List<Int>,
    val flags: Short,
)

object StorageBoxEntriesRequestPacketCodec : PacketCodec<StorageBoxEntriesRequestPacket>() {
    override fun CodecScope<StorageBoxEntriesRequestPacket>.body(): StorageBoxEntriesRequestPacket {
        val listType = field(S8) { it.listType }
        val itemIds = field(S32LE.listPrefixed(U8)) { it.itemIds }
        val flags = field(S16LE) { it.flags }
        return StorageBoxEntriesRequestPacket(listType, itemIds, flags)
    }
}
