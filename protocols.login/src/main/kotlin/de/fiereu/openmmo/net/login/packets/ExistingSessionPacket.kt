package de.fiereu.openmmo.net.login.packets

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.github.maltalex.ineter.base.IPv6Address
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.MalformedPacketException
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.bytesPrefixed
import de.fiereu.bytecodec.fixedBytes

/**
 * Tells a client that the account it just logged in with is still playing, and carries everything
 * needed to reach that session: its id and key, and the server hosting it.
 */
data class ExistingSessionPacket(
    val sessionId: Long,
    val sessionKey: ByteArray,
    val gameServerId: UByte,
    val serverName: String,
    val localAddress: IPAddress,
    val localHostname: String,
    val port: UShort,
    /** Five bytes between the port and the node count whose meaning is not established yet. */
    val unknown: ByteArray,
    val nodes: List<GameServerNode>,
) {
  override fun equals(other: Any?): Boolean =
      other is ExistingSessionPacket &&
          sessionId == other.sessionId &&
          sessionKey.contentEquals(other.sessionKey) &&
          gameServerId == other.gameServerId &&
          serverName == other.serverName &&
          localAddress == other.localAddress &&
          localHostname == other.localHostname &&
          port == other.port &&
          unknown.contentEquals(other.unknown) &&
          nodes == other.nodes

  override fun hashCode(): Int {
    var h = sessionId.hashCode()
    h = h * 31 + sessionKey.contentHashCode()
    h = h * 31 + gameServerId.hashCode()
    h = h * 31 + serverName.hashCode()
    h = h * 31 + localAddress.hashCode()
    h = h * 31 + localHostname.hashCode()
    h = h * 31 + port.hashCode()
    h = h * 31 + unknown.contentHashCode()
    h = h * 31 + nodes.hashCode()
    return h
  }
}

private val SessionKeyCodec = bytesPrefixed(U8)
private val LocalAddressCodec = bytesPrefixed(U8)
private val UnknownCodec = fixedBytes(5)

object ExistingSessionPacketCodec : PacketCodec<ExistingSessionPacket>() {
  override fun CodecScope<ExistingSessionPacket>.body(): ExistingSessionPacket {
    val sessionId = field(S64LE) { it.sessionId }
    val sessionKey = field(SessionKeyCodec) { it.sessionKey }
    val gameServerId = field(U8) { it.gameServerId.toInt() }.toUByte()
    val serverName = field(Utf16LeNullTerminated) { it.serverName }
    val localBytes = field(LocalAddressCodec) { it.localAddress.toBigEndianArray() }
    val localAddress = IPAddress.of(localBytes)
    val localHostname = field(Utf16LeNullTerminated) { it.localHostname }
    val port = field(S32LE) { it.port.toInt() }.toUShort()
    val unknown = field(UnknownCodec) { it.unknown }

    val nodeCount = field(U8) { it.nodes.size }
    val nodes = ArrayList<GameServerNode>(nodeCount)
    repeat(nodeCount) { i ->
      val id = field(U8) { it.nodes[i].id.toInt() }.toUByte()
      val ipv4 =
          field(TaggedIpCodec) { it.nodes[i].iPv4Address } as? IPv4Address
              ?: throw MalformedPacketException("Expected IPv4 address for game server node")
      val ipv6 =
          field(TaggedIpCodec) { it.nodes[i].iPv6Address } as? IPv6Address
              ?: throw MalformedPacketException("Expected IPv6 address for game server node")
      val nodePort = field(S16LE) { it.nodes[i].port.toShort() }.toUShort()
      val weight = field(U8) { it.nodes[i].weight.toInt() }.toUByte()
      nodes += GameServerNode(ipv4, ipv6, nodePort, weight, id)
    }
    return ExistingSessionPacket(
        sessionId,
        sessionKey,
        gameServerId,
        serverName,
        localAddress,
        localHostname,
        port,
        unknown,
        nodes,
    )
  }
}
