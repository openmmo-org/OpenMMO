package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.LoadEntityPacket
import de.fiereu.openmmo.net.game.packets.LoadEntityPacketCodec
import de.fiereu.openmmo.net.game.packets.LocalPlayerStatePacket
import de.fiereu.openmmo.net.game.packets.LocalPlayerStatePacketCodec
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacketCodec
import de.fiereu.openmmo.net.game.packets.SinglePokemonAddPacket
import de.fiereu.openmmo.net.game.packets.SinglePokemonAddPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class T0PlayerPartyPacketsTest :
    FunSpec({
      test("LocalPlayerStatePacket encodes validated fixed header layout") {
        val bytes =
            LocalPlayerStatePacketCodec.encodeToBytes(
                LocalPlayerStatePacket(
                    region = 75,
                    mapId = 15000,
                    moveSpeed = 0.05f,
                    x = 100,
                    y = 1000,
                    z = 25000,
                    money = 250000,
                    gender = 5,
                    skinTone = 30000,
                    hairColor = 30000,
                    playtime = 0.9,
                    flags = 27,
                    partyDex = emptyList(),
                    partyForms = emptyList(),
                    pokedexSeen = listOf(1, 4),
                    pokedexCaught = listOf(1),
                    badges = listOf(10),
                    variables = emptyList(),
                ))

        bytes[0] shouldBe 75.toByte()
        le16(bytes, 1) shouldBe 15000
        le16(bytes, 7) shouldBe 100
        le16(bytes, 9) shouldBe 1000
        le16(bytes, 11) shouldBe 25000
        le32(bytes, 13) shouldBe 250000
        bytes[17] shouldBe 5.toByte()
        le16(bytes, 18) shouldBe 30000
        le16(bytes, 20) shouldBe 30000
        bytes[30] shouldBe 27.toByte()
        le16(bytes, 31) shouldBe 0
        bytes.size.shouldBeGreaterThan(34)
      }

      test("PokemonContainerPacket encodes validated party carrier prefix") {
        val bytes =
            PokemonContainerPacketCodec.encodeToBytes(
                PokemonContainerPacket(
                    container = PokemonContainer.PARTY,
                    hasChange = true,
                    delete = false,
                    pokemon = listOf(sampleWirePokemon()),
                ))

        bytes[0] shouldBe 1.toByte()
        bytes[1] shouldBe 1.toByte()
        bytes[2] shouldBe 1.toByte()
        bytes.size.shouldBeGreaterThan(3)
      }

      test("PokemonContainerPacket codec preserves zero IVs") {
        val pokemon =
            sampleWirePokemon()
                .copy(
                    iVs =
                        IVs().also {
                          it.hp = 0
                          it.atk = 0
                          it.def = 0
                          it.spAtk = 0
                          it.spDef = 0
                          it.spd = 0
                        },
                )
        val bytes =
            PokemonContainerPacketCodec.encodeToBytes(
                PokemonContainerPacket(
                    container = PokemonContainer.PARTY,
                    hasChange = true,
                    delete = false,
                    pokemon = listOf(pokemon),
                ))
        val decoded = PokemonContainerPacketCodec.decodeBytes(bytes)

        decoded.pokemon.first().iVs.hp shouldBe 0
        decoded.pokemon.first().iVs.atk shouldBe 0
        decoded.pokemon.first().iVs.def shouldBe 0
        decoded.pokemon.first().iVs.spAtk shouldBe 0
        decoded.pokemon.first().iVs.spDef shouldBe 0
        decoded.pokemon.first().iVs.spd shouldBe 0
      }

      test("SinglePokemonAddPacket encodes validated single-add header") {
        val bytes =
            SinglePokemonAddPacketCodec.encodeToBytes(SinglePokemonAddPacket(sampleWirePokemon()))
        val decoded = SinglePokemonAddPacketCodec.decodeBytes(bytes)

        bytes[0] shouldBe 0.toByte()
        decoded.pokemon.dexId shouldBe 1
        decoded.pokemon.container shouldBe PokemonContainer.PARTY
      }

      test("LoadEntityPacket encodes entity id and follower flag") {
        val bytes =
            LoadEntityPacketCodec.encodeToBytes(
                LoadEntityPacket(
                    entityId = 0x0102030405060708L,
                    skin = de.fiereu.openmmo.net.game.codecs.SkinSet(),
                    name = "TheOtherBag",
                    regionId = 1,
                    bankId = 51,
                    mapId = 3,
                    x = 4,
                    y = 4,
                    z = 0,
                    facing = de.fiereu.openmmo.common.enums.Direction.DOWN,
                    hasFollower = true,
                    followerDexId = 1,
                ))

        le64(bytes, 0) shouldBe 0x0102030405060708L
        bytes[8] shouldBe 0.toByte()
        bytes.contains(0x04.toByte()) shouldBe true
        bytes.takeLast(2).toByteArray().let { le16(it, 0) } shouldBe 1
      }
    })

private fun sampleWirePokemon(): Pokemon =
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
        iVs =
            IVs().also {
              it.hp = 10
              it.atk = 10
              it.def = 10
              it.spAtk = 10
              it.spDef = 10
              it.spd = 10
            },
        moves =
            listOf(PokemonMove(33, 35), PokemonMove(0, 0), PokemonMove(0, 0), PokemonMove(0, 0)),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.of(2026, 7, 8, 0, 0),
    )

private fun le16(bytes: ByteArray, offset: Int): Int =
    (bytes[offset].toInt() and 0xff) or ((bytes[offset + 1].toInt() and 0xff) shl 8)

private fun le32(bytes: ByteArray, offset: Int): Int =
    (bytes[offset].toInt() and 0xff) or
        ((bytes[offset + 1].toInt() and 0xff) shl 8) or
        ((bytes[offset + 2].toInt() and 0xff) shl 16) or
        ((bytes[offset + 3].toInt() and 0xff) shl 24)

private fun le64(bytes: ByteArray, offset: Int): Long =
    (0 until 8).fold(0L) { acc, i -> acc or ((bytes[offset + i].toLong() and 0xffL) shl (8 * i)) }
