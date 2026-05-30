package de.fiereu.openmmo.server.login.auth

import de.fiereu.openmmo.common.enums.LoginState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class InMemoryUserStoreTest :
    FunSpec({
      test("seeds the admin and test users") {
        val store = InMemoryUserStore()
        store.getUserId("admin") shouldNotBe null
        store.getUserId("test") shouldNotBe null
      }

      test("authenticate succeeds when password hash matches SHA-1 hex") {
        val store = InMemoryUserStore()
        val sha1Hex = sha1HexOf("admin")
        val result = store.authenticate("admin", sha1Hex)
        result.state shouldBe LoginState.AUTHED
        result.userId shouldBe store.getUserId("admin")
      }

      test("authenticate is case-insensitive on username") {
        val store = InMemoryUserStore()
        val sha1Hex = sha1HexOf("test")
        val result = store.authenticate("TEST", sha1Hex)
        result.state shouldBe LoginState.AUTHED
      }

      test("authenticate fails for unknown user") {
        val store = InMemoryUserStore()
        store.authenticate("nobody", "whatever").state shouldBe LoginState.INVALID_PASSWORD
      }

      test("authenticate fails for wrong password") {
        val store = InMemoryUserStore()
        store.authenticate("admin", "wrong").state shouldBe LoginState.INVALID_PASSWORD
      }

      test("addUser assigns ascending ids") {
        val store = InMemoryUserStore()
        val a = store.addUser("alice", "pw")
        val b = store.addUser("bob", "pw")
        (b > a) shouldBe true
      }
    })

private fun sha1HexOf(input: String): String {
  val digest = java.security.MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
  return digest.joinToString("") { "%02x".format(it) }
}
