package de.fiereu.openmmo.codegen.item

object RenderUtil {

  fun declaration(item: ParsedItem): String =
      "  /** ${item.ids.joinToString(", ")} */\n" +
          "  val ${item.identifier} = item(${escapeString(item.name)}, ${item.price})"

  fun register(item: ParsedItem): String =
      "reg.register(Items.${item.identifier}, ${item.ids.joinToString(", ")})"

  private fun escapeString(s: String): String =
      "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
}
