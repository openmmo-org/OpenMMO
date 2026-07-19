package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleMoveAppearance(
    val name: String,
    val gender: Byte,
    val id: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

private val BattleMoveAppearanceCodec: Codec<BattleMoveAppearance> =
    object : PacketCodec<BattleMoveAppearance>() {
      override fun CodecScope<BattleMoveAppearance>.body(): BattleMoveAppearance {
        val name = field(Utf16LeNullTerminated) { it.name }
        val gender = field(S8) { it.gender }
        val id = field(S32LE) { it.id }
        val kind = field(S8) { it.kind }
        val palettePack = field(S8) { it.palettePack }
        val slots = field(S16LE.repeat(4)) { it.slots }
        return BattleMoveAppearance(name, gender, id, kind, palettePack, slots)
      }
    }

data class BattleMoveEntry(
    val moveId: Int,
    val tier: Byte,
    val int1: Int,
    val int2: Int,
    val appearances: List<BattleMoveAppearance>,
)

private val BattleMoveEntryCodec: Codec<BattleMoveEntry> =
    object : PacketCodec<BattleMoveEntry>() {
      override fun CodecScope<BattleMoveEntry>.body(): BattleMoveEntry {
        val moveId = field(S32LE) { it.moveId }
        val tier = field(S8) { it.tier }
        val int1 = field(S32LE) { it.int1 }
        val int2 = field(S32LE) { it.int2 }
        val appearances = field(BattleMoveAppearanceCodec.listPrefixed(U8)) { it.appearances }
        return BattleMoveEntry(moveId, tier, int1, int2, appearances)
      }
    }

data class BattleMoveListPacket(
    val actionId: Short,
    val slot: Byte,
    val moves: List<BattleMoveEntry>,
)

object BattleMoveListPacketCodec : PacketCodec<BattleMoveListPacket>() {
  override fun CodecScope<BattleMoveListPacket>.body(): BattleMoveListPacket {
    val actionId = field(S16LE) { it.actionId }
    val slot = field(S8) { it.slot }
    val moves = field(BattleMoveEntryCodec.listPrefixed(U8)) { it.moves }
    return BattleMoveListPacket(actionId, slot, moves)
  }
}
