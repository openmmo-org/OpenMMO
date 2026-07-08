package de.fiereu.openmmo.server.game.domain

enum class EvolutionTrigger {
  LEVEL_UP,
  ITEM,
  TRADE,
  FRIENDSHIP,
  MOVE_LEARNED,
  LOCATION_CHECK
}

data class EvolutionContext(
    val trigger: EvolutionTrigger,
    val newLevel: Int? = null,
    val item: String? = null,
    val tradedWithHeldItem: String? = null,
    val friendship: Int? = null,
    val knownMoves: List<String> = emptyList(),
    val mapId: String? = null,
    val timeOfDay: TimeOfDay? = null,
)

data class EvolutionResult(
    val canEvolve: Boolean,
    val toSpecies: Int? = null,
    val via: EvolutionEdge? = null
)

object EvolutionChecker {
  fun checkEvolution(
      pokemon: OwnedPokemon,
      species: Species,
      context: EvolutionContext
  ): EvolutionResult {
    for (edge in species.evolutions) {
      if (matchesEvolution(pokemon, edge, context)) return EvolutionResult(true, edge.to, edge)
    }
    return EvolutionResult(false)
  }

  fun matchingEvolutions(
      pokemon: OwnedPokemon,
      species: Species,
      context: EvolutionContext
  ): List<EvolutionEdge> = species.evolutions.filter { matchesEvolution(pokemon, it, context) }

  private fun matchesEvolution(
      pokemon: OwnedPokemon,
      edge: EvolutionEdge,
      context: EvolutionContext
  ): Boolean =
      when (val method = edge.method) {
        is EvolutionMethod.Level ->
            context.trigger == EvolutionTrigger.LEVEL_UP &&
                (context.newLevel ?: pokemon.level) >= method.level
        is EvolutionMethod.Item ->
            context.trigger == EvolutionTrigger.ITEM &&
                normalize(context.item) == normalize(method.item)
        is EvolutionMethod.Trade ->
            context.trigger == EvolutionTrigger.TRADE &&
                (method.heldItem == null ||
                    normalize(context.tradedWithHeldItem ?: pokemon.heldItem) ==
                        normalize(method.heldItem))
        is EvolutionMethod.Friendship -> {
          val friendship = context.friendship ?: pokemon.friendship
          val timeOk = method.timeOfDay == null || method.timeOfDay == context.timeOfDay
          (context.trigger == EvolutionTrigger.LEVEL_UP ||
              context.trigger == EvolutionTrigger.FRIENDSHIP) && friendship >= 220 && timeOk
        }
        is EvolutionMethod.Move ->
            (context.trigger == EvolutionTrigger.LEVEL_UP ||
                context.trigger == EvolutionTrigger.MOVE_LEARNED) &&
                context.knownMoves.any { normalize(it) == normalize(method.move) }
        is EvolutionMethod.Location ->
            (context.trigger == EvolutionTrigger.LEVEL_UP ||
                context.trigger == EvolutionTrigger.LOCATION_CHECK) && context.mapId == method.mapId
        is EvolutionMethod.Other -> false
      }
}

internal fun normalizeToken(value: String?): String =
    (value ?: "").trim().lowercase().replace("_", "-").replace(" ", "-")

private fun normalize(value: String?): String = normalizeToken(value)
