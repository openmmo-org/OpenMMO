package de.fiereu.openmmo.launcher.client

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class FeedParserTest :
    FunSpec({
      test("reads the live main feed") {
        val main = FeedParser.parseMain(fixture("main_feed.txt"))

        main.loginHost shouldBe "loginserver.pokemmo.com"
        main.loginPort shouldBe 2106
        main.revision shouldBe 32763
        main.minRevision shouldBe 32763
      }

      test("reads every file entry of the live update feed") {
        val feed = FeedParser.parseUpdate(fixture("update_feed.txt"))

        feed.files.size shouldBe 174
        feed.files.map { it.name } shouldContain "data/strings/strings_en.xml"
      }

      test("keeps the windows client, which sits at the root rather than under bin") {
        val feed = FeedParser.parseUpdate(fixture("update_feed.txt"))
        val exe = feed.files.single { it.name == "PokeMMO.exe" }

        exe.os shouldBe "windows"
        exe.arch shouldBe "x64"
        exe.size shouldBe 120026160
        exe.sha256 shouldBe "ef9e0c0b46c2df4cf1e4c9b5c8857552a4d35dfe94e46db8226790b2f66b81f8"
      }

      test("marks the platform binaries executable") {
        val feed = FeedParser.parseUpdate(fixture("update_feed.txt"))

        feed.files.single { it.name == "bin/linux/x64/PokeMMO" }.executable shouldBe true
      }

      test("selects entries for the running platform") {
        val feed = FeedParser.parseUpdate(fixture("update_feed.txt"))
        val windows = feed.forPlatform(Platform(Os.WINDOWS, Arch.X64)).map { it.name }
        val linux = feed.forPlatform(Platform(Os.LINUX, Arch.X64)).map { it.name }

        windows shouldContain "PokeMMO.exe"
        windows.none { it.startsWith("bin/") } shouldBe true
        linux shouldContain "bin/linux/x64/PokeMMO"
        linux.contains("PokeMMO.exe") shouldBe false
      }

      test("declares only what carries a checked hash") {
        val feed =
            UpdateFeed(
                listOf(
                    RemoteFile("PokeMMO.exe", "a".repeat(64), 10),
                    RemoteFile("config.ini", "b".repeat(64), 10, onlyIfNotExists = true),
                ))

        feed.declares("PokeMMO.exe") shouldBe true
        feed.declares("config.ini") shouldBe false
        feed.declares("absent") shouldBe false
      }

      test("rejects names that escape the install directory") {
        FeedParser.sanitize("../../etc/passwd").shouldBeNull()
        FeedParser.sanitize("/etc/passwd").shouldBeNull()
        FeedParser.sanitize("C:/windows/system32").shouldBeNull()
        FeedParser.sanitize("data/../../escape").shouldBeNull()
        FeedParser.sanitize("").shouldBeNull()
        FeedParser.sanitize("data/sprites/sp.pak") shouldBe "data/sprites/sp.pak"
        FeedParser.sanitize("bin\\windows\\x64\\PokeMMO.exe") shouldBe "bin/windows/x64/PokeMMO.exe"
      }

      test("drops unusable entries instead of failing the feed") {
        val xml =
            """
            <?xml version="1.0" encoding="UTF-8" standalone="no"?>
            <update_feed>
              <file name="good.bin" sha256="${"a".repeat(64)}" size="10"/>
              <file name="../escape.bin" sha256="${"b".repeat(64)}" size="10"/>
              <file name="nohash.bin" size="10"/>
              <file name="badsize.bin" sha256="${"c".repeat(64)}" size="zero"/>
              <file name="empty.bin" sha256="${"d".repeat(64)}" size="0"/>
              <file name="shorthash.bin" sha256="abc" size="10"/>
            </update_feed>
            """
                .trimIndent()

        val feed = FeedParser.parseUpdate(xml.toByteArray())

        feed.files.map { it.name } shouldBe listOf("good.bin")
      }

      test("refuses a document that declares an external entity") {
        val xml =
            """
            <?xml version="1.0"?>
            <!DOCTYPE update_feed [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
            <update_feed><file name="&xxe;" sha256="${"a".repeat(64)}" size="1"/></update_feed>
            """
                .trimIndent()

        runCatching { FeedParser.parseUpdate(xml.toByteArray()) }.exceptionOrNull() shouldNotBe null
      }
    })

internal fun fixture(name: String): ByteArray =
    FeedParserTest::class.java.getResourceAsStream("/fixtures/feeds/$name")?.use { it.readBytes() }
        ?: error("Missing fixture: $name")
