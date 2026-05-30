package de.fiereu.openmmo.maps.generator

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

class MapsRenderer(
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
    private val chunkSize: Int = 60,
) {

  fun render(maps: List<ParsedMap>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )
    val chunks = maps.chunked(chunkSize)
    val target = File(outputDir, "de/fiereu/openmmo/maps/MapDefaults.kt")
    target.parentFile.mkdirs()
    FileOutput(target.toPath()).use { out ->
      engine.render("MapsRegistry.jte", mapOf("chunks" to chunks), out)
    }
  }
}
