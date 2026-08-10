package de.fiereu.openmmo.items

/** Not a data class on purpose. The catalogue instance is the identity, so equality is by it. */
class ItemDef(val name: String, val price: Int) {
  override fun toString(): String = name
}
