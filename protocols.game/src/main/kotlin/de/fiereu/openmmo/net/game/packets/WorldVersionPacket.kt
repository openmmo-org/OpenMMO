package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class WorldVersionPacket(
    val major: Short,
    val build: Short,
    val minor: Short,
)

object WorldVersionPacketCodec : PacketCodec<WorldVersionPacket>() {
    override fun CodecScope<WorldVersionPacket>.body(): WorldVersionPacket {
        val major = field(S16LE) { it.major }
        val build = field(S16LE) { it.build }
        val minor = field(S16LE) { it.minor }
        return WorldVersionPacket(major, build, minor)
    }
}
