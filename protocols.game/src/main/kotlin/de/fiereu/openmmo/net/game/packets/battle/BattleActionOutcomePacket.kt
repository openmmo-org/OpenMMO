package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.PokemonCodec

data class BattleActionRewardEntry(
    val itemId: Int,
    val quantity: Short,
)

data class BattleActionParticipant(
    val entityId: Long,
    val kind: Int,
    val intA: Int,
    val intB: Int,
    val intC: Int,
    val shortA: Short,
    val critByte: Byte?,
    val critShort: Short?,
    val capturedShort: Short?,
    val capturedByte: Byte?,
    val xpShort: Short?,
    val xpByteA: Byte?,
    val xpByteB: Byte?,
    val pokemon: Pokemon?,
    val statStages: List<Short>?,
)

private val BattleRewardEntryCodec: Codec<BattleActionRewardEntry> =
    object : PacketCodec<BattleActionRewardEntry>() {
      override fun CodecScope<BattleActionRewardEntry>.body(): BattleActionRewardEntry {
        val itemId = field(S32LE) { it.itemId }
        val quantity = field(S16LE) { it.quantity }
        return BattleActionRewardEntry(itemId, quantity)
      }
    }

private class BattleParticipantCodec(private val isCapture: Boolean) :
    PacketCodec<BattleActionParticipant>() {
  override fun CodecScope<BattleActionParticipant>.body(): BattleActionParticipant {
    val entityId = field(S64LE) { it.entityId }
    val kind = field(U8) { it.kind }
    val intA = field(S32LE) { it.intA }
    val intB = field(S32LE) { it.intB }
    val intC = field(S32LE) { it.intC }
    val shortA = field(S16LE) { it.shortA }
    var critByte: Byte? = null
    var critShort: Short? = null
    if (isCapture) {
      critByte = field(S8) { it.critByte!! }
      critShort = field(S16LE) { it.critShort!! }
    }
    var capturedShort: Short? = null
    var capturedByte: Byte? = null
    if (isCapture || kind == 1) {
      capturedShort = field(S16LE) { it.capturedShort!! }
      capturedByte = field(S8) { it.capturedByte!! }
    }
    var xpShort: Short? = null
    var xpByteA: Byte? = null
    var xpByteB: Byte? = null
    if (isCapture && kind == 0) {
      xpShort = field(S16LE) { it.xpShort!! }
      xpByteA = field(S8) { it.xpByteA!! }
      xpByteB = field(S8) { it.xpByteB!! }
    }
    var pokemon: Pokemon? = null
    var statStages: List<Short>? = null
    if (kind == 0) {
      val present = field(U8) { if (it.pokemon != null) 1 else 0 }
      if (present == 1) {
        pokemon = field(PokemonCodec) { it.pokemon!! }
        statStages = (0 until 6).map { i -> field(S16LE) { it.statStages!![i] } }
      }
    }
    return BattleActionParticipant(
        entityId,
        kind,
        intA,
        intB,
        intC,
        shortA,
        critByte,
        critShort,
        capturedShort,
        capturedByte,
        xpShort,
        xpByteA,
        xpByteB,
        pokemon,
        statStages)
  }
}

data class BattleActionOutcomePacket(
    val side: Byte,
    val category: Int,
    val value: Short,
    val extra: Int,
    val participants: List<BattleActionParticipant>,
    val rewards: List<BattleActionRewardEntry>?,
)

object BattleActionOutcomePacketCodec : PacketCodec<BattleActionOutcomePacket>() {
  override fun CodecScope<BattleActionOutcomePacket>.body(): BattleActionOutcomePacket {
    val side = field(S8) { it.side }
    val category = field(U8) { it.category }
    val value = field(S16LE) { it.value }
    val extra = field(S32LE) { it.extra }
    val isCapture = category == 2
    val participantCodec = BattleParticipantCodec(isCapture)
    val count = field(U8) { it.participants.size }
    val participants = (0 until count).map { i -> field(participantCodec) { it.participants[i] } }
    val rewards =
        if (category == 1) {
          val rewardCount = field(U8) { it.rewards!!.size }
          (0 until rewardCount).map { i -> field(BattleRewardEntryCodec) { it.rewards!![i] } }
        } else null
    return BattleActionOutcomePacket(side, category, value, extra, participants, rewards)
  }
}
