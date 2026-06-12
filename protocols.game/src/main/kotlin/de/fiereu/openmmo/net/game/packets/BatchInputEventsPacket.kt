package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class InputEventSample(
    val type: Byte,
    val timestamp: Long,
    val byteA: Byte,
    val byteB: Byte,
    val intA: Int,
    val shortA: Short,
    val shortB: Short,
    val intB: Int,
)

data class BatchInputEventsPacket(
    val hasEvents: Boolean,
    val events: List<InputEventSample>,
)

private val EventsCodec: Codec<List<InputEventSample>> =
    object : Codec<List<InputEventSample>> {
        override fun read(buf: ReadBuffer): List<InputEventSample> {
            val count = S16LE.read(buf).toInt()
            if (count == 0) return emptyList()
            var previous = S64LE.read(buf)
            val result = ArrayList<InputEventSample>(count)
            for (i in 0 until count) {
                val type = S8.read(buf)
                val delta = S16LE.read(buf).toInt()
                val timestamp = if (delta == -1) S64LE.read(buf) else previous + delta
                var byteA: Byte = 0
                var byteB: Byte = 0
                var intA = 0
                var shortA: Short = 0
                var shortB: Short = 0
                var intB = 0
                when (type.toInt()) {
                    0,
                    1 -> {
                        byteA = S8.read(buf)
                        byteB = S8.read(buf)
                        intA = S32LE.read(buf)
                        intB = S32LE.read(buf)
                    }

                    2,
                    3 -> {
                        byteA = S8.read(buf)
                        byteB = S8.read(buf)
                        shortA = S16LE.read(buf)
                        shortB = S16LE.read(buf)
                        intA = S32LE.read(buf)
                        intB = S32LE.read(buf)
                    }

                    4,
                    14 -> {
                        byteB = S8.read(buf)
                        shortA = S16LE.read(buf)
                        shortB = S16LE.read(buf)
                        intA = S32LE.read(buf)
                        intB = S32LE.read(buf)
                    }

                    5,
                    6,
                    7,
                    8 -> {
                        byteB = S8.read(buf)
                    }

                    9 -> {
                        shortA = S16LE.read(buf)
                        shortB = S16LE.read(buf)
                    }
                }
                result.add(
                    InputEventSample(type, timestamp, byteA, byteB, intA, shortA, shortB, intB)
                )
                previous = timestamp
            }
            return result
        }

        override fun write(buf: WriteBuffer, value: List<InputEventSample>) {
            S16LE.write(buf, value.size.toShort())
            if (value.isEmpty()) return
            var previous = value[0].timestamp
            S64LE.write(buf, previous)
            for (event in value) {
                S8.write(buf, event.type)
                val delta = event.timestamp - previous
                if (delta > 32767L) {
                    S16LE.write(buf, (-1).toShort())
                    S64LE.write(buf, event.timestamp)
                } else {
                    S16LE.write(buf, delta.toShort())
                }
                when (event.type.toInt()) {
                    0,
                    1 -> {
                        S8.write(buf, event.byteA)
                        S8.write(buf, event.byteB)
                        S32LE.write(buf, event.intA)
                        S32LE.write(buf, event.intB)
                    }

                    2,
                    3 -> {
                        S8.write(buf, event.byteA)
                        S8.write(buf, event.byteB)
                        S16LE.write(buf, event.shortA)
                        S16LE.write(buf, event.shortB)
                        S32LE.write(buf, event.intA)
                        S32LE.write(buf, event.intB)
                    }

                    4,
                    14 -> {
                        S8.write(buf, event.byteB)
                        S16LE.write(buf, event.shortA)
                        S16LE.write(buf, event.shortB)
                        S32LE.write(buf, event.intA)
                        S32LE.write(buf, event.intB)
                    }

                    5,
                    6,
                    7,
                    8 -> {
                        S8.write(buf, event.byteB)
                    }

                    9 -> {
                        S16LE.write(buf, event.shortA)
                        S16LE.write(buf, event.shortB)
                    }
                }
                previous = event.timestamp
            }
        }
    }

object BatchInputEventsPacketCodec : PacketCodec<BatchInputEventsPacket>() {
    override fun CodecScope<BatchInputEventsPacket>.body(): BatchInputEventsPacket {
        val hasEvents = field(Bool) { it.hasEvents }
        val events = field(EventsCodec) { it.events }
        return BatchInputEventsPacket(hasEvents, events)
    }
}
