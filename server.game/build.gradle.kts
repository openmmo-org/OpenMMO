plugins {
  application
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.common.keys")
  id("buildsrc.convention.jooq-db")
  alias(libs.plugins.ksp)
}

group = "de.fiereu.openmmo"

version = "0.1.0"

application { mainClass.set("de.fiereu.openmmo.server.game.MainKt") }

jooqDb { packageName = "de.fiereu.openmmo.db.game" }

dependencies {
  api(project(":network"))
  api(project(":protocols.game"))
  api(project(":common"))
  api(project(":codegen"))

  implementation(libs.dagger)
  ksp(libs.dagger.compiler)

  implementation(libs.typesafe.config)
  implementation(libs.kotlinx.coroutines)
  implementation(libs.kotlin.logging)
  implementation(libs.logback)

  testImplementation(libs.bundles.kotest)
  testImplementation(libs.kotlinx.coroutines.test)
}

tasks.named<JavaExec>("run") {
  listOf(
          "OPENMMO_SESSION_SECRET",
          "GAME_DB_HOST",
          "GAME_DB_PORT",
          "GAME_DB_NAME",
          "GAME_DB_USER",
          "GAME_DB_PASSWORD",
          "GAME_DB_SEED_DEV")
      .forEach { key -> env.fetchOrNull(key)?.let { environment(key, it) } }
}

listOf("classes", "processResources").forEach { taskName ->
  tasks.named(taskName) { dependsOn("copyPublicKeyGame", "copyPrivateKeyGame") }
}
