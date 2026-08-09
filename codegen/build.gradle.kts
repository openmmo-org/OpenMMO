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

// ROMs (gitignored) that dialog codegen reads offsets from
val romsDir = rootProject.layout.projectDirectory.dir("roms")

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
  // Item ids scripts hand to giveItem. Both GBA games number items the same, so one decomp is
  // enough, like moves and species. The client holds one item table per region, so the region
  // decides what a number means. Checked against a real client: region 0 reads 3, 4 and 13 as
  // Great Ball, Poke Ball and Potion, matching the decomp, while region 1 and region 5 read the
  // same numbers as unrelated items.
  register("item") {
    mainClass.set("de.fiereu.openmmo.codegen.item.Main")
    inputDirs.from(sourceDecompDir)
    extraArgs.set(listOf("kanto|${sourceDecompDir.asFile.absolutePath}"))
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
  // Dialog ids are ROM offsets, so the generator reads a ROM from the roms folder,
  // picking it by its GBA header game code. Each region is "package|gameCode|decompDir".
  register("dialog") {
    mainClass.set("de.fiereu.openmmo.codegen.dialog.Main")
    val fireredDir = rootProject.layout.projectDirectory.dir("decomp/pokefirered")
    inputDirs.from(sourceDecompDir, fireredDir, romsDir)
    extraArgs.set(
        listOf(
            romsDir.asFile.absolutePath,
            "hoenn|BPEE|${sourceDecompDir.asFile.absolutePath}",
            "kanto|BPRE|${fireredDir.asFile.absolutePath}",
        ))
  }
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
