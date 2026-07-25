package de.fiereu.openmmo.codegen.dialog

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

/** Emits one Kotlin file per location, each holding an enum of that location's dialog lines. */
class DialogRenderer(
    region: String,
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
) {
  private val basePackage = "de.fiereu.openmmo.dialog.generated.$region"

  fun render(lines: List<DialogLine>) {
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

    lines
        .groupBy { RenderUtil.location(it.label) }
        .forEach { (location, group) ->
          val className = RenderUtil.className(location)
          val entries =
              group
                  .map { DialogEntry(RenderUtil.entryName(it.label), it.textId, it.preview) }
                  .distinctBy { it.name }
                  .sortedBy { it.textId }
          FileOutput(File(packageRoot, "${className.trim('`')}.kt").toPath()).use { out ->
            engine.render(
                "LocationDialog.jte",
                mapOf("pkg" to basePackage, "enumName" to className, "entries" to entries),
                out)
          }
        }
  }
}
