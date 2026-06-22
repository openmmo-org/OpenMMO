package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class BattleCancelRequestPacket

object BattleCancelRequestPacketCodec : PacketCodec<BattleCancelRequestPacket>() {
    override fun CodecScope<BattleCancelRequestPacket>.body(): BattleCancelRequestPacket {
        return BattleCancelRequestPacket()
    }
}
