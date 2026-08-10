package de.fiereu.openmmo.codegen.dialog

data class DialogLine(val label: String, val textId: Int, val text: String)

/** A single enum entry inside a location's dialog enum. */
data class DialogEntry(val name: String, val textId: Int, val preview: String)
