package de.fiereu.openmmo.common.dialog

/**
 * A dialog line the client can show, named by its [textId]. The generated dialog enums implement
 * it.
 */
interface DialogLine {
  val textId: Int
}
