package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.ArchiveClient
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.client.UpdateFeed
import java.nio.file.Files
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArchivePatchFailedException(message: String, cause: Throwable? = null) :
    Exception(message, cause)

class ArchivePatcher(
    private val install: ManagedInstall,
    private val client: ArchiveClient,
    private val platform: Platform = Platform.current(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend fun apply(
      fromRevision: Int,
      toRevision: Int,
      currentFeed: UpdateFeed,
  ): UpdateFeed =
      withContext(dispatcher) {
        val sourceTar = install.resolve(".source-archive.tar")
        val deltaFile = install.resolve(".delta-patch.xdelta")
        val targetTar = install.resolve(".target-archive.tar")

        try {
          println(
              "[ArchivePatcher] 1. Creating canonical tar of current client (${currentFeed.files.size} files)")
          CanonicalTar.create(install.client, currentFeed.files, sourceTar)

          println("[ArchivePatcher] 2. Downloading delta patch $fromRevision -> $toRevision")
          client.downloadDelta(fromRevision, toRevision, deltaFile)

          println("[ArchivePatcher] 3. Applying xdelta patch to reconstruct target archive")
          XDeltaApplier.apply(sourceTar, deltaFile, targetTar, install.tools, platform, client.http)

          println("[ArchivePatcher] 4. Extracting restored client into ${install.client}")
          CanonicalTar.extract(targetTar, install.client)

          println("[ArchivePatcher] 5. Fetching target update feed for revision $toRevision")
          val targetFeed = client.fetchUpdateFeed(toRevision)
          println("[ArchivePatcher] Finished delta patch workflow successfully")
          targetFeed
        } catch (e: CancellationException) {
          throw e
        } catch (e: Exception) {
          System.err.println("[ArchivePatcher] Error applying delta patch:")
          e.printStackTrace()
          throw ArchivePatchFailedException(
              "Failed to apply archive delta patch $fromRevision -> $toRevision: ${e.message}",
              e,
          )
        } finally {
          Files.deleteIfExists(sourceTar)
          Files.deleteIfExists(deltaFile)
          Files.deleteIfExists(targetTar)
        }
      }
}
