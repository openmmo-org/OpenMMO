package de.fiereu.openmmo.common.auth

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset

class SessionTokenTest :
    FunSpec({
      val secret = ByteArray(32) { it.toByte() }

      test("issuer + verifier roundtrip recovers userId and issuedAt") {
        val fixed = Clock.fixed(Instant.ofEpochSecond(1_700_000_000), ZoneOffset.UTC)
        val issuer = SessionTokenIssuer(secret, fixed)
        val token = issuer.issue(userId = 42L)
        val verified = SessionTokenVerifier(secret, clock = fixed).verify(token.bytes)
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

      test("token is accepted right up to its max age") {
        val issuedAt = Instant.ofEpochSecond(1_700_000_000)
        val token = SessionTokenIssuer(secret, Clock.fixed(issuedAt, ZoneOffset.UTC)).issue(7L)
        val atLimit = Clock.fixed(issuedAt.plus(Duration.ofMinutes(5)), ZoneOffset.UTC)

        val verified =
            SessionTokenVerifier(secret, Duration.ofMinutes(5), atLimit).verify(token.bytes)

        verified.shouldNotBeNull()
        verified.userId shouldBe 7L
      }

      test("token past its max age is rejected") {
        val issuedAt = Instant.ofEpochSecond(1_700_000_000)
        val token = SessionTokenIssuer(secret, Clock.fixed(issuedAt, ZoneOffset.UTC)).issue(7L)
        val tooLate = Clock.fixed(issuedAt.plusSeconds(301), ZoneOffset.UTC)

        SessionTokenVerifier(secret, Duration.ofMinutes(5), tooLate).verify(token.bytes) shouldBe
            null
      }

      test("max age is configurable independently of the default") {
        val issuedAt = Instant.ofEpochSecond(1_700_000_000)
        val token = SessionTokenIssuer(secret, Clock.fixed(issuedAt, ZoneOffset.UTC)).issue(7L)
        val later = Clock.fixed(issuedAt.plusSeconds(60), ZoneOffset.UTC)

        SessionTokenVerifier(secret, Duration.ofSeconds(30), later).verify(token.bytes) shouldBe
            null
        SessionTokenVerifier(secret, Duration.ofHours(1), later)
            .verify(token.bytes)
            .shouldNotBeNull()
      }

      test("token dated beyond the skew leeway is rejected") {
        val now = Instant.ofEpochSecond(1_700_000_000)
        val future = Clock.fixed(now.plusSeconds(120), ZoneOffset.UTC)
        val token = SessionTokenIssuer(secret, future).issue(7L)

        SessionTokenVerifier(secret, Duration.ofMinutes(5), Clock.fixed(now, ZoneOffset.UTC))
            .verify(token.bytes) shouldBe null
      }

      test("token inside the skew leeway is accepted") {
        val now = Instant.ofEpochSecond(1_700_000_000)
        val slightlyAhead = Clock.fixed(now.plusSeconds(10), ZoneOffset.UTC)
        val token = SessionTokenIssuer(secret, slightlyAhead).issue(7L)

        SessionTokenVerifier(secret, Duration.ofMinutes(5), Clock.fixed(now, ZoneOffset.UTC))
            .verify(token.bytes)
            .shouldNotBeNull()
      }

      test("an unauthentic token with an out of range timestamp is rejected, not thrown") {
        val bytes = ByteArray(32)
        java.nio.ByteBuffer.wrap(bytes).putLong(1L).putLong(Long.MAX_VALUE)

        SessionTokenVerifier(secret).verify(bytes) shouldBe null
      }

      test("the skew leeway is thirty seconds") {
        val now = Instant.ofEpochSecond(1_700_000_000)
        val verifier =
            SessionTokenVerifier(secret, Duration.ofMinutes(5), Clock.fixed(now, ZoneOffset.UTC))
        fun issuedAhead(seconds: Long) =
            SessionTokenIssuer(secret, Clock.fixed(now.plusSeconds(seconds), ZoneOffset.UTC))
                .issue(7L)
                .bytes

        verifier.verify(issuedAhead(30)).shouldNotBeNull()
        verifier.verify(issuedAhead(31)) shouldBe null
      }

      test("non-positive max age rejected") {
        shouldThrow<IllegalArgumentException> { SessionTokenVerifier(secret, Duration.ZERO) }
        shouldThrow<IllegalArgumentException> {
          SessionTokenVerifier(secret, Duration.ofMinutes(-1))
        }
      }
    })
