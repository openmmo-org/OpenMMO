package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleStatusEffectData(
    val durationTurns: Int,
    val effectType: Byte,
    val sourceSlot: Byte,
)

data class BattlePokemonStatusPacket(
    val entityId: Long,
    val statusId: Byte,
    val statusEffect: BattleStatusEffectData?,
)

object BattlePokemonStatusPacketCodec : PacketCodec<BattlePokemonStatusPacket>() {
  override fun CodecScope<BattlePokemonStatusPacket>.body(): BattlePokemonStatusPacket {
    val entityId = field(S64LE) { it.entityId }
    val statusId = field(S8) { it.statusId }
    val present = field(U8) { if (it.statusEffect != null) 1 else 0 } == 1
    val statusEffect =
        if (present) {
          val durationTurns = field(S32LE) { it.statusEffect!!.durationTurns }
          val effectType = field(S8) { it.statusEffect!!.effectType }
          val sourceSlot = field(S8) { it.statusEffect!!.sourceSlot }
          BattleStatusEffectData(durationTurns, effectType, sourceSlot)
        } else null
    return BattlePokemonStatusPacket(entityId, statusId, statusEffect)
  }
}
