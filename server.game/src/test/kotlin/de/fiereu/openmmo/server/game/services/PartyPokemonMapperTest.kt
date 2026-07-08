package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.server.game.domain.IVs
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.PokemonDepositTarget
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class PartyPokemonMapperTest :
    FunSpec({
      test("CharacterStore exposes domain OwnedPokemon party and mapper creates wire party") {
        val store = CharacterStore()
        val character = store.getCharactersByUser(1).first()
        val party = store.getParty(character.info.id)

        party.shouldHaveSize(1)
        party.first().speciesId shouldBe 1

        val wire = PartyPokemonMapper.toWireParty(party)
        wire.shouldHaveSize(1)
        wire.first().container shouldBe PokemonContainer.PARTY
        wire.first().containerSlot shouldBe 0
        wire.first().dexId shouldBe 1
        wire.first().moves.shouldHaveSize(4)
      }

      test("zero IVs are preserved in domain to wire mapping") {
        val store = CharacterStore()
        val character = store.getCharactersByUser(1).first()
        val zeroIvMon = store.getParty(character.info.id).first().copy(ivs = IVs(0, 0, 0, 0, 0, 0))

        val wire = PartyPokemonMapper.toWireParty(listOf(zeroIvMon)).first()

        wire.iVs.hp shouldBe 0
        wire.iVs.atk shouldBe 0
        wire.iVs.def shouldBe 0
        wire.iVs.spAtk shouldBe 0
        wire.iVs.spDef shouldBe 0
        wire.iVs.spd shouldBe 0
      }

      test("caught Pokemon persistence fills party then overflows to PC storage") {
        val store = CharacterStore()
        val character = store.getCharactersByUser(1).first()
        val starter = store.getParty(character.info.id).first()

        repeat(5) { index ->
          store.addCaughtPokemon(
              character.info.id, starter.copy(uid = "caught-party-$index")) shouldBe
              PokemonDepositTarget.PARTY
        }
        store.getParty(character.info.id).shouldHaveSize(6)

        store.addCaughtPokemon(character.info.id, starter.copy(uid = "caught-pc")) shouldBe
            PokemonDepositTarget.PC
        store.getCharacter(character.info.id)!!.pcStorage.shouldHaveSize(1)
      }
    })
