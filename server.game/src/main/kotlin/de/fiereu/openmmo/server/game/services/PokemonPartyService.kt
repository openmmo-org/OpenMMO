package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.SinglePokemonAddPacket
import de.fiereu.openmmo.server.game.domain.OwnedPokemon
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.PokemonDepositTarget
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonPartyService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val sessionRegistry: SessionRegistry,
) {
  /**
   * Persists a newly owned Pokemon and emits S2C 0x14 (single-mon add delta) to the owning client.
   *
   * 0x13 remains the bulk container refresh. Do not send a redundant 0x13 here unless client
   * testing proves the party UI needs an explicit refresh after this delta.
   *
   * Production caller is the battle catch-flow (Opus follow-up PR #10): BattleService receives the
   * sidecar's caughtPokemon, converts it to domain OwnedPokemon, then calls this method.
   */
  fun addCaughtPokemon(
      characterId: Long,
      pokemon: OwnedPokemon,
  ): PokemonDepositTarget? {
    val target = characterStore.addCaughtPokemon(characterId, pokemon) ?: return null
    val stored = characterStore.getCharacter(characterId) ?: return target
    val slot =
        when (target) {
          PokemonDepositTarget.PARTY -> stored.pokemon.indexOfFirst { it.uid == pokemon.uid }
          PokemonDepositTarget.PC -> stored.pcStorage.indexOfFirst { it.uid == pokemon.uid }
        }.coerceAtLeast(0)
    val container =
        when (target) {
          PokemonDepositTarget.PARTY -> PokemonContainer.PARTY
          PokemonDepositTarget.PC -> PokemonContainer.PC
        }
    sessionRegistry
        .getByCharacterId(characterId)
        ?.send(
            SinglePokemonAddPacket(PartyPokemonMapper.toWirePokemon(pokemon, container, slot)),
        )
    return target
  }

  fun getParty(characterId: Long): List<OwnedPokemon> = characterStore.getParty(characterId)
}
