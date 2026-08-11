package de.fiereu.openmmo.launcher.client

enum class Os(val feedName: String) {
  WINDOWS("windows"),
  LINUX("linux"),
  MACOS("macos");

  companion object {
    fun current(): Os {
      val name = System.getProperty("os.name").orEmpty().lowercase()
      return when {
        // Before the windows check, because "darwin" contains "win".
        name.contains("mac") || name.contains("darwin") -> MACOS
        name.contains("win") -> WINDOWS
        else -> LINUX
      }
    }
  }
}

enum class Arch(val feedName: String) {
  X64("x64"),
  ARM64("arm64");

  companion object {
    fun current(): Arch {
      val name = System.getProperty("os.arch").orEmpty().lowercase()
      return if (name == "aarch64" || name.startsWith("arm")) ARM64 else X64
    }
  }
}

data class Platform(val os: Os, val arch: Arch) {
  companion object {
    fun current(): Platform = Platform(Os.current(), Arch.current())
  }
}
