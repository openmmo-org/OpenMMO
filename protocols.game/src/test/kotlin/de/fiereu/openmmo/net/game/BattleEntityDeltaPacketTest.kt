package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.BattleEntityDeltaPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * Validates the fieldMask-driven S2C 0x16 BattleEntityDelta codec against real bytes from
 * captures/2026-07-07-232143-first-manual.log — the mon-state deltas the server sent at battle
 * start (they carry level/hp/stats). Confirms the codec is correct so the event-emission layer can
 * construct these packets with confidence.
 */
class BattleEntityDeltaPacketTest :
    FunSpec({
      // L210 — player mon full-state delta: fieldMask 0x21b (exp, stats[6], curHp, faint,
      // happiness).
      val fullStateDelta =
          hex("00c088dd554ca5191b02000007fb00000019000d000c000c000e000c000b00004300")
      // L273 — exp-only delta: fieldMask 0x1 (level + exp points).
      val expDelta = hex("00c088dd554ca51901000000071e010000")

      test("round-trips both real 0x16 samples byte-exact") {
        for (bytes in listOf(fullStateDelta, expDelta)) {
          val decoded = BattleEntityDeltaPacketCodec.decodeBytes(bytes)
          BattleEntityDeltaPacketCodec.encodeToBytes(decoded).toList() shouldBe bytes.toList()
        }
      }

      test("decodes the validated fields of the battle-start full-state delta") {
        val p = BattleEntityDeltaPacketCodec.decodeBytes(fullStateDelta)
        p.fieldMask shouldBe 0x21b
        p.experienceLevel shouldBe 7 // matches the 0x30 open (player mon L7)
        p.experiencePoints shouldBe 251
        p.currentHp shouldBe 11 // matches the 0x30 open (player curHp 11)
        p.faintFlag shouldBe 0
        p.happiness shouldBe 67
        p.statValues shouldBe listOf<Short>(25, 13, 12, 12, 14, 12)
      }

      test("decodes the exp-only delta") {
        val p = BattleEntityDeltaPacketCodec.decodeBytes(expDelta)
        p.fieldMask shouldBe 0x1
        p.experienceLevel shouldBe 7
        p.experiencePoints shouldBe 286
        p.currentHp shouldBe null // not present when the fieldMask bit is clear
      }
    })

private fun hex(s: String): ByteArray =
    ByteArray(s.length / 2) {
      ((s[it * 2].digitToInt(16) shl 4) or s[it * 2 + 1].digitToInt(16)).toByte()
    }
