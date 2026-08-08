package de.fiereu.openmmo.common.auth

import java.nio.ByteBuffer
import java.security.MessageDigest
import java.time.Clock
import java.time.Duration
import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/** Opaque token issued by the login server and validated by the game server. */
data class SessionToken(
    val userId: Long,
    val issuedAt: Instant,
    val bytes: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is SessionToken &&
          userId == other.userId &&
          issuedAt == other.issuedAt &&
          bytes.contentEquals(other.bytes)

  override fun hashCode(): Int =
      (userId.hashCode() * 31 + issuedAt.hashCode()) * 31 + bytes.contentHashCode()
}

class SessionTokenIssuer(secret: ByteArray, private val clock: Clock = Clock.systemUTC()) {
  init {
    require(secret.isNotEmpty()) { "session token secret must not be empty" }
  }

  private val secretKey = SecretKeySpec(secret, MAC_ALGORITHM)

  fun issue(userId: Long): SessionToken {
    val issuedAt = clock.instant()
    val bytes = build(userId, issuedAt.epochSecond, secretKey)
    return SessionToken(userId, issuedAt, bytes)
  }
}

class SessionTokenVerifier(
    secret: ByteArray,
    private val maxAge: Duration = DEFAULT_MAX_AGE,
    private val clock: Clock = Clock.systemUTC(),
) {
  init {
    require(secret.isNotEmpty()) { "session token secret must not be empty" }
    require(!maxAge.isNegative && !maxAge.isZero) { "session token maxAge must be positive" }
  }

  private val secretKey = SecretKeySpec(secret, MAC_ALGORITHM)

  fun verify(bytes: ByteArray): SessionToken? {
    if (bytes.size != TOKEN_SIZE) return null
    val buf = ByteBuffer.wrap(bytes)
    val userId = buf.long
    val epochSeconds = buf.long
    // Authenticate before building an Instant, which throws on an out of range value.
    val expected = build(userId, epochSeconds, secretKey)
    if (!MessageDigest.isEqual(bytes, expected)) return null
    val issuedAt = Instant.ofEpochSecond(epochSeconds)
    if (isExpired(issuedAt)) return null
    return SessionToken(userId, issuedAt, bytes)
  }

  private fun isExpired(issuedAt: Instant): Boolean {
    val now = clock.instant()
    if (issuedAt.isAfter(now.plus(CLOCK_SKEW_LEEWAY))) return true
    return issuedAt.isBefore(now.minus(maxAge))
  }
}

private const val MAC_ALGORITHM = "HmacSHA256"
private const val PREFIX_SIZE = 16
private const val MAC_SIZE = 16
private const val TOKEN_SIZE = PREFIX_SIZE + MAC_SIZE

private val DEFAULT_MAX_AGE: Duration = Duration.ofMinutes(5)

// Tolerates drift between the two servers' clocks. A token dated further ahead than this would
// outlive maxAge by the difference.
private val CLOCK_SKEW_LEEWAY: Duration = Duration.ofSeconds(30)

private fun build(userId: Long, epochSecond: Long, key: SecretKeySpec): ByteArray {
  val out = ByteArray(TOKEN_SIZE)
  val prefix = ByteBuffer.wrap(out, 0, PREFIX_SIZE)
  prefix.putLong(userId)
  prefix.putLong(epochSecond)
  val mac = Mac.getInstance(MAC_ALGORITHM)
  mac.init(key)
  mac.update(out, 0, PREFIX_SIZE)
  val tag = mac.doFinal()
  System.arraycopy(tag, 0, out, PREFIX_SIZE, MAC_SIZE)
  return out
}
