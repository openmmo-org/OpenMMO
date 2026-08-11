package de.fiereu.openmmo.launcher.launch

import de.fiereu.openmmo.launcher.FeedTls
import de.fiereu.openmmo.launcher.client.Arch
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.Os
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.patch.RuntimeTree
import java.nio.file.Files
import java.nio.file.Path

class UnverifiedClientException(name: String) :
    Exception(
        "Refusing to launch $name because neither the feed nor a patch produced it. " +
            "Repair the install and try again.")

// PokeMMO keeps the x64 Windows client at the root instead of under bin.
fun executableName(platform: Platform): String =
    when {
      platform.os == Os.WINDOWS && platform.arch == Arch.X64 -> "PokeMMO.exe"
      platform.os == Os.WINDOWS -> "bin/windows/${platform.arch.feedName}/PokeMMO.exe"
      else -> "bin/${platform.os.feedName}/${platform.arch.feedName}/PokeMMO"
    }

class GameLaunch(
    private val install: ManagedInstall,
    private val platform: Platform = Platform.current(),
) {

  fun verifiedExecutable(runtime: RuntimeTree): Path {
    val name = executableName(platform)
    if (!runtime.declares(name)) throw UnverifiedClientException(name)

    val executable = install.runtime.resolve(name)
    if (!Files.isRegularFile(executable)) throw UnverifiedClientException(name)
    return executable
  }

  fun start(runtime: RuntimeTree, args: List<String> = emptyList()): Process {
    val executable = verifiedExecutable(runtime)
    if (platform.os != Os.WINDOWS && !executable.toFile().setExecutable(true, false)) {
      error("Could not mark $executable executable")
    }

    return ProcessBuilder(listOf(executable.toString()) + trustArguments() + args)
        .directory(install.runtime.toFile())
        .inheritIO()
        .start()
  }

  /**
   * A loopback feed is self signed, so the client is pointed at the trust store the dev server
   * wrote.
   */
  private fun trustArguments(): List<String> {
    if (!FeedOrigin.isLoopback) return emptyList()
    val trustStore = install.root.resolve(DEV_TRUSTSTORE)
    if (!Files.isRegularFile(trustStore)) {
      error("No dev trust store at $trustStore. Start the feed server with :launcher:feedServer.")
    }
    return listOf(
        "-Djavax.net.ssl.trustStore=$trustStore",
        "-Djavax.net.ssl.trustStorePassword=${FeedTls.password.concatToString()}",
        "-Djavax.net.ssl.trustStoreType=PKCS12",
    )
  }
}
