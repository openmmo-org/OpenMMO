package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.openmmo.net.game.packets.LoadMapPacketCodec
import de.fiereu.openmmo.net.game.packets.MapData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private fun hexToBytes(hex: String): ByteArray =
    ByteArray(hex.length / 2) { i -> hex.substring(i * 2, i * 2 + 2).toInt(16).toByte() }

/**
 * Validation bar for the 2026-07-09 LoadMapPacket root-cause (docs/protocol/loadmap-spec.md,
 * Kimi-Decode-Economy): must decode every golden 0x10 sample byte-exact (decodeBytes itself asserts
 * zero bytes left over) with sane values before the fix could land. All 3 known samples across
 * every capture file we have are covered here.
 */
class LoadMapPacketGoldenTest :
    FunSpec({
      test("decodes golden sample 1 (first-manual.log, 51B) byte-exact") {
        val bytes =
            hexToBytes(
                "030285010200000a3b00da003a00d9003900d8003800d7004d00cb004c00ca004b00c9004a00c8000400140103001301000203")
        val decoded = LoadMapPacketCodec.decodeBytes(bytes)

        decoded.deleteCache shouldBe true
        decoded.reloadPlayer shouldBe true
        decoded.romType shouldBe 2
        decoded.bankId shouldBe 0x85
        decoded.mapId shouldBe 1
        decoded.regionId shouldBe 2
        val special = decoded.mapData as MapData.SpecialMapData
        special.rF1 shouldBe 0
        special.borderConnections.map { it.key to it.value } shouldBe
            listOf(
                59 to 218,
                58 to 217,
                57 to 216,
                56 to 215,
                77 to 203,
                76 to 202,
                75 to 201,
                74 to 200,
                4 to 276,
                3 to 275,
            )
        special.weather.ordinal shouldBe 2
        special.mapType.ordinal shouldBe 3
      }

      test("decodes golden sample 2 (burner-login-bag.log, 11B) byte-exact") {
        val bytes = hexToBytes("0302860103270000000008")
        val decoded = LoadMapPacketCodec.decodeBytes(bytes)

        decoded.romType shouldBe 2
        decoded.bankId shouldBe 0x86
        decoded.mapId shouldBe 1
        decoded.regionId shouldBe 3
        val special = decoded.mapData as MapData.SpecialMapData
        special.rF1 shouldBe 39
        special.borderConnections shouldBe emptyList()
        special.lighting.ordinal shouldBe 0
        special.weather.ordinal shouldBe 0
        special.mapType.ordinal shouldBe 8 // INSIDE -- matches the Littleroot bedroom
      }

      test("decodes golden sample 3 (burner-login-bag.log, 51B) byte-exact") {
        val bytes =
            hexToBytes(
                "030285010300000a3b00da003a00d9003900d8003800d7004d00cb004c00ca004b00c9004a00c8000400140103001301000203")
        val decoded = LoadMapPacketCodec.decodeBytes(bytes)

        decoded.romType shouldBe 2
        decoded.bankId shouldBe 0x85
        decoded.mapId shouldBe 1
        decoded.regionId shouldBe 3
        val special = decoded.mapData as MapData.SpecialMapData
        special.rF1 shouldBe 0
        special.borderConnections.size shouldBe 10
      }
    })
