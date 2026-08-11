package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.RemoteFile
import de.fiereu.openmmo.launcher.client.UpdateFeed
import de.fiereu.openmmo.launcher.client.sha256
import de.fiereu.openmmo.launcher.parseHexBytes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

private const val STRINGS =
    """<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<strings lang="en" lang_full="English" is_primary="1">
  <string id="9">Day-Care</string>
  <string id="24">The game server could not validate the game client.</string>
</strings>
"""

class PatchEngineTest :
    FunSpec({
      fun setup(): Pair<ManagedInstall, UpdateFeed> {
        val install = ManagedInstall(createTempDirectory("patch")).create()
        Files.write(install.resolve("PokeMMO.exe"), "..loginserver.pokemmo.com..".toByteArray())
        val strings = install.resolve("data/strings/strings_en.xml")
        Files.createDirectories(strings.parent)
        Files.write(strings, STRINGS.toByteArray())
        Files.write(install.resolve("data/data.pak"), "untouched".toByteArray())
        val feed =
            UpdateFeed(
                listOf(
                    RemoteFile("PokeMMO.exe", "a".repeat(64), 27),
                    RemoteFile("data/strings/strings_en.xml", "b".repeat(64), 10),
                    RemoteFile("data/data.pak", "c".repeat(64), 9),
                ))
        return install to feed
      }

      test("rewrites a binary string and leaves everything else alone") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe",
                        "Host",
                        find = "loginserver.pokemmo.com",
                        replace = "openmmo.example.invalid",
                    )))

        val tree = PatchEngine(install).apply(manifest, feed)

        Files.readString(install.runtime.resolve("PokeMMO.exe")) shouldBe
            "..openmmo.example.invalid.."
        Files.readString(install.runtime.resolve("data/data.pak")) shouldBe "untouched"
        tree.files["PokeMMO.exe"] shouldBe Origin.PATCHED
        tree.files["data/data.pak"] shouldBe Origin.FEED
      }

      test("never writes through to the pristine tree") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe",
                        "Host",
                        "loginserver.pokemmo.com",
                        "openmmo.example.invalid")))

        PatchEngine(install).apply(manifest, feed)

        Files.readString(install.resolve("PokeMMO.exe")) shouldContain "loginserver.pokemmo.com"
      }

      test("resolves a replacement supplied at apply time") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe",
                        "Host",
                        "loginserver.pokemmo.com",
                        replaceRef = "login.host")))

        val engine = PatchEngine(install, values = mapOf("login.host" to "openmmo.example.invalid"))
        engine.apply(manifest, feed)

        Files.readString(install.runtime.resolve("PokeMMO.exe")) shouldContain
            "openmmo.example.invalid"
      }

      test("fails loudly when a binary patch no longer matches") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe",
                        "Gone",
                        "aaaaaaaaaaaaaaaaaaaaaaa",
                        "bbbbbbbbbbbbbbbbbbbbbbb")))

        val error = shouldThrow<PatchFailedException> { PatchEngine(install).apply(manifest, feed) }

        error.message shouldContain "found nothing to replace"
      }

      test("fails when a runtime value the manifest asks for is missing") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe", "Key", "loginserver.pokemmo.com", replaceRef = "absent")))

        shouldThrow<PatchFailedException> { PatchEngine(install).apply(manifest, feed) }
      }

      test("rewrites the bytes a signature points at and leaves the rest of the match alone") {
        val (install, feed) = setup()
        val code = parseHexBytes("CC 83 7A 20 03 75 1F E8 44 33 22 11 90 41 83 7F 10 00 CC")
        Files.write(install.resolve("PokeMMO.exe"), code)
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinarySignaturePatch(
                        "PokeMMO.exe",
                        "SkipCall",
                        signature = "83 7A 20 03 75 ?? E8 ?? ?? ?? ?? 90 41 83 7F 10 00",
                        replace = "B8 01 00 00 00",
                        offset = 6,
                    )))

        val tree = PatchEngine(install).apply(manifest, feed)

        Files.readAllBytes(install.runtime.resolve("PokeMMO.exe")) shouldBe
            parseHexBytes("CC 83 7A 20 03 75 1F B8 01 00 00 00 90 41 83 7F 10 00 CC")
        Files.readAllBytes(install.resolve("PokeMMO.exe")) shouldBe code
        tree.files["PokeMMO.exe"] shouldBe Origin.PATCHED
      }

      test("fails loudly when a signature no longer matches") {
        val (install, feed) = setup()
        Files.write(install.resolve("PokeMMO.exe"), parseHexBytes("CC CC CC CC"))
        val manifest =
            PatchManifest(
                32763, listOf(BinarySignaturePatch("PokeMMO.exe", "Gone", "83 7A ?? 03", "90 90")))

        val error = shouldThrow<PatchFailedException> { PatchEngine(install).apply(manifest, feed) }

        error.message shouldContain "found nothing to replace"
      }

      test("adds strings without disturbing the ones already there") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    StringsPatch(
                        "data/strings/strings_en.xml",
                        "Branding",
                        mapOf(1000000001 to "OpenMMO", 1000000000 to "Connected to OpenMMO"))))

        PatchEngine(install).apply(manifest, feed)

        val patched = Files.readString(install.runtime.resolve("data/strings/strings_en.xml"))
        patched shouldContain "Day-Care"
        patched shouldContain """id="1000000000""""
        patched shouldContain "Connected to OpenMMO"
        patched shouldContain """id="1000000001""""
      }

      test("refuses to overwrite a string id the client already uses") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(StringsPatch("data/strings/strings_en.xml", "Clash", mapOf(24 to "hijack"))))

        val error = shouldThrow<PatchFailedException> { PatchEngine(install).apply(manifest, feed) }

        error.message shouldContain "would overwrite string ids"
      }

      test("adds a file the feed does not carry and records it as added") {
        val (install, feed) = setup()
        val payload = "openmmo mod".toByteArray()
        val manifest =
            PatchManifest(
                32763,
                listOf(FilePatch("data/mods/openmmo.zip", "Mod", "mod.zip", sha256(payload))))

        val tree =
            PatchEngine(
                    install, assets = { name -> if (name == "mod.zip") payload else error(name) })
                .apply(manifest, feed)

        Files.readAllBytes(install.runtime.resolve("data/mods/openmmo.zip")) shouldBe payload
        tree.files["data/mods/openmmo.zip"] shouldBe Origin.ADDED
      }

      test("refuses an asset whose bytes do not match the manifest") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763, listOf(FilePatch("data/mods/openmmo.zip", "Mod", "mod.zip", "f".repeat(64))))

        shouldThrow<PatchFailedException> {
          PatchEngine(install, assets = { "wrong".toByteArray() }).apply(manifest, feed)
        }
      }

      test("refuses to patch a target the feed does not declare") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(32763, listOf(BinaryStringPatch("stowaway.dll", "N", "aaaa", "bbbb")))

        val error = shouldThrow<PatchFailedException> { PatchEngine(install).apply(manifest, feed) }

        error.message shouldContain "not in the feed"
      }

      test("points @client at the executable of whichever platform is running") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        CLIENT_TARGET,
                        "Host",
                        "loginserver.pokemmo.com",
                        "openmmo.example.invalid")))

        val tree = PatchEngine(install, clientExecutable = "PokeMMO.exe").apply(manifest, feed)

        tree.files["PokeMMO.exe"] shouldBe Origin.PATCHED
        Files.readString(install.runtime.resolve("PokeMMO.exe")) shouldContain
            "openmmo.example.invalid"
      }

      test("fails when @client cannot be resolved") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(32763, listOf(BinaryStringPatch(CLIENT_TARGET, "Host", "aaaa", "bbbb")))

        shouldThrow<PatchFailedException> { PatchEngine(install).apply(manifest, feed) }
      }

      test("declares exactly what it produced, so nothing else may be launched") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe",
                        "Host",
                        "loginserver.pokemmo.com",
                        "openmmo.example.invalid")))

        val tree = PatchEngine(install).apply(manifest, feed)

        tree.declares("PokeMMO.exe") shouldBe true
        tree.declares("data/data.pak") shouldBe true
        tree.declares("evil.exe") shouldBe false
      }

      test("patching a file that was hard linked last run leaves the pristine copy alone") {
        val (install, feed) = setup()
        val unpatched =
            PatchManifest(
                32763, listOf(FilePatch("extra.bin", "X", "x", sha256("x".toByteArray()))))
        val engine = PatchEngine(install, assets = { "x".toByteArray() })
        engine.apply(unpatched, feed)

        val patched =
            PatchManifest(
                32763,
                listOf(
                    StringsPatch(
                        "data/strings/strings_en.xml", "Branding", mapOf(1000000000 to "OpenMMO"))))
        engine.apply(patched, feed)

        Files.readString(install.resolve("data/strings/strings_en.xml")) shouldBe STRINGS
      }

      test("keeps the state the client writes into its own working directory") {
        val (install, feed) = setup()
        val manifest =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe",
                        "Host",
                        "loginserver.pokemmo.com",
                        "openmmo.example.invalid")))
        val engine = PatchEngine(install)
        engine.apply(manifest, feed)

        // The client keeps its settings, chosen ROMs and caches beside the binary it runs.
        val config = install.runtime.resolve("config/settings.properties")
        Files.createDirectories(config.parent)
        Files.write(config, "roms.configured=true".toByteArray())
        val rom = install.runtime.resolve("roms/emerald.gba")
        Files.createDirectories(rom.parent)
        Files.write(rom, "rom".toByteArray())

        engine.apply(manifest, feed)

        Files.readString(config) shouldBe "roms.configured=true"
        Files.exists(rom) shouldBe true
      }

      test("rebuilds from scratch so a removed patch leaves nothing behind") {
        val (install, feed) = setup()
        val payload = "temporary".toByteArray()
        val withExtra =
            PatchManifest(32763, listOf(FilePatch("data/extra.bin", "Extra", "x", sha256(payload))))
        val engine = PatchEngine(install, assets = { payload })

        engine.apply(withExtra, feed)
        Files.exists(install.runtime.resolve("data/extra.bin")) shouldBe true

        val without =
            PatchManifest(
                32763,
                listOf(
                    BinaryStringPatch(
                        "PokeMMO.exe",
                        "Host",
                        "loginserver.pokemmo.com",
                        "openmmo.example.invalid")))
        val tree = engine.apply(without, feed)

        Files.exists(install.runtime.resolve("data/extra.bin")) shouldBe false
        tree.declares("data/extra.bin") shouldBe false
      }
    })
