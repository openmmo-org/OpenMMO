package de.fiereu.openmmo.server.login.auth

import de.fiereu.openmmo.common.enums.LoginState
import de.fiereu.openmmo.server.login.config.AdminAccountConfig
import de.fiereu.openmmo.server.login.config.LoginServerConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.security.MessageDigest
import kotlinx.coroutines.test.runTest

private class EmptyUserStore : UserService {
  val added = mutableListOf<Pair<String, String>>()
  private var users = 0

  override suspend fun authenticate(username: String, password: String) =
      UserService.AuthResult(LoginState.INVALID_PASSWORD)

  override suspend fun getUserId(username: String): Int? = null

  override suspend fun findForToken(userId: Int): UserService.TokenUser? = null

  override suspend fun hasAnyUser(): Boolean = users > 0

  override suspend fun addUser(username: String, password: String): Int {
    added += username to password
    return ++users
  }
}

private fun config(admin: AdminAccountConfig?) =
    LoginServerConfig(
        host = "127.0.0.1",
        port = 2106,
        checksumSize = 16,
        rootKeyResource = "game.private.pem",
        sessionSecret = ByteArray(32),
        admin = admin,
    )

class AdminAccountBootstrapTest :
    FunSpec({
      test("creates the configured account when the database holds nobody") {
        runTest {
          val users = EmptyUserStore()

          AdminAccountBootstrap(users, config(AdminAccountConfig("root", "hunter2"))).ensureAdmin()

          users.added shouldBe listOf("root" to "hunter2")
        }
      }

      test("creates nobody when the config names nobody") {
        runTest {
          val users = EmptyUserStore()

          AdminAccountBootstrap(users, config(null)).ensureAdmin()

          users.added shouldBe emptyList()
        }
      }

      test("leaves a populated database alone, so a changed password is not a reset") {
        runTest {
          val users = EmptyUserStore()
          users.addUser("someone", "their-password")
          users.added.clear()

          AdminAccountBootstrap(users, config(AdminAccountConfig("root", "hunter2"))).ensureAdmin()

          users.added shouldBe emptyList()
        }
      }

      test("running twice adds one account, not two") {
        runTest {
          val users = EmptyUserStore()
          val bootstrap = AdminAccountBootstrap(users, config(AdminAccountConfig("root", "pw")))

          bootstrap.ensureAdmin()
          bootstrap.ensureAdmin()

          users.added shouldBe listOf("root" to "pw")
        }
      }

      test("the store hashes the password the way the client sends it") {
        runTest {
          val store = InMemoryUserStore()
          store.addUser("root", "hunter2")

          val sha1 =
              MessageDigest.getInstance("SHA-1").digest("hunter2".toByteArray()).joinToString("") {
                "%02x".format(it)
              }

          store.authenticate("root", sha1).state shouldBe LoginState.AUTHED
        }
      }

      test("a blank or oversized username is refused before it reaches the database") {
        runCatching { AdminAccountConfig("", "pw") }.isFailure shouldBe true
        runCatching { AdminAccountConfig("a".repeat(33), "pw") }.isFailure shouldBe true
        runCatching { AdminAccountConfig("root", " ") }.isFailure shouldBe true
      }

      test("the password stays out of the string form that would reach a log") {
        AdminAccountConfig("root", "hunter2").toString() shouldBe
            "AdminAccountConfig(username=root)"
      }
    })
