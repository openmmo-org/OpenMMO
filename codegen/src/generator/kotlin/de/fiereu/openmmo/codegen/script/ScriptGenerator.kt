package de.fiereu.openmmo.codegen.script

import java.io.File

/**
 * Emits one Kotlin file per map holding the [de.fiereu.openmmo.server.game.script.Script] objects
 * for that map's npcs and signs. Single msgbox scripts are filled in, anything with branching or
 * extra commands is left as a TODO stub with the decomp body in a comment. Returns the fully
 * qualified per map map values so the caller can build the registry.
 */
class ScriptGenerator(
    private val region: String,
    private val outputDir: File,
) {
  private val basePackage = "de.fiereu.openmmo.server.game.script.generated.$region"
  private val dialogPackage = "de.fiereu.openmmo.dialog.generated.$region"

  fun generate(
      decompDir: File,
      romsDir: File,
      gameCode: String,
      assigned: MutableSet<String>,
  ): List<String> {
    val scripts = ScriptIndex.build(decompDir)
    val events = MapEventIndex.build(decompDir)
    val dialogRefs = DialogRefIndex.build(decompDir, romsDir, gameCode)

    val packageDir = File(outputDir, basePackage.replace('.', '/'))
    if (packageDir.exists()) packageDir.deleteRecursively()
    packageDir.mkdirs()

    val mapValues = mutableListOf<String>()
    var simple = 0
    var todo = 0
    for ((mapName, labels) in events.byMap) {
      val stubs = labels.filter { assigned.add(it) }.map { toStub(it, scripts, dialogRefs) }
      if (stubs.isEmpty()) continue
      File(packageDir, "$mapName.kt").writeText(renderMapFile(mapName, stubs))
      mapValues.add("$basePackage.${mapName}Scripts")
      simple += stubs.count { it.kind != ScriptKind.TODO }
      todo += stubs.count { it.kind == ScriptKind.TODO }
    }
    println("[script] $region: ${mapValues.size} maps, $simple filled, $todo todo stubs")
    return mapValues
  }

  private fun toStub(
      label: String,
      scripts: ScriptIndex,
      dialogRefs: Map<String, DialogRef>,
  ): ScriptStub {
    val commands =
        scripts.commandsFor(label)
            ?: return ScriptStub(label, ScriptKind.TODO, null, listOf("script body not found"))
    val msgbox = singleMsgbox(commands) ?: return ScriptStub(label, ScriptKind.TODO, null, commands)
    val ref =
        dialogRefs[msgbox.textLabel] ?: return ScriptStub(label, ScriptKind.TODO, null, commands)
    val kind = if (msgbox.type == "MSGBOX_SIGN") ScriptKind.SIGN else ScriptKind.SAY
    return ScriptStub(label, kind, ref, emptyList())
  }

  private data class Msgbox(val textLabel: String, val type: String)

  /** The single meaningful command is a msgbox, ignoring cosmetic framing around it. */
  private fun singleMsgbox(commands: List<String>): Msgbox? {
    val meaningful = commands.filter { opcode(it) !in FRAMING }
    if (meaningful.size != 1) return null
    val cmd = meaningful.single()
    if (opcode(cmd) != "msgbox") return null
    val args = cmd.removePrefix("msgbox").substringBefore('@').trim().split(",")
    val textLabel = args[0].trim()
    if (textLabel.isEmpty() || !textLabel.first().isLetter()) return null
    val type = args.getOrNull(1)?.trim().takeUnless { it.isNullOrEmpty() } ?: "MSGBOX_DEFAULT"
    return Msgbox(textLabel, type)
  }

  private fun opcode(command: String): String =
      command.substringBefore(' ').substringBefore('\t').trim()

  private fun renderMapFile(mapName: String, stubs: List<ScriptStub>): String {
    val imports = sortedSetOf<String>()
    stubs.forEach { it.dialogRef?.let { ref -> imports.add("$dialogPackage.${ref.className}") } }

    val sb = StringBuilder()
    sb.append("package ").append(basePackage).append("\n\n")
    imports.forEach { sb.append("import ").append(it).append('\n') }
    sb.append("import de.fiereu.openmmo.server.game.script.Script\n")
    sb.append("import de.fiereu.openmmo.server.game.script.ScriptContext\n\n")

    for (stub in stubs) renderStub(sb, stub)

    sb.append("internal val ").append(mapName).append("Scripts: Map<String, Script> =\n")
    sb.append("    mapOf(\n")
    for (stub in stubs) {
      sb.append("        \"").append(stub.label).append("\" to ").append(stub.label).append(",\n")
    }
    sb.append("    )\n")
    return sb.toString()
  }

  private fun renderStub(sb: StringBuilder, stub: ScriptStub) {
    when (stub.kind) {
      ScriptKind.SIGN -> renderBody(sb, stub.label, "ctx.sign(${stub.dialogRef!!.reference})")
      ScriptKind.SAY -> renderBody(sb, stub.label, "ctx.say(${stub.dialogRef!!.reference})")
      ScriptKind.TODO -> {
        sb.append("/**\n * Not ported yet. Decomp body:\n * ```\n")
        for (line in stub.decompLines) {
          sb.append(" * ").append(line.replace("*/", "* /")).append('\n')
        }
        sb.append(" * ```\n */\n")
        renderBody(sb, stub.label, "TODO(\"port ${stub.label}\")")
      }
    }
  }

  private fun renderBody(sb: StringBuilder, label: String, body: String) {
    sb.append("internal object ").append(label).append(" : Script {\n")
    sb.append("  override suspend fun run(ctx: ScriptContext) = ").append(body).append("\n")
    sb.append("}\n\n")
  }

  private companion object {
    // Cosmetic commands that wrap a plain message and do not change what is shown.
    val FRAMING =
        setOf(
            "lock",
            "lockall",
            "faceplayer",
            "release",
            "releaseall",
            "closemessage",
            "waitmessage",
            "waitbuttonpress",
            "end",
            "return",
        )
  }
}
