package de.fiereu.openmmo.common.auth

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset

class RememberMeTokenTest :
    FunSpec({
      val secret = ByteArray(32) { it.toByte() }
      val issuedAt = Instant.ofEpochSecond(1_700_000_000)
      val at = { i: Instant -> Clock.fixed(i, ZoneOffset.UTC) }
      val thirtyDays = Duration.ofDays(30)

      test("a token round-trips its user, epoch and issue time") {
        val token = RememberMeTokenIssuer(secret, at(issuedAt)).issue(userId = 42, epoch = 7)

        val verified = RememberMeTokenVerifier(secret, thirtyDays, at(issuedAt)).verify(token.bytes)

        verified.shouldNotBeNull()
        verified.userId shouldBe 42
        verified.epoch shouldBe 7
        verified.issuedAt shouldBe issuedAt
      }

      test("the token is the 32 bytes the client stores") {
        RememberMeTokenIssuer(secret, at(issuedAt)).issue(1, 0).bytes.size shouldBe 32
      }

      test("tampered bytes fail verification") {
        val token = RememberMeTokenIssuer(secret, at(issuedAt)).issue(1, 0)
        val mutated = token.bytes.copyOf()
        mutated[0] = (mutated[0] + 1).toByte()

        RememberMeTokenVerifier(secret, thirtyDays, at(issuedAt)).verify(mutated) shouldBe null
      }

      test("a different secret fails verification") {
        val token = RememberMeTokenIssuer(secret, at(issuedAt)).issue(1, 0)
        val other = ByteArray(32) { (it + 1).toByte() }

        RememberMeTokenVerifier(other, thirtyDays, at(issuedAt)).verify(token.bytes) shouldBe null
      }

      test("the epoch is covered by the mac, so it cannot be edited in place") {
        val token = RememberMeTokenIssuer(secret, at(issuedAt)).issue(userId = 42, epoch = 7)
        val bumped = token.bytes.copyOf()
        // The epoch is the second int of the prefix.
        bumped[7] = (bumped[7] + 1).toByte()

        RememberMeTokenVerifier(secret, thirtyDays, at(issuedAt)).verify(bumped) shouldBe null
      }

      test("a token is accepted right up to the idle window and rejected past it") {
        val token = RememberMeTokenIssuer(secret, at(issuedAt)).issue(1, 0)
        val atLimit = at(issuedAt.plus(thirtyDays))
        val pastLimit = at(issuedAt.plus(thirtyDays).plusSeconds(1))

        RememberMeTokenVerifier(secret, thirtyDays, atLimit).verify(token.bytes).shouldNotBeNull()
        RememberMeTokenVerifier(secret, thirtyDays, pastLimit).verify(token.bytes) shouldBe null
      }

      test("a token dated beyond the skew leeway is rejected") {
        val ahead = RememberMeTokenIssuer(secret, at(issuedAt.plusSeconds(31))).issue(1, 0)

        RememberMeTokenVerifier(secret, thirtyDays, at(issuedAt)).verify(ahead.bytes) shouldBe null
      }

      test("an unauthentic token with an out of range timestamp is rejected, not thrown") {
        val bytes = ByteArray(32)
        java.nio.ByteBuffer.wrap(bytes).putInt(1).putInt(0).putLong(Long.MAX_VALUE)

        RememberMeTokenVerifier(secret, thirtyDays, at(issuedAt)).verify(bytes) shouldBe null
      }

      test("empty secret and non-positive max age are rejected") {
        shouldThrow<IllegalArgumentException> { RememberMeTokenIssuer(ByteArray(0)) }
        shouldThrow<IllegalArgumentException> { RememberMeTokenVerifier(ByteArray(0), thirtyDays) }
        shouldThrow<IllegalArgumentException> { RememberMeTokenVerifier(secret, Duration.ZERO) }
      }
    })
