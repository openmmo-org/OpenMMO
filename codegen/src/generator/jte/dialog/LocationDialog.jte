@import java.util.List
@import de.fiereu.openmmo.codegen.dialog.DialogEntry
@import de.fiereu.openmmo.codegen.dialog.RenderUtil

@param String pkg
@param String enumName
@param List<DialogEntry> entries
package ${pkg}

/**
 * Overworld dialog lines for this location, generated from the decomp text. Each [textId] is
 * 0x10000000 or the string's retail ROM offset, ready to send in a DialogAction packet.
 */
enum class ${enumName}(val textId: Int) {
@for(DialogEntry e : entries)
  ${RenderUtil.INSTANCE.entry(e)}
@endfor
}
