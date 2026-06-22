package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class KeepAlivePacket(val canJoin: Boolean, val sessionData: ByteArray) {
    override fun equals(other: Any?): Boolean =
        other is KeepAlivePacket &&
                canJoin == other.canJoin &&
                sessionData.contentEquals(other.sessionData)

    override fun hashCode(): Int = canJoin.hashCode() * 31 + sessionData.contentHashCode()
}

private val KeepAliveSessionDataBytes: Codec<ByteArray> =
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

object KeepAlivePacketCodec : PacketCodec<KeepAlivePacket>() {
    override fun CodecScope<KeepAlivePacket>.body() =
        KeepAlivePacket(
            canJoin = field(Bool, KeepAlivePacket::canJoin),
            sessionData = field(KeepAliveSessionDataBytes, KeepAlivePacket::sessionData),
        )
}
