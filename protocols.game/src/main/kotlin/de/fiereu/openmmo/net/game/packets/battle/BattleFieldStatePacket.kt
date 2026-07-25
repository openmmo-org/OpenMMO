package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.utils.hexToBytes

/**
 * The field state for a wild encounter (opcode 0x30). Each side sends a monster count that sizes
 * the client's slot array followed by that many monster blocks. The player side closes with the
 * active monster's detail, the wild side carries a single wild block.
 */
data class BattleFieldStatePacket(
    val playerName: String,
    val playerId: Long,
    val playerParty: List<BattleMonBlock>,
    val activeSlot: Int,
    val wildParty: List<BattleMonBlock>,
)

private val PREAMBLE = "02000000000000000000000000ff000000000016000000ff00200006".hexToBytes()
private val APPEARANCE = "ff00024c031aac0f00038001a400040000000000".hexToBytes()

object BattleFieldStatePacketCodec : PacketCodec<BattleFieldStatePacket>() {
  override fun CodecScope<BattleFieldStatePacket>.body(): BattleFieldStatePacket {
    constant(PREAMBLE)
    val playerName = field(Utf16LeNullTerminated) { it.playerName }
    reserved(0)
    val playerId = field(S64LE) { it.playerId }
    constant(APPEARANCE)
    constant(1) // player side marker
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
    // Closes the player side after the active detail: [01][active level][00 x5].
    constant(1)
    field(S8) { it.playerParty[it.activeSlot].level }
    padding(5)
    constant(1) // wild side marker
    val wildCount = field(U8) { it.wildParty.size }
    reserved(0)
    val wildParty = List(wildCount) { i -> field(BattleWildBlockCodec) { it.wildParty[i] } }
    return BattleFieldStatePacket(playerName, playerId, party, active.slot, wildParty)
  }
}
