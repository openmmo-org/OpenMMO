package de.fiereu.openmmo.common.utils

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HexTest :
    FunSpec({
      test("decodes a hex string into bytes") {
        "0c2e0dff00".hexToBytes() shouldBe byteArrayOf(0x0c, 0x2e, 0x0d, -1, 0)
      }

      test("decodes upper case digits") { "FF0A".hexToBytes() shouldBe byteArrayOf(-1, 0x0a) }

      test("encodes bytes as lower case hex") {
        byteArrayOf(0x0c, 0x2e, 0x0d, -1, 0).toHex() shouldBe "0c2e0dff00"
      }

      test("round-trips an empty value") {
        "".hexToBytes() shouldBe byteArrayOf()
        byteArrayOf().toHex() shouldBe ""
      }

      test("rejects an odd length") { shouldThrow<IllegalArgumentException> { "abc".hexToBytes() } }

      test("rejects a non hex digit") {
        shouldThrow<IllegalArgumentException> { "0g".hexToBytes() }
      }
    })
