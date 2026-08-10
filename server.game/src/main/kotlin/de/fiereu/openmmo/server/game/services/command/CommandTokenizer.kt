package de.fiereu.openmmo.server.game.services.command

/**
 * Splits a command line into arguments. Runs of whitespace separate arguments, double quotes group
 * words into one argument (`give "Great Ball" 5` -> `give`, `Great Ball`, `5`), and a backslash
 * escapes the next character inside quotes. An unterminated quote takes the rest of the line.
 */
internal fun tokenizeCommandLine(line: String): List<String> {
  val args = mutableListOf<String>()
  val current = StringBuilder()
  var inQuotes = false
  var hasToken = false
  var i = 0
  while (i < line.length) {
    val c = line[i]
    when {
      inQuotes && c == '\\' && i + 1 < line.length -> {
        current.append(line[i + 1])
        i++
      }
      c == '"' -> {
        inQuotes = !inQuotes
        hasToken = true
      }
      !inQuotes && c.isWhitespace() -> {
        if (hasToken) args += current.toString()
        current.clear()
        hasToken = false
      }
      else -> {
        current.append(c)
        hasToken = true
      }
    }
    i++
  }
  if (hasToken) args += current.toString()
  return args
}
