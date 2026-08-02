package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8

/** Character-selection deletion request, captured as opcode 0x64 followed by the character id. */
data class DeleteCharacterPacket(val characterId: Long)

object DeleteCharacterPacketCodec : PacketCodec<DeleteCharacterPacket>() {
  override fun CodecScope<DeleteCharacterPacket>.body(): DeleteCharacterPacket =
      DeleteCharacterPacket(field(S64LE) { it.characterId })
}

/** Captured character deletion result. */
data class DeleteCharacterResultPacket(val result: Int, val characterId: Long)

object DeleteCharacterResultPacketCodec : PacketCodec<DeleteCharacterResultPacket>() {
  override fun CodecScope<DeleteCharacterResultPacket>.body(): DeleteCharacterResultPacket {
    val result = field(U8) { it.result }
    val characterId = field(S64LE) { it.characterId }
    return DeleteCharacterResultPacket(result, characterId)
  }
}
