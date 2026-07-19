package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.enums.BattleAction

// For SWITCH, moveOrItemId carries the target party index.
data class BattleActionSelectPacket(
    val slotRefPacked: Byte,
    val action: BattleAction,
    val moveOrItemId: Short,
    val targetEntityId: Long,
    val extraFlag: Byte,
)

private val BattleActionSelectBody: Codec<BattleActionSelectPacket> =
    object : Codec<BattleActionSelectPacket> {
      override fun read(buf: ReadBuffer): BattleActionSelectPacket {
        val slotRefPacked = S8.read(buf)
        val action =
            BattleAction.fromId(S8.read(buf))
                ?: throw MalformedPacketException("unknown battle action kind")
        return when (action) {
          BattleAction.MOVE -> {
            val moveId = S16LE.read(buf)
            val extraFlag = S8.read(buf)
            BattleActionSelectPacket(slotRefPacked, action, moveId, 0, extraFlag)
          }
          BattleAction.ITEM -> {
            val itemId = S16LE.read(buf)
            val targetEntityId = S64LE.read(buf)
            val extraFlag = S8.read(buf)
            BattleActionSelectPacket(slotRefPacked, action, itemId, targetEntityId, extraFlag)
          }
          BattleAction.SWITCH -> {
            val partyIndex = S16LE.read(buf)
            BattleActionSelectPacket(slotRefPacked, action, partyIndex, 0, 0)
          }
          BattleAction.RUN -> BattleActionSelectPacket(slotRefPacked, action, 0, 0, 0)
        }
      }

      override fun write(buf: WriteBuffer, value: BattleActionSelectPacket) {
        S8.write(buf, value.slotRefPacked)
        S8.write(buf, value.action.id)
        when (value.action) {
          BattleAction.MOVE -> {
            S16LE.write(buf, value.moveOrItemId)
            S8.write(buf, value.extraFlag)
          }
          BattleAction.ITEM -> {
            S16LE.write(buf, value.moveOrItemId)
            S64LE.write(buf, value.targetEntityId)
            S8.write(buf, value.extraFlag)
          }
          BattleAction.SWITCH -> S16LE.write(buf, value.moveOrItemId)
          BattleAction.RUN -> {
            // RUN carries no tail bytes.
          }
        }
      }
    }

object BattleActionSelectPacketCodec : PacketCodec<BattleActionSelectPacket>() {
  override fun CodecScope<BattleActionSelectPacket>.body(): BattleActionSelectPacket {
    return field(BattleActionSelectBody) { it }
  }
}
