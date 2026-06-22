package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class OverworldWeatherControlPacket(
    val effectType: Byte,
    val skyId: Short?,
    val skyVariant: Short?,
    val particleId: Short?,
    val particleVariant: Short?,
    val paramA: Float?,
    val paramB: Float?,
    val paramC: Float?,
    val paramD: Float?,
    val duration: Short?,
)

object OverworldWeatherControlPacketCodec : PacketCodec<OverworldWeatherControlPacket>() {
  override fun CodecScope<OverworldWeatherControlPacket>.body(): OverworldWeatherControlPacket {
    val effectType = field(S8) { it.effectType }
    val t = effectType.toInt()
    var skyId: Short? = null
    var skyVariant: Short? = null
    var particleId: Short? = null
    var particleVariant: Short? = null
    var paramA: Float? = null
    var paramB: Float? = null
    var paramC: Float? = null
    var paramD: Float? = null
    var duration: Short? = null
    if (t == 0) {
      skyId = field(S16LE) { it.skyId!! }
      skyVariant = field(S16LE) { it.skyVariant!! }
    } else if (t == 4) {
      particleId = field(S16LE) { it.particleId!! }
      particleVariant = field(S16LE) { it.particleVariant!! }
      paramA = field(F32LE) { it.paramA!! }
      paramB = field(F32LE) { it.paramB!! }
      paramC = field(F32LE) { it.paramC!! }
      paramD = field(F32LE) { it.paramD!! }
      duration = field(S16LE) { it.duration!! }
    } else if (t == 5) {
      duration = field(S16LE) { it.duration!! }
    }
    return OverworldWeatherControlPacket(
        effectType = effectType,
        skyId = skyId,
        skyVariant = skyVariant,
        particleId = particleId,
        particleVariant = particleVariant,
        paramA = paramA,
        paramB = paramB,
        paramC = paramC,
        paramD = paramD,
        duration = duration,
    )
  }
}
