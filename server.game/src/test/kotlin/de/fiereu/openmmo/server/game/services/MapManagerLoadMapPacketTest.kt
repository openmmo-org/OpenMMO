package de.fiereu.openmmo.server.game.services

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.LoadMapPacketCodec
import de.fiereu.openmmo.net.game.packets.MapData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * Regression test for the 2026-07-09 LoadMapPacket root-cause: MapManager.createLoadMapPacket must
 * pick the branch the real client actually expects (docs/protocol/loadmap-spec.md) and the result
 * must round-trip through the wire codec cleanly either way.
 */
class MapManagerLoadMapPacketTest :
    FunSpec({
      test("an INSIDE map emits SpecialMapData and round-trips clean") {
        val manager = MapManager()
        val map = MapDef(regionId = 1, bankId = 51, mapId = 3, mapType = MapType.INSIDE)

        val packet = manager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true)

        packet.romType shouldBe 2
        packet.mapData.shouldBeInstanceOfSpecial()
        val decoded = LoadMapPacketCodec.decodeBytes(LoadMapPacketCodec.encodeToBytes(packet))
        decoded shouldBe packet
      }

      test("a non-INSIDE map emits GbaMapData and round-trips clean") {
        val manager = MapManager()
        val map = MapDef(regionId = 1, bankId = 50, mapId = 0, mapType = MapType.CITY)

        val packet = manager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true)

        packet.romType shouldBe 1
        packet.mapData.shouldBeInstanceOfGba()
        val decoded = LoadMapPacketCodec.decodeBytes(LoadMapPacketCodec.encodeToBytes(packet))
        decoded shouldBe packet
      }
    })

private fun MapData.shouldBeInstanceOfSpecial() {
  check(this is MapData.SpecialMapData) { "expected SpecialMapData, got $this" }
}

private fun MapData.shouldBeInstanceOfGba() {
  check(this is MapData.GbaMapData) { "expected GbaMapData, got $this" }
}
