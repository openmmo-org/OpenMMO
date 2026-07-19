package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class BattlePartySwitchPacket(val monsterEntityId: Long)

object BattlePartySwitchPacketCodec : PacketCodec<BattlePartySwitchPacket>() {
  override fun CodecScope<BattlePartySwitchPacket>.body(): BattlePartySwitchPacket {
    val monsterEntityId = field(S64LE) { it.monsterEntityId }
    return BattlePartySwitchPacket(monsterEntityId)
  }
}
