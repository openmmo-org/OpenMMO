plugins {
  `kotlin-dsl`
}

kotlin {
  jvmToolchain(25)
}

dependencies {
  implementation(libs.kotlinGradlePlugin)
  implementation(libs.spotlessGradlePlugin)
  implementation(libs.junit5)
}
