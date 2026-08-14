import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.common.keys")
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.compose)
  alias(libs.plugins.compose.compiler)
}

dependencies {
  implementation(libs.bundles.crypto)
  implementation(libs.kotlinx.coroutines)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.tomlkt)
  implementation(libs.vcdiff.core)
  implementation(compose.desktop.currentOs)
  implementation(compose.material3)
  testImplementation(libs.bundles.kotest)
  testImplementation(libs.kotlinx.coroutines.test)
}

compose.desktop {
  application {
    mainClass = "de.fiereu.openmmo.launcher.ui.MainKt"
    jvmArgs += "--enable-native-access=ALL-UNNAMED"

    nativeDistributions {
      // jlink ships only what is asked for, and a missing module fails at runtime, not at build.
      modules(
          "java.net.http",
          "java.xml",
          "jdk.httpserver",
          "jdk.crypto.ec",
          "java.naming",
          "jdk.unsupported",
      )
      targetFormats(TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Dmg)
      packageName = "OpenMMO"
      packageVersion = project.version.toString()
      description = "Provisions and patches a PokeMMO client for OpenMMO"
      vendor = "openmmo-org"
      licenseFile.set(rootProject.file("LICENSE"))

      // Only the platform subdirectories of this root are copied, hence the staged "common".
      appResourcesRootDir.set(layout.buildDirectory.dir("appResources"))
    }
  }
}

val stageAppResources by
    tasks.registering(Copy::class) {
      group = "openmmo"
      description = "Stages the patch manifests and keys where the packaging step picks them up"
      dependsOn("copyPublicKeys", "copyPrivateKeyFeed")
      from(layout.projectDirectory.dir("manifests"))
      // Only public keys ship. Development runs from Gradle, where the private one is on the
      // classpath already.
      from(layout.projectDirectory.dir("src/main/resources")) { include("*.public.pem") }
      into(layout.buildDirectory.dir("appResources/common"))
    }

// The Compose plugin registers this one lazily, so it cannot be looked up by name here.
tasks.matching { it.name == "prepareAppResources" }.configureEach { dependsOn(stageAppResources) }

val feedOrigin =
    (project.findProperty("openmmo.feedOrigin") as String?) ?: "https://127.0.0.1:20443"

val archiveOrigin =
    (project.findProperty("openmmo.archiveOrigin") as String?)
        ?: "https://github.com/openmmo-org/archive"

val archiveRawOrigin =
    (project.findProperty("openmmo.archiveRawOrigin") as String?)
        ?: "https://raw.githubusercontent.com/openmmo-org/archive/master"

// Loopback by default. A hostname cannot be padded, so a replacement must be exactly 23 wide.

val launcherPropertiesDir = layout.buildDirectory.dir("launcherProperties")

val writeLauncherProperties by
    tasks.registering(WriteProperties::class) {
      group = "openmmo"
      description = "Bakes the feed and archive origins into the launcher"
      destinationFile.set(launcherPropertiesDir.map { it.file("launcher.properties") })
      property("feed.origin", feedOrigin)
      property("archive.origin", archiveOrigin)
      property("archive.rawOrigin", archiveRawOrigin)
    }

sourceSets.main.get().resources.srcDir(launcherPropertiesDir)

tasks.named("processResources") { dependsOn(writeLauncherProperties) }

fun JavaExec.devFeed() {
  group = "application"
  mainClass.set("de.fiereu.openmmo.launcher.launch.DevFeedServerCli")
  classpath(sourceSets.main.get().runtimeClasspath)
  systemProperty("openmmo.manifests", layout.projectDirectory.dir("manifests").asFile.path)
  listOf(
          "openmmo.root",
          "openmmo.manifests",
          "openmmo.devFeedPort",
          "openmmo.revision",
          "openmmo.archiveOrigin",
          "openmmo.archiveRawOrigin")
      .forEach { name -> (project.findProperty(name) as String?)?.let { systemProperty(name, it) } }
  standardInput = System.`in`
}

tasks.register<JavaExec>("feedServer") {
  description = "Serves the development feed on loopback"
  devFeed()
}

tasks.register<JavaExec>("dev") {
  description = "Serves the development feed and starts the launcher against it"
  devFeed()
  systemProperty("openmmo.launchUi", "true")
}

tasks.register<JavaExec>("launcherUi") {
  group = "application"
  description = "Runs the OpenMMO launcher window"

  mainClass.set("de.fiereu.openmmo.launcher.ui.MainKt")
  classpath(sourceSets.main.get().runtimeClasspath)
  systemProperty("openmmo.manifests", layout.projectDirectory.dir("manifests").asFile.path)
  listOf("openmmo.root", "openmmo.manifests").forEach { name ->
    (project.findProperty(name) as String?)?.let { systemProperty(name, it) }
  }
  // Skiko loads its native renderer, which Java 25 warns about unless it is allowed up front.
  jvmArgs("--enable-native-access=ALL-UNNAMED")
  maxHeapSize = "1g"
}

tasks.register<JavaExec>("patchClient") {
  group = "application"
  description = "Applies a patch manifest to the managed install and builds the runtime tree"

  mainClass.set("de.fiereu.openmmo.launcher.patch.PatchCli")
  classpath(sourceSets.main.get().runtimeClasspath)
  listOf("openmmo.root", "openmmo.manifest").forEach { name ->
    (project.findProperty(name) as String?)?.let { systemProperty(name, it) }
  }
  maxHeapSize = "1g"
}

listOf("classes", "processResources").forEach { taskName ->
  tasks.named(taskName) { dependsOn("copyPublicKeys", "copyPrivateKeyFeed") }
}
