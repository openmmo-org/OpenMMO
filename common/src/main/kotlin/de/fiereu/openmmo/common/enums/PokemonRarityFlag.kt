package de.fiereu.openmmo.common.enums

/** A rarity trait packed into a monster's flag short, one trait per bit. */
enum class PokemonRarityFlag(val bit: Int) {
  SHINY(0),
  HIDDEN_ABILITY(1),
  ALPHA(2),
  SECRET_SHINY(3),
  FATEFUL_ENCOUNTER(4),
  RAID_ENCOUNTER(5);

  val mask: Int = 1 shl bit

  fun isSet(bits: Int): Boolean = (bits and mask) != 0
}
