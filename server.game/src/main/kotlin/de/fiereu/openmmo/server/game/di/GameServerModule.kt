package de.fiereu.openmmo.server.game.di

import dagger.Module
import dagger.Provides
import de.fiereu.openmmo.common.auth.SessionTokenVerifier
import de.fiereu.openmmo.common.io.PemKeyLoader
import de.fiereu.openmmo.common.io.resource
import de.fiereu.openmmo.server.game.battle.BattleSessionClient
import de.fiereu.openmmo.server.game.config.GameServerConfig
import io.netty.channel.EventLoopGroup
import io.netty.channel.MultiThreadIoEventLoopGroup
import io.netty.channel.nio.NioIoHandler
import java.security.interfaces.ECPrivateKey
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
object GameServerModule {

  @Provides
  @Singleton
  fun rootKey(config: GameServerConfig): ECPrivateKey =
      PemKeyLoader.loadEcPrivate(resource(config.rootKeyResource))

  @Provides
  @Singleton
  fun tokenVerifier(config: GameServerConfig): SessionTokenVerifier =
      SessionTokenVerifier(config.sessionSecret)

  @Provides
  @Singleton
  @Named("boss")
  fun bossGroup(): EventLoopGroup = MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory())

  @Provides
  @Singleton
  @Named("worker")
  fun workerGroup(): EventLoopGroup = MultiThreadIoEventLoopGroup(0, NioIoHandler.newFactory())

  @Provides
  @Singleton
  fun coroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  /**
   * Client for the Battle-Service sidecar (Track A). Defaults to the sidecar's localhost bind;
   * override with BATTLE_SERVICE_HOST / BATTLE_SERVICE_PORT.
   */
  @Provides
  @Singleton
  fun battleSessionClient(): BattleSessionClient {
    val host = System.getenv("BATTLE_SERVICE_HOST") ?: "127.0.0.1"
    val port = System.getenv("BATTLE_SERVICE_PORT")?.toIntOrNull() ?: 7801
    return BattleSessionClient(host, port)
  }
}
