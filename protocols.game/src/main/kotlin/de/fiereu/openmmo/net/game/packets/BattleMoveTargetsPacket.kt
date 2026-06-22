package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleTargetAppearance(
    val name: String,
    val gender: Byte,
    val id: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

private val BattleTargetAppearanceCodec: Codec<BattleTargetAppearance> =
    object : PacketCodec<BattleTargetAppearance>() {
        override fun CodecScope<BattleTargetAppearance>.body(): BattleTargetAppearance {
            val name = field(Utf16LeNullTerminated) { it.name }
            val gender = field(S8) { it.gender }
            val id = field(S32LE) { it.id }
            val kind = field(S8) { it.kind }
            val palettePack = field(S8) { it.palettePack }
            val slots = field(S16LE.repeat(4)) { it.slots }
            return BattleTargetAppearance(name, gender, id, kind, palettePack, slots)
        }
    }

data class BattleMoveTargetEntry(
    val entityId: Long,
    val weight: Float,
    val short1: Short,
    val int1: Int,
    val int2: Int,
    val appearance: BattleTargetAppearance,
)

private val BattleMoveTargetEntryCodec: Codec<BattleMoveTargetEntry> =
    object : PacketCodec<BattleMoveTargetEntry>() {
        override fun CodecScope<BattleMoveTargetEntry>.body(): BattleMoveTargetEntry {
            val entityId = field(S64LE) { it.entityId }
            val weight = field(F32LE) { it.weight }
            val short1 = field(S16LE) { it.short1 }
            val int1 = field(S32LE) { it.int1 }
            val int2 = field(S32LE) { it.int2 }
            val appearance = field(BattleTargetAppearanceCodec) { it.appearance }
            return BattleMoveTargetEntry(entityId, weight, short1, int1, int2, appearance)
        }
    }

data class BattleMoveTargetsPacket(
    val actionId: Int,
    val slot: Byte,
    val tier: Byte,
    val targets: List<BattleMoveTargetEntry>,
)

object BattleMoveTargetsPacketCodec : PacketCodec<BattleMoveTargetsPacket>() {
    override fun CodecScope<BattleMoveTargetsPacket>.body(): BattleMoveTargetsPacket {
        val actionId = field(S32LE) { it.actionId }
        val slot = field(S8) { it.slot }
        val tier = field(S8) { it.tier }
        val targets = field(BattleMoveTargetEntryCodec.listPrefixed(U16LE)) { it.targets }
        return BattleMoveTargetsPacket(actionId, slot, tier, targets)
    }
}
