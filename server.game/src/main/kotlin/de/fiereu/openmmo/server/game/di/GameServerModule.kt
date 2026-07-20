package de.fiereu.openmmo.server.game.di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dagger.Module
import dagger.Provides
import de.fiereu.openmmo.common.auth.SessionTokenVerifier
import de.fiereu.openmmo.common.io.PemKeyLoader
import de.fiereu.openmmo.common.io.resource
import de.fiereu.openmmo.server.game.config.GameServerConfig
import de.fiereu.openmmo.server.game.storage.CharacterRepository
import de.fiereu.openmmo.server.game.storage.JooqCharacterRepository
import de.fiereu.openmmo.server.game.world.interest.InterestPolicy
import de.fiereu.openmmo.server.game.world.interest.PassThroughInterestPolicy
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

  @Provides @Singleton fun interestPolicy(impl: PassThroughInterestPolicy): InterestPolicy = impl

  @Provides
  @Singleton
  fun characterRepository(impl: JooqCharacterRepository): CharacterRepository = impl

  // Never touch the database while building the Dagger graph. The explicit
  // migrate() call in main() is the fail-fast connection check.
  @Provides
  @Singleton
  fun dataSource(config: GameServerConfig): DataSource =
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
  fun dbDispatcher(config: GameServerConfig): CoroutineDispatcher =
      Dispatchers.IO.limitedParallelism(config.db.poolSize)
}
