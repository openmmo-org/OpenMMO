package de.fiereu.openmmo.codegen.move

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

class MovesRenderer(
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
    private val registryChunkSize: Int = 50,
) {

  fun render(moves: List<ParsedMove>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val packageRoot = File(outputDir, BASE_PACKAGE.replace('.', '/'))
    if (packageRoot.exists()) packageRoot.deleteRecursively()

    val chunks = moves.sortedBy { it.id }.chunked(registryChunkSize)
    val registry = File(outputDir, "${BASE_PACKAGE.replace('.', '/')}/GeneratedMoves.kt")
    registry.parentFile.mkdirs()
    FileOutput(registry.toPath()).use { out ->
      engine.render("MovesRegistry.jte", mapOf("chunks" to chunks), out)
    }
  }

  companion object {
    private const val BASE_PACKAGE = "de.fiereu.openmmo.moves.generated"
  }
}
