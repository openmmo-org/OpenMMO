package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.BagInventoryPacket
import de.fiereu.openmmo.net.game.packets.BagInventoryPacketCodec
import de.fiereu.openmmo.net.game.packets.BagItemEntry
import de.fiereu.openmmo.net.game.packets.BagOpenRequestPacket
import de.fiereu.openmmo.net.game.packets.BagOpenRequestPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BagInventoryPacketTest :
    FunSpec({
      test("BagOpenRequestPacket is empty C2S 0x70 body") {
        BagOpenRequestPacketCodec.encodeToBytes(BagOpenRequestPacket()) shouldBe byteArrayOf()
        BagOpenRequestPacketCodec.decodeBytes(byteArrayOf())::class shouldBe
            BagOpenRequestPacket::class
      }

      test("BagInventoryPacket encodes validated header and 43-byte item entries") {
        val packet =
            BagInventoryPacket(
                containerId = 0x0000,
                entries =
                    listOf(
                        BagItemEntry(
                            slot = 45,
                            categoryFlags = 0x01020304,
                            subType = 0x02,
                            itemId = 17,
                            quantity = 3,
                            maxStack = 99,
                            flag1 = 1,
                            flag2 = 0,
                            entityId = 0,
                            unknownA = 7,
                            slotDuplicate = 45,
                            timestampBytes = byteArrayOf(0x91.toByte(), 0x9e.toByte(), 0x57),
                            state = 0x0001,
                        ),
                    ),
            )

        val bytes = BagInventoryPacketCodec.encodeToBytes(packet)
        bytes.size shouldBe 3 + 43
        le16(bytes, 0) shouldBe 0x0000
        bytes[2] shouldBe 1.toByte()
        bytes[3] shouldBe 45.toByte()
        le32(bytes, 4) shouldBe 0x01020304
        bytes[8] shouldBe 0x02.toByte()
        le16(bytes, 9) shouldBe 17
        le32(bytes, 11) shouldBe 3
        le32(bytes, 15) shouldBe 99
        bytes.takeLast(4).toByteArray() shouldBe byteArrayOf(-1, -1, -1, -1)
        BagInventoryPacketCodec.decodeBytes(bytes) shouldBe packet
      }

      test("BagInventoryPacket supports validated small container with empty payload") {
        val bytes = BagInventoryPacketCodec.encodeToBytes(BagInventoryPacket(0x0100, emptyList()))
        bytes shouldBe byteArrayOf(0x00, 0x01, 0x00)
      }
    })

private fun le16(bytes: ByteArray, offset: Int): Int =
    (bytes[offset].toInt() and 0xff) or ((bytes[offset + 1].toInt() and 0xff) shl 8)

private fun le32(bytes: ByteArray, offset: Int): Int =
    (bytes[offset].toInt() and 0xff) or
        ((bytes[offset + 1].toInt() and 0xff) shl 8) or
        ((bytes[offset + 2].toInt() and 0xff) shl 16) or
        ((bytes[offset + 3].toInt() and 0xff) shl 24)
