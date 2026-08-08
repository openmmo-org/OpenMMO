package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.common.utils.toHex
import de.fiereu.openmmo.net.game.packets.battle.BattleActionEvent
import de.fiereu.openmmo.net.game.packets.battle.BattleEffectTarget
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacketCodec
import de.fiereu.openmmo.net.game.packets.battle.BattleEventBody
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BattleEntityMoveEventPacketTest :
    FunSpec({
      // A captured Growl, which is a status move whose only effect is one stat drop.
      test("round-trips a captured stat change byte for byte") {
        val bytes = fixture("game/s2c/33/stat_change_growl.bin")
        val decoded = BattleEntityMoveEventPacketCodec.decodeBytes(bytes)

        decoded.sourceMove shouldBe 45.toShort()
        val target = decoded.targets.single()
        target.targetMove shouldBe 0.toShort()
        target.subEvents.single().body shouldBe
            BattleEventBody.StatChange(stat = 1, stageDelta = -1)
        BattleEntityMoveEventPacketCodec.encodeToBytes(decoded).toHex() shouldBe bytes.toHex()
      }

      test("round-trips every body type and entity-flag combination") {
        val packet =
            BattleEntityMoveEventPacket(
                sourceEntity = 0x1122334455667788L,
                sourceMove = 0x0200,
                kind = 1,
                targets =
                    listOf(
                        BattleEffectTarget(
                            entityId = 10L,
                            targetMove = 0x0200,
                            subEvents =
                                listOf(
                                    BattleActionEvent(null, null, BattleEventBody.HpUpdate(14)),
                                    BattleActionEvent(20L, null, BattleEventBody.StatChange(1, -2)),
                                    BattleActionEvent(null, 30L, BattleEventBody.Faint(true)),
                                    BattleActionEvent(
                                        40L, 50L, BattleEventBody.EffectivenessMessage),
                                    BattleActionEvent(null, null, BattleEventBody.MoveFailed(33)))),
                        BattleEffectTarget(
                            entityId = 60L, targetMove = 0, subEvents = emptyList())))

        val bytes = BattleEntityMoveEventPacketCodec.encodeToBytes(packet)
        BattleEntityMoveEventPacketCodec.decodeBytes(bytes) shouldBe packet
      }
    })
