@import java.util.List
@import de.fiereu.openmmo.codegen.item.ParsedItem

@param List<ParsedItem> items
package de.fiereu.openmmo.items.generated

/**
 * Item ids the client uses, generated from the GBA decomp item header. The client keys items by
 * regionId * 1000 + itemId and both GBA games share one item region, so this object covers Hoenn
 * and Kanto alike. Generated, do not edit by hand.
 */
object Items {
@for(ParsedItem item : items)
  const val ${item.getName()} = ${item.getId()}
@endfor
}
