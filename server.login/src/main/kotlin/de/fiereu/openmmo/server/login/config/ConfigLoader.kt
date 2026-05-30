package de.fiereu.openmmo.server.login.config

import com.typesafe.config.ConfigFactory

object ConfigLoader {
  fun load(): LoginServerConfig {
    val config = ConfigFactory.load()
    val secret = config.getString("server.sessionSecret")
    require(secret.isNotEmpty()) { "server.sessionSecret must not be empty" }
    return LoginServerConfig(
        host = config.getString("server.host"),
        port = config.getInt("server.port"),
        checksumSize = config.getInt("server.checksumSize"),
        rootKeyResource = config.getString("server.rootKeyResource"),
        sessionSecret = secret.toByteArray(Charsets.UTF_8),
    )
  }
}
