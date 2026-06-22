package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class AppearanceRequestResultPacket(
    val entityId: Long,
    val accepted: Boolean,
)

object AppearanceRequestResultPacketCodec : PacketCodec<AppearanceRequestResultPacket>() {
    override fun CodecScope<AppearanceRequestResultPacket>.body(): AppearanceRequestResultPacket {
        val entityId = field(S64LE) { it.entityId }
        val accepted = field(Bool) { it.accepted }
        return AppearanceRequestResultPacket(entityId, accepted)
    }
}
