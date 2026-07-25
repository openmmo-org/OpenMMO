package de.fiereu.openmmo.codegen.script

/** How a ported script shows its line: a sign box, a spoken box, or not ported yet. */
enum class ScriptKind {
  SIGN,
  SAY,
  TODO,
}

/** A reference to a generated dialog enum entry, for example `LittlerootTown.TownSign`. */
data class DialogRef(val className: String, val entryName: String) {
  val reference: String
    get() = "$className.$entryName"
}

/**
 * One generated script object. [label] is the decomp script label and also the Kotlin object name.
 * SIGN and SAY carry a [dialogRef], TODO carries the [decompLines] so the port can be filled in.
 */
data class ScriptStub(
    val label: String,
    val kind: ScriptKind,
    val dialogRef: DialogRef?,
    val decompLines: List<String>,
)
