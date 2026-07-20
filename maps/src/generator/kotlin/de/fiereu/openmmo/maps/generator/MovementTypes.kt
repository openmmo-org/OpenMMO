package de.fiereu.openmmo.maps.generator

import java.io.File

class MovementTypes(private val ids: Map<String, Int>) {

  fun ref(name: String): String =
      if (name in ids) "MovementType.${name.removePrefix("MOVEMENT_TYPE_")}"
      else "MovementType.NONE"

  fun facingRef(name: String): String = movementToFacingRef(ids[name] ?: 0)

  companion object {
    private const val HEADER = "include/constants/event_object_movement.h"

    fun read(dir: File): MovementTypes = MovementTypes(readMovementDefines(File(dir, HEADER)))

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
