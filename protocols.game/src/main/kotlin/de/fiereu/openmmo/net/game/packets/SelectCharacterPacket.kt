package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class SelectCharacterPacket(val characterId: Long, val characterIdHash: Long)

object SelectCharacterPacketCodec : PacketCodec<SelectCharacterPacket>() {
  override fun CodecScope<SelectCharacterPacket>.body(): SelectCharacterPacket {
    val characterId = field(S64LE) { it.characterId }
    val characterIdHash = field(S64LE) { it.characterIdHash }
    return SelectCharacterPacket(characterId, characterIdHash)
  }
}
