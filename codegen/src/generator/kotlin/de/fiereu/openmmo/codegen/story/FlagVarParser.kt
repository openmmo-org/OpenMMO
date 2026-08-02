package de.fiereu.openmmo.codegen.story

import java.io.File

data class StoryConstant(val name: String, val numericId: Int)

/** Reads GBA ids from pret headers. */
object FlagVarParser {
  private data class Macro(
      val parameters: List<String>?,
      val expression: String,
      val numericHint: Int?,
  )

  fun flags(decompDir: File): List<StoryConstant> =
      constants(decompDir, "include/constants/flags.h", "FLAG_")

  fun vars(decompDir: File): List<StoryConstant> =
      constants(decompDir, "include/constants/vars.h", "VAR_")

  /** Emerald new-game reset flags. */
  fun initialFlags(decompDir: File): List<String> =
      scriptFlags(
          decompDir,
          "data/scripts/new_game.inc",
          "EventScript_ResetAllMapFlags",
      )

  /** Gender-specific flags normally established by InsideOfTruck's intro script. */
  fun maleIntroFlags(decompDir: File): List<String> =
      scriptFlags(
          decompDir,
          "data/maps/InsideOfTruck/scripts.inc",
          "InsideOfTruck_EventScript_SetIntroFlagsMale",
      )

  fun femaleIntroFlags(decompDir: File): List<String> =
      scriptFlags(
          decompDir,
          "data/maps/InsideOfTruck/scripts.inc",
          "InsideOfTruck_EventScript_SetIntroFlagsFemale",
      )

  private fun scriptFlags(decompDir: File, relativePath: String, label: String): List<String> {
    val file = File(decompDir, relativePath)
    if (!file.exists()) return emptyList()
    val result = mutableListOf<String>()
    var inBlock = false
    for (rawLine in file.readLines()) {
      val line = rawLine.substringBefore('@').trim()
      if (!inBlock) {
        if (line == "$label::") inBlock = true
        continue
      }
      if (line.endsWith("::")) break
      Regex("""^setflag\s+(FLAG_[A-Za-z0-9_]+)\b""")
          .find(line)
          ?.groupValues
          ?.get(1)
          ?.let(result::add)
      if (line == "end" || line == "return") break
    }
    return result.distinct()
  }

  private fun constants(
      decompDir: File,
      relativePath: String,
      prefix: String,
  ): List<StoryConstant> {
    val target = File(decompDir, relativePath)
    require(target.exists()) { "Missing ${target.path}" }

    // Load sibling constants before the target header.
    val targetConstants = parseConstants(target)
    val macros = linkedMapOf<String, Macro>()
    targetConstants.forEach { (name, macro) -> macros[name] = macro }
    File(decompDir, "include/constants")
        .walkTopDown()
        .filter { it.isFile && it.extension == "h" && it != target }
        .sortedBy(File::getPath)
        .forEach { file ->
          parseConstants(file).forEach { (name, macro) -> macros.putIfAbsent(name, macro) }
        }

    val wanted =
        targetConstants
            .filter { (name, macro) -> name.startsWith(prefix) && macro.parameters == null }
            .keys
    val evaluator = MacroEvaluator(macros)
    val unresolved = mutableListOf<String>()
    val result =
        wanted.mapNotNull { name ->
          evaluator.resolve(name)?.let { StoryConstant(name, it) }
              ?: run {
                unresolved += name
                null
              }
        }
    check(unresolved.isEmpty()) {
      "Could not resolve ${unresolved.size} $prefix constants in ${target.path}: " +
          unresolved.take(12).joinToString()
    }
    return result.distinctBy(StoryConstant::name)
  }

  private fun parseMacros(file: File): LinkedHashMap<String, Macro> {
    val result = linkedMapOf<String, Macro>()
    val define = Regex("""^#define\s+([A-Za-z_]\w*)(?:\(([^)]*)\))?\s+(.+)$""")
    val blockComment = Regex("""/\*.*?\*/""")
    for (line in file.readLines()) {
      val match = define.find(line.trim()) ?: continue
      val name = match.groupValues[1]
      val parameters =
          match.groupValues[2].takeIf(String::isNotEmpty)?.split(',')?.map(String::trim)
      val raw = match.groupValues[3]
      val comment = raw.substringAfter("//", "")
      val expression = raw.substringBefore("//").replace(blockComment, "").trim()
      if (expression.isEmpty()) continue
      val hint = Regex("""(?:0x[0-9A-Fa-f]+|\b\d+\b)""").find(comment)?.value?.let(::parseNumber)
      result[name] = Macro(parameters, expression, hint)
    }
    return result
  }

  private fun parseConstants(file: File): LinkedHashMap<String, Macro> =
      linkedMapOf<String, Macro>().apply {
        putAll(parseMacros(file))
        putAll(parseEnumConstants(file))
      }

  /** Treat C enum members as ordinary integer macros, including implicit incrementing values. */
  private fun parseEnumConstants(file: File): LinkedHashMap<String, Macro> {
    val result = linkedMapOf<String, Macro>()
    var inEnum = false
    var previous: String? = null
    for (rawLine in file.readLines()) {
      val line = rawLine.substringBefore("//").replace(Regex("""/\*.*?\*/"""), "").trim()
      if (!inEnum) {
        if (line.startsWith("enum") && line.contains('{')) {
          inEnum = true
          previous = null
        }
        continue
      }
      if (line.startsWith("}")) {
        inEnum = false
        previous = null
        continue
      }
      val member = line.removeSuffix(",").trim()
      val match = Regex("""^([A-Za-z_]\w*)(?:\s*=\s*(.+))?$""").matchEntire(member) ?: continue
      val name = match.groupValues[1]
      val explicit = match.groupValues[2].takeIf(String::isNotEmpty)
      val expression = explicit ?: previous?.let { "($it + 1)" } ?: "0"
      result[name] = Macro(parameters = null, expression = expression, numericHint = null)
      previous = name
    }
    return result
  }

  private class MacroEvaluator(private val macros: Map<String, Macro>) {
    private val memo = mutableMapOf<String, Int>()
    private val resolving = mutableSetOf<String>()

    fun resolve(name: String): Int? {
      memo[name]?.let {
        return it
      }
      if (!resolving.add(name)) return null
      val macro = macros[name]
      val value =
          if (macro == null || macro.parameters != null) null
          else ExpressionParser(macro.expression, this, emptyMap()).parse() ?: macro.numericHint
      resolving.remove(name)
      if (value != null) memo[name] = value
      return value
    }

    fun resolve(name: String, arguments: List<Int>, locals: Map<String, Int>): Int? {
      locals[name]?.let {
        return it
      }
      val macro = macros[name] ?: return null
      val parameters = macro.parameters ?: return if (arguments.isEmpty()) resolve(name) else null
      if (parameters.size != arguments.size) return null
      val callLocals = locals + parameters.zip(arguments)
      return ExpressionParser(macro.expression, this, callLocals).parse() ?: macro.numericHint
    }
  }

  private class ExpressionParser(
      expression: String,
      private val evaluator: MacroEvaluator,
      private val locals: Map<String, Int>,
  ) {
    private val tokens =
        Regex("""0x[0-9A-Fa-f]+|\d+|[A-Za-z_]\w*|<<|>>|[(),+\-*/%|&~]""")
            .findAll(expression)
            .map { it.value }
            .toList()
    private val compactExpression = expression.filterNot(Char::isWhitespace)
    private var position = 0

    fun parse(): Int? {
      if (tokens.joinToString("") != compactExpression) return null
      val value = parseBitwiseOr() ?: return null
      return value.takeIf { position == tokens.size }
    }

    private fun parseBitwiseOr(): Int? = binary(::parseBitwiseAnd, "|") { a, b -> a or b }

    private fun parseBitwiseAnd(): Int? = binary(::parseShift, "&") { a, b -> a and b }

    private fun parseShift(): Int? {
      var value = parseAdditive() ?: return null
      while (peek() == "<<" || peek() == ">>") {
        val operator = take()
        val right = parseAdditive() ?: return null
        value = if (operator == "<<") value shl right else value shr right
      }
      return value
    }

    private fun parseAdditive(): Int? {
      var value = parseMultiplicative() ?: return null
      while (peek() == "+" || peek() == "-") {
        val operator = take()
        val right = parseMultiplicative() ?: return null
        value = if (operator == "+") value + right else value - right
      }
      return value
    }

    private fun parseMultiplicative(): Int? {
      var value = parseUnary() ?: return null
      while (peek() == "*" || peek() == "/" || peek() == "%") {
        val operator = take()
        val right = parseUnary() ?: return null
        if ((operator == "/" || operator == "%") && right == 0) return null
        value =
            when (operator) {
              "*" -> value * right
              "/" -> value / right
              else -> value % right
            }
      }
      return value
    }

    private fun parseUnary(): Int? =
        when (peek()) {
          "+" -> {
            take()
            parseUnary()
          }
          "-" -> {
            take()
            parseUnary()?.let { -it }
          }
          "~" -> {
            take()
            parseUnary()?.inv()
          }
          else -> parsePrimary()
        }

    private fun parsePrimary(): Int? {
      val token = take() ?: return null
      if (token == "(") {
        val value = parseBitwiseOr() ?: return null
        if (take() != ")") return null
        return value
      }
      if (token.firstOrNull()?.isDigit() == true) return parseNumber(token)
      if (token.firstOrNull()?.isLetter() != true && token.firstOrNull() != '_') return null
      if (peek() != "(") return evaluator.resolve(token, emptyList(), locals)

      take()
      val arguments = mutableListOf<Int>()
      if (peek() != ")") {
        while (true) {
          arguments += parseBitwiseOr() ?: return null
          if (peek() != ",") break
          take()
        }
      }
      if (take() != ")") return null
      return evaluator.resolve(token, arguments, locals)
    }

    private fun binary(
        operand: () -> Int?,
        operator: String,
        combine: (Int, Int) -> Int,
    ): Int? {
      var value = operand() ?: return null
      while (peek() == operator) {
        take()
        value = combine(value, operand() ?: return null)
      }
      return value
    }

    private fun peek(): String? = tokens.getOrNull(position)

    private fun take(): String? = tokens.getOrNull(position++)
  }

  private fun parseNumber(value: String): Int =
      if (value.startsWith("0x", ignoreCase = true)) value.drop(2).toInt(16) else value.toInt()
}
