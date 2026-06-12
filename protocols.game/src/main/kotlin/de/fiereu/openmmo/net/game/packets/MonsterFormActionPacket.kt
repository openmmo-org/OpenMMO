package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class MonsterFormActionPacket(val monsterEntityId: Long, val stateValue: Short)

object MonsterFormActionPacketCodec : PacketCodec<MonsterFormActionPacket>() {
    override fun CodecScope<MonsterFormActionPacket>.body(): MonsterFormActionPacket {
        val monsterEntityId = field(S64LE) { it.monsterEntityId }
        val stateValue = field(S16LE) { it.stateValue }
        return MonsterFormActionPacket(monsterEntityId, stateValue)
    }
}
