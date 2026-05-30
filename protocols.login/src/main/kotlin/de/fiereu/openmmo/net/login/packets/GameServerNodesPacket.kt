package de.fiereu.openmmo.net.login.packets

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.github.maltalex.ineter.base.IPv6Address
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.MalformedPacketException
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.bytesPrefixed
import de.fiereu.openmmo.common.enums.LoginState

data class GameServerNode(
    val iPv4Address: IPv4Address,
    val iPv6Address: IPv6Address,
    val port: UShort = 7777u,
    val weight: UByte,
)

data class GameServerData(
    val gameServerId: UByte,
    val userId: Int,
    val sessionToken: ByteArray,
    val localAddress: IPAddress,
    val localHostname: String,
    val port: UShort,
)

data class GameServerNodesPacket(
    val loginState: LoginState,
    val gameServerData: GameServerData? = null,
    val nodes: List<GameServerNode> = emptyList(),
)

private val SessionTokenCodec = bytesPrefixed(U8)
private val LocalAddressCodec = bytesPrefixed(U8)

object GameServerNodesPacketCodec : PacketCodec<GameServerNodesPacket>() {
  override fun CodecScope<GameServerNodesPacket>.body(): GameServerNodesPacket {
    val stateId = field(U8) { it.loginState.id }
    val loginState = LoginState.entries.find { it.id == stateId } ?: LoginState.SYSTEM_ERROR
    if (loginState != LoginState.AUTHED) return GameServerNodesPacket(loginState)

    val userId =
        field(S32LE) {
          val gsd =
              it.gameServerData
                  ?: throw MalformedPacketException(
                      "GameServerData must be provided for AUTHED state")
          gsd.userId
        }
    val sessionToken = field(SessionTokenCodec) { it.gameServerData!!.sessionToken }
    val gameServerId = field(U8) { it.gameServerData!!.gameServerId.toInt() }.toUByte()
    val localBytes =
        field(LocalAddressCodec) { it.gameServerData!!.localAddress.toBigEndianArray() }
    val localAddress = IPAddress.of(localBytes)
    val localHostname = field(Utf16LeNullTerminated) { it.gameServerData!!.localHostname }
    val port = field(S32LE) { it.gameServerData!!.port.toInt() }.toUShort()

    val nodeCount = field(U8) { it.nodes.size }
    val nodes = ArrayList<GameServerNode>(nodeCount)
    repeat(nodeCount) { i ->
      field(U8) { i }
      val ipv4 =
          field(TaggedIpCodec) { it.nodes[i].iPv4Address } as? IPv4Address
              ?: throw MalformedPacketException("Expected IPv4 address for game server node")
      val ipv6 =
          field(TaggedIpCodec) { it.nodes[i].iPv6Address } as? IPv6Address
              ?: throw MalformedPacketException("Expected IPv6 address for game server node")
      val nodePort = field(S16LE) { it.nodes[i].port.toShort() }.toUShort()
      val weight = field(U8) { it.nodes[i].weight.toInt() }.toUByte()
      nodes += GameServerNode(ipv4, ipv6, nodePort, weight)
    }
    return GameServerNodesPacket(
        LoginState.AUTHED,
        GameServerData(gameServerId, userId, sessionToken, localAddress, localHostname, port),
        nodes,
    )
  }
}
