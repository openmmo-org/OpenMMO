package de.fiereu.openmmo.codegen.story

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

/**
 * Emits one Kotlin object of flag key constants and one of var key constants per region. Each
 * constant value is the region namespaced story key ("hoenn/FLAG_ADVENTURE_STARTED") that scripts
 * hand to [de.fiereu.openmmo.server.game.script.ScriptContext]. The namespace keeps the two
 * regions' overlapping names from colliding in the store.
 */
class StoryConstantsRenderer(
    region: String,
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
) {
  private val namespace = region
  private val basePackage = "de.fiereu.openmmo.story.generated.$region"
  private val objectPrefix = region.replaceFirstChar { it.uppercase() }

  fun render(flags: List<String>, vars: List<String>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val packageRoot = File(outputDir, basePackage.replace('.', '/'))
    if (packageRoot.exists()) packageRoot.deleteRecursively()
    packageRoot.mkdirs()

    write(engine, packageRoot, "${objectPrefix}Flags", flags)
    write(engine, packageRoot, "${objectPrefix}Vars", vars)
  }

  private fun write(
      engine: TemplateEngine,
      packageRoot: File,
      objectName: String,
      names: List<String>,
  ) {
    FileOutput(File(packageRoot, "$objectName.kt").toPath()).use { out ->
      engine.render(
          "StoryConstants.jte",
          mapOf(
              "pkg" to basePackage,
              "objectName" to objectName,
              "namespace" to namespace,
              "names" to names,
          ),
          out,
      )
    }
  }
}
