package de.fiereu.openmmo.codegen.pokemon

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

class PokemonRenderer(
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
    private val registryChunkSize: Int = 50,
) {

  fun render(species: List<ParsedSpecies>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val packageRoot = File(outputDir, BASE_PACKAGE.replace('.', '/'))
    if (packageRoot.exists()) packageRoot.deleteRecursively()

    val chunks = species.sortedBy { it.id }.chunked(registryChunkSize)
    val registry = File(outputDir, "${BASE_PACKAGE.replace('.', '/')}/GeneratedSpecies.kt")
    registry.parentFile.mkdirs()
    FileOutput(registry.toPath()).use { out ->
      engine.render("SpeciesRegistry.jte", mapOf("chunks" to chunks), out)
    }
  }

  companion object {
    private const val BASE_PACKAGE = "de.fiereu.openmmo.pokemon.generated"
  }
}
