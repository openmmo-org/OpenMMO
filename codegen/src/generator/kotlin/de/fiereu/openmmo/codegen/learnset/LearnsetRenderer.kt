package de.fiereu.openmmo.codegen.learnset

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

class LearnsetRenderer(
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
    private val registryChunkSize: Int = 50,
) {

  fun render(learnsets: List<ParsedLearnset>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val packageRoot = File(outputDir, BASE_PACKAGE.replace('.', '/'))
    if (packageRoot.exists()) packageRoot.deleteRecursively()

    val chunks = learnsets.sortedBy { it.dexId }.chunked(registryChunkSize)
    val registry = File(outputDir, "${BASE_PACKAGE.replace('.', '/')}/GeneratedLearnsets.kt")
    registry.parentFile.mkdirs()
    FileOutput(registry.toPath()).use { out ->
      engine.render("LearnsetRegistry.jte", mapOf("chunks" to chunks), out)
    }
  }

  companion object {
    private const val BASE_PACKAGE = "de.fiereu.openmmo.pokemon.generated"
  }
}
