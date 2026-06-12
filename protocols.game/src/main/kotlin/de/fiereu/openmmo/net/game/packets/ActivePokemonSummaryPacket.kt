package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SummaryRankingEntry(
    val entityId: Long,
    val rank: Byte,
    val flags: Byte,
    val name: String,
    val value1: Short,
    val species: Short,
    val ribbons: Long,
    val value2: Byte,
    val value3: Int,
    val value4: Byte,
    val flag1: Boolean,
    val flag2: Boolean,
)

data class SummaryAppearance(
    val name: String,
    val gender: Byte,
    val formId: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

data class SummaryAbility(
    val slot: Byte,
    val sourceId: Long,
    val appearance: SummaryAppearance,
)

data class SummaryMove(
    val moveId: Byte,
    val slot: Byte,
    val locked: Boolean,
    val intA: Int,
    val intB: Int,
    val intC: Int,
    val shortA: Short,
    val shortB: Short,
    val byteA: Byte,
    val unlockedShort: Short,
    val boolA: Boolean,
    val boolB: Boolean,
    val unlockedByteA: Byte,
    val boolC: Boolean,
    val boolD: Boolean,
    val unlockedByteB: Byte,
    val unlockedByteC: Byte,
    val unlockedByteD: Byte,
)

data class ActivePokemonSummaryPacket(
    val pokemon: SummaryRankingEntry,
    val abilities: List<SummaryAbility>,
    val moves: List<SummaryMove>,
)

private object SummaryRankingEntryCodec : PacketCodec<SummaryRankingEntry>() {
    override fun CodecScope<SummaryRankingEntry>.body(): SummaryRankingEntry {
        val entityId = field(S64LE, SummaryRankingEntry::entityId)
        val rank = field(S8, SummaryRankingEntry::rank)
        val flags = field(S8, SummaryRankingEntry::flags)
        val name = field(Utf16LeNullTerminated, SummaryRankingEntry::name)
        val value1 = field(S16LE, SummaryRankingEntry::value1)
        val species = field(S16LE, SummaryRankingEntry::species)
        reserved(byte = 0)
        val ribbons = field(S64LE, SummaryRankingEntry::ribbons)
        val value2 = field(S8, SummaryRankingEntry::value2)
        val value3 = field(S32LE, SummaryRankingEntry::value3)
        val value4 = field(S8, SummaryRankingEntry::value4)
        val flag1 = field(Bool, SummaryRankingEntry::flag1)
        val flag2 = field(Bool, SummaryRankingEntry::flag2)
        reserved(byte = 0)
        return SummaryRankingEntry(
            entityId,
            rank,
            flags,
            name,
            value1,
            species,
            ribbons,
            value2,
            value3,
            value4,
            flag1,
            flag2,
        )
    }
}

private object SummaryAppearanceCodec : PacketCodec<SummaryAppearance>() {
    override fun CodecScope<SummaryAppearance>.body(): SummaryAppearance {
        val name = field(Utf16LeNullTerminated, SummaryAppearance::name)
        val gender = field(S8, SummaryAppearance::gender)
        val formId = field(S32LE, SummaryAppearance::formId)
        val kind = field(S8, SummaryAppearance::kind)
        val palettePack = field(S8, SummaryAppearance::palettePack)
        val slots = field(S16LE.repeat(4), SummaryAppearance::slots)
        return SummaryAppearance(name, gender, formId, kind, palettePack, slots)
    }
}

private object SummaryAbilityCodec : PacketCodec<SummaryAbility>() {
    override fun CodecScope<SummaryAbility>.body(): SummaryAbility {
        val slot = field(S8, SummaryAbility::slot)
        val sourceId = field(S64LE, SummaryAbility::sourceId)
        val appearance = field(SummaryAppearanceCodec, SummaryAbility::appearance)
        return SummaryAbility(slot, sourceId, appearance)
    }
}

private object SummaryMoveCodec : PacketCodec<SummaryMove>() {
    override fun CodecScope<SummaryMove>.body(): SummaryMove {
        val moveId = field(S8, SummaryMove::moveId)
        val slot = field(S8, SummaryMove::slot)
        val locked = field(Bool, SummaryMove::locked)
        if (!locked) {
            val intA = field(S32LE, SummaryMove::intA)
            val intB = field(S32LE, SummaryMove::intB)
            val intC = field(S32LE, SummaryMove::intC)
            val shortA = field(S16LE, SummaryMove::shortA)
            val shortB = field(S16LE, SummaryMove::shortB)
            val byteA = field(S8, SummaryMove::byteA)
            return SummaryMove(
                moveId,
                slot,
                locked,
                intA,
                intB,
                intC,
                shortA,
                shortB,
                byteA,
                0,
                false,
                false,
                0,
                false,
                false,
                0,
                0,
                0,
            )
        }
        val unlockedShort = field(S16LE, SummaryMove::unlockedShort)
        val boolA = field(Bool, SummaryMove::boolA)
        val boolB = field(Bool, SummaryMove::boolB)
        val unlockedByteA = field(S8, SummaryMove::unlockedByteA)
        val boolC = field(Bool, SummaryMove::boolC)
        val boolD = field(Bool, SummaryMove::boolD)
        val unlockedByteB = field(S8, SummaryMove::unlockedByteB)
        val unlockedByteC = field(S8, SummaryMove::unlockedByteC)
        val unlockedByteD = field(S8, SummaryMove::unlockedByteD)
        return SummaryMove(
            moveId,
            slot,
            locked,
            0,
            0,
            0,
            0,
            0,
            0,
            unlockedShort,
            boolA,
            boolB,
            unlockedByteA,
            boolC,
            boolD,
            unlockedByteB,
            unlockedByteC,
            unlockedByteD,
        )
    }
}

private val SummaryAbilityListPrefixedU8: Codec<List<SummaryAbility>> =
    object : Codec<List<SummaryAbility>> {
        override fun read(buf: ReadBuffer): List<SummaryAbility> {
            val n = U8.read(buf)
            return List(n) { SummaryAbilityCodec.read(buf) }
        }

        override fun write(buf: WriteBuffer, value: List<SummaryAbility>) {
            U8.write(buf, value.size)
            value.forEach { SummaryAbilityCodec.write(buf, it) }
        }
    }

private val SummaryMoveListPrefixedU8: Codec<List<SummaryMove>> =
    object : Codec<List<SummaryMove>> {
        override fun read(buf: ReadBuffer): List<SummaryMove> {
            val n = U8.read(buf)
            return List(n) { SummaryMoveCodec.read(buf) }
        }

        override fun write(buf: WriteBuffer, value: List<SummaryMove>) {
            U8.write(buf, value.size)
            value.forEach { SummaryMoveCodec.write(buf, it) }
        }
    }

object ActivePokemonSummaryPacketCodec : PacketCodec<ActivePokemonSummaryPacket>() {
    override fun CodecScope<ActivePokemonSummaryPacket>.body(): ActivePokemonSummaryPacket {
        val pokemon = field(SummaryRankingEntryCodec, ActivePokemonSummaryPacket::pokemon)
        val abilities = field(SummaryAbilityListPrefixedU8, ActivePokemonSummaryPacket::abilities)
        val moves = field(SummaryMoveListPrefixedU8, ActivePokemonSummaryPacket::moves)
        return ActivePokemonSummaryPacket(pokemon, abilities, moves)
    }
}
