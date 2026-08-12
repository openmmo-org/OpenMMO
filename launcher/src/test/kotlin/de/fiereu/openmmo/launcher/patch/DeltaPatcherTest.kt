package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.DeltaClient
import de.fiereu.openmmo.launcher.client.DeltaFilePatch
import de.fiereu.openmmo.launcher.client.DeltaManifest
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.RemoteFile
import de.fiereu.openmmo.launcher.client.TestHttpServer
import de.fiereu.openmmo.launcher.client.UpdateFeed
import de.fiereu.openmmo.launcher.client.sha256
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.net.http.HttpClient
import java.nio.file.Files
import java.util.Base64
import kotlin.io.path.createTempDirectory

private const val PATCH_BASE64 =
    "1sPEAAUCgR1yZXZpc2lvbi50eHQjOTAwMDE3ZjZkYzI3NTZlMjI5ZTBmNjU2ZDgxZTEzM2Y5ZTA0NDExYzcwNjcxZjNkODJlZTY1M2U0MmU1YTk4Mi8vcmV2aXNpb24udHh0IzE3NDg2NzRiYjA3YWQ2NmQ1NTM4YzNhZWVjMDkwMTdkMzMyNDRlZDkyNDg0NDMzMjNmNDNjMDM2N2FiMjZlMjAvBA8FAAUBAAMQAQYzMjc2MwY="

class DeltaPatcherTest :
    FunSpec({
      test("applies delta patch to pristine file and updates update feed hashes") {
        val tempDir = createTempDirectory("delta_patcher_test")
        try {
          val install = ManagedInstall(tempDir)
          install.create()

          val sourceFile = install.resolve("revision.txt")
          Files.writeString(sourceFile, "32824")
          val sourceHash = sha256(sourceFile)

          val patchBytes = Base64.getDecoder().decode(PATCH_BASE64)
          val server = TestHttpServer(mapOf("/32824/revision.txt.xdelta" to patchBytes))
          server.start()

          try {
            val deltaClient = DeltaClient(HttpClient.newHttpClient(), server.origin)
            val deltaManifest =
                DeltaManifest(
                    fromRevision = 32824,
                    toRevision = 32763,
                    patches =
                        listOf(
                            DeltaFilePatch(
                                name = "revision.txt",
                                deltaUrl = "revision.txt.xdelta",
                                sourceSha256 = sourceHash,
                                targetSha256 = "",
                            )),
                )

            val currentFeed =
                UpdateFeed(
                    listOf(
                        RemoteFile(name = "revision.txt", sha256 = sourceHash, size = 5),
                    ))

            val patcher = DeltaPatcher(install, deltaClient)
            val updatedFeed = patcher.apply(deltaManifest, currentFeed)

            Files.readString(sourceFile).trim() shouldBe "32763"
            updatedFeed.files.size shouldBe 1
          } finally {
            server.stop()
          }
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }
    })
