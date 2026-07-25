package de.fiereu.openmmo.codegen.script

import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * The interactable script labels each map references, grouped by the owning map (the map folder
 * name). Only npc (object event) and sign (bg event) scripts are collected, since those are the
 * entry points a player can trigger.
 */
class MapEventIndex private constructor(val byMap: Map<String, List<String>>) {

  companion object {
    private val json = Json { ignoreUnknownKeys = true }

    fun build(decompDir: File): MapEventIndex {
      val mapsDir = File(decompDir, "data/maps")
      val out = LinkedHashMap<String, List<String>>()
      if (mapsDir.isDirectory) {
        mapsDir
            .listFiles { f -> f.isDirectory }
            ?.sortedBy { it.name }
            ?.forEach { dir ->
              val mapJson = File(dir, "map.json")
              if (mapJson.isFile) {
                val labels = parse(mapJson)
                if (labels.isNotEmpty()) out[dir.name] = labels
              }
            }
      }
      return MapEventIndex(out)
    }

    private fun parse(mapJson: File): List<String> {
      val root = json.parseToJsonElement(mapJson.readText()).jsonObject
      val labels = LinkedHashSet<String>()
      for (key in listOf("object_events", "bg_events")) {
        val arr = root[key]?.jsonArray ?: continue
        for (event in arr) {
          val script = event.jsonObject["script"]?.jsonPrimitive?.content
          if (script != null && isLabel(script)) labels.add(script)
        }
      }
      return labels.toList()
    }

    private fun isLabel(value: String): Boolean =
        value.isNotBlank() && value != "0x0" && value != "NULL" && value.first().isLetter()
  }
}
