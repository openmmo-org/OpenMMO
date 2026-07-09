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
 * Regression test for MapManager.createLoadMapPacket, covering both the 2026-07-09 LoadMapPacket
 * byte-layout root-cause (PR #41) and the same-day partial revert of it: PR #41 also made INSIDE/
 * SECRET_BASE maps emit SpecialMapData (romType=2), which regressed indoor spawn entirely --
 * confirmed live (capture + game-server.log): the packet sends fine, no server-side exception, but
 * the client's own ROM-mismatch check rejects our placeholder SpecialMapData values ("Possible rom
 * corruption detected") since we have no real ground truth for OUR maps' SpecialMapData fields
 * (only a different, unrelated golden INSIDE sample). Reverted to always emitting GbaMapData
 * (romType=1) -- the byte layout fix and the GbaConnection targetRegion fix from #41 are kept; only
 * the branch CHOICE is reverted, pending real per-map ground truth.
 */
class MapManagerLoadMapPacketTest :
    FunSpec({
      test("an INSIDE map emits GbaMapData (romType=1) and round-trips clean") {
        val manager = MapManager()
        val map = MapDef(regionId = 1, bankId = 51, mapId = 3, mapType = MapType.INSIDE)

        val packet = manager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true)

        packet.romType shouldBe 1
        packet.mapData.shouldBeInstanceOfGba()
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

private fun MapData.shouldBeInstanceOfGba() {
  check(this is MapData.GbaMapData) { "expected GbaMapData, got $this" }
}
