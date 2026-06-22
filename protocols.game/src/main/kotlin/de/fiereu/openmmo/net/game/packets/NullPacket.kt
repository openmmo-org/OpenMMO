package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

class NullPacket

private object DrainRemaining : Codec<Unit> {
    override fun read(buf: ReadBuffer) {
        if (buf.remaining() > 0) buf.skip(buf.remaining())
    }

    override fun write(buf: WriteBuffer, value: Unit) {}
}

object NullPacketCodec : PacketCodec<NullPacket>() {
    override fun CodecScope<NullPacket>.body(): NullPacket {
        structural(DrainRemaining)
        return NullPacket()
    }
}
