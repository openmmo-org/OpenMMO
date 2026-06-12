package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BreedingStatContribution(
    val source: Byte,
    val weight: Float,
    val amount: Int,
)

data class BreedingStatEntry(
    val guaranteed: Boolean,
    val statId: Short,
    val contributions: List<BreedingStatContribution>,
)

data class BreedingForecastPacket(
    val parentA: Long,
    val parentB: Long,
    val hasPreview: Boolean,
    val species: Short,
    val form: Byte,
    val statEntries: List<BreedingStatEntry>,
    val shininessTypes: List<Byte>,
    val valueIds: List<Short>,
    val valueSources: List<Byte>,
    val gender: Byte,
    val nature: Short,
    val shiny: Boolean,
    val cost: Int,
    val secondaryCost: Int,
)

private object BreedingStatContributionCodec : PacketCodec<BreedingStatContribution>() {
    override fun CodecScope<BreedingStatContribution>.body(): BreedingStatContribution {
        val source = field(S8, BreedingStatContribution::source)
        val weight = field(F32LE, BreedingStatContribution::weight)
        val amount = field(S32LE, BreedingStatContribution::amount)
        return BreedingStatContribution(source, weight, amount)
    }
}

private val BreedingStatContributionListPrefixedU8: Codec<List<BreedingStatContribution>> =
    object : Codec<List<BreedingStatContribution>> {
        override fun read(buf: ReadBuffer): List<BreedingStatContribution> {
            val n = U8.read(buf)
            return List(n) { BreedingStatContributionCodec.read(buf) }
        }

        override fun write(buf: WriteBuffer, value: List<BreedingStatContribution>) {
            U8.write(buf, value.size)
            value.forEach { BreedingStatContributionCodec.write(buf, it) }
        }
    }

private object BreedingStatEntryCodec : PacketCodec<BreedingStatEntry>() {
    override fun CodecScope<BreedingStatEntry>.body(): BreedingStatEntry {
        val guaranteed = field(Bool, BreedingStatEntry::guaranteed)
        val statId = field(S16LE, BreedingStatEntry::statId)
        val contributions =
            field(BreedingStatContributionListPrefixedU8, BreedingStatEntry::contributions)
        return BreedingStatEntry(guaranteed, statId, contributions)
    }
}

object BreedingForecastPacketCodec : PacketCodec<BreedingForecastPacket>() {
    override fun CodecScope<BreedingForecastPacket>.body(): BreedingForecastPacket {
        val parentA = field(S64LE, BreedingForecastPacket::parentA)
        val parentB = field(S64LE, BreedingForecastPacket::parentB)
        val hasPreview = field(Bool, BreedingForecastPacket::hasPreview)
        if (!hasPreview) {
            return BreedingForecastPacket(
                parentA,
                parentB,
                hasPreview,
                0,
                0,
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList(),
                0,
                0,
                false,
                0,
                0,
            )
        }
        val species = field(S16LE, BreedingForecastPacket::species)
        val form = field(S8, BreedingForecastPacket::form)
        val statCount = field(U8) { it.statEntries.size }
        val statEntries = List(statCount) { i -> field(BreedingStatEntryCodec) { it.statEntries[i] } }
        val shininessCount = field(U8) { it.shininessTypes.size }
        val shininessTypes = List(shininessCount) { i -> field(S8) { it.shininessTypes[i] } }
        val valueCount = field(U8) { it.valueIds.size }
        val valueIds = List(valueCount) { i -> field(S16LE) { it.valueIds[i] } }
        val valueSources = List(valueCount) { i -> field(S8) { it.valueSources[i] } }
        val gender = field(S8, BreedingForecastPacket::gender)
        val nature = field(S16LE, BreedingForecastPacket::nature)
        val shiny = field(Bool, BreedingForecastPacket::shiny)
        val cost = field(S32LE, BreedingForecastPacket::cost)
        val secondaryCost = field(S32LE, BreedingForecastPacket::secondaryCost)
        return BreedingForecastPacket(
            parentA,
            parentB,
            hasPreview,
            species,
            form,
            statEntries,
            shininessTypes,
            valueIds,
            valueSources,
            gender,
            nature,
            shiny,
            cost,
            secondaryCost,
        )
    }
}
