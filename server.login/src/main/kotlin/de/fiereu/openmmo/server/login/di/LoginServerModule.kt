package de.fiereu.openmmo.server.login.di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.fiereu.openmmo.common.auth.SessionTokenIssuer
import de.fiereu.openmmo.common.io.PemKeyLoader
import de.fiereu.openmmo.common.io.resource
import de.fiereu.openmmo.server.login.auth.JooqUserStore
import de.fiereu.openmmo.server.login.auth.UserService
import de.fiereu.openmmo.server.login.config.LoginServerConfig
import io.netty.channel.EventLoopGroup
import io.netty.channel.MultiThreadIoEventLoopGroup
import io.netty.channel.nio.NioIoHandler
import java.security.interfaces.ECPrivateKey
import javax.inject.Named
import javax.inject.Singleton
import javax.sql.DataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

@Module
abstract class LoginServerModule {

  @Binds @Singleton abstract fun userService(impl: JooqUserStore): UserService

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

    // Never touch the database while building the Dagger graph. The explicit
    // migrate() call in main() is the fail-fast connection check.
    @Provides
    @Singleton
    fun dataSource(config: LoginServerConfig): DataSource =
        HikariDataSource(
            HikariConfig().apply {
              jdbcUrl = config.db.jdbcUrl
              username = config.db.user
              password = config.db.password
              maximumPoolSize = config.db.poolSize
              initializationFailTimeout = -1
            })

    @Provides
    @Singleton
    fun dslContext(dataSource: DataSource): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)

    @Provides
    @Singleton
    @Named("db")
    fun dbDispatcher(config: LoginServerConfig): CoroutineDispatcher =
        Dispatchers.IO.limitedParallelism(config.db.poolSize)
  }
}
