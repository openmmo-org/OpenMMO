package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class SetCharacterNamePacket(
    val characterEntityId: Long,
    val newName: String,
)

object SetCharacterNamePacketCodec : PacketCodec<SetCharacterNamePacket>() {
  override fun CodecScope<SetCharacterNamePacket>.body(): SetCharacterNamePacket {
    val characterEntityId = field(S64LE) { it.characterEntityId }
    val newName = field(Utf16LeNullTerminated) { it.newName }
    return SetCharacterNamePacket(characterEntityId, newName)
  }
}
