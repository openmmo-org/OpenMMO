dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    mavenCentral()
    // Compose Multiplatform pulls its androidx artifacts from here and nowhere else.
    google { content { includeGroupByRegex("androidx.*") } }
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "openmmo"

include(":keys")
include(":launcher")
include(":common")
include(":bytecodec")
include(":common.test")
include(":network")
include(":protocols.login")
include(":protocols.game")
include(":server.login")
include(":server.game")
include(":codegen")
