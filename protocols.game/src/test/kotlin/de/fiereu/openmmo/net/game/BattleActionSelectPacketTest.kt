package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BattleActionSelectPacketTest :
    FunSpec({
      test("decodes a captured move selection") {
        val bytes = byteArrayOf(0x00, 0x00, 0x2B, 0x00, 0x00)
        val decoded = BattleActionSelectPacketCodec.decodeBytes(bytes)
        decoded shouldBe
            BattleActionSelectPacket(
                slotRefPacked = 0,
                action = BattleAction.MOVE,
                moveOrItemId = 43,
                targetEntityId = 0,
                extraFlag = 0,
            )
        BattleActionSelectPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      test("decodes a captured item throw") {
        val bytes =
            byteArrayOf(
                0x00,
                0x01,
                0x8C.toByte(),
                0x13,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0xFF.toByte())
        val decoded = BattleActionSelectPacketCodec.decodeBytes(bytes)
        decoded shouldBe
            BattleActionSelectPacket(
                slotRefPacked = 0,
                action = BattleAction.ITEM,
                moveOrItemId = 5004,
                targetEntityId = 0,
                extraFlag = -1,
            )
        BattleActionSelectPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      test("decodes a run selection with no tail") {
        val bytes = byteArrayOf(0x00, 0x03)
        val decoded = BattleActionSelectPacketCodec.decodeBytes(bytes)
        decoded shouldBe
            BattleActionSelectPacket(
                slotRefPacked = 0,
                action = BattleAction.RUN,
                moveOrItemId = 0,
                targetEntityId = 0,
                extraFlag = 0,
            )
        BattleActionSelectPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      test("decodes a captured switch to a party index") {
        val bytes = byteArrayOf(0x00, 0x02, 0x01, 0x00)
        val decoded = BattleActionSelectPacketCodec.decodeBytes(bytes)
        decoded shouldBe
            BattleActionSelectPacket(
                slotRefPacked = 0,
                action = BattleAction.SWITCH,
                moveOrItemId = 1,
                targetEntityId = 0,
                extraFlag = 0,
            )
        BattleActionSelectPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }
    })
