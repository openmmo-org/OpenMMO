package de.fiereu.openmmo.codegen.dialog

object RenderUtil {

  private val KEYWORDS =
      setOf(
          "as",
          "break",
          "class",
          "continue",
          "do",
          "else",
          "false",
          "for",
          "fun",
          "if",
          "in",
          "interface",
          "is",
          "null",
          "object",
          "package",
          "return",
          "super",
          "this",
          "throw",
          "true",
          "try",
          "typealias",
          "typeof",
          "val",
          "var",
          "when",
          "while")

  /** Location the label belongs to: everything before `_Text_`, or "Misc" when it has none. */
  fun location(label: String): String =
      if ("_Text_" in label) label.substringBefore("_Text_") else "Misc"

  /** The enum entry name: the part after `_Text_`, or the whole label, made a valid identifier. */
  fun entryName(label: String): String =
      ident(if ("_Text_" in label) label.substringAfter("_Text_") else label)

  fun className(location: String): String = ident(location)

  private fun ident(s: String): String {
    val safe = if (s.isNotEmpty() && s[0].isDigit()) "_$s" else s
    return if (safe in KEYWORDS) "`$safe`" else safe
  }

  fun entry(e: DialogEntry): String = "/** ${e.preview} */ ${e.name}(0x${"%08X".format(e.textId)}),"

  /** Turns raw `.string` content into a short single-line preview safe inside a KDoc comment. */
  fun preview(content: String): String {
    val text =
        content
            .replace("\\n", " ")
            .replace("\\l", " ")
            .replace("\\p", " ")
            .replace("$", "")
            .replace(Regex("\\s+"), " ")
            .replace("*/", "* /")
            .trim()
    return if (text.length > 70) text.take(70) + "…" else text
  }
}
