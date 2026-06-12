package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.PokemonCodec

data class SocialProfileDialogOpenPacket(
    val status: Byte,
    val content: Pokemon?,
)

object SocialProfileDialogOpenPacketCodec : PacketCodec<SocialProfileDialogOpenPacket>() {
    override fun CodecScope<SocialProfileDialogOpenPacket>.body(): SocialProfileDialogOpenPacket {
        val status = field(S8) { it.status }
        val content = if (status.toInt() == 0) field(PokemonCodec) { it.content!! } else null
        return SocialProfileDialogOpenPacket(status, content)
    }
}
