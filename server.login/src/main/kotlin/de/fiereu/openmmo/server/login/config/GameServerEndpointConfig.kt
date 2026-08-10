package de.fiereu.openmmo.server.login.config

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.github.maltalex.ineter.base.IPv6Address

/** Retail sends loopback for [localAddress] and [localHostname] whatever the nodes are. */
data class GameServerEndpointConfig(
    val ipv4Address: IPv4Address = IPv4Address.of("127.0.0.1"),
    val ipv6Address: IPv6Address = IPv6Address.of("::1"),
    val port: Int = 7777,
    val localAddress: IPAddress = IPv4Address.of("127.0.0.1"),
    val localHostname: String = "localhost",
) {
  init {
    require(port in 1..UShort.MAX_VALUE.toInt()) { "gameServer.port is out of range: $port" }
    require(localHostname.isNotBlank()) { "gameServer.localHostname must not be blank" }
  }
}
