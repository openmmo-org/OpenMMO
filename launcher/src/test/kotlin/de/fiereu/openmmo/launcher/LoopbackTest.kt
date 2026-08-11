package de.fiereu.openmmo.launcher

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.net.InetAddress
import java.net.URI

class LoopbackTest :
    FunSpec({
      test("shortest form") { Loopback.literal(23) shouldBe "[0:0:0:0:0:ffff:7f00:1]" }

      test("longest form") {
        Loopback.literal(41) shouldBe "[0000:0000:0000:0000:0000:ffff:7f00:0001]"
      }

      test("every supported width resolves to the loopback address") {
        for (width in Loopback.MIN_WIDTH..Loopback.MAX_WIDTH) {
          val literal = Loopback.literal(width)
          literal.length shouldBe width
          InetAddress.getByName(literal).hostAddress shouldBe "127.0.0.1"
        }
      }

      test("redirects an origin and keeps its length") {
        val origin = "https://dl.pokemmo.com"
        val redirected = Loopback.origin(4711, origin.length)

        redirected shouldBe "https://127.0.0.1:4711"
        URI(redirected + "/live/current/feeds/main_feed.txt").host shouldBe "127.0.0.1"
      }

      test("pads a longer origin with slashes the path absorbs") {
        val origin = "https://files.pokemmo.com"
        val redirected = Loopback.origin(4711, origin.length)

        redirected.length shouldBe origin.length
        redirected shouldBe "https://127.0.0.1:4711///"
        URI(redirected + "live/current/feeds/main_feed.txt").port shouldBe 4711
      }

      test("falls back to a dead port when the origin is too short") {
        val origin = "https://dl.pokemmo.eu"
        val redirected = Loopback.origin(4711, origin.length)

        redirected.length shouldBe origin.length
        redirected shouldBe "https://127.0.0.1:1//"
      }

      test("rejects widths it cannot write") {
        shouldThrow<IllegalArgumentException> { Loopback.literal(Loopback.MIN_WIDTH - 1) }
        shouldThrow<IllegalArgumentException> { Loopback.literal(Loopback.MAX_WIDTH + 1) }
      }
    })
