package de.fiereu.openmmo.launcher.client

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.net.http.HttpClient
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

class DeltaClientTest :
    FunSpec({
      test("fetches delta manifest from delta origin") {
        val jsonManifest =
            """
            {
              "fromRevision": 32824,
              "toRevision": 32763,
              "patches": [
                {
                  "name": "revision.txt",
                  "deltaUrl": "revision.txt.xdelta",
                  "sourceSha256": "abc",
                  "targetSha256": "def",
                  "targetSize": 5
                }
              ]
            }
            """
                .trimIndent()

        val server = TestHttpServer(mapOf("/32824/manifest.json" to jsonManifest.toByteArray()))
        server.start()

        try {
          val client = DeltaClient(HttpClient.newHttpClient(), server.origin)
          val manifest = client.fetchManifest(32824)

          manifest shouldNotBe null
          manifest!!.fromRevision shouldBe 32824
          manifest.toRevision shouldBe 32763
          manifest.patches.size shouldBe 1
          manifest.patches[0].name shouldBe "revision.txt"
        } finally {
          server.stop()
        }
      }

      test("returns null when manifest is not found") {
        val server = TestHttpServer(emptyMap())
        server.start()

        try {
          val client = DeltaClient(HttpClient.newHttpClient(), server.origin)
          val manifest = client.fetchManifest(99999)

          manifest shouldBe null
        } finally {
          server.stop()
        }
      }

      test("downloads patch file to target directory") {
        val patchContent = "XDELTA_PATCH_BYTES".toByteArray()
        val server = TestHttpServer(mapOf("/32824/revision.txt.xdelta" to patchContent))
        server.start()

        val tempDir = createTempDirectory("delta_test")
        val targetPath = tempDir.resolve("patch.tmp")

        try {
          val client = DeltaClient(HttpClient.newHttpClient(), server.origin)
          val patch = DeltaFilePatch(name = "revision.txt", deltaUrl = "revision.txt.xdelta")

          client.downloadPatch(32824, patch, targetPath)

          Files.exists(targetPath) shouldBe true
          Files.readAllBytes(targetPath) shouldBe patchContent
        } finally {
          server.stop()
          tempDir.toFile().deleteRecursively()
        }
      }
    })
