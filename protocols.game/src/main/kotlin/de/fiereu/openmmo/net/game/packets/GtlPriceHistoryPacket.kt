package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GtlPriceHistoryPoint(
    val timestampSeconds: Int,
    val price: Int,
)

data class GtlPriceHistoryPacket(
    val itemType: Int,
    val variant: Short,
    val points: List<GtlPriceHistoryPoint>,
)

private object GtlPriceHistoryPointCodec : PacketCodec<GtlPriceHistoryPoint>() {
    override fun CodecScope<GtlPriceHistoryPoint>.body(): GtlPriceHistoryPoint {
        val timestampSeconds = field(S32LE, GtlPriceHistoryPoint::timestampSeconds)
        val price = field(S32LE, GtlPriceHistoryPoint::price)
        return GtlPriceHistoryPoint(timestampSeconds, price)
    }
}

private val GtlPriceHistoryPointListPrefixedU16: Codec<List<GtlPriceHistoryPoint>> =
    object : Codec<List<GtlPriceHistoryPoint>> {
        override fun read(buf: ReadBuffer): List<GtlPriceHistoryPoint> {
            val n = U16LE.read(buf)
            return List(n) { GtlPriceHistoryPointCodec.read(buf) }
        }

        override fun write(buf: WriteBuffer, value: List<GtlPriceHistoryPoint>) {
            U16LE.write(buf, value.size)
            value.forEach { GtlPriceHistoryPointCodec.write(buf, it) }
        }
    }

object GtlPriceHistoryPacketCodec : PacketCodec<GtlPriceHistoryPacket>() {
    override fun CodecScope<GtlPriceHistoryPacket>.body(): GtlPriceHistoryPacket {
        val itemType = field(U8, GtlPriceHistoryPacket::itemType)
        val variant = field(S16LE, GtlPriceHistoryPacket::variant)
        val points = field(GtlPriceHistoryPointListPrefixedU16, GtlPriceHistoryPacket::points)
        return GtlPriceHistoryPacket(itemType, variant, points)
    }
}
