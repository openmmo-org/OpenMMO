package de.fiereu.openmmo.net.login

import de.fiereu.network.Protocol
import de.fiereu.network.c2s
import de.fiereu.network.s2c
import de.fiereu.openmmo.net.login.packets.ExistingSessionPacket
import de.fiereu.openmmo.net.login.packets.ExistingSessionPacketCodec
import de.fiereu.openmmo.net.login.packets.GameServerListPacket
import de.fiereu.openmmo.net.login.packets.GameServerListPacketCodec
import de.fiereu.openmmo.net.login.packets.GameServerNodesPacket
import de.fiereu.openmmo.net.login.packets.GameServerNodesPacketCodec
import de.fiereu.openmmo.net.login.packets.JoinGameServerPacket
import de.fiereu.openmmo.net.login.packets.JoinGameServerPacketCodec
import de.fiereu.openmmo.net.login.packets.LoginKickPacket
import de.fiereu.openmmo.net.login.packets.LoginKickPacketCodec
import de.fiereu.openmmo.net.login.packets.LoginRequestPacket
import de.fiereu.openmmo.net.login.packets.LoginRequestPacketCodec
import de.fiereu.openmmo.net.login.packets.LoginResponsePacket
import de.fiereu.openmmo.net.login.packets.LoginResponsePacketCodec
import de.fiereu.openmmo.net.login.packets.MfaChallengePacket
import de.fiereu.openmmo.net.login.packets.MfaChallengePacketCodec
import de.fiereu.openmmo.net.login.packets.MfaResponsePacket
import de.fiereu.openmmo.net.login.packets.MfaResponsePacketCodec
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacket
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacketCodec
import de.fiereu.openmmo.net.login.packets.SentCredentialsPacket
import de.fiereu.openmmo.net.login.packets.SentCredentialsPacketCodec
import de.fiereu.openmmo.net.login.packets.ToSConfirmationPacket
import de.fiereu.openmmo.net.login.packets.ToSConfirmationPacketCodec
import de.fiereu.openmmo.net.login.packets.ToSPacket
import de.fiereu.openmmo.net.login.packets.ToSPacketCodec

object LoginProtocol : Protocol() {
  init {
    s2c<LoginResponsePacket>(0x01u, LoginResponsePacketCodec)
    c2s<RequestGameServerListPacket>(0x02u, RequestGameServerListPacketCodec)
    c2s<JoinGameServerPacket>(0x03u, JoinGameServerPacketCodec)
    s2c<GameServerNodesPacket>(0x03u, GameServerNodesPacketCodec)
    c2s<ToSConfirmationPacket>(0x04u, ToSConfirmationPacketCodec)
    s2c<LoginKickPacket>(0x05u, LoginKickPacketCodec)
    s2c<SentCredentialsPacket>(0x07u, SentCredentialsPacketCodec)
    s2c<MfaChallengePacket>(0x08u, MfaChallengePacketCodec)
    c2s<MfaResponsePacket>(0x08u, MfaResponsePacketCodec)
    c2s<LoginRequestPacket>(0x11u, LoginRequestPacketCodec)
    s2c<ToSPacket>(0x14u, ToSPacketCodec)
    s2c<GameServerListPacket>(0x22u, GameServerListPacketCodec)
    s2c<ExistingSessionPacket>(0x26u, ExistingSessionPacketCodec)
  }
}
