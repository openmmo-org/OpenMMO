package de.fiereu.openmmo.server.game.storage

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class RowDeltaTest :
    FunSpec({
      test("a new key is changed") {
        rowDelta(mapOf(1 to "a"), mapOf(1 to "a", 2 to "b")) shouldBe
            RowDelta(mapOf(2 to "b"), emptySet())
      }

      test("a different value is changed") {
        rowDelta(mapOf(1 to "a"), mapOf(1 to "b")) shouldBe RowDelta(mapOf(1 to "b"), emptySet())
      }

      test("a dropped key is removed") {
        rowDelta(mapOf(1 to "a", 2 to "b"), mapOf(1 to "a")) shouldBe RowDelta(emptyMap(), setOf(2))
      }

      test("equal versions produce an empty delta") {
        val delta = rowDelta(mapOf(1 to "a"), mapOf(1 to "a"))
        delta.isEmpty shouldBe true
      }

      test("an empty base changes everything") {
        rowDelta(emptyMap<Int, String>(), mapOf(1 to "a")) shouldBe
            RowDelta(mapOf(1 to "a"), emptySet())
      }

      test("a key only table tracks added and dropped keys") {
        rowDelta(setOf("a", "b"), setOf("b", "c")) shouldBe RowDelta(mapOf("c" to Unit), setOf("a"))
      }

      test("an unchanged key only table produces an empty delta") {
        rowDelta(setOf("a"), setOf("a")).isEmpty shouldBe true
      }
    })
