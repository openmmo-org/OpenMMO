package de.fiereu.openmmo.server.game.battle

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.BattleEntityDeltaPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json

class BattleEventTranslatorTest :
    FunSpec({
      test("hpDelta builds a fieldMask=currentHp 0x16 that encodes + round-trips") {
        val entityId = 0x19a54c55dd88c000L
        val p = BattleEventTranslator.hpDelta(entityId, currentHp = 5)
        p.fieldMask shouldBe 8
        p.currentHp shouldBe 5.toShort()
        // entityId(8) + fieldMask(4) + currentHp(2) = 14 bytes; only the HP field present.
        val bytes = BattleEntityDeltaPacketCodec.encodeToBytes(p)
        bytes.size shouldBe 14
        val decoded = BattleEntityDeltaPacketCodec.decodeBytes(bytes)
        decoded.entityId shouldBe entityId
        decoded.currentHp shouldBe 5.toShort()
        decoded.experienceLevel shouldBe null
      }

      test("hpDeltas maps both sides' current HP from a sidecar snapshot to entity-keyed 0x16s") {
        val sides =
            Json.parseToJsonElement(
                """[{"side":"player","active":[{"hpCurrent":20,"hpMax":41}],"benchCount":0},""" +
                    """{"side":"wild","active":[{"hpCurrent":7,"hpMax":22}],"benchCount":0}]""",
            )
        val deltas = BattleEventTranslator.hpDeltas(sides, playerEntityId = 1L, wildEntityId = 2L)
        deltas.map { it.entityId to it.currentHp } shouldBe
            listOf(1L to 20.toShort(), 2L to 7.toShort())
      }
    })
