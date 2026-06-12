package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8

data class DisconnectNoticePacket(
    val buildInfo: Byte?,
    val reasonMode: Byte?,
    val forced: Boolean,
)

object DisconnectNoticePacketCodec : PacketCodec<DisconnectNoticePacket>() {
    override fun CodecScope<DisconnectNoticePacket>.body(): DisconnectNoticePacket {
        val hasBuildInfo = field(U8) { if (it.buildInfo != null) 1 else 0 } == 1
        val buildInfo = if (hasBuildInfo) field(S8) { it.buildInfo!! } else null
        val hasReasonMode = field(U8) { if (it.reasonMode != null) 1 else 0 } == 1
        val reasonMode = if (hasReasonMode) field(S8) { it.reasonMode!! } else null
        val forced = field(U8) { if (it.forced) 1 else 0 } == 1
        return DisconnectNoticePacket(buildInfo, reasonMode, forced)
    }
}
