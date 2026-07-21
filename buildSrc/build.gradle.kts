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
    create("jooqDb") {
      id = "buildsrc.convention.jooq-db"
      implementationClass = "buildsrc.convention.JooqDbPlugin"
    }
  }
}

dependencies {
  implementation(libs.kotlinGradlePlugin)
  implementation(libs.spotlessGradlePlugin)
  implementation(libs.sonarlintGradlePlugin)
  implementation(libs.jooqCodegenGradlePlugin)
  implementation(libs.jooq.meta)
  implementation(libs.junit5)
}
