package de.fiereu.openmmo.server.game.db

import de.fiereu.openmmo.server.game.config.GameServerConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource
import org.flywaydb.core.Flyway

private val log = KotlinLogging.logger {}

@Singleton
class DatabaseBootstrap
@Inject
constructor(
    private val dataSource: DataSource,
    private val config: GameServerConfig,
) {

  fun migrate() {
    val locations = buildList {
      add("classpath:db/migration")
      if (config.db.seedDev) add("classpath:db/dev")
    }
    val result =
        Flyway.configure()
            .dataSource(dataSource)
            .locations(*locations.toTypedArray())
            .load()
            .migrate()
    log.info { "Applied ${result.migrationsExecuted} database migrations" }
  }
}
