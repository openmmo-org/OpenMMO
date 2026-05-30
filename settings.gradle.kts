dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    mavenCentral()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "openmmo"

include(":keys")
include(":patcher")
include(":common")
include(":bytecodec")
include(":network")
include(":protocols.login")
include(":protocols.game")
include(":server.login")
include(":server.game")
include(":maps")
