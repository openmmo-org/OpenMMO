package de.fiereu.openmmo.launcher.launch

import de.fiereu.openmmo.launcher.MAIN_NAME
import de.fiereu.openmmo.launcher.NEWS_NAME
import de.fiereu.openmmo.launcher.SIGNATURE_NAME
import de.fiereu.openmmo.launcher.client.Arch
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.Os
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.patch.BinaryStringPatch
import de.fiereu.openmmo.launcher.patch.Origin
import de.fiereu.openmmo.launcher.patch.Patch
import de.fiereu.openmmo.launcher.patch.RuntimeTree
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import io.kotest.matchers.string.shouldStartWith
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

class GameLaunchTest :
    FunSpec({
      test("finds the client where each platform keeps it") {
        executableName(Platform(Os.WINDOWS, Arch.X64)) shouldBe "PokeMMO.exe"
        executableName(Platform(Os.WINDOWS, Arch.ARM64)) shouldBe "bin/windows/arm64/PokeMMO.exe"
        executableName(Platform(Os.LINUX, Arch.X64)) shouldBe "bin/linux/x64/PokeMMO"
        executableName(Platform(Os.LINUX, Arch.ARM64)) shouldBe "bin/linux/arm64/PokeMMO"
        executableName(Platform(Os.MACOS, Arch.ARM64)) shouldBe "bin/macos/arm64/PokeMMO"
      }

      test("refuses to launch a client the runtime tree does not declare") {
        val install = ManagedInstall(createTempDirectory("launch")).create()
        val platform = Platform(Os.WINDOWS, Arch.X64)
        Files.write(install.runtime.resolve("PokeMMO.exe"), "stowaway".toByteArray())
        val tree = RuntimeTree(install.runtime, mapOf("data/data.pak" to Origin.FEED))

        val error =
            shouldThrow<UnverifiedClientException> {
              GameLaunch(install, platform).verifiedExecutable(tree)
            }

        error.message shouldContain "neither the feed nor a patch produced it"
      }

      test("refuses when the tree declares a client that is not on disk") {
        val install = ManagedInstall(createTempDirectory("launch")).create()
        val platform = Platform(Os.WINDOWS, Arch.X64)
        val tree = RuntimeTree(install.runtime, mapOf("PokeMMO.exe" to Origin.PATCHED))

        shouldThrow<UnverifiedClientException> {
          GameLaunch(install, platform).verifiedExecutable(tree)
        }
      }

      fun replacements(patches: List<Patch>) =
          patches.associate {
            it as BinaryStringPatch
            it.find to it.replace!!
          }

      test("every replacement is exactly as wide as the url it replaces") {
        listOf("https://feed.openmmo.dev", "https://127.0.0.1:4321", "https://127.0.0.1:65535")
            .forEach { origin ->
              replacements(feedPatches(origin)).forEach { (find, replace) ->
                replace.length shouldBe find.length
              }
            }
      }

      test("redirects every feed url the client carries") {
        val patches = replacements(feedPatches("https://feed.openmmo.dev"))

        patches.size shouldBe 12
        patches.getValue("https://dl.pokemmo.com/live/current/feeds/main_feed.txt") shouldStartWith
            "https://feed.openmmo.dev/main.xml"
        patches.getValue(
            "https://dl.pokemmo.com/live/current/feeds/main_feed.sig256") shouldStartWith
            "https://feed.openmmo.dev/main.sig256"
        patches.getValue("https://dl.pokemmo.com/news_feed.txt") shouldStartWith
            "https://feed.openmmo.dev/news.xml"
      }

      test("pads with a query the server discards, never a trailing slash") {
        // A static host answers main_feed.txt/// with a 404, while a query string is dropped before
        // the file is looked up.
        replacements(feedPatches("https://feed.openmmo.dev")).forEach { (_, replace) ->
          val padding = replace.substringAfter('?', "")
          replace.substringBefore('?') shouldNotContain "//x"
          padding.all { it == 'x' } shouldBe true
        }
      }

      test("the development profile points the same urls at the loopback feed") {
        val patches = replacements(feedPatches("https://127.0.0.1:4321"))

        patches.size shouldBe 12
        patches.values.forEach { it shouldStartWith "https://127.0.0.1:4321/" }
      }

      test("the dev server answers the paths the client is patched to ask for") {
        // The patched url and the path the dev server answers have to match.
        OPENMMO_FEED_PATHS.getValue(FeedFile.MAIN) shouldBe "/$MAIN_NAME"
        OPENMMO_FEED_PATHS.getValue(FeedFile.SIGNATURE) shouldBe "/$SIGNATURE_NAME"
        OPENMMO_FEED_PATHS.getValue(FeedFile.NEWS) shouldBe "/$NEWS_NAME"
      }

      test("reads the origin the build baked in") {
        // Properties escapes the colons on write, so this also checks it survives the round trip.
        FeedOrigin.configured shouldBe "https://127.0.0.1:$DEV_FEED_PORT"
        FeedOrigin.isLoopback shouldBe true
      }

      test("falls the login host back to a dead end rather than PokeMMO") {
        val patch = loginHostPatch() as BinaryStringPatch

        patch.find shouldBe "loginserver.pokemmo.com"
        patch.replace shouldBe DEAD_LOGIN_HOST
        patch.replace!!.length shouldBe patch.find.length
      }

      test("refuses a url too narrow to hold the replacement") {
        // The news url is only 36 characters, which is what forces the short news.xml name.
        shouldThrow<IllegalArgumentException> {
          padUrl("https://feed.openmmo.dev/news_feed.xml", 36)
        }
      }
    })
