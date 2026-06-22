package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class GmPanelVariantPacket(
    val variant: Byte,
)

object GmPanelVariantPacketCodec : PacketCodec<GmPanelVariantPacket>() {
    override fun CodecScope<GmPanelVariantPacket>.body(): GmPanelVariantPacket {
        val variant = field(S8) { it.variant }
        return GmPanelVariantPacket(variant)
    }
}
