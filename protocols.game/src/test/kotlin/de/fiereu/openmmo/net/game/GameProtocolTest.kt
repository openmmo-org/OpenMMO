package de.fiereu.openmmo.net.game

import de.fiereu.network.Direction
import de.fiereu.network.Side
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.EntityMovePacket
import de.fiereu.openmmo.net.game.packets.JoinPacket
import de.fiereu.openmmo.net.game.packets.JoinResponsePacket
import de.fiereu.openmmo.net.game.packets.KeepAlivePacket
import de.fiereu.openmmo.net.game.packets.LoadMapPacket
import de.fiereu.openmmo.net.game.packets.NullPacket
import de.fiereu.openmmo.net.game.packets.TokenPayloadPacket
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class GameProtocolTest :
    FunSpec({
      test("at least one registration per known opcode") {
        val opcodes = GameProtocol.registrations.map { it.opcode }.toSet()
        opcodes shouldContainAll
            setOf(
                0x01u.toUByte(),
                0x02u.toUByte(),
                0x03u.toUByte(),
                0x04u.toUByte(),
                0x05u.toUByte(),
                0x06u.toUByte(),
                0x07u.toUByte(),
                0x08u.toUByte(),
                0x09u.toUByte(),
                0x0Du.toUByte(),
                0x0Eu.toUByte(),
                0x10u.toUByte(),
                0x11u.toUByte(),
                0x12u.toUByte(),
                0x13u.toUByte(),
                0x1Bu.toUByte(),
                0x20u.toUByte(),
                0x21u.toUByte(),
                0x22u.toUByte(),
                0x25u.toUByte(),
                0x26u.toUByte(),
                0xB2u.toUByte(),
                0xB4u.toUByte(),
                0xB9u.toUByte(),
                0xC2u.toUByte(),
                0xE4u.toUByte(),
            )
      }

      test("compressed flag is set") { GameProtocol.compressed shouldBe true }

      test("0x01 split: client outgoing JoinPacket, server outgoing JoinResponsePacket") {
        GameProtocol.outgoingRegistration(Side.CLIENT, JoinPacket::class)?.opcode shouldBe 0x01u
        GameProtocol.outgoingRegistration(Side.SERVER, JoinResponsePacket::class)?.opcode shouldBe
            0x01u
      }

      test("0xE4 EntityMovePacket is S2C only") {
        GameProtocol.outgoingRegistration(Side.SERVER, EntityMovePacket::class)?.opcode shouldBe
            0xE4u
        GameProtocol.outgoingRegistration(Side.CLIENT, EntityMovePacket::class) shouldBe null
      }

      test("0x20 split: NullPacket C2S, TokenPayloadPacket S2C") {
        GameProtocol.incomingRegistration(Side.SERVER, 0x20u)?.type shouldBe NullPacket::class
        GameProtocol.incomingRegistration(Side.CLIENT, 0x20u)?.type shouldBe
            TokenPayloadPacket::class
      }

      test("bidi packets are registered both directions") {
        val bidi =
            listOf(
                ChatMessagePacket::class,
                LoadMapPacket::class,
                KeepAlivePacket::class,
            )
        for (type in bidi) {
          val serverOut = GameProtocol.outgoingRegistration(Side.SERVER, type)
          val clientOut = GameProtocol.outgoingRegistration(Side.CLIENT, type)
          (serverOut != null && clientOut != null) shouldBe true
          serverOut?.direction shouldBe Direction.S2C
          clientOut?.direction shouldBe Direction.C2S
          serverOut?.opcode shouldBe clientOut?.opcode
        }
      }
    })
