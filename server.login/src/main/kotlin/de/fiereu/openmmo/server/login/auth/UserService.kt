package de.fiereu.openmmo.server.login.auth

import de.fiereu.openmmo.common.enums.LoginState
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

interface UserService {
  data class AuthResult(val state: LoginState, val userId: Int? = null)

  fun authenticate(username: String, password: String): AuthResult

  fun getUserId(username: String): Int?
}

@Singleton
class InMemoryUserStore @Inject constructor() : UserService {

  private data class UserInfo(val id: Int, val passwordHash: String, val username: String)

  private val users = ConcurrentHashMap<String, UserInfo>()
  private val nextId = AtomicInteger(1)

  init {
    addUser("admin", "admin")
    addUser("test", "test")
  }

  @Suppress("kotlin:S4790")
  fun addUser(username: String, password: String): Int {
    val id = nextId.getAndIncrement()
    val sha1 = MessageDigest.getInstance("SHA-1").digest(password.toByteArray())
    val hex = sha1.joinToString("") { "%02x".format(it) }
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

  override fun getUserId(username: String): Int? = users[username.lowercase()]?.id
}
