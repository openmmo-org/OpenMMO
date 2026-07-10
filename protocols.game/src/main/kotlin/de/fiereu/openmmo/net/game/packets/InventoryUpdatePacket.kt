package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.WriteBuffer

/**
 * S2C 0x40 (`f.oo0_0`) -- initialises/updates a bag pocket. See docs/protocol/bag-spec.md.
 * `reset=true` replaces the pocket's contents entirely; the real client sends this once per pocket
 * at login/spawn, which is what allocates `f.ln1_2.sa0[pocketIndex]` and prevents the `f.TT` bag-UI
 * NPE (see captures/client-crash-dumps).
 */
data class InventoryUpdatePacket(
    val pocketIndex: Int,
    val reset: Boolean,
    val entries: List<InventoryItemEntry>,
) {
  init {
    require(pocketIndex in 0..0xff) { "pocketIndex must fit U8" }
    require(entries.size <= 0xffff) { "entry count must fit U16LE" }
  }
}

/**
 * Base 14-byte `zg_1` entry (flags=0x00). The real layout has optional flag-gated fields
 * (D91/KX0/QZ1/etc, see bag-spec.md section 3) that our server never populates, so the codec always
 * writes flags=0x00 and only implements the fields our items actually carry.
 */
data class InventoryItemEntry(
    val instanceId: Long,
    val itemId: Short,
    val quantity: Short,
    val pocketIndex: Int,
) {
  init {
    require(pocketIndex in 0..0xff) { "pocketIndex must fit U8" }
  }
}

private val InventoryItemEntryCodec: Codec<InventoryItemEntry> =
    object : PacketCodec<InventoryItemEntry>() {
      override fun CodecScope<InventoryItemEntry>.body(): InventoryItemEntry {
        field(U8) { 0 } // flags -- always 0x00, no optional zg_1 fields populated
        val instanceId = field(S64LE, InventoryItemEntry::instanceId)
        val itemId = field(S16LE, InventoryItemEntry::itemId)
        val quantity = field(S16LE, InventoryItemEntry::quantity)
        val pocketIndex = field(U8, InventoryItemEntry::pocketIndex)
        return InventoryItemEntry(
            instanceId = instanceId,
            itemId = itemId,
            quantity = quantity,
            pocketIndex = pocketIndex,
        )
      }
    }

private val InventoryEntryListPrefixedU16: Codec<List<InventoryItemEntry>> =
    object : Codec<List<InventoryItemEntry>> {
      override fun read(buf: ReadBuffer): List<InventoryItemEntry> {
        val count = U16LE.read(buf)
        return List(count) { InventoryItemEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<InventoryItemEntry>) {
        U16LE.write(buf, value.size)
        value.forEach { InventoryItemEntryCodec.write(buf, it) }
      }
    }

object InventoryUpdatePacketCodec : PacketCodec<InventoryUpdatePacket>() {
  override fun CodecScope<InventoryUpdatePacket>.body(): InventoryUpdatePacket {
    val pocketIndex = field(U8, InventoryUpdatePacket::pocketIndex)
    val reset = field(Bool, InventoryUpdatePacket::reset)
    val entries = field(InventoryEntryListPrefixedU16, InventoryUpdatePacket::entries)
    return InventoryUpdatePacket(pocketIndex = pocketIndex, reset = reset, entries = entries)
  }
}
