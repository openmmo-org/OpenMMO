package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleRankingEntry(
    val entityId: Long,
    val flag1: Byte,
    val flag2: Byte,
    val name: String,
    val value1: Short,
    val genderId: Short,
    val ribbons: Long,
    val byte1: Byte,
    val int1: Int,
    val byte2: Byte,
    val bool1: Boolean,
    val bool2: Boolean,
)

private val BattleRankingEntryCodec: Codec<BattleRankingEntry> =
    object : PacketCodec<BattleRankingEntry>() {
        override fun CodecScope<BattleRankingEntry>.body(): BattleRankingEntry {
            val entityId = field(S64LE) { it.entityId }
            val flag1 = field(S8) { it.flag1 }
            val flag2 = field(S8) { it.flag2 }
            val name = field(de.fiereu.bytecodec.Utf16LeNullTerminated) { it.name }
            val value1 = field(S16LE) { it.value1 }
            val genderId = field(S16LE) { it.genderId }
            field(S8) { 0 }
            val ribbons = field(S64LE) { it.ribbons }
            val byte1 = field(S8) { it.byte1 }
            val int1 = field(S32LE) { it.int1 }
            val byte2 = field(S8) { it.byte2 }
            val bool1 = field(Bool) { it.bool1 }
            val bool2 = field(Bool) { it.bool2 }
            field(S8) { 0 }
            return BattleRankingEntry(
                entityId, flag1, flag2, name, value1, genderId, ribbons, byte1, int1, byte2, bool1,
                bool2
            )
        }
    }

data class BattleTierUpdate(
    val tierIndex: Byte,
    val unlocked: Boolean,
    val expiry: Long,
)

private val BattleTierUpdateCodec: Codec<BattleTierUpdate> =
    object : PacketCodec<BattleTierUpdate>() {
        override fun CodecScope<BattleTierUpdate>.body(): BattleTierUpdate {
            val tierIndex = field(S8) { it.tierIndex }
            val unlocked = field(Bool) { it.unlocked }
            val expiry = field(S64LE) { it.expiry }
            field(S64LE) { 0L }
            return BattleTierUpdate(tierIndex, unlocked, expiry)
        }
    }

data class BattleMoveTarget(
    val targetType: Byte,
    val short1: Short,
    val short2: Short,
    val short3: Short,
    val byte1: Byte,
    val byte2: Byte,
    val flag1: Boolean,
    val flag2: Boolean,
)

private val BattleMoveTargetCodec: Codec<BattleMoveTarget> =
    object : PacketCodec<BattleMoveTarget>() {
        override fun CodecScope<BattleMoveTarget>.body(): BattleMoveTarget {
            val targetType = field(S8) { it.targetType }
            return when (targetType.toInt()) {
                0 -> {
                    val short1 = field(S16LE) { it.short1 }
                    val short2 = field(S16LE) { it.short2 }
                    val short3 = field(S16LE) { it.short3 }
                    BattleMoveTarget(targetType, short1, short2, short3, 0, 0, false, false)
                }

                1 -> {
                    val short1 = field(S16LE) { it.short1 }
                    field(S8) { 0 }
                    val byte1 = field(S8) { it.byte1 }
                    val byte2 = field(S8) { it.byte2 }
                    val flag1 = field(Bool) { it.flag1 }
                    val flag2 = field(Bool) { it.flag2 }
                    val short2 = field(S16LE) { it.short2 }
                    BattleMoveTarget(targetType, short1, short2, 0, byte1, byte2, flag1, flag2)
                }

                else -> {
                    val short1 = field(S16LE) { it.short1 }
                    val short2 = field(S16LE) { it.short2 }
                    BattleMoveTarget(targetType, short1, short2, 0, 0, 0, false, false)
                }
            }
        }
    }

data class BattleSelectablePokemon(
    val int1: Int,
    val int2: Int,
    val short1: Short,
    val short2: Short,
    val tierIndex: Byte,
    val targetsA: List<BattleMoveTarget>,
    val targetsB: List<BattleMoveTarget>,
)

private val BattleSelectablePokemonCodec: Codec<BattleSelectablePokemon> =
    object : PacketCodec<BattleSelectablePokemon>() {
        override fun CodecScope<BattleSelectablePokemon>.body(): BattleSelectablePokemon {
            val int1 = field(S32LE) { it.int1 }
            val int2 = field(S32LE) { it.int2 }
            val short1 = field(S16LE) { it.short1 }
            val short2 = field(S16LE) { it.short2 }
            val tierIndex = field(S8) { it.tierIndex }
            val targetsA = field(BattleMoveTargetCodec.listPrefixed(U8)) { it.targetsA }
            val targetsB = field(BattleMoveTargetCodec.listPrefixed(U8)) { it.targetsB }
            return BattleSelectablePokemon(int1, int2, short1, short2, tierIndex, targetsA, targetsB)
        }
    }

data class BattleStorageItem(
    val typeByte: Byte,
    val short1: Short,
    val short2: Short,
    val short3: Short,
    val short4: Short,
    val byte1: Byte,
    val pokemonByte: Byte,
    val boxByte: Byte,
)

private val BattleStorageItemCodec: Codec<BattleStorageItem> =
    object : PacketCodec<BattleStorageItem>() {
        override fun CodecScope<BattleStorageItem>.body(): BattleStorageItem {
            val typeByte = field(S8) { it.typeByte }
            val short1 = field(S16LE) { it.short1 }
            val short2 = field(S16LE) { it.short2 }
            val short3 = field(S16LE) { it.short3 }
            val short4 = field(S16LE) { it.short4 }
            val byte1 = field(S8) { it.byte1 }
            val pokemonByte = field(S8) { it.pokemonByte }
            val boxByte = field(S8) { it.boxByte }
            return BattleStorageItem(
                typeByte, short1, short2, short3, short4, byte1, pokemonByte, boxByte
            )
        }
    }

data class BattleScreenOpenPacket(
    val active: Boolean,
    val wild: Boolean,
    val participants: List<BattleRankingEntry>,
    val tierUpdates: List<BattleTierUpdate>,
    val pokemon: List<BattleSelectablePokemon>,
    val storageItems: List<BattleStorageItem>,
)

object BattleScreenOpenPacketCodec : PacketCodec<BattleScreenOpenPacket>() {
    override fun CodecScope<BattleScreenOpenPacket>.body(): BattleScreenOpenPacket {
        val active = field(U8) { if (it.active) 1 else 0 } == 1
        if (!active) {
            return BattleScreenOpenPacket(
                false, false, emptyList(), emptyList(), emptyList(), emptyList()
            )
        }
        val wild = field(U8) { if (it.wild) 1 else 0 } == 1
        var participants = emptyList<BattleRankingEntry>()
        var tierUpdates = emptyList<BattleTierUpdate>()
        var pokemon = emptyList<BattleSelectablePokemon>()
        if (wild) {
            participants = field(BattleRankingEntryCodec.listPrefixed(U8)) { it.participants }
        } else {
            tierUpdates = field(BattleTierUpdateCodec.listPrefixed(U8)) { it.tierUpdates }
            pokemon = field(BattleSelectablePokemonCodec.listPrefixed(U8)) { it.pokemon }
        }
        val storageItems = field(BattleStorageItemCodec.listPrefixed(U8)) { it.storageItems }
        return BattleScreenOpenPacket(active, wild, participants, tierUpdates, pokemon, storageItems)
    }
}
