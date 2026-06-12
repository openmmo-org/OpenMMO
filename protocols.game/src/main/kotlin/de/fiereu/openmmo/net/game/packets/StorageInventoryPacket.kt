package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class StorageQuantity(val id: Short, val count: Int)

data class StorageByteQuantity(val id: Byte, val count: Int)

data class StorageInventoryPacket(
    val header: Short,
    val totalA: Int,
    val totalB: Int,
    val valueC: Int,
    val usedA: Int,
    val usedB: Int,
    val listA: List<StorageQuantity>,
    val listB: List<StorageByteQuantity>,
    val listC: List<StorageQuantity>,
    val listD: List<StorageQuantity>,
)

private val StorageQuantityCodec: Codec<StorageQuantity> =
    object : PacketCodec<StorageQuantity>() {
        override fun CodecScope<StorageQuantity>.body(): StorageQuantity {
            val id = field(S16LE) { it.id }
            val count = field(S32LE) { it.count }
            return StorageQuantity(id, count)
        }
    }

private val StorageByteQuantityCodec: Codec<StorageByteQuantity> =
    object : PacketCodec<StorageByteQuantity>() {
        override fun CodecScope<StorageByteQuantity>.body(): StorageByteQuantity {
            val id = field(S8) { it.id }
            val count = field(S32LE) { it.count }
            return StorageByteQuantity(id, count)
        }
    }

object StorageInventoryPacketCodec : PacketCodec<StorageInventoryPacket>() {
    override fun CodecScope<StorageInventoryPacket>.body(): StorageInventoryPacket {
        val header = field(S16LE) { it.header }
        val totalA = field(S32LE) { it.totalA }
        val totalB = field(S32LE) { it.totalB }
        val valueC = field(S32LE) { it.valueC }
        val usedA = field(S32LE) { it.usedA }
        val usedB = field(S32LE) { it.usedB }
        val listA = field(StorageQuantityCodec.listPrefixed(U8)) { it.listA }
        val listB = field(StorageByteQuantityCodec.listPrefixed(U8)) { it.listB }
        val listC = field(StorageQuantityCodec.listPrefixed(U8)) { it.listC }
        val listD = field(StorageQuantityCodec.listPrefixed(U8)) { it.listD }
        return StorageInventoryPacket(
            header, totalA, totalB, valueC, usedA, usedB, listA, listB, listC, listD
        )
    }
}
