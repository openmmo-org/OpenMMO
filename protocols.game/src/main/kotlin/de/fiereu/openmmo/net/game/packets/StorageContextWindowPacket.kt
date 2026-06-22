package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class StorageBoxItem(
    val slot: Byte,
    val valueA: Short,
    val valueB: Short,
    val valueC: Short,
    val valueD: Short,
    val quantity: Byte,
    val ball: Byte,
    val location: Byte,
)

data class StorageContextWindowPacket(
    val kind: Byte,
    val items: List<StorageBoxItem>,
)

object StorageContextWindowPacketCodec : PacketCodec<StorageContextWindowPacket>() {
  override fun CodecScope<StorageContextWindowPacket>.body(): StorageContextWindowPacket {
    val kind = field(S8) { it.kind }
    val count = field(S8) { it.items.size.toByte() }.toInt()
    val items = ArrayList<StorageBoxItem>(if (count > 0) count else 0)
    repeat(if (count > 0) count else 0) { i ->
      val slot = field(S8) { it.items[i].slot }
      val valueA = field(S16LE) { it.items[i].valueA }
      val valueB = field(S16LE) { it.items[i].valueB }
      val valueC = field(S16LE) { it.items[i].valueC }
      val valueD = field(S16LE) { it.items[i].valueD }
      val quantity = field(S8) { it.items[i].quantity }
      val ball = field(S8) { it.items[i].ball }
      val location = field(S8) { it.items[i].location }
      items.add(StorageBoxItem(slot, valueA, valueB, valueC, valueD, quantity, ball, location))
    }
    return StorageContextWindowPacket(kind, items)
  }
}
