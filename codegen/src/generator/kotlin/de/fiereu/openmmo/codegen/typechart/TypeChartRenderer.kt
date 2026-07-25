package de.fiereu.openmmo.codegen.typechart

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

class TypeChartRenderer(
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
) {

  fun render(matchups: List<ParsedMatchup>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val packageRoot = File(outputDir, BASE_PACKAGE.replace('.', '/'))
    if (packageRoot.exists()) packageRoot.deleteRecursively()

    val registry = File(packageRoot, "GeneratedTypeChart.kt")
    registry.parentFile.mkdirs()
    FileOutput(registry.toPath()).use { out ->
      engine.render("TypeChartRegistry.jte", mapOf("matchups" to matchups), out)
    }
  }

  companion object {
    private const val BASE_PACKAGE = "de.fiereu.openmmo.typechart.generated"
  }
}
