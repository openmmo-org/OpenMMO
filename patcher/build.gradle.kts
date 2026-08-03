plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.common.keys")
}

dependencies {
  implementation(libs.bundles.crypto)
  testImplementation(libs.bundles.kotest)
}

fun String.evalEnvVars(): String =
    replace(Regex("\\$\\{([^}]+)\\}")) { System.getenv(it.groupValues[1]) ?: it.value }

tasks.register<JavaExec>("run") {
  group = "application"
  description = "Patches the PokeMMO client and runs the patched copy"

  val pokemmoExecutable = (project.findProperty("pokemmo.executable") as String).evalEnvVars()
  val pokemmoWorkingDir = (project.findProperty("pokemmo.workingDir") as String).evalEnvVars()
  val patchedExecutable = layout.buildDirectory.file("PokeMMO-openmmo.exe")

  mainClass.set("de.fiereu.openmmo.patcher.Launcher")
  classpath(sourceSets.main.get().runtimeClasspath)
  systemProperty("openmmo.executable", pokemmoExecutable)
  systemProperty("openmmo.workingDir", pokemmoWorkingDir)
  systemProperty("openmmo.output", patchedExecutable.get().asFile.path)
  maxHeapSize = "1g"
}

listOf("classes", "processResources").forEach { taskName ->
  tasks.named(taskName) { dependsOn("copyPublicKeys", "copyPrivateKeyFeed") }
}
