package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class NetAddress(
    val tag: Int,
    val handle: Int?,
    val idHigh: Long?,
    val idLow: Long?,
)

data class GameServerEndpoint(
    val publicAddress: NetAddress,
    val privateAddress: NetAddress,
    val port: Short,
    val flags: Byte,
)

data class ServerEndpointListPacket(
    val digest: ByteArray,
    val endpoints: List<GameServerEndpoint>,
) {
  override fun equals(other: Any?): Boolean =
      other is ServerEndpointListPacket &&
          digest.contentEquals(other.digest) &&
          endpoints == other.endpoints

  override fun hashCode(): Int = digest.contentHashCode() * 31 + endpoints.hashCode()
}

private val NetAddressCodec: Codec<NetAddress> =
    object : PacketCodec<NetAddress>() {
      override fun CodecScope<NetAddress>.body(): NetAddress {
        val tag = field(U8) { it.tag }
        return if (tag == 4) {
          val handle = field(S32LE) { it.handle!! }
          NetAddress(tag, handle, null, null)
        } else {
          val idHigh = field(S64LE) { it.idHigh!! }
          val idLow = field(S64LE) { it.idLow!! }
          NetAddress(tag, null, idHigh, idLow)
        }
      }
    }

private val GameServerEndpointCodec: Codec<GameServerEndpoint> =
    object : PacketCodec<GameServerEndpoint>() {
      override fun CodecScope<GameServerEndpoint>.body(): GameServerEndpoint {
        field(S8) { 0 }
        val publicAddress = field(NetAddressCodec) { it.publicAddress }
        val privateAddress = field(NetAddressCodec) { it.privateAddress }
        val port = field(S16LE) { it.port }
        val flags = field(S8) { it.flags }
        return GameServerEndpoint(publicAddress, privateAddress, port, flags)
      }
    }

object ServerEndpointListPacketCodec : PacketCodec<ServerEndpointListPacket>() {
  override fun CodecScope<ServerEndpointListPacket>.body(): ServerEndpointListPacket {
    val digest = field(bytesPrefixed(U8)) { it.digest }
    val endpoints = field(GameServerEndpointCodec.listPrefixed(U8)) { it.endpoints }
    return ServerEndpointListPacket(digest, endpoints)
  }
}
