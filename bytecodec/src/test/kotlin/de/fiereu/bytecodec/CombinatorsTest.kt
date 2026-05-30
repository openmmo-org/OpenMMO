package de.fiereu.bytecodec

import de.fiereu.bytecodec.test.assertBytesRoundtrip
import de.fiereu.bytecodec.test.assertValueRoundtrip
import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private enum class Color {
  RED,
  GREEN,
  BLUE
}

class CombinatorsTest :
    FunSpec({
      test("imap lifts an atom") {
        val codec = U8.imap(decode = { it + 1 }, encode = { it - 1 })
        codec.encodeToBytes(10) shouldBe byteArrayOf(0x09)
        codec.decodeBytes(byteArrayOf(0x09)) shouldBe 10
      }

      test("imapCatching wraps non-CodecException") {
        val codec =
            U8.imapCatching(
                decode = { error("boom") },
                encode = { _: Int -> 0 },
            )
        shouldThrow<MalformedPacketException> { codec.decodeBytes(byteArrayOf(0x00)) }
      }

      test("listPrefixed with U8 prefix") {
        val codec = U16LE.listPrefixed(U8)
        codec.assertValueRoundtrip(listOf(1, 2, 3))
        codec.encodeToBytes(emptyList()) shouldBe byteArrayOf(0x00)
        codec.encodeToBytes(listOf(0x1234, 0x5678)) shouldBe
            byteArrayOf(0x02, 0x34, 0x12, 0x78, 0x56)
      }

      test("repeat is fixed-count") {
        val codec = U8.repeat(3)
        codec.assertBytesRoundtrip(byteArrayOf(1, 2, 3))
        shouldThrow<MalformedPacketException> { codec.encodeToBytes(listOf(1, 2)) }
      }

      test("bytesPrefixed") {
        val codec = bytesPrefixed(U8)
        codec.assertBytesRoundtrip(byteArrayOf(0x03, 0xAA.toByte(), 0xBB.toByte(), 0xCC.toByte()))
      }

      test("stringPrefixed UTF-8") {
        val codec = stringPrefixed(U8, Charsets.UTF_8)
        codec.assertValueRoundtrip("hello")
        val bytes = "héllo".toByteArray(Charsets.UTF_8)
        codec.encodeToBytes("héllo")[0].toInt() shouldBe bytes.size
      }

      test("enumByOrdinalByte") {
        val codec = enumByOrdinalByte<Color>()
        codec.assertValueRoundtrip(Color.RED)
        codec.assertValueRoundtrip(Color.BLUE)
        codec.encodeToBytes(Color.BLUE) shouldBe byteArrayOf(0x02)
        shouldThrow<MalformedPacketException> { codec.decodeBytes(byteArrayOf(0x05)) }
      }

      test("enumBy with custom mapping") {
        val keys = mapOf(Color.RED to 10, Color.GREEN to 20, Color.BLUE to 30)
        val reverse = keys.entries.associate { (k, v) -> v to k }
        val codec =
            enumBy<Color>(
                prefix = U8,
                lookup = { reverse[it] ?: throw MalformedPacketException("bad key $it") },
                keyOf = { keys.getValue(it) },
            )
        codec.encodeToBytes(Color.GREEN) shouldBe byteArrayOf(20)
        codec.decodeBytes(byteArrayOf(30)) shouldBe Color.BLUE
      }

      test("choose dispatches on tag") {
        val u8List = U8.listPrefixed(U8)
        val codec: Codec<Any> =
            choose(
                tag = U8,
                identify = { v ->
                  when (v) {
                    is Int -> 0x01
                    is String -> 0x02
                    else -> error("unhandled $v")
                  }
                },
                0x01 to U16LE as Codec<out Any>,
                0x02 to AsciiNullTerminated as Codec<out Any>,
            )
        codec.encodeToBytes(0x1234) shouldBe byteArrayOf(0x01, 0x34, 0x12)
        codec.encodeToBytes("ok") shouldBe byteArrayOf(0x02, 0x6F, 0x6B, 0x00)
        shouldThrow<UnknownTagException> { codec.decodeBytes(byteArrayOf(0xFE.toByte())) }
        u8List.encodeToBytes(emptyList()).size shouldBe 1
      }

      test("optional with explicit flag") {
        val codec = U16LE.optional()
        codec.assertValueRoundtrip(null)
        codec.assertValueRoundtrip(0x1234)
        codec.encodeToBytes(null) shouldBe byteArrayOf(0x00)
        codec.encodeToBytes(0x1234) shouldBe byteArrayOf(0x01, 0x34, 0x12)
      }

      test("then pairs two codecs") {
        val codec = U8.then(U16LE)
        codec.assertValueRoundtrip(5 to 0x1234)
        codec.encodeToBytes(5 to 0x1234) shouldBe byteArrayOf(0x05, 0x34, 0x12)
      }

      test("bitsLE roundtrip") {
        val codec =
            bitsLE(U8) {
              bit(0, "a")
              bit(3, "b")
              bit(7, "c")
            }
        val set = codec.of("a" to true, "b" to false, "c" to true)
        set["a"] shouldBe true
        set["b"] shouldBe false
        set["c"] shouldBe true
        set[0] shouldBe true
        val bytes = codec.encodeToBytes(set)
        bytes shouldBe byteArrayOf(0b10000001.toByte())
        val decoded = codec.decodeBytes(bytes)
        decoded["a"] shouldBe true
        decoded["c"] shouldBe true
      }
    })
