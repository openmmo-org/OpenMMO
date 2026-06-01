buildscript {
  dependencies {
    classpath("org.flywaydb:flyway-database-postgresql:11.10.4")
    classpath("org.postgresql:postgresql:42.7.5")
  }
}

plugins {
  application
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.common.keys")
  alias(libs.plugins.ksp)
  alias(libs.plugins.flyway)
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

  implementation(libs.flyway.core)
  implementation(libs.flyway.postgresql)
  implementation(libs.postgresql)
  implementation(libs.hikari)

  testImplementation(libs.bundles.kotest)
  testImplementation(libs.kotlinx.coroutines.test)
}

val gameDbUrl = "jdbc:postgresql://localhost:${System.getProperty("GAME_DB_PORT") ?: "20021"}/${System.getProperty("GAME_DB_NAME") ?: "openmmo_game_db"}"
flyway {
  driver = "org.postgresql.Driver"
  url = gameDbUrl
  user = System.getProperty("GAME_DB_USER") ?: "openmmo_game_user"
  password = System.getProperty("GAME_DB_PASSWORD") ?: "changeMe!"
  locations = arrayOf("classpath:db/migration")
  configurations = arrayOf("runtimeClasspath")
}

tasks.named("flywayMigrate") { dependsOn("classes") }

listOf("classes", "processResources").forEach { taskName ->
  tasks.named(taskName) { dependsOn("copyPublicKeyGame", "copyPrivateKeyGame") }
}
