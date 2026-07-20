package de.fiereu.openmmo.common.enums

enum class Region(val id: Int) {
  KANTO(0),
  HOENN(1);

  companion object {
    fun byId(id: Int): Region? = entries.find { it.id == id }
  }
}
