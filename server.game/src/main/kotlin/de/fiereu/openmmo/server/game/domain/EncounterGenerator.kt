package de.fiereu.openmmo.server.game.domain

/** Encounter method names mirror the old clean-room TS model, not PokéMMO wire packets. */
enum class EncounterMethod {
  GRASS,
  SURF,
  OLD_ROD,
  GOOD_ROD,
  SUPER_ROD,
  ROCK_SMASH,
  GIFT,
  STATIC
}

data class EncounterSlot(
    val species: Int,
    val minLevel: Int,
    val maxLevel: Int,
    /** Relative weight within the method. */
    val rate: Int,
)

data class EncounterTable(
    val mapId: String,
    val method: EncounterMethod,
    val slots: List<EncounterSlot>,
)

fun interface EncounterTableSource {
  fun tablesFor(mapId: String, method: EncounterMethod): List<EncounterTable>
}

data class EncounterConfig(
    val shinyRate: Int,
    val shinyCharmRate: Int? = null,
    val customEncounters: Map<String, CustomEncounterConfig> = emptyMap(),
)

data class CustomEncounterConfig(
    val shinyBoost: Boolean = false,
    val catchRateModifier: Double? = null,
)

data class EncounterRollOptions(
    val mapId: String,
    val method: EncounterMethod,
    val tables: List<EncounterTable>,
    val speciesById: Map<Int, Species>,
    val config: EncounterConfig,
    val hasShinyCharm: Boolean = false,
    val rng: () -> Double = Math::random,
)

data class WildPokemonDraft(
    val speciesId: Int,
    val level: Int,
    val nature: Nature,
    val ability: String,
    val gender: Gender,
    val shiny: Boolean,
    val ivs: IVs,
    val pid: UInt,
    val friendship: Int,
    val metLocation: String,
)

object EncounterGenerator {
  fun generateEncounter(options: EncounterRollOptions): WildPokemonDraft? {
    val rng = options.rng
    val table =
        options.tables.firstOrNull { it.mapId == options.mapId && it.method == options.method }
    if (table == null || table.slots.isEmpty()) return null

    val slot = weightedSlot(table.slots, rng) ?: return null
    val species =
        options.speciesById[slot.species]
            ?: error("missing species data for encounter species ${slot.species}")
    val level = randomInt(slot.minLevel, slot.maxLevel, rng)
    val pid = ShinyRoller.randomU32(rng)
    return WildPokemonDraft(
        speciesId = species.id,
        level = level,
        nature = Nature.entries[(pid % Nature.entries.size.toUInt()).toInt()],
        ability = chooseAbility(species, rng),
        gender = rollGender(species.genderRatio, rng),
        shiny =
            ShinyRoller.isPidSelectedShiny(
                personalityId = pid,
                shinyRate = options.config.shinyRate,
                charmRate = options.config.shinyCharmRate,
                hasShinyCharm = options.hasShinyCharm,
                boosted = options.config.customEncounters[options.mapId]?.shinyBoost == true,
            ),
        ivs = randomIvs(rng),
        pid = pid,
        friendship = 70,
        metLocation = options.mapId,
    )
  }

  fun generateEncounter(
      mapId: String,
      method: EncounterMethod,
      tableSource: EncounterTableSource,
      speciesById: Map<Int, Species>,
      config: EncounterConfig,
      hasShinyCharm: Boolean = false,
      rng: () -> Double = Math::random,
  ): WildPokemonDraft? =
      generateEncounter(
          EncounterRollOptions(
              mapId = mapId,
              method = method,
              tables = tableSource.tablesFor(mapId, method),
              speciesById = speciesById,
              config = config,
              hasShinyCharm = hasShinyCharm,
              rng = rng,
          ),
      )

  fun shinyRateForMap(config: EncounterConfig, mapId: String, hasShinyCharm: Boolean): Int {
    val base =
        ShinyRoller.shinyRateWithCharm(config.shinyRate, config.shinyCharmRate, hasShinyCharm)
    return if (config.customEncounters[mapId]?.shinyBoost == true) maxOf(1, base / 2) else base
  }

  private fun weightedSlot(slots: List<EncounterSlot>, rng: () -> Double): EncounterSlot? {
    val total = slots.sumOf { maxOf(0, it.rate) }
    if (total <= 0) return null
    var roll = rng() * total
    for (slot in slots) {
      roll -= maxOf(0, slot.rate)
      if (roll < 0) return slot
    }
    return slots.lastOrNull()
  }

  private fun randomIvs(rng: () -> Double): IVs =
      IVs(
          hp = randomInt(0, 31, rng),
          atk = randomInt(0, 31, rng),
          def = randomInt(0, 31, rng),
          spa = randomInt(0, 31, rng),
          spd = randomInt(0, 31, rng),
          spe = randomInt(0, 31, rng),
      )

  private fun chooseAbility(species: Species, rng: () -> Double): String {
    if (species.abilities.isEmpty()) return "unknown"
    return species.abilities[
            (rng() * species.abilities.size).toInt().coerceIn(0, species.abilities.lastIndex)]
  }

  private fun rollGender(genderRatio: Double, rng: () -> Double): Gender {
    if (genderRatio < 0) return Gender.GENDERLESS
    return if (rng() < genderRatio) Gender.MALE else Gender.FEMALE
  }

  private fun randomInt(min: Int, max: Int, rng: () -> Double): Int {
    val lo = minOf(min, max)
    val hi = maxOf(min, max)
    return lo + (rng() * (hi - lo + 1)).toInt().coerceIn(0, hi - lo)
  }
}
