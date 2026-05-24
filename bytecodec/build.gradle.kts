plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
}

dependencies { testImplementation(libs.bundles.kotest) }
