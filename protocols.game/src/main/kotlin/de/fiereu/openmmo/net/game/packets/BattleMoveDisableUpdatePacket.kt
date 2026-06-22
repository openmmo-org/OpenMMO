package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleMoveDisableEntry(
    val present: Boolean,
    val disabled: Boolean,
    val disableTurns: Short,
)

data class BattleMoveDisableSide(
    val entries: List<BattleMoveDisableEntry>,
)

data class BattleMoveDisableUpdatePacket(
    val sides: List<BattleMoveDisableSide>,
)

private val BattleMoveDisableEntryCodec: Codec<BattleMoveDisableEntry> =
    object : PacketCodec<BattleMoveDisableEntry>() {
      override fun CodecScope<BattleMoveDisableEntry>.body(): BattleMoveDisableEntry {
        val present = field(Bool) { it.present }
        return if (present) {
          val disabled = field(Bool) { it.disabled }
          val disableTurns = field(S16LE) { it.disableTurns }
          BattleMoveDisableEntry(true, disabled, disableTurns)
        } else {
          BattleMoveDisableEntry(false, false, 0)
        }
      }
    }

private val BattleMoveDisableSideCodec: Codec<BattleMoveDisableSide> =
    object : PacketCodec<BattleMoveDisableSide>() {
      override fun CodecScope<BattleMoveDisableSide>.body(): BattleMoveDisableSide {
        val entries = field(BattleMoveDisableEntryCodec.listPrefixed(U8)) { it.entries }
        return BattleMoveDisableSide(entries)
      }
    }

object BattleMoveDisableUpdatePacketCodec : PacketCodec<BattleMoveDisableUpdatePacket>() {
  override fun CodecScope<BattleMoveDisableUpdatePacket>.body(): BattleMoveDisableUpdatePacket {
    val sides = field(BattleMoveDisableSideCodec.repeat(2)) { it.sides }
    return BattleMoveDisableUpdatePacket(sides)
  }
}
