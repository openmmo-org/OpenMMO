package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.BattleOpenPacket
import de.fiereu.openmmo.net.game.packets.BattleOpenPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * S2C 0x30 battle-open, validated against real bytes from
 * captures/2026-07-07-232143-first-manual.log — a fled encounter (L174, wild #506 L3) and the
 * Patrat catch (L259, wild #504 L4).
 */
class BattleOpenPacketTest :
    FunSpec({
      // L174 — first (fled) encounter: player mon #501 L6 20/23, wild #506 L3 16/16.
      val fledEncounter =
          hex(
              "02000000000000000000000000ff000000000016000000ff002000064f00740068006500" +
                  "72004200610067000000000090c8afe045a519ff00005c0326fc0afc0f140200008000" +
                  "140000000000010100000100c088dd554ca519f501060000000000001400170000000" +
                  "0ff030143002100270000000000010000f5010600000000000003ff00000000666666" +
                  "6601060000000000010100000100c088c55778aa1afa01030000000000001000100000" +
                  "0000ff0300010000fa010300000000000003ff000000006666666600000000",
          )
      // L259 — the Patrat catch: player mon #501 L7 11/25, wild #504 L4 18/18.
      val catchEncounter =
          hex(
              "02000000000000000000000000ff000000000016000000ff002000064f00740068006500" +
                  "72004200610067000000000090c8afe045a519ff00005c0326fc0afc0f140200008000" +
                  "140000000000010100000100c088dd554ca519f501070000000000000b001900000000" +
                  "ff030143002100270037000000010000f5010700000000000003ff0000000066666666" +
                  "01060000000000010100000100c00889ad78aa1af8010400000000000012001200000000" +
                  "ff0300010000f8010400000000000003ff000000006666666600000000",
          )

      test("round-trips both real samples byte-exact") {
        for (bytes in listOf(fledEncounter, catchEncounter)) {
          bytes.size shouldBe 206
          val decoded = BattleOpenPacketCodec.decodeBytes(bytes)
          BattleOpenPacketCodec.encodeToBytes(decoded).toList() shouldBe bytes.toList()
        }
      }

      test("decodes validated mon fields — Patrat catch sample") {
        val p = BattleOpenPacketCodec.decodeBytes(catchEncounter)
        p.playerSpecies shouldBe 501
        p.playerLevel shouldBe 7
        p.playerCurrentHp shouldBe 11
        p.playerMaxHp shouldBe 25
        p.wildSpecies shouldBe 504 // Patrat
        p.wildLevel shouldBe 4
        p.wildCurrentHp shouldBe 18
        p.wildMaxHp shouldBe 18
      }

      test("decodes validated mon fields — fled encounter sample") {
        val p = BattleOpenPacketCodec.decodeBytes(fledEncounter)
        p.wildSpecies shouldBe 506
        p.wildLevel shouldBe 3
        p.playerLevel shouldBe 6
        p.playerCurrentHp shouldBe 20
        p.playerMaxHp shouldBe 23
      }

      test("wild() factory patches the validated fields onto the template") {
        val p =
            BattleOpenPacket.wild(
                playerSpecies = 25,
                playerLevel = 12,
                playerCurrentHp = 30,
                playerMaxHp = 41,
                wildSpecies = 504,
                wildLevel = 5,
                wildCurrentHp = 22,
                wildMaxHp = 22,
            )
        p.raw.size shouldBe 206
        p.playerSpecies shouldBe 25
        p.playerLevel shouldBe 12
        p.wildSpecies shouldBe 504
        p.wildLevel shouldBe 5
        p.wildCurrentHp shouldBe 22
        // survives a codec round-trip
        BattleOpenPacketCodec.decodeBytes(BattleOpenPacketCodec.encodeToBytes(p)) shouldBe p
      }
    })

private fun hex(s: String): ByteArray =
    ByteArray(s.length / 2) {
      ((s[it * 2].digitToInt(16) shl 4) or s[it * 2 + 1].digitToInt(16)).toByte()
    }
