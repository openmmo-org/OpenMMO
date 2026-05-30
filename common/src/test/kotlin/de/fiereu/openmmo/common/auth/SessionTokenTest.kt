package de.fiereu.openmmo.common.auth

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class SessionTokenTest :
    FunSpec({
      val secret = ByteArray(32) { it.toByte() }

      test("issuer + verifier roundtrip recovers userId and issuedAt") {
        val fixed = Clock.fixed(Instant.ofEpochSecond(1_700_000_000), ZoneOffset.UTC)
        val issuer = SessionTokenIssuer(secret, fixed)
        val token = issuer.issue(userId = 42L)
        val verified = SessionTokenVerifier(secret).verify(token.bytes)
        verified.shouldNotBeNull()
        verified.userId shouldBe 42L
        verified.issuedAt shouldBe Instant.ofEpochSecond(1_700_000_000)
      }

      test("tampered bytes fail verification") {
        val token = SessionTokenIssuer(secret).issue(userId = 1L)
        val mutated = token.bytes.copyOf()
        mutated[0] = (mutated[0] + 1).toByte()
        SessionTokenVerifier(secret).verify(mutated) shouldBe null
      }

      test("different secret fails verification") {
        val token = SessionTokenIssuer(secret).issue(userId = 1L)
        val other = ByteArray(32) { (it + 1).toByte() }
        SessionTokenVerifier(other).verify(token.bytes) shouldBe null
      }

      test("wrong length fails verification fast") {
        SessionTokenVerifier(secret).verify(ByteArray(0)) shouldBe null
        SessionTokenVerifier(secret).verify(ByteArray(31)) shouldBe null
        SessionTokenVerifier(secret).verify(ByteArray(33)) shouldBe null
      }

      test("empty secret rejected") {
        shouldThrow<IllegalArgumentException> { SessionTokenIssuer(ByteArray(0)) }
        shouldThrow<IllegalArgumentException> { SessionTokenVerifier(ByteArray(0)) }
      }
    })
