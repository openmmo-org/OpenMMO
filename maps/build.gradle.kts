plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
}

dependencies {
  api(project(":common"))
  api(project(":protocols.game"))
  api(libs.dagger)
}

val generator: SourceSet by sourceSets.creating

dependencies {
  "generatorImplementation"(libs.jte)
  "generatorImplementation"(libs.kotlinx.serialization.json)
}

val pokeemeraldDir = rootProject.layout.projectDirectory.dir("decomp/pokeemerald").asFile
val generatedSrc = layout.buildDirectory.dir("generated/source/maps/main/kotlin").get().asFile
val templatesDir = layout.projectDirectory.dir("src/generator/jte").asFile
val jteCacheDir = layout.buildDirectory.dir("jte-classes").get().asFile

val generateMaps by
    tasks.registering(JavaExec::class) {
      group = "openmmo"
      description = "Render MapDefaults.kt from pokeemerald data via JTE"
      dependsOn(tasks.named("${generator.name}Classes"))
      inputs.dir(pokeemeraldDir.toPath())
      inputs.dir(templatesDir.toPath())
      outputs.dir(generatedSrc.toPath())
      classpath = generator.runtimeClasspath
      mainClass.set("de.fiereu.openmmo.maps.generator.Main")
      args = listOf(pokeemeraldDir.absolutePath, generatedSrc.absolutePath, templatesDir.absolutePath, jteCacheDir.absolutePath)
    }

sourceSets.main { kotlin.srcDir(generateMaps) }
