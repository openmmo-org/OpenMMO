package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.battle.BattleActionEvent
import de.fiereu.openmmo.net.game.packets.battle.BattleEffectTarget
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacketCodec
import de.fiereu.openmmo.net.game.packets.battle.BattleEventBody
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BattleEntityMoveEventPacketTest :
    FunSpec({
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
