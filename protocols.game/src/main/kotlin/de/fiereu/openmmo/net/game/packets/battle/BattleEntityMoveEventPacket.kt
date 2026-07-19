package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleMoveDataEntry(
    val entityId: Long,
    val value: Short,
    val actionCount: Int,
)

private val BattleMoveDataEntryCodec: Codec<BattleMoveDataEntry> =
    object : PacketCodec<BattleMoveDataEntry>() {
      override fun CodecScope<BattleMoveDataEntry>.body(): BattleMoveDataEntry {
        val entityId = field(S64LE) { it.entityId }
        val value = field(S16LE) { it.value }
        val actionCount = field(U8) { it.actionCount }
        return BattleMoveDataEntry(entityId, value, actionCount)
      }
    }

data class BattleEntityMoveEventPacket(
    val entityId: Long,
    val value: Short,
    val kind: Byte,
    val entries: List<BattleMoveDataEntry>,
)

object BattleEntityMoveEventPacketCodec : PacketCodec<BattleEntityMoveEventPacket>() {
  override fun CodecScope<BattleEntityMoveEventPacket>.body(): BattleEntityMoveEventPacket {
    val entityId = field(S64LE) { it.entityId }
    val value = field(S16LE) { it.value }
    val kind = field(S8) { it.kind }
    val entries = field(BattleMoveDataEntryCodec.listPrefixed(U8)) { it.entries }
    return BattleEntityMoveEventPacket(entityId, value, kind, entries)
  }
}
