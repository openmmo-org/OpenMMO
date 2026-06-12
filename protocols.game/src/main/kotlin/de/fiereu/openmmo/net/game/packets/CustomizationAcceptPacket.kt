package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class CustomizationAcceptPacket(val entityId: Long, val verificationToken: Long)

object CustomizationAcceptPacketCodec : PacketCodec<CustomizationAcceptPacket>() {
    override fun CodecScope<CustomizationAcceptPacket>.body(): CustomizationAcceptPacket {
        val entityId = field(S64LE) { it.entityId }
        val verificationToken = field(S64LE) { it.verificationToken }
        return CustomizationAcceptPacket(entityId, verificationToken)
    }
}
