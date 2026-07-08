package de.fiereu.openmmo.server.game.domain

data class BreedingParent(val pokemon: OwnedPokemon, val species: Species)

data class BreedingPairResult(
    val canBreed: Boolean,
    val reason: String? = null,
    val eggSpeciesId: Int? = null
)

data class EggDraft(
    val speciesId: Int,
    val nature: Nature,
    val ivs: IVs,
    val inheritedFrom: Map<StatKey, ParentSide>,
    val eggSteps: Int,
)

enum class ParentSide {
  MOTHER,
  FATHER
}

object BreedingRules {
  private val statKeys =
      listOf(StatKey.HP, StatKey.ATK, StatKey.DEF, StatKey.SPA, StatKey.SPD, StatKey.SPE)
  private val natures = Nature.entries.toList()

  fun canBreed(a: BreedingParent, b: BreedingParent): BreedingPairResult {
    if (EggGroup.UNDISCOVERED in a.species.eggGroups ||
        EggGroup.UNDISCOVERED in b.species.eggGroups) {
      return BreedingPairResult(false, "undiscovered egg group cannot breed")
    }
    val aDitto = isDitto(a.species)
    val bDitto = isDitto(b.species)
    if (aDitto && bDitto) return BreedingPairResult(false, "two Ditto cannot breed")
    if (a.pokemon.gender == Gender.GENDERLESS && !bDitto)
        return BreedingPairResult(false, "genderless Pokemon require Ditto")
    if (b.pokemon.gender == Gender.GENDERLESS && !aDitto)
        return BreedingPairResult(false, "genderless Pokemon require Ditto")
    if (!aDitto && !bDitto && a.pokemon.gender == b.pokemon.gender) {
      return BreedingPairResult(false, "parents must be opposite gender unless Ditto is involved")
    }
    if (!aDitto && !bDitto && !shareEggGroup(a.species.eggGroups, b.species.eggGroups)) {
      return BreedingPairResult(false, "no shared egg group")
    }
    return BreedingPairResult(true, eggSpeciesId = eggSpecies(a, b))
  }

  fun createEggDraft(
      mother: BreedingParent,
      father: BreedingParent,
      baseEggSteps: Int = 5120,
      destinyKnot: Boolean = false,
      rng: () -> Double = Math::random,
  ): EggDraft {
    val possible = canBreed(mother, father)
    if (!possible.canBreed || possible.eggSpeciesId == null)
        error(possible.reason ?: "parents cannot breed")
    val nature = inheritedNature(mother, father, rng)
    val (ivs, inheritedFrom) =
        inheritIvs(mother.pokemon.ivs, father.pokemon.ivs, if (destinyKnot) 5 else 3, rng)
    return EggDraft(possible.eggSpeciesId, nature, ivs, inheritedFrom, maxOf(1, baseEggSteps))
  }

  fun hatchFriendship(speciesBaseFriendship: Int = 70): Int = speciesBaseFriendship.coerceIn(0, 255)

  private fun isDitto(species: Species): Boolean =
      EggGroup.DITTO in species.eggGroups || species.name.lowercase() == "ditto"

  private fun shareEggGroup(a: List<EggGroup>, b: List<EggGroup>): Boolean =
      a.any { it != EggGroup.DITTO && it != EggGroup.UNDISCOVERED && it in b }

  private fun eggSpecies(a: BreedingParent, b: BreedingParent): Int {
    if (isDitto(a.species)) return b.species.id
    if (isDitto(b.species)) return a.species.id
    return if (a.pokemon.gender == Gender.FEMALE) a.species.id else b.species.id
  }

  private fun inheritedNature(a: BreedingParent, b: BreedingParent, rng: () -> Double): Nature {
    val everstoneParents = listOf(a, b).filter { normalizeItem(it.pokemon.heldItem) == "everstone" }
    if (everstoneParents.isEmpty()) return randomNature(rng)
    val chosen =
        everstoneParents[
            (rng() * everstoneParents.size).toInt().coerceIn(0, everstoneParents.lastIndex)]
    return chosen.pokemon.nature
  }

  private fun inheritIvs(
      mother: IVs,
      father: IVs,
      inheritedCount: Int,
      rng: () -> Double
  ): Pair<IVs, Map<StatKey, ParentSide>> {
    var ivs = IVs(randIv(rng), randIv(rng), randIv(rng), randIv(rng), randIv(rng), randIv(rng))
    val inheritedFrom = linkedMapOf<StatKey, ParentSide>()
    val remaining = statKeys.toMutableList()
    repeat(minOf(inheritedCount, statKeys.size)) {
      val stat =
          remaining.removeAt((rng() * remaining.size).toInt().coerceIn(0, remaining.lastIndex))
      val source = if (rng() < 0.5) ParentSide.MOTHER else ParentSide.FATHER
      ivs = ivs.with(stat, if (source == ParentSide.MOTHER) mother[stat] else father[stat])
      inheritedFrom[stat] = source
    }
    return ivs to inheritedFrom
  }

  private fun randomNature(rng: () -> Double): Nature =
      natures[(rng() * natures.size).toInt().coerceIn(0, natures.lastIndex)]

  private fun randIv(rng: () -> Double): Int = (rng() * 32).toInt().coerceIn(0, 31)

  private fun normalizeItem(item: String?): String =
      (item ?: "").trim().lowercase().replace(" ", "-").replace("_", "-")
}
