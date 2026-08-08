package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.utils.hexToBytes

/** Who the player is fighting. The byte rides twice, before the name and again after the id. */
enum class OpposingSide(val wireValue: Byte) {
  WILD(0xFF.toByte()),
  TRAINER(0x04);

  companion object {
    fun byWireValue(value: Byte): OpposingSide? = entries.find { it.wireValue == value }
  }
}

/**
 * The field state that opens a battle (opcode 0x30). Each side sends a monster count that sizes the
 * client's slot array followed by that many blocks. The player side closes with its active
 * monster's detail, the opposing side closes with one shared detail for whichever monster is out.
 *
 * A trainer battle differs from a wild one in more than values: [opposing] flips two bytes, and the
 * opposing side carries [trainerId] plus two extra halfwords that a wild battle leaves out.
 */
data class BattleFieldStatePacket(
    val playerName: String,
    val playerId: Long,
    /** The player's own overworld appearance, the same bytes their 0x05 spawn carries. */
    val playerAppearance: ByteArray,
    /** Picks the battle backdrop. Outdoors is 0, forest 9, caves 12. */
    val background: Byte,
    val opposing: OpposingSide,
    /** The decomp trainer id, which the client draws the name and sprite from. Zero on a wild. */
    val trainerId: Short,
    val playerParty: List<BattleMonBlock>,
    val activeSlot: Int,
    val opponentParty: List<BattleOpponentBlock>,
    val opponentActiveSlot: Int,
) {
  init {
    require(playerAppearance.size == APPEARANCE_SIZE) {
      "A field state carries exactly $APPEARANCE_SIZE appearance bytes"
    }
  }

  // ByteArray breaks the generated equals, and the fixtures compare whole packets.
  override fun equals(other: Any?): Boolean =
      this === other ||
          (other is BattleFieldStatePacket &&
              playerName == other.playerName &&
              playerId == other.playerId &&
              playerAppearance.contentEquals(other.playerAppearance) &&
              background == other.background &&
              opposing == other.opposing &&
              trainerId == other.trainerId &&
              playerParty == other.playerParty &&
              activeSlot == other.activeSlot &&
              opponentParty == other.opponentParty &&
              opponentActiveSlot == other.opponentActiveSlot)

  override fun hashCode(): Int =
      listOf<Any>(
              playerName,
              playerId,
              playerAppearance.contentHashCode(),
              background,
              opposing,
              trainerId,
              playerParty,
              activeSlot,
              opponentParty,
              opponentActiveSlot)
          .hashCode()

  companion object {
    const val APPEARANCE_SIZE = 14
  }
}

private val HEAD = "0200000000".hexToBytes()
private val AFTER_BACKGROUND = "00000000000000ff000000000016000000".hexToBytes()
private val AFTER_OPPOSING = "00200006".hexToBytes()

object BattleFieldStatePacketCodec : PacketCodec<BattleFieldStatePacket>() {
  override fun CodecScope<BattleFieldStatePacket>.body(): BattleFieldStatePacket {
    constant(HEAD)
    val background = field(S8) { it.background }
    constant(AFTER_BACKGROUND)
    val opposingByte = field(S8) { it.opposing.wireValue }
    val opposing =
        OpposingSide.byWireValue(opposingByte)
            ?: throw MalformedPacketException("Unknown opposing side $opposingByte")
    constant(AFTER_OPPOSING)
    val playerName = field(Utf16LeNullTerminated) { it.playerName }
    reserved(0)
    val playerId = field(S64LE) { it.playerId }
    field(S8) { it.opposing.wireValue }
    val appearance =
        field(fixedBytes(BattleFieldStatePacket.APPEARANCE_SIZE)) { it.playerAppearance }
    padding(5)

    constant(1)
    val partyCount = field(U8) { it.playerParty.size }
    reserved(0)
    val party =
        List(partyCount) { i ->
          val block = field(BattleFullBlockCodec) { it.playerParty[i] }
          // The trailing byte flags the last party block; 0 says another block follows.
          field(S8) { if (i == it.playerParty.lastIndex) 1.toByte() else 0.toByte() }
          block
        }
    val active =
        field(BattleActiveDetailCodec) {
          BattleActiveDetail.of(it.activeSlot, it.playerParty[it.activeSlot])
        }

    // Opens the opposing side. A trainer adds its id and two more halfwords here.
    field(S8) { if (it.opposing == OpposingSide.TRAINER) 2.toByte() else 1.toByte() }
    constant(6)
    reserved(0)
    val trainerId =
        if (opposing == OpposingSide.TRAINER) field(S16LE) { it.trainerId } else 0.toShort()
    padding(4)
    if (opposing == OpposingSide.TRAINER) padding(2)

    constant(1)
    val opponentCount = field(U8) { it.opponentParty.size }
    reserved(0)
    val opponents =
        List(opponentCount) { i ->
          val block = field(BattleOpponentBlockCodec) { it.opponentParty[i] }
          field(S8) { if (i == it.opponentParty.lastIndex) 1.toByte() else 0.toByte() }
          block
        }
    val opponentActive =
        field(BattleActiveDetailCodec) {
          val mon = it.opponentParty[it.opponentActiveSlot]
          BattleActiveDetail(mon.slot, mon.species, mon.level, mon.gender)
        }
    padding(4)

    return BattleFieldStatePacket(
        playerName,
        playerId,
        appearance,
        background,
        opposing,
        trainerId,
        party,
        active.slot,
        opponents,
        opponentActive.slot,
    )
  }
}
