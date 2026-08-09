package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.MAX_PARTY_SIZE
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.SocialListEntryAddPacket
import de.fiereu.openmmo.net.game.packets.battle.ItemStack
import de.fiereu.openmmo.net.game.packets.battle.itemStacksPacket
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleRng
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.battle.acquiredMonsterDelta
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject
import javax.inject.Singleton

/** Party, healing, and bag operations used by ROM-derived overworld scripts. */
@Singleton
class StoryPlayerService
@Inject
constructor(
    private val characters: CharacterStore,
    private val pokemonFactory: WildMonFactory,
    private val species: SpeciesRegistry,
    private val moves: MoveRegistry,
) {

  /** Gives a story Pokemon and syncs it. */
  suspend fun givePokemon(
      session: SessionContext,
      state: PlayerState,
      dexId: Int,
      level: Int,
      moveIds: List<Int>,
  ): Pokemon? {
    val characterId = state.characterId ?: return null
    val stored = characters.getCharacter(characterId) ?: return null
    if (stored.pokemon.size >= MAX_PARTY_SIZE) return null
    val rolled = pokemonFactory.create(dexId, level, BattleRng()) ?: return null
    val pokemon =
        rolled.copy(
            ownerId = characterId,
            container = PokemonContainer.PARTY,
            containerSlot = stored.pokemon.size.toShort(),
            ot = stored.info.name,
            moves = paddedMoves(moveIds),
        )
    // Only tell the client about it once the database has it.
    if (!characters.addPokemon(characterId, pokemon)) return null
    // Send the granted Pokemon's full record.
    session.send(SocialListEntryAddPacket(pokemon))
    species.get(dexId)?.let { session.send(acquiredMonsterDelta(pokemon, it)) }
    session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = characters.getCharacter(characterId)?.pokemon ?: listOf(pokemon),
        ))
    return pokemon
  }

  fun healParty(session: SessionContext, state: PlayerState) {
    val characterId = state.characterId ?: return
    val stored = characters.getCharacter(characterId) ?: return
    val healed =
        stored.pokemon.map { pokemon ->
          val definition = species.get(pokemon.dexId) ?: return@map pokemon
          pokemon.copy(
              hp = StatCalculator.computeAll(definition, pokemon).hp.toShort(),
              moves =
                  pokemon.moves.map { move ->
                    val maxPp = moves.get(move.id.toInt())?.pp ?: move.pp.toInt()
                    PokemonMove(move.id, maxPp.toByte())
                  },
          )
        }
    healed.forEach { characters.updatePokemon(characterId, it) }
    session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = healed,
        ))
  }

  suspend fun giveItem(
      session: SessionContext,
      state: PlayerState,
      itemId: Int,
      quantity: Int
  ): Boolean {
    val characterId = state.characterId ?: return false
    if (!characters.addItem(characterId, itemId, quantity)) return false
    val items = characters.getCharacter(characterId)?.items ?: return false
    session.send(storyItemStacksPacket(items))
    return true
  }

  private fun paddedMoves(moveIds: List<Int>): List<PokemonMove> =
      moveIds.take(MAX_MOVES).map { id ->
        PokemonMove(id.toShort(), (moves.get(id)?.pp ?: 0).toByte())
      } + List((MAX_MOVES - moveIds.size).coerceAtLeast(0)) { PokemonMove(0, 0) }

  private companion object {
    const val MAX_MOVES = 4
  }
}

/** Builds a stable full bag snapshot. */
fun storyItemStacksPacket(items: Map<Int, Int>) =
    itemStacksPacket(
        items.entries
            .sortedBy { it.key }
            .map { (itemId, quantity) ->
              ItemStack(
                  objectId = (itemId.toLong() shl 16) or ITEM_ENTITY_TAG,
                  itemId = itemId.toShort(),
                  quantity = quantity.toShort(),
              )
            })

private const val ITEM_ENTITY_TAG = 0x5000L
