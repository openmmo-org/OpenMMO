package de.fiereu.bytecodec

import de.fiereu.bytecodec.test.assertBytesRoundtrip
import de.fiereu.bytecodec.test.assertValueRoundtrip
import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AtomsTest :
    FunSpec({
      test("Bool roundtrips") {
        Bool.assertValueRoundtrip(true)
        Bool.assertValueRoundtrip(false)
        Bool.encodeToBytes(true) shouldBe byteArrayOf(0x01)
        Bool.encodeToBytes(false) shouldBe byteArrayOf(0x00)
        Bool.decodeBytes(byteArrayOf(0x05)) shouldBe true
      }

      test("U8 wire format") {
        U8.encodeToBytes(0) shouldBe byteArrayOf(0x00)
        U8.encodeToBytes(255) shouldBe byteArrayOf(0xFF.toByte())
        U8.decodeBytes(byteArrayOf(0xFF.toByte())) shouldBe 255
        shouldThrow<IllegalArgumentException> { U8.encodeToBytes(256) }
      }

      test("S8 wire format") {
        S8.assertValueRoundtrip(0)
        S8.assertValueRoundtrip(-1)
        S8.assertValueRoundtrip(127)
        S8.assertValueRoundtrip(-128)
      }

      test("U16LE wire format") {
        U16LE.encodeToBytes(0x1234) shouldBe byteArrayOf(0x34, 0x12)
        U16LE.decodeBytes(byteArrayOf(0x34, 0x12)) shouldBe 0x1234
      }

      test("U16BE wire format") {
        U16BE.encodeToBytes(0x1234) shouldBe byteArrayOf(0x12, 0x34)
        U16BE.decodeBytes(byteArrayOf(0x12, 0x34)) shouldBe 0x1234
      }

      test("S16LE signed") {
        S16LE.assertValueRoundtrip((-1).toShort())
        S16LE.assertValueRoundtrip(Short.MIN_VALUE)
        S16LE.assertValueRoundtrip(Short.MAX_VALUE)
      }

      test("S32LE wire format") {
        S32LE.encodeToBytes(0x11223344) shouldBe byteArrayOf(0x44, 0x33, 0x22, 0x11)
        S32LE.decodeBytes(byteArrayOf(0x44, 0x33, 0x22, 0x11)) shouldBe 0x11223344
        S32LE.assertValueRoundtrip(Int.MIN_VALUE)
        S32LE.assertValueRoundtrip(Int.MAX_VALUE)
      }

      test("S32BE wire format") {
        S32BE.encodeToBytes(0x11223344) shouldBe byteArrayOf(0x11, 0x22, 0x33, 0x44)
        S32BE.assertValueRoundtrip(Int.MIN_VALUE)
      }

      test("U32LE roundtrip across full range") {
        U32LE.assertValueRoundtrip(0L)
        U32LE.assertValueRoundtrip(0xFFFFFFFFL)
        U32LE.encodeToBytes(0xFFFFFFFFL) shouldBe
            byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
      }

      test("U32BE roundtrip") {
        U32BE.encodeToBytes(0x11223344L) shouldBe byteArrayOf(0x11, 0x22, 0x33, 0x44)
      }

      test("S64LE wire format") {
        S64LE.encodeToBytes(0x0102030405060708L) shouldBe
            byteArrayOf(0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01)
        S64LE.assertValueRoundtrip(Long.MIN_VALUE)
        S64LE.assertValueRoundtrip(Long.MAX_VALUE)
      }

      test("S64BE wire format") {
        S64BE.encodeToBytes(0x0102030405060708L) shouldBe
            byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08)
      }

      test("F32 / F64 roundtrip") {
        F32LE.assertValueRoundtrip(3.14f)
        F32BE.assertValueRoundtrip(-0.0f)
        F64LE.assertValueRoundtrip(2.718281828)
        F64BE.assertValueRoundtrip(Double.NEGATIVE_INFINITY)
      }

      test("fixedBytes preserves payload") {
        val codec = fixedBytes(4)
        val payload = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        codec.assertBytesRoundtrip(payload)
        shouldThrow<MalformedPacketException> { codec.encodeToBytes(byteArrayOf(0, 0, 0)) }
      }

      test("unknownBytes is lossless") {
        val codec = unknownBytes(3)
        codec.assertBytesRoundtrip(byteArrayOf(0xDE.toByte(), 0xAD.toByte(), 0xBE.toByte()))
      }

      test("reserved strict checks value") {
        val codec = reserved(0xAB, strict = true)
        codec.decodeBytes(byteArrayOf(0xAB.toByte())) shouldBe Unit
        shouldThrow<ReservedByteMismatchException> { codec.decodeBytes(byteArrayOf(0x01)) }
      }

      test("reserved non-strict accepts anything on read but writes literal") {
        val codec = reserved(0x00)
        codec.decodeBytes(byteArrayOf(0xFF.toByte())) shouldBe Unit
        codec.encodeToBytes(Unit) shouldBe byteArrayOf(0x00)
      }

      test("padding writes fill bytes") {
        val codec = padding(3, fill = 0xAA.toByte())
        codec.encodeToBytes(Unit) shouldBe byteArrayOf(0xAA.toByte(), 0xAA.toByte(), 0xAA.toByte())
      }

      test("Utf16LeNullTerminated roundtrip") {
        Utf16LeNullTerminated.assertValueRoundtrip("hello")
        Utf16LeNullTerminated.assertValueRoundtrip("")
        Utf16LeNullTerminated.assertValueRoundtrip("naïve résumé")
        Utf16LeNullTerminated.encodeToBytes("ab") shouldBe
            byteArrayOf(0x61, 0x00, 0x62, 0x00, 0x00, 0x00)
      }

      test("Utf16BeNullTerminated roundtrip") {
        Utf16BeNullTerminated.assertValueRoundtrip("hello")
        Utf16BeNullTerminated.encodeToBytes("ab") shouldBe
            byteArrayOf(0x00, 0x61, 0x00, 0x62, 0x00, 0x00)
      }

      test("AsciiNullTerminated roundtrip") {
        AsciiNullTerminated.assertValueRoundtrip("hello")
        AsciiNullTerminated.encodeToBytes("ab") shouldBe byteArrayOf(0x61, 0x62, 0x00)
      }
    })
