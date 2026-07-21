package de.fiereu.openmmo.server.login.auth

import de.fiereu.openmmo.common.enums.LoginState
import de.fiereu.openmmo.db.login.tables.references.USERS
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

@Singleton
class JooqUserStore
@Inject
constructor(
    private val dsl: DSLContext,
    @param:Named("db") private val dispatcher: CoroutineDispatcher,
) : UserService {

  override suspend fun authenticate(username: String, password: String): UserService.AuthResult =
      withContext(dispatcher) {
        val user = dsl.selectFrom(USERS).where(USERS.USERNAME.eq(username.lowercase())).fetchOne()
        if (user == null || user.passwordHash != password) {
          UserService.AuthResult(LoginState.INVALID_PASSWORD)
        } else {
          UserService.AuthResult(LoginState.AUTHED, user.id)
        }
      }

  override suspend fun getUserId(username: String): Int? =
      withContext(dispatcher) {
        dsl.select(USERS.ID)
            .from(USERS)
            .where(USERS.USERNAME.eq(username.lowercase()))
            .fetchOne(USERS.ID)
      }

  suspend fun addUser(username: String, password: String): Int =
      withContext(dispatcher) {
        dsl.insertInto(USERS)
            .set(USERS.USERNAME, username.lowercase())
            .set(USERS.DISPLAY_NAME, username)
            .set(USERS.PASSWORD_HASH, sha1Hex(password))
            .returning(USERS.ID)
            .fetchSingle()
            .id!!
      }
}
