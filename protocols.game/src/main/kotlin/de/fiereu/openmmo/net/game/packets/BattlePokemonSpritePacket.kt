package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class BattlePokemonSpritePacket(
    val entityId: Long,
    val spriteId: Short,
)

object BattlePokemonSpritePacketCodec : PacketCodec<BattlePokemonSpritePacket>() {
  override fun CodecScope<BattlePokemonSpritePacket>.body(): BattlePokemonSpritePacket {
    val entityId = field(S64LE) { it.entityId }
    val spriteId = field(S16LE) { it.spriteId }
    return BattlePokemonSpritePacket(entityId, spriteId)
  }
}
