package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleActionSelectPacket(
    val slotRefPacked: Byte,
    val actionKindId: Byte,
    val payloadVariant: Int,
    val moveOrItemId: Short,
    val targetEntityId: Long,
    val extraFlag: Byte,
)

private val BattleActionSelectBody: Codec<BattleActionSelectPacket> =
    object : Codec<BattleActionSelectPacket> {
      override fun read(buf: ReadBuffer): BattleActionSelectPacket {
        val slotRefPacked = S8.read(buf)
        val actionKindId = S8.read(buf)
        return BattleActionSelectPacket(slotRefPacked, actionKindId, 0, 0, 0, 0)
      }

      override fun write(buf: WriteBuffer, value: BattleActionSelectPacket) {
        S8.write(buf, value.slotRefPacked)
        S8.write(buf, value.actionKindId)
        when (value.payloadVariant) {
          1 -> {
            S16LE.write(buf, value.moveOrItemId)
            S8.write(buf, value.extraFlag)
          }

          2,
          3 -> {
            S16LE.write(buf, value.moveOrItemId)
          }

          4 -> {
            S16LE.write(buf, value.moveOrItemId)
            S64LE.write(buf, value.targetEntityId)
            S8.write(buf, value.extraFlag)
          }
        }
      }
    }

object BattleActionSelectPacketCodec : PacketCodec<BattleActionSelectPacket>() {
  override fun CodecScope<BattleActionSelectPacket>.body(): BattleActionSelectPacket {
    return field(BattleActionSelectBody) { it }
  }
}
