package de.fiereu.openmmo.server.login.auth

import de.fiereu.openmmo.server.login.config.LoginServerConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class AdminAccountBootstrap
@Inject
constructor(
    private val users: UserService,
    private val config: LoginServerConfig,
) {

  /**
   * Creates the configured account on a database that holds no user at all. It never touches an
   * existing one, so a changed password in the config does not reset the account it created.
   */
  suspend fun ensureAdmin() {
    val admin = config.admin ?: return
    if (users.hasAnyUser()) return
    val id = users.addUser(admin.username, admin.password)
    log.info { "Created '${admin.username}' as the first account, user $id" }
  }
}
