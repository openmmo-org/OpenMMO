package de.fiereu.openmmo.launcher

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BytePatternTest :
    FunSpec({
      test("reads a signature the way a disassembler prints one") {
        val pattern = BytePattern.parse("83 7A 20 03 75 ?? E8 ?? ?? ?? ?? 90 41 83 7F 10 00")

        pattern.size shouldBe 17
        pattern.matchesAt(
            parseHexBytes("83 7A 20 03 75 1F E8 04 03 02 01 90 41 83 7F 10 00"), 0) shouldBe true
        // Only the wildcards may differ.
        pattern.matchesAt(
            parseHexBytes("83 7A 20 03 75 FF E8 AA BB CC DD 90 41 83 7F 10 00"), 0) shouldBe true
        pattern.matchesAt(
            parseHexBytes("83 7A 20 03 74 1F E8 04 03 02 01 90 41 83 7F 10 00"), 0) shouldBe false
      }

      test("accepts lower case and any run of whitespace") {
        BytePattern.parse(" b8\t01\n00 ") shouldMatch parseHexBytes("B8 01 00")
      }

      test("rejects a token that is not a byte") {
        listOf("B8 1", "B8 GG", "B8 012", "B8 -1", "B8 ?").forEach {
          shouldThrow<IllegalArgumentException> { BytePattern.parse(it) }
        }
      }

      test("rejects an empty pattern, which would match everywhere") {
        shouldThrow<IllegalArgumentException> { BytePattern.parse("   ") }
        shouldThrow<IllegalArgumentException> { parseHexBytes("") }
      }

      test("refuses a wildcard where concrete bytes are required") {
        shouldThrow<IllegalArgumentException> { parseHexBytes("B8 ?? 00") }
      }

      test("matches a string byte for byte") {
        val pattern = BytePattern.ofText("abc")

        pattern.size shouldBe 3
        pattern.matchesAt("..abc".toByteArray(Charsets.ISO_8859_1), 2) shouldBe true
        pattern.matchesAt("..abd".toByteArray(Charsets.ISO_8859_1), 2) shouldBe false
      }
    })

private infix fun BytePattern.shouldMatch(data: ByteArray) {
  size shouldBe data.size
  matchesAt(data, 0) shouldBe true
}
