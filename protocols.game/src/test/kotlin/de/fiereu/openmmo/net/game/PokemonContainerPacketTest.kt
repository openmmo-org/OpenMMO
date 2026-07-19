package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.codecs.PokemonCodec
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.util.Base64

private fun testMon(id: Long, dex: Int, slot: Short) =
    Pokemon(
        id = id,
        ownerId = 0x9000L,
        container = PokemonContainer.PARTY,
        containerSlot = slot,
        dexId = dex,
        seed = 0,
        ot = "Test",
        nickname = "",
        level = 5,
        hp = 20,
        xp = 100,
        eVs = EVs(),
        iVs = IVs(),
        moves = List(4) { PokemonMove(0, 0) },
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.of(2026, 1, 1, 0, 0, 0),
    )

private const val CAPTURED_PARTY =
    "AQEBAMABAAAAAAAAAACQAQAAAAAAAJABAAAAAAABAADvAbpwfdsAkAEAAAAAAFQAZQBzAHQAAAAAAAAABRQAAAClAAAAADIAIQArAAAAAAAjHgAAAAAAAAAAAAAAAAAAAQAAAAAAAAAEBQL/////AwDvvfceAAAAIAAAAAAAAACGhlNqAAD//wA="

class PokemonContainerPacketTest :
    FunSpec({
      test("round-trips a captured party container and exposes the monster fields") {
        val bytes = Base64.getDecoder().decode(CAPTURED_PARTY)
        val decoded = PokemonContainerPacketCodec.decodeBytes(bytes)
        val mon = decoded.pokemon.single()
        mon.dexId shouldBe 495
        mon.ot shouldBe "Test"
        mon.level shouldBe 5.toByte()
        mon.hp shouldBe 20.toShort()
        mon.moves.map { it.id } shouldBe listOf<Short>(33, 43, 0, 0)
        mon.containerSlot shouldBe 0.toShort()
        // Starter monsters roll fixed 15s across every stat.
        mon.iVs.total shouldBe 90
        mon.iVs.hp shouldBe 15
        mon.caughtAt shouldBe LocalDateTime.of(2026, 7, 12, 12, 20, 22)
        mon.isShiny shouldBe false
        mon.isEgg shouldBe false
        PokemonContainerPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      test("EVs, rarity flags, and egg survive a monster codec round-trip") {
        val mon = PokemonContainerPacketCodec.decodeBytes(bytes()).pokemon.single()
        // Distinct per-stat values catch a wrong wire order (speed is the fourth EV byte).
        val evs =
            EVs().apply {
              hp = 4
              atk = 8
              def = 12
              spAtk = 16
              spDef = 20
              spd = 24
            }
        val rare =
            mon.copy(
                eVs = evs,
                isShiny = true,
                hasHiddenAbility = false,
                isAlpha = true,
                isSecret = true,
                isFatefulEncounter = false,
                isRaidEncounter = true,
                isEgg = true,
            )
        val roundTripped = PokemonCodec.decodeBytes(PokemonCodec.encodeToBytes(rare))
        roundTripped.eVs.hp shouldBe 4
        roundTripped.eVs.atk shouldBe 8
        roundTripped.eVs.def shouldBe 12
        roundTripped.eVs.spAtk shouldBe 16
        roundTripped.eVs.spDef shouldBe 20
        roundTripped.eVs.spd shouldBe 24
        roundTripped.isShiny shouldBe true
        roundTripped.hasHiddenAbility shouldBe false
        roundTripped.isAlpha shouldBe true
        roundTripped.isSecret shouldBe true
        roundTripped.isFatefulEncounter shouldBe false
        roundTripped.isRaidEncounter shouldBe true
        roundTripped.isEgg shouldBe true
      }

      test("a two-monster party container round-trips") {
        val packet =
            PokemonContainerPacket(
                container = PokemonContainer.PARTY,
                hasChange = true,
                delete = false,
                pokemon =
                    listOf(
                        testMon(0x000000000001C000L, dex = 495, slot = 0),
                        testMon(0x000000000002C000L, dex = 504, slot = 1),
                    ),
            )
        val bytes = PokemonContainerPacketCodec.encodeToBytes(packet)
        val decoded = PokemonContainerPacketCodec.decodeBytes(bytes)
        decoded.pokemon.size shouldBe 2
        decoded.pokemon[0].dexId shouldBe 495
        decoded.pokemon[1].dexId shouldBe 504
        decoded.pokemon[1].containerSlot shouldBe 1.toShort()
        PokemonContainerPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }
    })

private fun bytes(): ByteArray = Base64.getDecoder().decode(CAPTURED_PARTY)
