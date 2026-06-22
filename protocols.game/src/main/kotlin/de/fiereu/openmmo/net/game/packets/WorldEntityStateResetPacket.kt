package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class WorldEntityStateResetPacket(
    val entityType: Short,
)

object WorldEntityStateResetPacketCodec : PacketCodec<WorldEntityStateResetPacket>() {
    override fun CodecScope<WorldEntityStateResetPacket>.body(): WorldEntityStateResetPacket {
        val entityType = field(S16LE) { it.entityType }
        return WorldEntityStateResetPacket(entityType)
    }
}
