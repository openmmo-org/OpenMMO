package de.fiereu.network.checksum

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.Unpooled

class ChecksumTest :
    FunSpec({
      test("NoOp produces empty tag") {
        NoOpChecksum.size shouldBe 0
        NoOpChecksum.calculate(Unpooled.wrappedBuffer(byteArrayOf(1, 2, 3))).size shouldBe 0
        NoOpChecksum.verify(Unpooled.wrappedBuffer(byteArrayOf(1, 2, 3)), ByteArray(0)) shouldBe
            true
      }

      test("CRC16 verify matches calculate") {
        val c = Crc16Checksum()
        val data = byteArrayOf(0x10, 0x20, 0x30, 0x40, 0x50)
        val tag = c.calculate(Unpooled.wrappedBuffer(data))
        tag.size shouldBe 2
        Crc16Checksum().verify(Unpooled.wrappedBuffer(data), tag) shouldBe true
        Crc16Checksum().verify(Unpooled.wrappedBuffer(byteArrayOf(0, 0)), tag) shouldBe false
      }

      test("HMAC-SHA256 round counter advances on every verify") {
        val key = ByteArray(16) { it.toByte() }
        val calc = HmacSha256Checksum(8, key)
        val ver = HmacSha256Checksum(8, key)
        val data = byteArrayOf(0xAA.toByte(), 0xBB.toByte(), 0xCC.toByte())
        val tag0 = calc.calculate(Unpooled.wrappedBuffer(data))
        val tag1 = calc.calculate(Unpooled.wrappedBuffer(data))
        tag0.contentEquals(tag1) shouldBe false
        ver.verify(Unpooled.wrappedBuffer(data), tag0) shouldBe true
        ver.verify(Unpooled.wrappedBuffer(data), tag1) shouldBe true
      }

      test("ChecksumFactory dispatches on size") {
        ChecksumFactory.create(0).size shouldBe 0
        ChecksumFactory.create(2).size shouldBe 2
        ChecksumFactory.create(8, ByteArray(16)).size shouldBe 8
      }
    })
