package de.fiereu.openmmo.codegen.dialog

import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object DialogTable {

  private val json = Json { prettyPrint = true }

  fun file(dataDir: File, region: String): File = File(dataDir, "$region.json")

  fun read(file: File): List<DialogLine>? {
    if (!file.isFile) return null
    val root = json.parseToJsonElement(file.readText()).jsonObject
    return root.getValue("lines").jsonArray.map { element ->
      val line = element.jsonObject
      DialogLine(
          label = line.getValue("label").jsonPrimitive.content,
          textId = line.getValue("id").jsonPrimitive.int,
          text = line.getValue("text").jsonPrimitive.content,
      )
    }
  }

  fun write(file: File, region: String, gameCode: String, lines: List<DialogLine>) {
    val body =
        JsonObject(
            mapOf(
                "region" to JsonPrimitive(region),
                "gameCode" to JsonPrimitive(gameCode),
                "lines" to
                    JsonArray(
                        lines.map {
                          JsonObject(
                              mapOf(
                                  "label" to JsonPrimitive(it.label),
                                  "id" to JsonPrimitive(it.textId),
                                  "text" to JsonPrimitive(it.text),
                              ))
                        }),
            ))
    file.parentFile.mkdirs()
    file.writeText(json.encodeToString(JsonObject.serializer(), body) + "\n")
  }
}
