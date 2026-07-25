package de.fiereu.openmmo.codegen.script

import de.fiereu.openmmo.codegen.dialog.Charmap
import de.fiereu.openmmo.codegen.dialog.RenderUtil
import de.fiereu.openmmo.codegen.dialog.RomIndex
import de.fiereu.openmmo.codegen.dialog.TextParser
import java.io.File

/**
 * Maps a decomp text label to the generated dialog enum entry it became, mirroring the dialog
 * generator so a script can reference the same entry. Only labels that resolved to a real ROM
 * offset are present, and only the first label to claim an entry name in a location, since the
 * dialog enum keeps one entry per name.
 */
object DialogRefIndex {
  fun build(decompDir: File, romsDir: File, gameCode: String): Map<String, DialogRef> {
    val rom = RomIndex.find(romsDir, gameCode) ?: return emptyMap()
    val charmap = Charmap.load(File(decompDir, "charmap.txt"))
    val resolved =
        TextParser(decompDir).parseAll().mapNotNull { text ->
          val bytes = charmap.encode(text.content) ?: return@mapNotNull null
          if (rom.offsetOf(bytes) < 0) null else text.label
        }

    val refs = HashMap<String, DialogRef>()
    resolved
        .groupBy { RenderUtil.location(it) }
        .forEach { (location, labels) ->
          val className = RenderUtil.className(location).trim('`')
          val takenNames = HashSet<String>()
          for (label in labels) {
            val entry = RenderUtil.entryName(label)
            if (takenNames.add(entry)) refs[label] = DialogRef(className, entry)
          }
        }
    return refs
  }
}
