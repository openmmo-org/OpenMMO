package de.fiereu.openmmo.codegen.item

/** One item constant: the decomp name without its ITEM_ prefix and the id the client uses. */
data class ParsedItem(val name: String, val id: Int)
