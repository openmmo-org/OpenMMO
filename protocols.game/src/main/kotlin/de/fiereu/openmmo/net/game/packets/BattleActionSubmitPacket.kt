package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TierSlotSelection(val tierIndex: Byte, val slot: Byte)

sealed class BattleAction

data class SinglePokemonAction(val pokemonEntityId: Long, val moveSlot: Byte) : BattleAction()

data class MultiTierAction(val selections: List<TierSlotSelection>) : BattleAction()

private val BattleActionCodec: Codec<BattleAction> =
    object : Codec<BattleAction> {
      override fun read(buf: ReadBuffer): BattleAction {
        val first = buf.readByte().toInt() and 0xFF
        return if (first == 0) {
          var id = 0L
          for (i in 0 until 8) {
            id = id or ((buf.readByte().toLong() and 0xFF) shl (i * 8))
          }
          val moveSlot = buf.readByte()
          SinglePokemonAction(id, moveSlot)
        } else {
          val selections = ArrayList<TierSlotSelection>(first)
          repeat(first) {
            val tierIndex = buf.readByte()
            val slot = buf.readByte()
            selections.add(TierSlotSelection(tierIndex, slot))
          }
          MultiTierAction(selections)
        }
      }

      override fun write(buf: WriteBuffer, value: BattleAction) {
        when (value) {
          is SinglePokemonAction -> {
            buf.writeByte(0.toByte())
            var v = value.pokemonEntityId
            for (i in 0 until 8) {
              buf.writeByte((v and 0xFF).toByte())
              v = v ushr 8
            }
            buf.writeByte(value.moveSlot)
          }

          is MultiTierAction -> {
            buf.writeByte(value.selections.size.toByte())
            for (s in value.selections) {
              buf.writeByte(s.tierIndex)
              buf.writeByte(s.slot)
            }
          }
        }
      }
    }

data class BattleActionSubmitPacket(val action: BattleAction)

object BattleActionSubmitPacketCodec : PacketCodec<BattleActionSubmitPacket>() {
  override fun CodecScope<BattleActionSubmitPacket>.body(): BattleActionSubmitPacket {
    val action = field(BattleActionCodec) { it.action }
    return BattleActionSubmitPacket(action)
  }
}
