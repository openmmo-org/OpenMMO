package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EggIncubatorSlot(
    val value: Short,
)

data class EggIncubatorSlotsPacket(
    val slots: List<EggIncubatorSlot>,
)

private object EggIncubatorSlotCodec : PacketCodec<EggIncubatorSlot>() {
    override fun CodecScope<EggIncubatorSlot>.body(): EggIncubatorSlot {
        field(S64LE) { 0L }
        val value = field(S16LE, EggIncubatorSlot::value)
        return EggIncubatorSlot(value)
    }
}

private val EggIncubatorSlotListPrefixedU8: Codec<List<EggIncubatorSlot>> =
    object : Codec<List<EggIncubatorSlot>> {
        override fun read(buf: ReadBuffer): List<EggIncubatorSlot> {
            val n = U8.read(buf)
            return List(n) { EggIncubatorSlotCodec.read(buf) }
        }

        override fun write(buf: WriteBuffer, value: List<EggIncubatorSlot>) {
            U8.write(buf, value.size)
            value.forEach { EggIncubatorSlotCodec.write(buf, it) }
        }
    }

object EggIncubatorSlotsPacketCodec : PacketCodec<EggIncubatorSlotsPacket>() {
    override fun CodecScope<EggIncubatorSlotsPacket>.body(): EggIncubatorSlotsPacket {
        val slots = field(EggIncubatorSlotListPrefixedU8, EggIncubatorSlotsPacket::slots)
        return EggIncubatorSlotsPacket(slots)
    }
}
