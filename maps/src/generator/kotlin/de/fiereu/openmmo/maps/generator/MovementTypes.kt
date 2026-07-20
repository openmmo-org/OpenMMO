package de.fiereu.openmmo.maps.generator

import java.io.File

class MovementTypes(
    private val regionName: String,
    private val ids: Map<String, Int>,
    private val shared: Set<String>,
) {

  fun ref(name: String): String {
    if (name !in ids) return "MovementType.NONE"
    val stripped = name.removePrefix("MOVEMENT_TYPE_")
    return if (name in shared) "MovementType.$stripped"
    else "MovementType.${regionName.uppercase()}_$stripped"
  }

  fun facingRef(name: String): String = movementToFacingRef(ids[name] ?: 0)

  companion object {
    private const val HEADER = "include/constants/event_object_movement.h"

    fun build(regionDirs: Map<String, File>): Map<String, MovementTypes> {
      val idsByRegion = regionDirs.mapValues { readMovementDefines(File(it.value, HEADER)) }
      val shared =
          idsByRegion.values
              .flatMap { it.keys }
              .toSet()
              .filter { name ->
                val ids = idsByRegion.values.mapNotNull { it[name] }
                ids.size == idsByRegion.size && ids.toSet().size == 1
              }
              .toSet()
      return idsByRegion.mapValues { (region, ids) -> MovementTypes(region, ids, shared) }
    }

    private fun readMovementDefines(file: File): Map<String, Int> {
      if (!file.exists()) return emptyMap()
      val pattern = Regex("""^#define\s+(MOVEMENT_TYPE_\w+)\s+(0x[0-9A-Fa-f]+|\d+)""")
      return file
          .readLines()
          .mapNotNull { pattern.find(it.trim()) }
          .associate {
            val value = it.groupValues[2]
            it.groupValues[1] to
                if (value.startsWith("0x")) value.substring(2).toInt(16) else value.toInt()
          }
    }
  }
}
