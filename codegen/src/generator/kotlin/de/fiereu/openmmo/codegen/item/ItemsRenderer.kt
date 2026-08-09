package de.fiereu.openmmo.codegen.item

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

class ItemsRenderer(
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
) {

  fun render(items: List<ParsedItem>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val packageRoot = File(outputDir, BASE_PACKAGE.replace('.', '/'))
    if (packageRoot.exists()) packageRoot.deleteRecursively()
    packageRoot.mkdirs()

    FileOutput(File(packageRoot, "Items.kt").toPath()).use { out ->
      engine.render("Items.jte", mapOf("items" to items), out)
    }
  }

  private companion object {
    const val BASE_PACKAGE = "de.fiereu.openmmo.items.generated"
  }
}
