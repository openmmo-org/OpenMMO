package de.fiereu.openmmo.codegen.trainer

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.FileOutput
import gg.jte.resolve.DirectoryCodeResolver
import java.io.File

/** Emits one object of trainer registrations per region, namespaced the same way story keys are. */
class TrainerRenderer(
    private val region: String,
    private val templatesDir: File,
    private val outputDir: File,
    private val classCacheDir: File,
    private val chunkSize: Int = 25,
) {

  fun render(trainers: List<ParsedTrainer>) {
    classCacheDir.mkdirs()
    val engine =
        TemplateEngine.create(
            DirectoryCodeResolver(templatesDir.toPath()),
            classCacheDir.toPath(),
            ContentType.Plain,
        )

    val objectName = "Generated${region.replaceFirstChar { it.uppercase() }}Trainers"
    val file = File(outputDir, "${BASE_PACKAGE.replace('.', '/')}/$objectName.kt")
    file.parentFile.mkdirs()
    FileOutput(file.toPath()).use { out ->
      engine.render(
          "TrainerRegistry.jte",
          mapOf(
              "pkg" to BASE_PACKAGE,
              "objectName" to objectName,
              "region" to region,
              "chunks" to trainers.chunked(chunkSize),
          ),
          out,
      )
    }
  }

  companion object {
    private const val BASE_PACKAGE = "de.fiereu.openmmo.trainer.generated"
  }
}
