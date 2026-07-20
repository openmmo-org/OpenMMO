plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.convention.jte-codegen")
}

dependencies {
  api(project(":common"))
  api(project(":protocols.game"))
  api(libs.dagger)
  "generatorImplementation"(libs.jte)
  "generatorImplementation"(libs.kotlinx.serialization.json)
}

// Region name -> source decomp. Only non-NDS (GBA, 2D) decomps that share the pret
// map format are listed. The NDS decomps (pokeblack, pokeheartgold, pokeplatinum) use
// 3D maps and are not parsed here. Region ids live in the per-region constants.
val regionSources =
    mapOf(
        "hoenn" to "pokeemerald",
        "kanto" to "pokefirered",
    )

// Single source-of-truth decomp for the non-region-specific data (moves, species). The two
// GBA decomps agree on the national dex; where they differ (held items, safari flee rate) this
// is the canonical pick, same as byRegion is for maps.
val sourceDecompDir = rootProject.layout.projectDirectory.dir("decomp/pokeemerald")

jteCodegen {
  register("maps") {
    mainClass.set("de.fiereu.openmmo.codegen.maps.Main")
    inputDirs.from(
        regionSources.values.map { rootProject.layout.projectDirectory.dir("decomp/$it") })
    extraArgs.set(
        regionSources.map { (region, decomp) ->
          "$region|${rootProject.layout.projectDirectory.dir("decomp/$decomp").asFile.absolutePath}"
        })
  }
  register("moves") {
    mainClass.set("de.fiereu.openmmo.codegen.move.Main")
    templatesSubdir.set("move")
    inputDirs.from(sourceDecompDir)
    extraArgs.set(listOf(sourceDecompDir.asFile.absolutePath))
  }
  register("pokemon") {
    mainClass.set("de.fiereu.openmmo.codegen.pokemon.Main")
    inputDirs.from(sourceDecompDir)
    extraArgs.set(listOf(sourceDecompDir.asFile.absolutePath))
  }
}
