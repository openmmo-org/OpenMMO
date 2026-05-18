package de.fiereu.openmmo.server.login.auth

import de.fiereu.openmmo.common.enums.LoginState
import java.util.concurrent.ConcurrentHashMap

private val log = io.github.oshai.kotlinlogging.KotlinLogging.logger {}

interface UserService {
  data class AuthResult(val state: LoginState, val userId: Int? = null)

  fun authenticate(username: String, password: String): AuthResult

  fun getUserId(username: String): Int?
}

class InMemoryUserStore : UserService {
  private val users = ConcurrentHashMap<String, UserInfo>()
  private val nextId = java.util.concurrent.atomic.AtomicInteger(1)

  data class UserInfo(val id: Int, val passwordHash: String, val username: String)

  init {
    addUser("admin", "admin")
    addUser("test", "test")
  }

  /**
   * PokeMMO client sends a transformed password hash. We store the value the client sends and
   * compare directly. For initial setup, we compute SHA-1 as a best guess.
   */
  fun addUser(username: String, password: String): Int {
    val id = nextId.getAndIncrement()
    val sha1 = java.security.MessageDigest.getInstance("SHA-1")
    val hashBytes = sha1.digest(password.toByteArray())
    val hex = hashBytes.joinToString("") { "%02x".format(it) }
    users[username.lowercase()] = UserInfo(id, hex, username)
    return id
  }

  override fun authenticate(username: String, password: String): UserService.AuthResult {
    val user =
        users[username.lowercase()] ?: return UserService.AuthResult(LoginState.INVALID_PASSWORD)
    if (user.passwordHash != password) {
      return UserService.AuthResult(LoginState.INVALID_PASSWORD)
    }
    return UserService.AuthResult(LoginState.AUTHED, user.id)
  }

  override fun getUserId(username: String): Int? {
    return users[username.lowercase()]?.id
  }
}
