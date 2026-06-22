package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

private val BreedingPartyEntityIds: Codec<List<Long>> =
    object : Codec<List<Long>> {
      override fun read(buf: ReadBuffer): List<Long> {
        val count = (buf.remaining() - 2) / 8
        val result = ArrayList<Long>(count)
        repeat(count) {
          var v = 0L
          for (i in 0 until 8) {
            v = v or ((buf.readByte().toLong() and 0xFF) shl (i * 8))
          }
          result.add(v)
        }
        return result
      }

      override fun write(buf: WriteBuffer, value: List<Long>) {
        for (id in value) {
          for (i in 0 until 8) {
            buf.writeByte(((id shr (i * 8)) and 0xFF).toByte())
          }
        }
      }
    }

data class SubmitBreedingPartyPacket(
    val sessionId: Byte,
    val pokemonEntityIds: List<Long>,
    val slotIndex: Byte,
    val stateFlag: Byte,
)

object SubmitBreedingPartyPacketCodec : PacketCodec<SubmitBreedingPartyPacket>() {
  override fun CodecScope<SubmitBreedingPartyPacket>.body(): SubmitBreedingPartyPacket {
    val sessionId = field(S8) { it.sessionId }
    val pokemonEntityIds = field(BreedingPartyEntityIds) { it.pokemonEntityIds }
    val slotIndex = field(S8) { it.slotIndex }
    val stateFlag = field(S8) { it.stateFlag }
    return SubmitBreedingPartyPacket(sessionId, pokemonEntityIds, slotIndex, stateFlag)
  }
}
