package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.optional
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.net.game.codecs.CharacterInfoCodecShort

data class SelectedCharacterPacket(val character: CharacterInfo?)

private val OptionalCharacterInfo = CharacterInfoCodecShort.optional()

object SelectedCharacterPacketCodec : PacketCodec<SelectedCharacterPacket>() {
  override fun CodecScope<SelectedCharacterPacket>.body() =
      SelectedCharacterPacket(
          character = field(OptionalCharacterInfo, SelectedCharacterPacket::character),
      )
}
