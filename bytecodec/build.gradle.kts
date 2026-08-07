plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
}

dependencies {
  testImplementation(project(":common.test"))
  testImplementation(libs.bundles.kotest)
}
