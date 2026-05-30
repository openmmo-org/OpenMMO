import javax.inject.Inject
import org.gradle.process.ExecOperations

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

abstract class CloneOrUpdateGitRepoTask : DefaultTask() {

  @get:Input abstract val remote: Property<String>

  @get:Input abstract val ref: Property<String>

  @get:OutputDirectory abstract val target: DirectoryProperty

  @get:Inject abstract val execOps: ExecOperations

  @TaskAction
  fun run() {
    val dir = target.get().asFile
    val refValue = ref.get()
    if (File(dir, ".git").exists()) {
      execOps.exec {
        workingDir = dir
        commandLine("git", "fetch", "--depth", "1", "origin", refValue)
      }
      execOps.exec {
        workingDir = dir
        commandLine("git", "checkout", "--detach", "FETCH_HEAD")
      }
    } else {
      if (dir.exists()) dir.deleteRecursively()
      dir.parentFile.mkdirs()
      execOps.exec {
        commandLine(
            "git",
            "clone",
            "--depth",
            "1",
            "--branch",
            refValue,
            remote.get(),
            dir.absolutePath,
        )
      }
    }
  }
}

val pokeemeraldRef = providers.gradleProperty("pokemmo.pokeemerald.ref").orElse("master")

val clonePokeemerald by
    tasks.registering(CloneOrUpdateGitRepoTask::class) {
      group = "openmmo"
      description = "Clone or fast-forward pret/pokeemerald into build/pokeemerald"
      remote.set("https://github.com/pret/pokeemerald.git")
      ref.set(pokeemeraldRef)
      target.set(layout.buildDirectory.dir("pokeemerald"))
    }

val pokeemeraldDir = layout.buildDirectory.dir("pokeemerald")
val generatedSrc = layout.buildDirectory.dir("generated/source/maps/main/kotlin")
val templatesDir = layout.projectDirectory.dir("src/generator/jte")
val jteCacheDir = layout.buildDirectory.dir("jte-classes")

val pokeemeraldArg = pokeemeraldDir.get().asFile.absolutePath
val generatedArg = generatedSrc.get().asFile.absolutePath
val templatesArg = templatesDir.asFile.absolutePath
val jteCacheArg = jteCacheDir.get().asFile.absolutePath

val generateMaps by
    tasks.registering(JavaExec::class) {
      group = "openmmo"
      description = "Render MapDefaults.kt from pokeemerald data via JTE"
      dependsOn(clonePokeemerald, tasks.named("${generator.name}Classes"))
      inputs.dir(pokeemeraldDir)
      inputs.dir(templatesDir)
      outputs.dir(generatedSrc)
      classpath = generator.runtimeClasspath
      mainClass.set("de.fiereu.openmmo.maps.generator.Main")
      args = listOf(pokeemeraldArg, generatedArg, templatesArg, jteCacheArg)
    }

sourceSets.main { kotlin.srcDir(generateMaps) }
