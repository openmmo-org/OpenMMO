package de.fiereu.openmmo.server.game.config

data class GameServerConfig(
    val host: String,
    val port: Int,
    val checksumSize: Int,
    val rootKeyResource: String,
    val sessionSecret: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is GameServerConfig &&
          host == other.host &&
          port == other.port &&
          checksumSize == other.checksumSize &&
          rootKeyResource == other.rootKeyResource &&
          sessionSecret.contentEquals(other.sessionSecret)

  override fun hashCode(): Int {
    var h = host.hashCode()
    h = h * 31 + port
    h = h * 31 + checksumSize
    h = h * 31 + rootKeyResource.hashCode()
    h = h * 31 + sessionSecret.contentHashCode()
    return h
  }
}
