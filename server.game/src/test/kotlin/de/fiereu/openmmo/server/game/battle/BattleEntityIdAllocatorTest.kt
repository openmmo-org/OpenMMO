package de.fiereu.openmmo.server.game.battle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class BattleEntityIdAllocatorTest :
    FunSpec({
      test("allocates unique ids carved into the battle-mon space (disjoint from char/NPC)") {
        val alloc = BattleEntityIdAllocator()
        val a = alloc.next()
        val b = alloc.next()

        // Two mons per battle → the two ids must differ.
        a shouldNotBe b

        // Low-word mon tag 0xC000 — disjoint from the character range (CharacterStore …|0x9000).
        (a and 0xFFFFL) shouldBe 0xC000L
        (b and 0xFFFFL) shouldBe 0xC000L

        // Top-16 battle-space tag 0x0BB0 — disjoint from the NPC range (0x1A69…/0x1A6B…).
        ((a ushr 48) and 0xFFFFL) shouldBe 0x0BB0L
        ((b ushr 48) and 0xFFFFL) shouldBe 0x0BB0L
      }

      test("a mon id can never equal a character id or an NPC id") {
        val alloc = BattleEntityIdAllocator()
        // Character ids: (uniqueId shl 16) or 0x9000  (CharacterStore.generateEntityId)
        val charIds = (1L..64L).map { (it shl 16) or 0x9000L }
        // NPC ids: base | idx (NpcService static) and the 0x1A69… dynamic counter.
        val npcIds =
            (0L..64L).map { 0x1A6BFE24CC88E000L or it } + (1L..64L).map { 0x1A69000000000000L + it }
        val monIds = (1..1000).map { alloc.next() }.toSet()

        monIds.intersect(charIds.toSet()) shouldBe emptySet()
        monIds.intersect(npcIds.toSet()) shouldBe emptySet()
        monIds.size shouldBe 1000 // all unique
      }
    })
