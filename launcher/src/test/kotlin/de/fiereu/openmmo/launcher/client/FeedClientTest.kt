package de.fiereu.openmmo.launcher.client

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.net.http.HttpClient

class FeedClientTest :
    FunSpec({
      val routes =
          mapOf(
              "/live/current/feeds/main_feed.txt" to fixture("main_feed.txt"),
              "/live/current/feeds/main_feed.sig256" to fixture("main_feed.sig256"),
              "/live/current/feeds/update_feed.txt" to fixture("update_feed.txt"),
              "/live/current/feeds/update_feed.sig256" to fixture("update_feed.sig256"),
          )

      test("verifies the real live signatures against a key the client trusts") {
        val server = TestHttpServer(routes)
        server.start()
        try {
          val feeds = FeedClient(HttpClient.newHttpClient(), mirrors = listOf(server.origin)).load()

          feeds.main.revision shouldBe 32763
          feeds.update.files.size shouldBe 174
          feeds.mirror shouldBe server.origin
        } finally {
          server.stop()
        }
      }

      test("rejects a feed whose body was altered") {
        val tampered = routes.toMutableMap()
        val original = fixture("main_feed.txt").decodeToString()
        tampered["/live/current/feeds/main_feed.txt"] =
            original.replace("loginserver.pokemmo.com", "evil.example.com!!!").toByteArray()

        val server = TestHttpServer(tampered)
        server.start()
        try {
          shouldThrow<FeedUnavailableException> {
            FeedClient(HttpClient.newHttpClient(), mirrors = listOf(server.origin)).load()
          }
        } finally {
          server.stop()
        }
      }

      test("moves to the next mirror and leaves no state behind from the failed one") {
        val broken = TestHttpServer(routes).apply { failing = routes.keys }
        val healthy = TestHttpServer(routes)
        broken.start()
        healthy.start()
        try {
          val feeds =
              FeedClient(
                      HttpClient.newHttpClient(),
                      mirrors = listOf(broken.origin, healthy.origin),
                  )
                  .load()

          feeds.mirror shouldBe healthy.origin
          feeds.update.files.size shouldBe 174
        } finally {
          broken.stop()
          healthy.stop()
        }
      }

      test("reports every mirror it tried when none works") {
        val broken = TestHttpServer(routes).apply { failing = routes.keys }
        broken.start()
        try {
          val error =
              shouldThrow<FeedUnavailableException> {
                FeedClient(
                        HttpClient.newHttpClient(),
                        mirrors = listOf(broken.origin, "${broken.origin}/absent"),
                    )
                    .load()
              }

          error.failures.size shouldBe 2
        } finally {
          broken.stop()
        }
      }
    })
