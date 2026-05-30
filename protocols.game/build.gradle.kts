plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
}

dependencies {
  api(project(":network"))
  api(project(":common"))
  testImplementation(libs.bundles.kotest)
}
