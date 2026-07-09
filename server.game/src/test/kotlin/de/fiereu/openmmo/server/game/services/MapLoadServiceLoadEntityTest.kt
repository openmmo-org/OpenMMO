package de.fiereu.openmmo.server.game.services

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.LoadEntityPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

/**
 * Regression test for the 2026-07-09 cosmetics entity/name corruption bug: a character created with
 * real (non-empty) create-cosmetics bytes corrupted every LoadEntityPacket field after its own skin
 * field -- name decoded to garbage, trailing bytes unread. Root-cause evidence: decoding a real
 * captured LoadEntityPacket (entityId=167936, a cosmetics-bearing character) back through this
 * codec. DefaultSkinSetCodec's write() special-cases opaque/raw cosmetics bytes (no length marker)
 * but its read() always expects the structured mask+per-slot layout.
 */
class MapLoadServiceLoadEntityTest :
    FunSpec({
      test("createLoadEntity round-trips cleanly for a character with real create cosmetics") {
        val service = MapLoadService(MapManager())
        val info =
            CharacterInfo(
                id = 167936L,
                name = "cpverify",
                userId = 1,
                rivalSex = 0,
                // Mirrors a real captured C2S 0x03 create-cosmetics tail -- any non-empty bytes
                // reproduce the bug, since the point is DefaultSkinSetCodec's write/read asymmetry.
                cosmetics =
                    byteArrayOf(
                        0x01,
                        0x02,
                        0x02,
                        0x4c,
                        0x03,
                        0x0b,
                        0x34,
                        0x0a,
                        0x00,
                        0x03,
                        0x40,
                        0x00,
                        0x74,
                        0x02,
                        0xa0.toByte()),
                lastLogin = LocalDateTime.of(2026, 7, 9, 0, 0),
                createdAt = LocalDateTime.of(2026, 7, 9, 0, 0),
                money = 3000,
                permissions = 8,
                remainingSafariSteps = 0,
                remainingSafariBalls = 0,
                pcExtraSlots = 0,
                battleBoxExtraSlots = 0,
                templateAmount = 0,
                positionRegionId = 1,
                positionBankId = 51,
                positionMapId = 3,
                positionX = 4,
                positionY = 4,
                repelLeft = 0,
                repelItemId = 0,
                lureLeft = 0,
                lureItemId = 0,
            )

        val packet = service.createLoadEntity(info, facing = Direction.DOWN, hasFollower = false)

        val bytes = LoadEntityPacketCodec.encodeToBytes(packet)
        val decoded = LoadEntityPacketCodec.decodeBytes(bytes)

        decoded.name shouldBe "cpverify"
        decoded.entityId shouldBe 167936L
        decoded.regionId shouldBe 1
        decoded.bankId shouldBe 51
        decoded.mapId shouldBe 3
      }
    })
