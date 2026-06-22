package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleTierSelectPacket(val pokemonSlotIndex: Byte, val tierIndex: Byte)

object BattleTierSelectPacketCodec : PacketCodec<BattleTierSelectPacket>() {
  override fun CodecScope<BattleTierSelectPacket>.body(): BattleTierSelectPacket {
    val pokemonSlotIndex = field(S8) { it.pokemonSlotIndex }
    val tierIndex = field(S8) { it.tierIndex }
    return BattleTierSelectPacket(pokemonSlotIndex, tierIndex)
  }
}
