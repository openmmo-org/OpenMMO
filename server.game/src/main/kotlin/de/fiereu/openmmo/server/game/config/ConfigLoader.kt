package de.fiereu.openmmo.server.game.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

private fun Config.stringOrNull(path: String): String? =
    if (hasPath(path)) getString(path) else null

object ConfigLoader {
  fun load(): GameServerConfig {
    val config = ConfigFactory.load()
    val secret = config.getString("server.sessionSecret")
    require(secret.isNotEmpty()) { "server.sessionSecret must not be empty" }
    val tokenMaxAge = config.getDuration("server.sessionTokenMaxAge")
    require(!tokenMaxAge.isNegative && !tokenMaxAge.isZero) {
      "server.sessionTokenMaxAge must be positive"
    }
    return GameServerConfig(
        host = config.getString("server.host"),
        port = config.getInt("server.port"),
        checksumSize = config.getInt("server.checksumSize"),
        rootKeyResource = config.getString("server.rootKeyResource"),
        rootKey = config.stringOrNull("server.rootKey"),
        rootKeyFile = config.stringOrNull("server.rootKeyFile"),
        sessionSecret = secret.toByteArray(Charsets.UTF_8),
        sessionTokenMaxAge = tokenMaxAge,
        db =
            DbConfig(
                host = config.getString("db.host"),
                port = config.getInt("db.port"),
                name = config.getString("db.name"),
                user = config.getString("db.user"),
                password = config.getString("db.password"),
                poolSize = config.getInt("db.poolSize"),
                seedDev = config.getBoolean("db.seedDev"),
            ),
    )
  }
}
