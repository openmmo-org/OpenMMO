package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleActionResultPacket(
    val targetCount: Byte,
    val targets: List<Byte>,
    val entityId: Long,
    val value: Int,
    val messageType: Byte,
    val move: Byte?,
)

object BattleActionResultPacketCodec : PacketCodec<BattleActionResultPacket>() {
  private const val MESSAGE_TYPE_WITH_MOVE: Int = 0

  override fun CodecScope<BattleActionResultPacket>.body(): BattleActionResultPacket {
    val targetCount = field(S8) { it.targetCount }
    val targets = ArrayList<Byte>()
    var entityId = 0L
    if (targetCount < 0) {
      entityId = field(S64LE) { it.entityId }
    } else {
      repeat(targetCount.toInt()) { i -> targets.add(field(S8) { p -> p.targets[i] }) }
    }
    val value = field(S32LE) { it.value }
    val messageType = field(S8) { it.messageType }
    val move = if (messageType.toInt() == MESSAGE_TYPE_WITH_MOVE) field(S8) { it.move!! } else null
    return BattleActionResultPacket(targetCount, targets, entityId, value, messageType, move)
  }
}
