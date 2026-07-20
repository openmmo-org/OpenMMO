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

val generatedSrc = layout.buildDirectory.dir("generated/source/maps/kotlin")
val templatesDir = layout.projectDirectory.dir("src/generator/jte")
val jteCacheDir = layout.buildDirectory.dir("jte-classes")

// Region name -> source decomp. Only non-NDS (GBA, 2D) decomps that share the pret
// map format are listed. The NDS decomps (pokeblack, pokeheartgold, pokeplatinum) use
// 3D maps and are not parsed here. Region ids live in the per-region constants.
val regionSources =
    mapOf(
        "hoenn" to "pokeemerald",
        "kanto" to "pokefirered",
    )

val generatedArg = generatedSrc.get().asFile.absolutePath
val templatesArg = templatesDir.asFile.absolutePath
val jteCacheArg = jteCacheDir.get().asFile.absolutePath

val decompDirs = regionSources.values.map { rootProject.layout.projectDirectory.dir("decomp/$it") }
val regionArgs =
    regionSources.map { (region, decomp) ->
      "$region|${rootProject.layout.projectDirectory.dir("decomp/$decomp").asFile.absolutePath}"
    }

val generateMaps by
    tasks.registering(JavaExec::class) {
      group = "openmmo"
      description = "Render the per-map MapDef sources from the GBA decomp data via JTE"
      dependsOn(tasks.named("${generator.name}Classes"))
      decompDirs.forEach { inputs.dir(it) }
      inputs.dir(templatesDir)
      outputs.dir(generatedSrc)
      classpath = generator.runtimeClasspath
      mainClass.set("de.fiereu.openmmo.maps.generator.Main")
      args = listOf(generatedArg, templatesArg, jteCacheArg) + regionArgs
      doFirst {
        decompDirs.forEach { dir ->
          if (!dir.file("data/maps/map_groups.json").asFile.exists()) {
            throw GradleException(
                "Decomp submodule '${dir.asFile.name}' is not initialized. " +
                    "Run: git submodule update --init --recursive")
          }
        }
      }
    }

// Generated maps are not committed; they are regenerated on every build.
sourceSets.main { kotlin.srcDir(generateMaps) }
