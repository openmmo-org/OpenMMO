package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
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

private fun entityIdBytes() =
    byteArrayOf(
        0x00, 0xC0.toByte(), 0xC8.toByte(), 0x2A, 0xEF.toByte(), 0xAD.toByte(), 0xCE.toByte(), 0x1A)

class MoveLearnPacketsTest :
    FunSpec({
      test("decodes a captured prompt for one move") {
        val bytes = entityIdBytes() + byteArrayOf(0x01, 0x9A.toByte(), 0x00)
        val decoded = MoveLearnPromptPacketCodec.decodeBytes(bytes)
        decoded shouldBe MoveLearnPromptPacket(ENTITY_ID, listOf(FURY_SWIPES))
        MoveLearnPromptPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      test("decodes a captured prompt for two moves") {
        val bytes = entityIdBytes() + byteArrayOf(0x02, 0x18, 0x02, 0x59, 0x01)
        val decoded = MoveLearnPromptPacketCodec.decodeBytes(bytes)
        decoded shouldBe MoveLearnPromptPacket(ENTITY_ID, listOf(536, 345))
        MoveLearnPromptPacketCodec.encodeToBytes(decoded) shouldBe bytes
      }

      // Fury Swipes took the slot Leer held.
      test("decodes a captured reply that swapped a move in") {
        val bytes =
            entityIdBytes() +
                byteArrayOf(
                    0x04,
                    0x0E,
                    0x02,
                    0x9A.toByte(),
                    0x00,
                    0x7A,
                    0x00,
                    0x37,
                    0x00,
                    0x01,
                    0x9A.toByte(),
                    0x00)
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
        val bytes =
            entityIdBytes() +
                byteArrayOf(
                    0x04, 0x0E, 0x02, 0x2B, 0x00, 0x7A, 0x00, 0x37, 0x00, 0x01, 0x9A.toByte(), 0x00)
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
