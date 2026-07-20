plugins {
  `kotlin-dsl`
}

kotlin {
  jvmToolchain(25)
}

gradlePlugin {
  plugins {
    create("jteCodegen") {
      id = "buildsrc.convention.jte-codegen"
      implementationClass = "buildsrc.convention.JteCodegenPlugin"
    }
  }
}

dependencies {
  implementation(libs.kotlinGradlePlugin)
  implementation(libs.spotlessGradlePlugin)
  implementation(libs.sonarlintGradlePlugin)
  implementation(libs.junit5)
}
