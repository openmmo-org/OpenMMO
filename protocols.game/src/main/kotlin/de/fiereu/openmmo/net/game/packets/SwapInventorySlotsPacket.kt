package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SlotSwap(
    val sourceListType: Byte,
    val sourceSlot: Short,
    val destinationListType: Byte,
    val destinationSlot: Short,
)

private val SlotSwapCodec: Codec<SlotSwap> =
    object : PacketCodec<SlotSwap>() {
      override fun CodecScope<SlotSwap>.body(): SlotSwap {
        val sourceListType = field(S8) { it.sourceListType }
        val sourceSlot = field(S16LE) { it.sourceSlot }
        val destinationListType = field(S8) { it.destinationListType }
        val destinationSlot = field(S16LE) { it.destinationSlot }
        return SlotSwap(sourceListType, sourceSlot, destinationListType, destinationSlot)
      }
    }

data class SwapInventorySlotsPacket(val swaps: List<SlotSwap>)

object SwapInventorySlotsPacketCodec : PacketCodec<SwapInventorySlotsPacket>() {
  override fun CodecScope<SwapInventorySlotsPacket>.body(): SwapInventorySlotsPacket {
    val swaps = field(SlotSwapCodec.listPrefixed(U8)) { it.swaps }
    return SwapInventorySlotsPacket(swaps)
  }
}
