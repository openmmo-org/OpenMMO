package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class GameServer(
    val id: UByte,
    val name: String,
    val currentPlayers: UShort = 0u,
    val maxPlayers: UShort = 0u,
    val joinable: Boolean,
)

data class GameServerListPacket(val gameServers: List<GameServer>)

object GameServerCodec : PacketCodec<GameServer>() {
  override fun CodecScope<GameServer>.body(): GameServer {
    val id = field(U8) { it.id.toInt() }.toUByte()
    val name = field(Utf16LeNullTerminated) { it.name }
    val currentPlayers = field(U16LE) { it.currentPlayers.toInt() }.toUShort()
    val maxPlayers = field(U16LE) { it.maxPlayers.toInt() }.toUShort()
    val joinable = field(Bool) { it.joinable }
    return GameServer(id, name, currentPlayers, maxPlayers, joinable)
  }
}

object GameServerListPacketCodec : PacketCodec<GameServerListPacket>() {
  override fun CodecScope<GameServerListPacket>.body(): GameServerListPacket {
    val count =
        field(U8) {
          require(it.gameServers.size <= Byte.MAX_VALUE) { "Too many game servers" }
          it.gameServers.size
        }
    if (count == 0) {
      field(U8) { 0 }
      field(U8) { 0 }
      return GameServerListPacket(emptyList())
    }
    field(U8) { it.gameServers.first().id.toInt() }
    val list = ArrayList<GameServer>(count)
    repeat(count) { i -> list += field(GameServerCodec) { it.gameServers[i] } }
    return GameServerListPacket(list)
  }
}
