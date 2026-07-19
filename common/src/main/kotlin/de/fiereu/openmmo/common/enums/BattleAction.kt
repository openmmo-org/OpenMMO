package de.fiereu.openmmo.common.enums

/** A player's battle-turn choice. */
enum class BattleAction(val id: Byte) {
  MOVE(0),
  ITEM(1),
  SWITCH(2),
  RUN(3);

  companion object {
    fun fromId(id: Byte): BattleAction? = entries.firstOrNull { it.id == id }
  }
}
