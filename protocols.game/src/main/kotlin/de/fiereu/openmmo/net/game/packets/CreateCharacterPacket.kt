package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.openmmo.net.game.codecs.DefaultSkinSetCodec
import de.fiereu.openmmo.net.game.codecs.SkinSet

/** The choices submitted by the client's character creator. */
data class CreateCharacterPacket(
    val name: String,
    val gender: Byte,
    val startingRegion: Byte,
    val appearance: SkinSet,
)

object CreateCharacterPacketCodec : PacketCodec<CreateCharacterPacket>() {
  override fun CodecScope<CreateCharacterPacket>.body(): CreateCharacterPacket {
    val name = field(Utf16LeNullTerminated) { it.name }
    val gender = field(S8) { it.gender }
    val startingRegion = field(S8) { it.startingRegion }
    val appearance = field(DefaultSkinSetCodec) { it.appearance }
    return CreateCharacterPacket(name, gender, startingRegion, appearance)
  }
}
