package de.fiereu.openmmo.codegen.dialog

import java.io.File

/**
 * The GBA text encoder built from a pret decomp charmap.txt. It turns the literal content of a
 * `.string` (letters, `\n`/`\l`/`\p` escapes, `{NAME}` placeholders, the `$` terminator) into the
 * exact bytes the game stores in the ROM, so the encoded form can be located there.
 */
class Charmap
private constructor(
    private val chars: Map<Char, ByteArray>,
    private val escapes: Map<String, ByteArray>,
    private val placeholders: Map<String, ByteArray>,
) {

  /** Encodes [text], or returns null if it uses a symbol the charmap cannot represent. */
  fun encode(text: String): ByteArray? {
    val out = ArrayList<Byte>(text.length + 1)
    var i = 0
    while (i < text.length) {
      val c = text[i]
      when {
        c == '\\' && i + 1 < text.length -> {
          val esc = escapes["\\" + text[i + 1]] ?: return null
          out.addAll(esc.toList())
          i += 2
        }
        c == '{' -> {
          val end = text.indexOf('}', i)
          if (end < 0) return null
          val bytes = placeholders[text.substring(i + 1, end)] ?: return null
          out.addAll(bytes.toList())
          i = end + 1
        }
        else -> {
          val bytes = chars[c] ?: return null
          out.addAll(bytes.toList())
          i++
        }
      }
    }
    return out.toByteArray()
  }

  companion object {
    // key = value where key may itself be the '=' char, value is one or more hex bytes, and an
    // optional trailing "@ comment" follows whitespace.
    private val entry = Regex("^(.*?)=\\s*([0-9A-Fa-f]{2}(?:\\s+[0-9A-Fa-f]{2})*)\\s*(?:@.*)?$")

    fun load(charmapFile: File): Charmap {
      val chars = HashMap<Char, ByteArray>()
      val escapes = HashMap<String, ByteArray>()
      val placeholders = HashMap<String, ByteArray>()
      for (raw in charmapFile.readLines()) {
        val line = raw.replace(Regex("\\s+@.*$"), "").trimEnd()
        val m = entry.matchEntire(line) ?: continue
        val key = m.groupValues[1].trim()
        val bytes =
            m.groupValues[2].split(Regex("\\s+")).map { it.toInt(16).toByte() }.toByteArray()
        if (key.isEmpty() || bytes.isEmpty()) continue
        if (key.length >= 2 && key.first() == '\'' && key.last() == '\'') {
          when (val inner = key.substring(1, key.length - 1)) {
            "\\n",
            "\\l",
            "\\p",
            "\\r" -> escapes[inner] = bytes
            "\\'" -> chars['\''] = bytes
            else -> if (inner.length == 1) chars[inner[0]] = bytes
          }
        } else {
          placeholders[key] = bytes
        }
      }
      return Charmap(chars, escapes, placeholders)
    }
  }
}
