plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
}

dependencies {
  api(project(":bytecodec"))
  api(libs.netty)
  api(libs.kotlinx.coroutines)
  implementation(libs.kotlin.logging)
  testImplementation(libs.bundles.kotest)
  testImplementation(libs.kotlinx.coroutines.test)
  testRuntimeOnly(libs.logback)
}
