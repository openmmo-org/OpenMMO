package de.fiereu.openmmo.server.game.battle

/**
 * The intermediate battle-event model — the decoupling layer between pokemon-showdown's compact
 * protocol log and PokéMMO's S2C event packets.
 *
 * [BattleLogParser] turns `TurnResult.log` lines into this model (pure, capture-independent). A
 * later, capture-#2-gated emitter maps each event to its S2C packet(s), keyed to the session's
 * tracked entity ids (see `BattleService.hpDeltas` / `BattleEntityIdAllocator`). Keeping the actor
 * as a [BattleSide]+slot (not an entity id) is deliberate: the parser stays independent of the
 * entity-id model, and the emitter resolves side → id at the wire edge.
 */
sealed interface BattleEvent

/** Which side of the battle a mon is on. Showdown positions `p1*` → [PLAYER], `p2*` → [WILD]. */
enum class BattleSide {
  PLAYER,
  WILD,
}

/** A mon referenced by a log line: its side, active slot (a/b/c → 0/1/2), and nickname. */
data class BattleActor(val side: BattleSide, val slot: Int, val name: String)

/**
 * Current HP + status from a Showdown `HP STATUS` token (e.g. `72/100`, `54/100 brn`, `0 fnt`).
 * [max] is null when the token carries no denominator (a fainted `0 fnt`). `fnt` sets [fainted] and
 * is not surfaced as a [status]; real conditions (`par`, `brn`, `tox`, …) are.
 */
data class HpStatus(val current: Int, val max: Int?, val fainted: Boolean, val status: String?)

/**
 * `|move|SOURCE|MOVE|TARGET` — an entity used a move ([target] null if none/only tags followed).
 */
data class MoveUsed(val source: BattleActor, val move: String, val target: BattleActor?) :
    BattleEvent

/** `|-damage|POKEMON|HP STATUS` — the mon took damage; new HP in [hp]. */
data class Damage(val target: BattleActor, val hp: HpStatus) : BattleEvent

/** `|-heal|POKEMON|HP STATUS` — the mon healed; new HP in [hp]. */
data class Heal(val target: BattleActor, val hp: HpStatus) : BattleEvent

/** `|faint|POKEMON` — the mon fainted. */
data class Faint(val target: BattleActor) : BattleEvent

/** `|switch|` (intentional) / `|drag|` (forced) — a mon entered the battle with [hp]. */
data class SwitchIn(val actor: BattleActor, val forced: Boolean, val hp: HpStatus) : BattleEvent

/** `|-boost|`/`|-unboost|POKEMON|STAT|AMOUNT` — [stages] is signed (unboost negative). */
data class StatChange(val target: BattleActor, val stat: String, val stages: Int) : BattleEvent

/** `|-status|POKEMON|STATUS` — a status condition was inflicted. */
data class StatusInflicted(val target: BattleActor, val status: String) : BattleEvent

/** `|win|USER` — the battle ended; [winner] is the raw Showdown username. */
data class BattleEnded(val winner: String) : BattleEvent

/** `|flee|` — the sidecar's marker that the player fled the wild battle. */
data object Fled : BattleEvent
