package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacketCodec
import de.fiereu.openmmo.net.game.packets.battle.BattleMonBlock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BattleFieldStatePacketTest :
    FunSpec({
      test("round-trips a two-party wild field state byte for byte") {
        val bytes = fixture("game/s2c/30/wild_two_party_scrubbed.bin")
        val decoded = BattleFieldStatePacketCodec.decodeBytes(bytes)
        decoded.playerName shouldBe "Test"
        decoded.playerParty.size shouldBe 2
        decoded.activeSlot shouldBe 0
        BattleFieldStatePacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      test("decodes the structured mon fields from the capture") {
        val bytes = fixture("game/s2c/30/wild_two_party_scrubbed.bin")
        val decoded = BattleFieldStatePacketCodec.decodeBytes(bytes)

        val snivy = decoded.playerParty[0]
        snivy.slot shouldBe 0
        snivy.species shouldBe 495.toShort()
        snivy.level shouldBe 6.toByte()
        snivy.gender shouldBe 0.toByte()
        snivy.abilityId shouldBe 65.toShort()
        snivy.maxHp shouldBe 22.toShort()
        snivy.currentHp shouldBe 22.toShort()
        snivy.movesPresent shouldBe true
        snivy.moveIds shouldBe listOf<Short>(33, 43, 0, 0)

        val patrat = decoded.playerParty[1]
        patrat.slot shouldBe 1
        patrat.species shouldBe 504.toShort()
        patrat.level shouldBe 2.toByte()
        patrat.gender shouldBe 1.toByte()
        patrat.abilityId shouldBe 50.toShort()
        patrat.moveIds shouldBe listOf<Short>(33, 0, 0, 0)

        val wild = decoded.wildParty.single()
        wild.species shouldBe 504.toShort()
        wild.level shouldBe 2.toByte()
        wild.maxHp shouldBe 14.toShort()
        wild.currentHp shouldBe 14.toShort()
        wild.movesPresent shouldBe false
      }

      test("round-trips a synthetic field state for a species without a capture") {
        val rattata =
            BattleMonBlock(
                slot = 0,
                entityId = 0x123C000L,
                species = 19,
                level = 7,
                gender = 1,
                abilityId = 50,
                maxHp = 21,
                currentHp = 17,
                movesPresent = true,
                moveIds = listOf(33, 39, 45, 98),
            )
        val wild =
            BattleMonBlock(
                slot = 0,
                entityId = 0x456C000L,
                species = 74,
                level = 9,
                gender = 0,
                abilityId = 0,
                maxHp = 26,
                currentHp = 26,
                movesPresent = false,
                moveIds = listOf(0, 0, 0, 0),
            )
        val packet =
            BattleFieldStatePacket(
                playerName = "Ash",
                playerId = 0x19000L,
                playerParty = listOf(rattata),
                activeSlot = 0,
                wildParty = listOf(wild),
            )
        val decoded =
            BattleFieldStatePacketCodec.decodeBytes(
                BattleFieldStatePacketCodec.encodeToBytes(packet))
        decoded shouldBe packet
      }
    })
