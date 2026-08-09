@import java.util.List
@import de.fiereu.openmmo.codegen.item.ParsedItem

@param List<ParsedItem> items
package de.fiereu.openmmo.items.generated

/**
 * Item ids the client uses, covering Hoenn and Kanto alike. Generated from the GBA decomp item
 * header, do not edit by hand.
 */
object Items {
@for(ParsedItem item : items)
  const val ${item.getName()} = ${item.getId()}
@endfor
}
