package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldEntityInteractPacket(
    val itemTypeId: Short,
    val targetEntityId: Long,
    val quantity: Short,
    val actionFlag: Byte,
    val reserved: Byte,
)

object WorldEntityInteractPacketCodec : PacketCodec<WorldEntityInteractPacket>() {
  override fun CodecScope<WorldEntityInteractPacket>.body(): WorldEntityInteractPacket {
    val itemTypeId = field(S16LE) { it.itemTypeId }
    val targetEntityId = field(S64LE) { it.targetEntityId }
    val quantity = field(S16LE) { it.quantity }
    val actionFlag = field(S8) { it.actionFlag }
    val reserved = field(S8) { it.reserved }
    return WorldEntityInteractPacket(itemTypeId, targetEntityId, quantity, actionFlag, reserved)
  }
}
