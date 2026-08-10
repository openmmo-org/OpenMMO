@import java.util.List
@import de.fiereu.openmmo.codegen.item.ParsedItem
@import de.fiereu.openmmo.codegen.item.RenderUtil

@param List<ParsedItem> items
package de.fiereu.openmmo.items.generated

import de.fiereu.openmmo.items.ItemDef

/** Generated, do not edit by hand. The KDoc on an entry is the wire ids it answers to. */
object Items {
@for(ParsedItem item : items)
${RenderUtil.INSTANCE.declaration(item)}
@endfor

  private fun item(name: String, price: Int) = ItemDef(name, price)
}
