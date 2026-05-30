package de.fiereu.openmmo.net.login

import de.fiereu.network.Direction
import de.fiereu.network.Side
import de.fiereu.openmmo.net.login.packets.ExistingSessionPacket
import de.fiereu.openmmo.net.login.packets.GameServerListPacket
import de.fiereu.openmmo.net.login.packets.GameServerNodesPacket
import de.fiereu.openmmo.net.login.packets.JoinGameServerPacket
import de.fiereu.openmmo.net.login.packets.LoginKickPacket
import de.fiereu.openmmo.net.login.packets.LoginRequestPacket
import de.fiereu.openmmo.net.login.packets.LoginResponsePacket
import de.fiereu.openmmo.net.login.packets.MfaChallengePacket
import de.fiereu.openmmo.net.login.packets.MfaResponsePacket
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacket
import de.fiereu.openmmo.net.login.packets.SentCredentialsPacket
import de.fiereu.openmmo.net.login.packets.ToSConfirmationPacket
import de.fiereu.openmmo.net.login.packets.ToSPacket
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import kotlin.reflect.KClass

class LoginProtocolTest :
    FunSpec({
      test("registers every documented opcode pair") {
        val expected: List<Triple<UByte, Direction, KClass<*>>> =
            listOf(
                Triple(0x01u, Direction.S2C, LoginResponsePacket::class),
                Triple(0x02u, Direction.C2S, RequestGameServerListPacket::class),
                Triple(0x03u, Direction.C2S, JoinGameServerPacket::class),
                Triple(0x03u, Direction.S2C, GameServerNodesPacket::class),
                Triple(0x04u, Direction.C2S, ToSConfirmationPacket::class),
                Triple(0x05u, Direction.S2C, LoginKickPacket::class),
                Triple(0x07u, Direction.S2C, SentCredentialsPacket::class),
                Triple(0x08u, Direction.S2C, MfaChallengePacket::class),
                Triple(0x08u, Direction.C2S, MfaResponsePacket::class),
                Triple(0x11u, Direction.C2S, LoginRequestPacket::class),
                Triple(0x14u, Direction.S2C, ToSPacket::class),
                Triple(0x22u, Direction.S2C, GameServerListPacket::class),
                Triple(0x26u, Direction.S2C, ExistingSessionPacket::class),
            )
        val actual = LoginProtocol.registrations.map { Triple(it.opcode, it.direction, it.type) }
        actual shouldContainAll expected
        LoginProtocol.registrations.size shouldBe expected.size
      }

      test("incoming/outgoing maps resolve per side") {
        LoginProtocol.incomingRegistration(Side.SERVER, 0x11u)?.type shouldBe
            LoginRequestPacket::class
        LoginProtocol.incomingRegistration(Side.CLIENT, 0x01u)?.type shouldBe
            LoginResponsePacket::class
        LoginProtocol.outgoingRegistration(Side.SERVER, GameServerListPacket::class)
            ?.opcode shouldBe 0x22u
        LoginProtocol.outgoingRegistration(Side.CLIENT, LoginRequestPacket::class)?.opcode shouldBe
            0x11u
      }
    })
