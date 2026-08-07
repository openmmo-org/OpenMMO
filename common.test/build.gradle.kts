plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
}

dependencies {
  api(project(":bytecodec"))
  api(project(":common"))
  api(libs.bundles.kotest)
  // Only DockerAvailable needs this, so consumers that never touch it stay clear of testcontainers.
  compileOnly(libs.testcontainers.postgresql)
}
