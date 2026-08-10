package de.fiereu.openmmo.launcher.client

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.IOException
import java.net.http.HttpClient
import java.nio.file.Files
import java.security.MessageDigest
import kotlin.io.path.createTempDirectory
import kotlinx.coroutines.test.runTest

private fun hashOf(bytes: ByteArray) =
    MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }

class ClientSyncTest :
    FunSpec({
      val alpha = "alpha".toByteArray()
      val beta = "beta".toByteArray()
      val windows = Platform(Os.WINDOWS, Arch.X64)

      fun install() = ManagedInstall(createTempDirectory("sync")).create()

      fun sync(install: ManagedInstall) =
          ClientSync(install, Downloader(HttpClient.newHttpClient()), windows)

      test("builds the content url with the cache buster the CDN expects") {
        val file = RemoteFile("data/data.pak", "abcdef0123456789".padEnd(64, '0'), 10)

        sync(install()).contentUrl("https://dl.pokemmo.com", file) shouldBe
            "https://dl.pokemmo.com/live/current/client/data/data.pak?v=abcdef01"
      }

      test("plans a download for a file that is absent") {
        val target = install()
        val feed = UpdateFeed(listOf(RemoteFile("a.bin", hashOf(alpha), alpha.size.toLong())))

        val plan = sync(target).plan(feed)

        plan.stale.map { it.name } shouldBe listOf("a.bin")
        plan.bytesToFetch shouldBe alpha.size.toLong()
        plan.isUpToDate shouldBe false
      }

      test("skips a file whose bytes already hash to the published value") {
        val target = install()
        Files.write(target.resolve("a.bin"), alpha)
        val feed = UpdateFeed(listOf(RemoteFile("a.bin", hashOf(alpha), alpha.size.toLong())))

        val plan = sync(target).plan(feed)

        plan.isUpToDate shouldBe true
        plan.current.map { it.name } shouldBe listOf("a.bin")
      }

      test("re-fetches a file whose contents drifted from the feed") {
        val target = install()
        Files.write(target.resolve("a.bin"), beta)
        val feed = UpdateFeed(listOf(RemoteFile("a.bin", hashOf(alpha), alpha.size.toLong())))

        sync(target).plan(feed).stale.map { it.name } shouldBe listOf("a.bin")
      }

      test("leaves an only_if_not_exists file alone once it is there") {
        val target = install()
        Files.write(target.resolve("config.ini"), beta)
        val feed =
            UpdateFeed(listOf(RemoteFile("config.ini", hashOf(alpha), 5, onlyIfNotExists = true)))

        sync(target).plan(feed).isUpToDate shouldBe true
      }

      test("ignores entries meant for another platform") {
        val target = install()
        val feed =
            UpdateFeed(
                listOf(
                    RemoteFile("PokeMMO.exe", hashOf(alpha), 5, os = "windows", arch = "x64"),
                    RemoteFile(
                        "bin/linux/x64/PokeMMO", hashOf(beta), 4, os = "linux", arch = "x64"),
                ))

        sync(target).plan(feed).stale.map { it.name } shouldBe listOf("PokeMMO.exe")
      }

      test("downloads a planned file into the pristine tree") {
        runTest {
          val target = install()
          val server =
              TestHttpServer(mapOf("/live/current/client/data/a.bin" to alpha)).also { it.start() }
          try {
            val feed =
                UpdateFeed(listOf(RemoteFile("data/a.bin", hashOf(alpha), alpha.size.toLong())))
            val syncer = sync(target)
            val plan = syncer.plan(feed)

            syncer.execute(plan, server.origin)

            Files.readAllBytes(target.resolve("data/a.bin")) shouldBe alpha
            syncer.plan(feed).isUpToDate shouldBe true
          } finally {
            server.stop()
          }
        }
      }

      test("retries a failing download and resumes from what already arrived") {
        runTest {
          val target = install()
          val server = TestHttpServer(mapOf("/live/current/client/data/a.bin" to alpha))
          server.failFirst = 3
          server.start()
          try {
            val feed =
                UpdateFeed(listOf(RemoteFile("data/a.bin", hashOf(alpha), alpha.size.toLong())))
            val syncer = sync(target)
            syncer.execute(syncer.plan(feed), server.origin)

            Files.readAllBytes(target.resolve("data/a.bin")) shouldBe alpha
            server.requests.size shouldBe 4
          } finally {
            server.stop()
          }
        }
      }

      test("gives up after the attempt limit and fails the sync") {
        runTest {
          val target = install()
          val server = TestHttpServer(mapOf("/live/current/client/data/a.bin" to alpha))
          server.failFirst = Int.MAX_VALUE
          server.start()
          try {
            val feed =
                UpdateFeed(listOf(RemoteFile("data/a.bin", hashOf(alpha), alpha.size.toLong())))
            val syncer =
                ClientSync(target, Downloader(HttpClient.newHttpClient()), windows, attempts = 3)

            shouldThrow<IOException> { syncer.execute(syncer.plan(feed), server.origin) }
            server.requests.size shouldBe 3
          } finally {
            server.stop()
          }
        }
      }

      test("refuses a feed name that would escape the install directory") {
        val target = install()

        runCatching { target.resolve("../escape.bin") }.isFailure shouldBe true
      }
    })
