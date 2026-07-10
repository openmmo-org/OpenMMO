package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.InventoryItemEntry
import de.fiereu.openmmo.net.game.packets.InventoryUpdatePacket
import de.fiereu.openmmo.net.game.packets.InventoryUpdatePacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class InventoryUpdatePacketTest :
    FunSpec({
      test("empty pocket-init is byte-identical to the real login capture") {
        // captures/2026-07-10-032212-indoor-maps.log line 25: S2C id=64 len=4 01010000
        val packet = InventoryUpdatePacket(pocketIndex = 1, reset = true, entries = emptyList())

        val bytes = InventoryUpdatePacketCodec.encodeToBytes(packet)

        bytes shouldBe byteArrayOf(0x01, 0x01, 0x00, 0x00)
        InventoryUpdatePacketCodec.decodeBytes(bytes) shouldBe packet
      }

      test("populated pocket encodes 14-byte base entries per docs/protocol/bag-spec.md") {
        val packet =
            InventoryUpdatePacket(
                pocketIndex = 1,
                reset = true,
                entries =
                    listOf(
                        InventoryItemEntry(
                            instanceId = 1L,
                            itemId = 17,
                            quantity = 3,
                            pocketIndex = 1,
                        ),
                        InventoryItemEntry(
                            instanceId = 2L,
                            itemId = 42,
                            quantity = 99,
                            pocketIndex = 1,
                        ),
                    ),
            )

        val bytes = InventoryUpdatePacketCodec.encodeToBytes(packet)

        bytes[0] shouldBe 1.toByte()
        bytes[1] shouldBe 1.toByte()
        le16(bytes, 2) shouldBe 2
        bytes.size shouldBe 4 + 2 * 14
        // first entry: flags=0, instanceId=1, itemId=17, quantity=3, pocketIndex=1
        bytes[4] shouldBe 0.toByte()
        le64(bytes, 5) shouldBe 1L
        le16(bytes, 13) shouldBe 17
        le16(bytes, 15) shouldBe 3
        bytes[17] shouldBe 1.toByte()
        InventoryUpdatePacketCodec.decodeBytes(bytes) shouldBe packet
      }
    })

private fun le16(bytes: ByteArray, offset: Int): Int =
    (bytes[offset].toInt() and 0xff) or ((bytes[offset + 1].toInt() and 0xff) shl 8)

private fun le64(bytes: ByteArray, offset: Int): Long {
  var result = 0L
  for (i in 0 until 8) {
    result = result or ((bytes[offset + i].toLong() and 0xff) shl (8 * i))
  }
  return result
}
