package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BattleActionSelectPacketTest :
    FunSpec({
      test("decodes a captured move selection") {
        val bytes = fixture("game/c2s/32/move_selection.bin")
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
        val bytes = fixture("game/c2s/32/item_throw.bin")
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
        val bytes = fixture("game/c2s/32/run.bin")
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
        val bytes = fixture("game/c2s/32/switch.bin")
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
