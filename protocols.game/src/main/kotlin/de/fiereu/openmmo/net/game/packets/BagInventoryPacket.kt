package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.WriteBuffer
import de.fiereu.bytecodec.fixedBytes

class BagOpenRequestPacket

object BagOpenRequestPacketCodec : PacketCodec<BagOpenRequestPacket>() {
  override fun CodecScope<BagOpenRequestPacket>.body(): BagOpenRequestPacket =
      BagOpenRequestPacket()
}

data class BagInventoryPacket(
    val containerId: Int,
    val entries: List<BagItemEntry>,
) {
  init {
    require(containerId in 0..0xffff) { "containerId must fit U16LE" }
    require(entries.size <= 0xff) { "bag entry count must fit U8" }
  }
}

data class BagItemEntry(
    val slot: Int,
    val categoryFlags: Int,
    val subType: Int,
    val itemId: Int,
    val quantity: Int,
    val maxStack: Int,
    val flag1: Int = 1,
    val flag2: Int = 0,
    val entityId: Long = 0,
    val unknownA: Int = 0,
    val slotDuplicate: Int = slot,
    val timestampBytes: ByteArray = DEFAULT_TIMESTAMP_BYTES,
    val state: Int = 0x0001,
) {
  init {
    require(slot in 0..0xff) { "slot must fit U8" }
    require(subType in 0..0xff) { "subType must fit U8" }
    require(itemId in 0..0xffff) { "itemId must fit U16LE" }
    require(unknownA in 0..0xff) { "unknownA must fit U8" }
    require(slotDuplicate in 0..0xff) { "slotDuplicate must fit U8" }
    require(timestampBytes.size == 3) { "timestampBytes must be exactly 3 bytes" }
    require(state in 0..0xffff) { "state must fit U16LE" }
  }

  override fun equals(other: Any?): Boolean =
      other is BagItemEntry &&
          slot == other.slot &&
          categoryFlags == other.categoryFlags &&
          subType == other.subType &&
          itemId == other.itemId &&
          quantity == other.quantity &&
          maxStack == other.maxStack &&
          flag1 == other.flag1 &&
          flag2 == other.flag2 &&
          entityId == other.entityId &&
          unknownA == other.unknownA &&
          slotDuplicate == other.slotDuplicate &&
          timestampBytes.contentEquals(other.timestampBytes) &&
          state == other.state

  override fun hashCode(): Int {
    var result = slot
    result = 31 * result + categoryFlags
    result = 31 * result + subType
    result = 31 * result + itemId
    result = 31 * result + quantity
    result = 31 * result + maxStack
    result = 31 * result + flag1
    result = 31 * result + flag2
    result = 31 * result + entityId.hashCode()
    result = 31 * result + unknownA
    result = 31 * result + slotDuplicate
    result = 31 * result + timestampBytes.contentHashCode()
    result = 31 * result + state
    return result
  }
}

private val DEFAULT_TIMESTAMP_BYTES = byteArrayOf(0x91.toByte(), 0x9e.toByte(), 0x57.toByte())
private val TERMINATOR = byteArrayOf(0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte())

private val BagItemEntryCodec: Codec<BagItemEntry> =
    object : PacketCodec<BagItemEntry>() {
      override fun CodecScope<BagItemEntry>.body(): BagItemEntry {
        val slot = field(U8, BagItemEntry::slot)
        val categoryFlags = field(S32LE, BagItemEntry::categoryFlags)
        val subType = field(U8, BagItemEntry::subType)
        val itemId = field(U16LE, BagItemEntry::itemId)
        val quantity = field(S32LE, BagItemEntry::quantity)
        val maxStack = field(S32LE, BagItemEntry::maxStack)
        val flag1 = field(S32LE, BagItemEntry::flag1)
        val flag2 = field(S32LE, BagItemEntry::flag2)
        val entityId = field(S64LE, BagItemEntry::entityId)
        val unknownA = field(U8, BagItemEntry::unknownA)
        val slotDuplicate = field(U8, BagItemEntry::slotDuplicate)
        val timestampBytes = field(fixedBytes(3), BagItemEntry::timestampBytes)
        val state = field(U16LE, BagItemEntry::state)
        field(fixedBytes(4)) { TERMINATOR }
        return BagItemEntry(
            slot = slot,
            categoryFlags = categoryFlags,
            subType = subType,
            itemId = itemId,
            quantity = quantity,
            maxStack = maxStack,
            flag1 = flag1,
            flag2 = flag2,
            entityId = entityId,
            unknownA = unknownA,
            slotDuplicate = slotDuplicate,
            timestampBytes = timestampBytes,
            state = state,
        )
      }
    }

private val BagEntryListPrefixedU8: Codec<List<BagItemEntry>> =
    object : Codec<List<BagItemEntry>> {
      override fun read(buf: ReadBuffer): List<BagItemEntry> {
        val count = U8.read(buf)
        return List(count) { BagItemEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<BagItemEntry>) {
        U8.write(buf, value.size)
        value.forEach { BagItemEntryCodec.write(buf, it) }
      }
    }

object BagInventoryPacketCodec : PacketCodec<BagInventoryPacket>() {
  override fun CodecScope<BagInventoryPacket>.body(): BagInventoryPacket {
    val containerId = field(U16LE, BagInventoryPacket::containerId)
    val entries = field(BagEntryListPrefixedU8, BagInventoryPacket::entries)
    return BagInventoryPacket(containerId, entries)
  }
}
