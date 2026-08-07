package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnPromptPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnPromptPacketCodec
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnReplyPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnReplyPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ENTITY_ID = 0x1ACEADEF2AC8C000L
private const val FURY_SWIPES: Short = 154
private const val LICK: Short = 122
private const val WATER_GUN: Short = 55
private const val LEER: Short = 43
private const val UNOVA_MOVE: Short = 526

class MoveLearnPacketsTest :
    FunSpec({
      test("decodes a captured prompt for one move") {
        val bytes = fixture("game/s2c/17/prompt_one_move_32710.bin")
        val decoded = MoveLearnPromptPacketCodec.decodeBytes(bytes)
        decoded shouldBe MoveLearnPromptPacket(ENTITY_ID, listOf(FURY_SWIPES))
        MoveLearnPromptPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      test("decodes a captured prompt for two moves") {
        val bytes = fixture("game/s2c/17/prompt_two_moves_32710.bin")
        val decoded = MoveLearnPromptPacketCodec.decodeBytes(bytes)
        decoded shouldBe MoveLearnPromptPacket(ENTITY_ID, listOf(536, 345))
        MoveLearnPromptPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      // Fury Swipes took the slot Leer held.
      test("decodes a captured reply that swapped a move in") {
        val bytes = fixture("game/c2s/0a/reply_swapped_32710.bin")
        val decoded = MoveLearnReplyPacketCodec.decodeBytes(bytes)
        decoded shouldBe
            MoveLearnReplyPacket(
                entityId = ENTITY_ID,
                moveIds = listOf(UNOVA_MOVE, FURY_SWIPES, LICK, WATER_GUN),
                offered = listOf(FURY_SWIPES),
            )
        MoveLearnReplyPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      // The moveset comes back as it was, so the player kept Leer.
      test("decodes a captured reply that declined") {
        val bytes = fixture("game/c2s/0a/reply_declined_32710.bin")
        val decoded = MoveLearnReplyPacketCodec.decodeBytes(bytes)
        decoded shouldBe
            MoveLearnReplyPacket(
                entityId = ENTITY_ID,
                moveIds = listOf(UNOVA_MOVE, LEER, LICK, WATER_GUN),
                offered = listOf(FURY_SWIPES),
            )
        MoveLearnReplyPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }
    })
