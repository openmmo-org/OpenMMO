package de.fiereu.openmmo.codegen.dialog

/**
 * One dialog line resolved from the decomp: its full label, packed textId, and readable preview.
 */
data class DialogLine(val label: String, val textId: Int, val preview: String)

/** A single enum entry inside a location's dialog enum. */
data class DialogEntry(val name: String, val textId: Int, val preview: String)
