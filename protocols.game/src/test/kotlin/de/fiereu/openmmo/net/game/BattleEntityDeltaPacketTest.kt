package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.assertBytesRoundtrip
import de.fiereu.openmmo.common.test.assertValueRoundtrip
import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.net.game.packets.LocalCharacterDeltaPacket
import de.fiereu.openmmo.net.game.packets.LocalCharacterDeltaPacketCodec
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacketCodec
import de.fiereu.openmmo.net.game.packets.battle.Experience
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private const val ENTITY_ID = 0x1AD03598_0408C000L

private val CAPTURED =
    listOf(
        "experience_32763.bin",
        "level_up_32763.bin",
        "move_slots_32763.bin",
        "acquired_32763.bin",
    )

private fun readDelta(name: String): BattleEntityDeltaPacket =
    BattleEntityDeltaPacketCodec.decodeBytes(fixture("game/s2c/16/$name"))

class BattleEntityDeltaPacketTest :
    FunSpec({
      test("an experience delta carries only the level and the running total") {
        val packet = readDelta("experience_32763.bin")

        packet.entityId shouldBe ENTITY_ID
        packet.experience shouldBe Experience(level = 14, points = 1644)
        packet.statValues.shouldBeNull()
        packet.currentHp.shouldBeNull()
      }

      test("a level up delta carries stats, hp and EVs but no experience") {
        val packet = readDelta("level_up_32763.bin")

        packet.experience.shouldBeNull()
        packet.statValues shouldBe listOf<Short>(39, 22, 20, 27, 25, 22)
        packet.currentHp shouldBe 12
        packet.evValues shouldBe listOf<Short>(5, 2, 14, 9, 0, 1)
      }

      test("a move slot delta carries four slots, pp ups, hp and the faint flag") {
        val packet = readDelta("move_slots_32763.bin")

        packet.moves?.slots shouldBe
            listOf<Pair<Short, Byte>>(
                225.toShort() to 20,
                232.toShort() to 35,
                108.toShort() to 20,
                52.toShort() to 25,
            )
        packet.moves?.ppUps shouldBe 0
        packet.currentHp shouldBe 39
        packet.faintFlag shouldBe 0
      }

      test("an acquired monster delta carries experience, stats, hp, faint and happiness") {
        val packet = readDelta("acquired_32763.bin")

        packet.experience shouldBe Experience(level = 8, points = 320)
        packet.statValues shouldBe listOf<Short>(25, 14, 13, 16, 15, 14)
        packet.currentHp shouldBe 14
        packet.faintFlag shouldBe 0
        packet.happiness shouldBe 75
      }

      // Only passes if every derived bit matches the one the real server sent.
      test("every captured delta re-encodes to the same bytes") {
        CAPTURED.forEach {
          BattleEntityDeltaPacketCodec.assertBytesRoundtrip(fixture("game/s2c/16/$it"))
        }
      }

      test("setting a group puts its fields on the wire and nothing else") {
        val packet =
            BattleEntityDeltaPacket(
                entityId = ENTITY_ID,
                experience = Experience(level = 6, points = 197),
                currentHp = 5,
            )

        // Entity id, mask, level, points, hp.
        BattleEntityDeltaPacketCodec.encodeToBytes(packet).size shouldBe (8 + 4 + 1 + 4 + 2)
        BattleEntityDeltaPacketCodec.assertValueRoundtrip(packet)
      }

      test("a local character delta carries the money it was given") {
        val bytes = fixture("game/s2c/0c/money_32763.bin")

        LocalCharacterDeltaPacketCodec.decodeBytes(bytes) shouldBe
            LocalCharacterDeltaPacket(money = 1900)
        LocalCharacterDeltaPacketCodec.assertBytesRoundtrip(bytes)
      }
    })
