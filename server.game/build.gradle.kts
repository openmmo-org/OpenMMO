plugins {
  application
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.common.keys")
  alias(libs.plugins.ksp)
}

group = "de.fiereu.openmmo"

version = "0.1.0"

application { mainClass.set("de.fiereu.openmmo.server.game.MainKt") }

dependencies {
  api(project(":network"))
  api(project(":protocols.game"))
  api(project(":common"))
  api(project(":maps"))

  implementation(libs.dagger)
  ksp(libs.dagger.compiler)

  implementation(libs.typesafe.config)
  implementation(libs.kotlinx.coroutines)
  implementation(libs.kotlin.logging)
  implementation(libs.logback)

  testImplementation(libs.bundles.kotest)
  testImplementation(libs.kotlinx.coroutines.test)
}

listOf("classes", "processResources").forEach { taskName ->
  tasks.named(taskName) { dependsOn("copyPublicKeyGame", "copyPrivateKeyGame") }
}
