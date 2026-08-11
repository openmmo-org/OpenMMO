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
  "generatorImplementation"(project(":common"))
  "generatorImplementation"(libs.jte)
  "generatorImplementation"(libs.kotlinx.serialization.json)
  testImplementation(sourceSets["generator"].output)
  testImplementation(libs.kotlinx.serialization.json)
  testImplementation(libs.bundles.kotest)
}

// GBA regions sharing the pret map format.
val regionSources =
    mapOf(
        "hoenn" to "pokeemerald",
        "kanto" to "pokefirered",
    )

// Single source-of-truth decomp for the non-region-specific data (moves, species). The two
// GBA decomps agree on the national dex; where they differ (held items, safari flee rate) this
// is the canonical pick, same as byRegion is for maps.
val sourceDecompDir = rootProject.layout.projectDirectory.dir("decomp/pokeemerald")

// The Gen 5 table the live client speaks, not the GBA decomps, which number the same items
// differently. Committed rather than a submodule because openmmo-org/pokeblack is private, taken
// from its src/data/items.json and container 54 of its src/data/text1.json.
val itemDataDir = rootProject.layout.projectDirectory.dir("decomp/pokeblack")

// Gitignored, and only the manual refresh tasks read them.
val romsDir = rootProject.layout.projectDirectory.dir("roms")

// Committed, because a ROM is the only place these ids exist and no runner has one.
val dialogDataDir = layout.projectDirectory.dir("dialog")

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
  register("item") {
    mainClass.set("de.fiereu.openmmo.codegen.item.Main")
    inputDirs.from(itemDataDir)
    extraArgs.set(listOf(itemDataDir.asFile.absolutePath))
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
  register("learnset") {
    mainClass.set("de.fiereu.openmmo.codegen.learnset.Main")
    inputDirs.from(sourceDecompDir)
    extraArgs.set(listOf(sourceDecompDir.asFile.absolutePath))
  }
  // Trainers differ per game, so this is by region like maps rather than from the canonical decomp.
  register("trainer") {
    mainClass.set("de.fiereu.openmmo.codegen.trainer.Main")
    inputDirs.from(
        regionSources.values.map { rootProject.layout.projectDirectory.dir("decomp/$it") })
    extraArgs.set(
        regionSources.map { (region, decomp) ->
          "$region|${rootProject.layout.projectDirectory.dir("decomp/$decomp").asFile.absolutePath}"
        })
  }
  // Per region flag and var key constants for scripts. Names come from each decomp, so this is by
  // region like maps. The generic story store in server.game does not depend on these, they are the
  // GBA adapter that gives ported scripts readable keys.
  register("story") {
    mainClass.set("de.fiereu.openmmo.codegen.story.Main")
    inputDirs.from(
        regionSources.values.map { rootProject.layout.projectDirectory.dir("decomp/$it") })
    extraArgs.set(
        regionSources.map { (region, decomp) ->
          "$region|${rootProject.layout.projectDirectory.dir("decomp/$decomp").asFile.absolutePath}"
        })
  }
  register("typechart") {
    mainClass.set("de.fiereu.openmmo.codegen.typechart.Main")
    inputDirs.from(sourceDecompDir)
    extraArgs.set(listOf(sourceDecompDir.asFile.absolutePath))
  }
  register("dialog") {
    mainClass.set("de.fiereu.openmmo.codegen.dialog.Main")
    inputDirs.from(dialogDataDir)
    extraArgs.set(listOf(dialogDataDir.asFile.absolutePath) + regionSources.keys)
  }
}

tasks.register<JavaExec>("refreshDialogTable") {
  group = "codegen"
  description =
      "Re-resolve codegen/dialog from the ROMs in roms/ (manual, run after a decomp bump, commit the result)"
  val fireredDir = rootProject.layout.projectDirectory.dir("decomp/pokefirered")
  classpath = sourceSets["generator"].runtimeClasspath
  mainClass.set("de.fiereu.openmmo.codegen.dialog.RefreshMain")
  args(
      romsDir.asFile.absolutePath,
      dialogDataDir.asFile.absolutePath,
      "hoenn|BPEE|${sourceDecompDir.asFile.absolutePath}",
      "kanto|BPRE|${fireredDir.asFile.absolutePath}",
  )
}

// One shot bootstrap of the overworld script stubs into server.game. Run by hand with
// `gradlew :codegen:generateScriptStubs`. Deliberately not wired into the build, the emitted files
// are committed source so hand written ports are never overwritten on a normal build.
tasks.register<JavaExec>("generateScriptStubs") {
  group = "codegen"
  description = "Bootstrap overworld script stubs into server.game (manual, not part of the build)"
  val fireredDir = rootProject.layout.projectDirectory.dir("decomp/pokefirered")
  val serverGameSrc = rootProject.layout.projectDirectory.dir("server.game/src/main/kotlin")
  classpath = sourceSets["generator"].runtimeClasspath
  mainClass.set("de.fiereu.openmmo.codegen.script.Main")
  args(
      serverGameSrc.asFile.absolutePath,
      romsDir.asFile.absolutePath,
      "hoenn|BPEE|${sourceDecompDir.asFile.absolutePath}",
      "kanto|BPRE|${fireredDir.asFile.absolutePath}",
  )
}
