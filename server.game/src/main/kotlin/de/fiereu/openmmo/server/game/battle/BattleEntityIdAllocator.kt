package de.fiereu.openmmo.server.game.battle

import java.util.concurrent.atomic.AtomicLong

/**
 * Allocates the per-mon 8-byte entity ids that key every S2C battle packet.
 *
 * Every battle packet (0x30 open, 0x16 delta, 0x33 move, 0x41 sprite, …) references a mon by an
 * 8-byte entity id; the client learns the ids from the battle-open and **desyncs/crashes** if a
 * later event carries a different id. Mons are transient (they exist only for the battle), so ids
 * are assigned here — 2 per wild battle (player active mon + wild mon) — and tracked in the battle
 * state so every emitted event reuses them.
 *
 * ### Id layout (carved so a mon id can NEVER collide with a character or NPC id)
 *
 * ```
 *   bits 48..63 : 0x0BB0   battle-mon space tag   (≠ NPC 0x1A69…/0x1A6B… top-16 → disjoint from NPCs)
 *   bits 16..47 : sequence  unique per allocation  (session-global AtomicLong)
 *   bits  0..15 : 0xC000   mon type tag           (≠ character 0x9000 low-word → disjoint from chars)
 * ```
 *
 * Range: `[0x0BB0_0000_0000_C000 … 0x0BB0_FFFF_FFFF_C000]`. The low-word tag alone guarantees no
 * overlap with the character range (`CharacterStore` `(uniqueId shl 16) or 0x9000`); the top-16 tag
 * guarantees no overlap with the NPC range (`NpcService` `0x1A6BFE24CC88E000 or idx` and the
 * `0x1A69…` dynamic counter). The 0xC000 low-word matches the real mon ids seen in capture #1
 * (player `…c000`, wild `…c000`).
 */
class BattleEntityIdAllocator {
  private val sequence = AtomicLong(0)

  /** The next unique battle-mon entity id. Thread-safe; monotonic within the session. */
  fun next(): Long {
    val seq = sequence.incrementAndGet() and SEQUENCE_MASK
    return SPACE_BASE or (seq shl 16)
  }

  companion object {
    /** Top-16 battle-mon tag (bits 48..63) OR the mon low-word tag (bits 0..15). */
    const val SPACE_BASE: Long = 0x0BB0_0000_0000_C000L
    /** The sequence occupies the middle 32 bits (16..47); masked so it never touches the tags. */
    private const val SEQUENCE_MASK: Long = 0xFFFF_FFFFL
  }
}
