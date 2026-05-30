package de.fiereu.openmmo.server.login.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import de.fiereu.openmmo.common.auth.SessionTokenIssuer
import de.fiereu.openmmo.common.io.PemKeyLoader
import de.fiereu.openmmo.common.io.resource
import de.fiereu.openmmo.server.login.auth.InMemoryUserStore
import de.fiereu.openmmo.server.login.auth.UserService
import de.fiereu.openmmo.server.login.config.LoginServerConfig
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
abstract class LoginServerModule {

  @Binds @Singleton abstract fun userService(impl: InMemoryUserStore): UserService

  companion object {
    @Provides
    @Singleton
    fun rootKey(config: LoginServerConfig): ECPrivateKey =
        PemKeyLoader.loadEcPrivate(resource(config.rootKeyResource))

    @Provides
    @Singleton
    fun tokenIssuer(config: LoginServerConfig): SessionTokenIssuer =
        SessionTokenIssuer(config.sessionSecret)

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
  }
}
