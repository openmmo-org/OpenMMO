package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TokenPayloadPacket(val payload: ByteArray) {
    override fun equals(other: Any?): Boolean =
        other is TokenPayloadPacket && payload.contentEquals(other.payload)

    override fun hashCode(): Int = payload.contentHashCode()
}

private val TokenPayloadBytes: Codec<ByteArray> =
    object : Codec<ByteArray> {
        override fun read(buf: ReadBuffer): ByteArray {
            val data = ByteArray(buf.remaining())
            if (data.isNotEmpty()) buf.readBytes(data)
            return data
        }

        override fun write(buf: WriteBuffer, value: ByteArray) {
            if (value.isNotEmpty()) buf.writeBytes(value)
        }
    }

object TokenPayloadPacketCodec : PacketCodec<TokenPayloadPacket>() {
    override fun CodecScope<TokenPayloadPacket>.body() =
        TokenPayloadPacket(
            payload = field(TokenPayloadBytes, TokenPayloadPacket::payload),
        )
}
