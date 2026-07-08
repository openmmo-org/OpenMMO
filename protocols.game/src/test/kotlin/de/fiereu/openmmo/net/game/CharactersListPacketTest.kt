package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.codecs.SkinSet
import de.fiereu.openmmo.net.game.packets.CharacterEntry
import de.fiereu.openmmo.net.game.packets.CharactersListPacket
import de.fiereu.openmmo.net.game.packets.CharactersListPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class CharactersListPacketTest :
    FunSpec({
      test("writes reserved prefix before embedded party pokemon") {
        val pokemon = sampleCharacterListPokemon().copy(id = 0x1122334455667788L)
        val bytes =
            CharactersListPacketCodec.encodeToBytes(
                CharactersListPacket(
                    listOf(
                        CharacterEntry(
                            characterInfo = sampleCharacterInfo(),
                            skinSet = SkinSet(),
                            pokemon = listOf(pokemon),
                        ))))
        val pokemonStart =
            bytes.indexOfSequence(
                byteArrayOf(0x88.toByte(), 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11))

        pokemonStart.shouldBeGreaterThan(1)
        bytes[pokemonStart - 2] shouldBe 1.toByte()
        bytes[pokemonStart - 1] shouldBe 0.toByte()
      }
    })

private fun sampleCharacterInfo(): CharacterInfo =
    CharacterInfo(
        id = 0x0102030405060708L,
        name = "TheOtherBag",
        userId = 1,
        rivalSex = 0,
        lastLogin = LocalDateTime.of(2026, 7, 8, 0, 0),
        createdAt = LocalDateTime.of(2026, 7, 8, 0, 0),
        money = 3000,
        permissions = 8,
        remainingSafariSteps = 0,
        remainingSafariBalls = 0,
        pcExtraSlots = 0,
        battleBoxExtraSlots = 0,
        templateAmount = 0,
        positionRegionId = 1,
        positionBankId = 51,
        positionMapId = 3,
        positionX = 4,
        positionY = 4,
        repelLeft = 0,
        repelItemId = 0,
        lureLeft = 0,
        lureItemId = 0,
    )

private fun sampleCharacterListPokemon(): Pokemon =
    Pokemon(
        id = 1L,
        container = PokemonContainer.PARTY,
        containerSlot = 0,
        dexId = 1,
        seed = 1,
        ot = "TheOtherBag",
        nickname = "Bulbasaur",
        level = 5,
        hp = 20,
        xp = 0,
        eVs = EVs(),
        iVs = IVs(),
        moves =
            listOf(
                PokemonMove(33, 35),
                PokemonMove(45, 40),
                PokemonMove(0, 0),
                PokemonMove(0, 0),
            ),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.of(2026, 7, 8, 0, 0),
    )

private fun ByteArray.indexOfSequence(needle: ByteArray): Int {
  for (i in 0..(size - needle.size)) {
    if (needle.indices.all { this[i + it] == needle[it] }) return i
  }
  return -1
}
