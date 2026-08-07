@import java.util.List
@import de.fiereu.openmmo.codegen.dialog.DialogEntry
@import de.fiereu.openmmo.codegen.dialog.RenderUtil

@param String pkg
@param String enumName
@param List<DialogEntry> entries
package ${pkg}

import de.fiereu.openmmo.common.dialog.DialogLine

/**
 * Overworld dialog lines for this location, generated from the decomp text. Each [textId] is the
 * region id shifted into the top nibble or the string's retail ROM offset, ready to send in a
 * DialogAction packet.
 */
enum class ${enumName}(override val textId: Int) : DialogLine {
@for(DialogEntry e : entries)
  ${RenderUtil.INSTANCE.entry(e)}
@endfor
}
