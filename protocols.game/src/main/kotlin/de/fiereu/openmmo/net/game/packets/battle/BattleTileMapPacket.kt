package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleSlotTile(
    val slot: Byte,
    val value: Byte,
)

data class BattleTileMapPacket(
    val groupId: Short,
    val slotTiles: List<BattleSlotTile>?,
)

object BattleTileMapPacketCodec : PacketCodec<BattleTileMapPacket>() {
  override fun CodecScope<BattleTileMapPacket>.body(): BattleTileMapPacket {
    val groupId = field(S16LE) { it.groupId }
    val present = field(U8) { if (it.slotTiles != null) 1 else 0 } == 1
    val slotTiles =
        if (present) {
          val count = field(S8) { it.slotTiles!!.size.toByte() }.toInt()
          (0 until count).map { i ->
            val slot = field(S8) { it.slotTiles!![i].slot }
            val value = field(S8) { it.slotTiles!![i].value }
            BattleSlotTile(slot, value)
          }
        } else null
    return BattleTileMapPacket(groupId, slotTiles)
  }
}
