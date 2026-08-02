package de.fiereu.openmmo.common.enums

enum class CharacterGender(val wireValue: Byte) {
  MALE(0),
  FEMALE(1);

  companion object {
    fun byWireValue(wireValue: Byte): CharacterGender? = entries.find { it.wireValue == wireValue }
  }
}
