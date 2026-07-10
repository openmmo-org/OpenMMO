package de.fiereu.openmmo.server.game.battle

/**
 * Parses pokemon-showdown SIM-PROTOCOL log lines (`TurnResult.log`) into the intermediate
 * [BattleEvent] model — the sidecar→S2C half of the battle translator, before the capture-#2-gated
 * wire emitters.
 *
 * Pure and total: unrecognized/pacing lines (`|turn|`, `|upkeep|`, `|-weather|`, blank, …) and any
 * malformed line are skipped, never thrown — a live battle stream must not crash the server on an
 * unexpected line. Line formats per
 * https://github.com/smogon/pokemon-showdown/blob/master/sim/SIM-PROTOCOL.md.
 */
object BattleLogParser {

  /** Parse a turn's log lines into events, in order; non-event/malformed lines are dropped. */
  fun parse(log: List<String>): List<BattleEvent> = log.mapNotNull(::parseLine)

  private fun parseLine(line: String): BattleEvent? {
    if (!line.startsWith("|")) return null
    // "|move|p1a: Snivy|Tackle|p2a: Patrat" -> ["", "move", "p1a: Snivy", "Tackle", "p2a: Patrat"].
    // SIM-PROTOCOL never puts a raw '|' inside a field, so a plain split is safe.
    val parts = line.split("|")
    return when (parts.getOrNull(1)) {
      "move" -> {
        val source = actor(parts.getOrNull(2)) ?: return null
        MoveUsed(source, parts.getOrElse(3) { "" }, moveTarget(parts.getOrNull(4)))
      }
      "-damage" -> Damage(actor(parts.getOrNull(2)) ?: return null, hp(parts.getOrNull(3)))
      "-heal" -> Heal(actor(parts.getOrNull(2)) ?: return null, hp(parts.getOrNull(3)))
      "faint" -> Faint(actor(parts.getOrNull(2)) ?: return null)
      "switch" -> SwitchIn(actor(parts.getOrNull(2)) ?: return null, false, hp(parts.getOrNull(4)))
      "drag" -> SwitchIn(actor(parts.getOrNull(2)) ?: return null, true, hp(parts.getOrNull(4)))
      "-boost" -> statChange(parts, sign = 1)
      "-unboost" -> statChange(parts, sign = -1)
      "-status" -> {
        val target = actor(parts.getOrNull(2)) ?: return null
        val status = parts.getOrNull(3)?.takeIf { it.isNotBlank() } ?: return null
        StatusInflicted(target, status)
      }
      "win" -> parts.getOrNull(2)?.takeIf { it.isNotBlank() }?.let { BattleEnded(it) }
      "flee" -> Fled
      else -> null
    }
  }

  private fun statChange(parts: List<String>, sign: Int): BattleEvent? {
    val target = actor(parts.getOrNull(2)) ?: return null
    val stat = parts.getOrNull(3)?.takeIf { it.isNotBlank() } ?: return null
    val amount = parts.getOrNull(4)?.trim()?.toIntOrNull() ?: return null
    return StatChange(target, stat, sign * amount)
  }

  /** A `|move|` TARGET field: a POKEMON ident, or absent/replaced by a `[tag]` (→ null). */
  private fun moveTarget(token: String?): BattleActor? =
      token?.takeIf { it.isNotBlank() && !it.startsWith("[") }?.let { actor(it) }

  /** Parse a `POSITION: NAME` ident, e.g. `p1a: Snivy`. Null if the token is missing/blank. */
  private fun actor(token: String?): BattleActor? {
    val t = token?.takeIf { it.isNotBlank() } ?: return null
    val colon = t.indexOf(':')
    val position = (if (colon >= 0) t.substring(0, colon) else t).trim()
    val name = if (colon >= 0) t.substring(colon + 1).trim() else ""
    val side = if (position.startsWith("p1")) BattleSide.PLAYER else BattleSide.WILD
    val slot =
        when (position.lastOrNull()) {
          'b' -> 1
          'c' -> 2
          else -> 0 // 'a' or unknown → the first active slot
        }
    return BattleActor(side, slot, name)
  }

  /** Parse a `HP STATUS` token: `72/100`, `54/100 brn`, `0 fnt`. Missing token → 0 HP, fainted. */
  private fun hp(token: String?): HpStatus {
    val t = token?.trim().orEmpty()
    val space = t.indexOf(' ')
    val hpPart = if (space >= 0) t.substring(0, space) else t
    val statusPart =
        if (space >= 0) t.substring(space + 1).trim().takeIf { it.isNotBlank() } else null
    val slash = hpPart.indexOf('/')
    val current = (if (slash >= 0) hpPart.substring(0, slash) else hpPart).trim().toIntOrNull() ?: 0
    val max = if (slash >= 0) hpPart.substring(slash + 1).trim().toIntOrNull() else null
    val fainted = current == 0 || statusPart == "fnt"
    val status = statusPart?.takeIf { it != "fnt" }
    return HpStatus(current, max, fainted, status)
  }
}
