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
    private val registryChunkSize: Int = 100,
) {

  fun render(maps: List<ParsedMap>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val packageRoot = File(outputDir, BASE_PACKAGE.replace('.', '/'))
    if (packageRoot.exists()) packageRoot.deleteRecursively()

    val references = mutableListOf<String>()
    for (map in maps) {
      val packageName = packageFor(map)
      val objectName = identifier(map.sourceName)
      references += "$packageName.$objectName"

      val target = File(outputDir, "${packageName.replace('.', '/')}/$objectName.kt")
      target.parentFile.mkdirs()
      FileOutput(target.toPath()).use { out ->
        engine.render(
            "MapDef.jte",
            mapOf("packageName" to packageName, "objectName" to objectName, "map" to map),
            out,
        )
      }
    }

    val chunks = references.sorted().chunked(registryChunkSize)
    val registry = File(outputDir, "${BASE_PACKAGE.replace('.', '/')}/GeneratedMaps.kt")
    registry.parentFile.mkdirs()
    FileOutput(registry.toPath()).use { out ->
      engine.render("MapsRegistry.jte", mapOf("chunks" to chunks), out)
    }
  }

  private fun packageFor(map: ParsedMap): String =
      "$BASE_PACKAGE.${identifier(map.regionName)}.${groupSegment(map.groupName)}"

  private fun groupSegment(groupName: String): String {
    val stripped = groupName.removePrefix("gMapGroup_")
    val sanitized = stripped.filter { it.isLetterOrDigit() }.lowercase()
    return sanitized.ifEmpty { "misc" }
  }

  private fun identifier(name: String): String {
    val sanitized = name.map { if (it.isLetterOrDigit() || it == '_') it else '_' }.joinToString("")
    return if (sanitized.firstOrNull()?.isDigit() == true) "_$sanitized" else sanitized
  }

  companion object {
    private const val BASE_PACKAGE = "de.fiereu.openmmo.maps.generated"
  }
}
