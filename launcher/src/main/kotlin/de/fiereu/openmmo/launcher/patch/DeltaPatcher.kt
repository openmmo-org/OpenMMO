package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.DeltaClient
import de.fiereu.openmmo.launcher.client.DeltaManifest
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.RemoteFile
import de.fiereu.openmmo.launcher.client.UpdateFeed
import de.fiereu.openmmo.launcher.client.sha256
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.coroutines.cancellation.CancellationException

class DeltaPatchFailedException(message: String) : Exception(message)

class DeltaPatcher(
    private val install: ManagedInstall,
    private val client: DeltaClient,
) {

  suspend fun apply(manifest: DeltaManifest, currentFeed: UpdateFeed): UpdateFeed {
    val patchMap = manifest.patches.associateBy { it.name }

    for (patch in manifest.patches) {
      val sourceFile = install.resolve(patch.name)
      if (!Files.isRegularFile(sourceFile)) {
        throw DeltaPatchFailedException("Source file for delta patch does not exist: ${patch.name}")
      }

      if (patch.sourceSha256.isNotEmpty()) {
        val actualSourceHash = sha256(sourceFile)
        if (!actualSourceHash.equals(patch.sourceSha256, ignoreCase = true)) {
          // Check if file was already patched to target
          if (patch.targetSha256.isNotEmpty()) {
            val targetHash = sha256(sourceFile)
            if (targetHash.equals(patch.targetSha256, ignoreCase = true)) {
              continue // Already patched
            }
          }
          throw DeltaPatchFailedException(
              "Source file ${patch.name} hash $actualSourceHash does not match expected ${patch.sourceSha256}")
        }
      }

      val patchFile = install.resolve("${patch.name}.patch.tmp")
      val targetTmp = install.resolve("${patch.name}.target.tmp")

      try {
        client.downloadPatch(manifest.fromRevision, patch, patchFile)
        XDeltaApplier.apply(sourceFile, patchFile, targetTmp)

        if (patch.targetSize > 0) {
          val actualSize = Files.size(targetTmp)
          if (actualSize != patch.targetSize) {
            throw DeltaPatchFailedException(
                "Patched file ${patch.name} size $actualSize does not match expected ${patch.targetSize}")
          }
        }

        if (patch.targetSha256.isNotEmpty()) {
          val actualTargetHash = sha256(targetTmp)
          if (!actualTargetHash.equals(patch.targetSha256, ignoreCase = true)) {
            throw DeltaPatchFailedException(
                "Patched file ${patch.name} hash $actualTargetHash does not match expected ${patch.targetSha256}")
          }
        }

        move(targetTmp, sourceFile)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        throw DeltaPatchFailedException(
            "Failed to apply delta patch for ${patch.name}: ${e.message}")
      } finally {
        Files.deleteIfExists(patchFile)
        Files.deleteIfExists(targetTmp)
      }
    }

    if (manifest.targetFiles.isNotEmpty()) {
      return UpdateFeed(manifest.targetFiles)
    }

    val updatedFiles =
        currentFeed.files.map { file ->
          val patch = patchMap[file.name]
          if (patch != null) {
            val sourceFile = install.resolve(file.name)
            val newHash =
                if (patch.targetSha256.isNotEmpty()) patch.targetSha256 else sha256(sourceFile)
            val newSize = if (patch.targetSize > 0) patch.targetSize else Files.size(sourceFile)
            RemoteFile(
                name = file.name,
                sha256 = newHash,
                size = newSize,
                os = file.os,
                arch = file.arch,
                executable = file.executable,
                onlyIfNotExists = file.onlyIfNotExists,
            )
          } else {
            file
          }
        }
    return UpdateFeed(updatedFiles)
  }

  private fun move(source: Path, target: Path) {
    try {
      Files.move(
          source,
          target,
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.ATOMIC_MOVE,
      )
    } catch (_: AtomicMoveNotSupportedException) {
      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING)
    }
  }
}
