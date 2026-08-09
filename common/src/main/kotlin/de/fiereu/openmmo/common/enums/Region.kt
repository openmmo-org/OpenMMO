package de.fiereu.openmmo.common.enums

enum class Region(val wireValue: Byte) {
  KANTO(0),
  HOENN(1);

  val displayName: String = name.lowercase().replaceFirstChar { it.uppercase() }

  companion object {
    fun byId(id: Int): Region? = entries.find { it.wireValue.toInt() == id }

    fun byWireValue(wireValue: Byte): Region? = entries.find { it.wireValue == wireValue }
  }
}
