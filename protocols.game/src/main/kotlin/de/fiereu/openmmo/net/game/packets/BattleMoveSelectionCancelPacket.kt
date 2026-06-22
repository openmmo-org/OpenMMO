package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.reserved

data class BattleMoveSelectionCancelPacket(
    val unit: Unit = Unit,
)

object BattleMoveSelectionCancelPacketCodec : PacketCodec<BattleMoveSelectionCancelPacket>() {
    override fun CodecScope<BattleMoveSelectionCancelPacket>.body(): BattleMoveSelectionCancelPacket {
        reserved(byte = 0)
        return BattleMoveSelectionCancelPacket()
    }
}
