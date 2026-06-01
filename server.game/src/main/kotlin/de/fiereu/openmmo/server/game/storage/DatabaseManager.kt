package de.fiereu.openmmo.server.game.storage

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource
import org.flywaydb.core.Flyway

private val log = KotlinLogging.logger {}

@Singleton
class DatabaseManager @Inject constructor() {
  val dataSource: DataSource

  init {
    val dbUrl =
        System.getProperty("GAME_DB_URL")
            ?: "jdbc:postgresql://localhost:${System.getProperty("GAME_DB_PORT") ?: "20021"}/${System.getProperty("GAME_DB_NAME") ?: "openmmo_game_db"}"
    val dbUser = System.getProperty("GAME_DB_USER") ?: "openmmo_game_user"
    val dbPassword = System.getProperty("GAME_DB_PASSWORD") ?: "changeMe!"

    val config = HikariConfig()
    config.jdbcUrl = dbUrl
    config.username = dbUser
    config.password = dbPassword
    config.maximumPoolSize = 10
    config.minimumIdle = 2
    config.idleTimeout = 30000
    config.connectionTimeout = 10000
    config.poolName = "GameDB-Pool"

    dataSource = HikariDataSource(config)

    Flyway.configure()
        .dataSource(dataSource)
        .locations("db/migration")
        .load()
        .migrate()

    log.info { "DatabaseManager initialized, Flyway migrations applied" }
  }
}
