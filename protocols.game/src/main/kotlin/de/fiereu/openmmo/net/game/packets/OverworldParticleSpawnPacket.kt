package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class OverworldParticleSpawnPacket(
    val effectType: Byte,
    val x: Short,
    val y: Short,
    val z: Short,
    val variant: Short,
)

object OverworldParticleSpawnPacketCodec : PacketCodec<OverworldParticleSpawnPacket>() {
    override fun CodecScope<OverworldParticleSpawnPacket>.body(): OverworldParticleSpawnPacket {
        val effectType = field(S8) { it.effectType }
        val x = field(S16LE) { it.x }
        val y = field(S16LE) { it.y }
        val z = field(S16LE) { it.z }
        val variant = field(S16LE) { it.variant }
        return OverworldParticleSpawnPacket(effectType, x, y, z, variant)
    }
}
