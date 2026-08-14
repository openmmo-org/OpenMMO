package de.fiereu.openmmo.launcher.client

import java.nio.file.Files
import java.nio.file.Path

// [client] is exactly what the feed describes, so every file there has a hash to check against.
// Patching reads it and writes [runtime], which can be rebuilt at any time.
class ManagedInstall(val root: Path) {

  val client: Path = root.resolve("client")
  val runtime: Path = root.resolve("runtime")
  val manifests: Path = root.resolve("manifests")
  val tools: Path = root.resolve("tools")

  fun create(): ManagedInstall {
    listOf(client, runtime, manifests, tools).forEach(Files::createDirectories)
    return this
  }

  fun resolve(name: String): Path {
    val sanitized = FeedParser.sanitize(name) ?: error("Unsafe feed name: $name")
    val resolved = client.resolve(sanitized).normalize()
    if (!resolved.startsWith(client.normalize())) error("Feed name escapes the install: $name")
    return resolved
  }

  fun revision(): Int? =
      runCatching { Files.readString(client.resolve("revision.txt")).trim().toInt() }.getOrNull()

  companion object {
    private const val DIRECTORY = "OpenMMO"

    fun defaultRoot(): Path {
      val home = Path.of(System.getProperty("user.home"))
      return when (Os.current()) {
        Os.WINDOWS ->
            System.getenv("LOCALAPPDATA")?.let { Path.of(it) }?.resolve(DIRECTORY)
                ?: home.resolve("AppData/Local/$DIRECTORY")
        Os.MACOS -> home.resolve("Library/Application Support/$DIRECTORY")
        Os.LINUX ->
            System.getenv("XDG_DATA_HOME")?.let { Path.of(it) }?.resolve(DIRECTORY)
                ?: home.resolve(".local/share/$DIRECTORY")
      }
    }
  }
}
