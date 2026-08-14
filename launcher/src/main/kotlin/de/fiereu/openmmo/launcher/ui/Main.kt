package de.fiereu.openmmo.launcher.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.launch.LauncherPipeline
import de.fiereu.openmmo.launcher.patch.PatchAssets
import java.nio.file.Path

private const val ROOT_PROPERTY = "openmmo.root"
private const val MANIFESTS_PROPERTY = "openmmo.manifests"

/** Set by the packaging step, and absent when running straight from Gradle. */
private const val PACKAGED_RESOURCES = "compose.application.resources.dir"

fun main() = application {
  val root = System.getProperty(ROOT_PROPERTY)?.let(Path::of) ?: ManagedInstall.defaultRoot()
  val manifestDir =
      System.getProperty(MANIFESTS_PROPERTY)?.let(Path::of)
          ?: System.getProperty(PACKAGED_RESOURCES)?.let(Path::of)
          ?: root.resolve("manifests")

  var state by remember { mutableStateOf(LauncherUiState()) }
  val scope = rememberCoroutineScope()
  val controller =
      remember(scope) {
        LauncherController(
            install = ManagedInstall(root),
            manifests = LauncherPipeline.manifestsIn(manifestDir),
            highestRevision = LauncherPipeline.highestSupportedRevisionIn(manifestDir),
            assets = PatchAssets.ofDirectory(manifestDir),
            scope = scope,
            onState = { state = it },
        )
      }

  Window(
      onCloseRequest = ::exitApplication,
      title = "OpenMMO",
      state = rememberWindowState(size = DpSize(520.dp, 320.dp)),
  ) {
    LauncherWindow(
        state = state,
        onPlay = { controller.play(onStarted = ::exitApplication) },
        onVerify = { controller.verify() })
  }
}
