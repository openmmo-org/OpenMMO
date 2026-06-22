package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

private val SimulationConfigPayload: Codec<ByteArray> =
    object : Codec<ByteArray> {
      override fun read(buf: ReadBuffer): ByteArray {
        val arr = ByteArray(buf.remaining())
        if (arr.isNotEmpty()) buf.readBytes(arr)
        return arr
      }

      override fun write(buf: WriteBuffer, value: ByteArray) {
        if (value.isNotEmpty()) buf.writeBytes(value)
      }
    }

data class BattleSimulationRequestPacket(val simulationConfig: ByteArray) {
  override fun equals(other: Any?): Boolean =
      other is BattleSimulationRequestPacket &&
          simulationConfig.contentEquals(other.simulationConfig)

  override fun hashCode(): Int = simulationConfig.contentHashCode()
}

object BattleSimulationRequestPacketCodec : PacketCodec<BattleSimulationRequestPacket>() {
  override fun CodecScope<BattleSimulationRequestPacket>.body(): BattleSimulationRequestPacket {
    val simulationConfig = field(SimulationConfigPayload) { it.simulationConfig }
    return BattleSimulationRequestPacket(simulationConfig)
  }
}
