package de.fiereu.openmmo.launcher.client

data class MainFeed(
    val loginHost: String,
    val loginPort: Int,
    val revision: Int,
    val minRevision: Int,
)

data class RemoteFile(
    val name: String,
    val sha256: String,
    val size: Long,
    val os: String = "",
    val arch: String = "",
    val executable: Boolean = false,
    val onlyIfNotExists: Boolean = false,
) {
  fun appliesTo(platform: Platform): Boolean =
      (os.isEmpty() || os == platform.os.feedName) &&
          (arch.isEmpty() || arch == platform.arch.feedName)

  val cacheBuster: String
    get() = sha256.take(8)
}

data class UpdateFeed(val files: List<RemoteFile>) {

  fun forPlatform(platform: Platform): List<RemoteFile> = files.filter { it.appliesTo(platform) }

  // only_if_not_exists entries are excluded, because sync never checks their bytes.
  fun declares(relativePath: String): Boolean {
    val needle = relativePath.replace('\\', '/')
    return files.any { !it.onlyIfNotExists && it.name == needle }
  }
}

data class Feeds(val main: MainFeed, val update: UpdateFeed, val mirror: String)
