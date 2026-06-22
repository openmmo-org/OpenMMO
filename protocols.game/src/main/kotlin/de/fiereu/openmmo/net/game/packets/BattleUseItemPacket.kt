package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class BattleUseItemPacket(
    val pokemonEntityId: Long,
    val quantity: Short,
)

object BattleUseItemPacketCodec : PacketCodec<BattleUseItemPacket>() {
    override fun CodecScope<BattleUseItemPacket>.body(): BattleUseItemPacket {
        val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
        val quantity = field(S16LE) { it.quantity }
        return BattleUseItemPacket(pokemonEntityId, quantity)
    }
}
