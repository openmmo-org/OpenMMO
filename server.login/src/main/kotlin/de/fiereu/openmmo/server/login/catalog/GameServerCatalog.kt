package de.fiereu.openmmo.server.login.catalog

import com.github.maltalex.ineter.base.IPAddress
import de.fiereu.openmmo.net.login.packets.GameServer
import de.fiereu.openmmo.net.login.packets.GameServerNode
import de.fiereu.openmmo.server.login.config.GameServerEndpointConfig
import javax.inject.Inject
import javax.inject.Singleton

data class GameServerEntry(
    val server: GameServer,
    val node: GameServerNode,
    val localAddress: IPAddress,
    val localHostname: String,
)

@Singleton
class GameServerCatalog @Inject constructor(endpoint: GameServerEndpointConfig) {
  private val entries: List<GameServerEntry> =
      listOf(
          GameServerEntry(
              server =
                  GameServer(
                      id = 0x00u,
                      name = "OpenMMO",
                      currentPlayers = 0u,
                      maxPlayers = 1u,
                      joinable = true,
                  ),
              node =
                  GameServerNode(
                      iPv4Address = endpoint.ipv4Address,
                      iPv6Address = endpoint.ipv6Address,
                      port = endpoint.port.toUShort(),
                      weight = 0x01u,
                  ),
              localAddress = endpoint.localAddress,
              localHostname = endpoint.localHostname,
          ),
      )

  fun list(): List<GameServer> = entries.map { it.server }

  fun find(id: UByte): GameServerEntry? = entries.firstOrNull { it.server.id == id }
}
