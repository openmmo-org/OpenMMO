package de.fiereu.openmmo.common.auth

import java.nio.ByteBuffer
import java.security.MessageDigest
import java.time.Clock
import java.time.Duration
import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Long lived credential the client stores in place of a password. The epoch is the user's current
 * token generation, so raising it in the database invalidates every token already issued to them.
 */
data class RememberMeToken(
    val userId: Int,
    val epoch: Int,
    val issuedAt: Instant,
    val bytes: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is RememberMeToken &&
          userId == other.userId &&
          epoch == other.epoch &&
          issuedAt == other.issuedAt &&
          bytes.contentEquals(other.bytes)

  override fun hashCode(): Int {
    var h = userId
    h = h * 31 + epoch
    h = h * 31 + issuedAt.hashCode()
    h = h * 31 + bytes.contentHashCode()
    return h
  }
}

class RememberMeTokenIssuer(secret: ByteArray, private val clock: Clock = Clock.systemUTC()) {
  init {
    require(secret.isNotEmpty()) { "remember me token secret must not be empty" }
  }

  private val secretKey = SecretKeySpec(secret, REMEMBER_ME_MAC_ALGORITHM)

  fun issue(userId: Int, epoch: Int): RememberMeToken {
    val issuedAt = clock.instant()
    return RememberMeToken(
        userId, epoch, issuedAt, buildRememberMe(userId, epoch, issuedAt.epochSecond, secretKey))
  }
}

class RememberMeTokenVerifier(
    secret: ByteArray,
    private val maxAge: Duration,
    private val clock: Clock = Clock.systemUTC(),
) {
  init {
    require(secret.isNotEmpty()) { "remember me token secret must not be empty" }
    require(!maxAge.isNegative && !maxAge.isZero) { "remember me maxAge must be positive" }
  }

  private val secretKey = SecretKeySpec(secret, REMEMBER_ME_MAC_ALGORITHM)

  /** Checks the mac and the age. The caller still has to match [RememberMeToken.epoch]. */
  fun verify(bytes: ByteArray): RememberMeToken? {
    if (bytes.size != REMEMBER_ME_TOKEN_SIZE) return null
    val buf = ByteBuffer.wrap(bytes)
    val userId = buf.int
    val epoch = buf.int
    val epochSeconds = buf.long
    val expected = buildRememberMe(userId, epoch, epochSeconds, secretKey)
    if (!MessageDigest.isEqual(bytes, expected)) return null
    val issuedAt = Instant.ofEpochSecond(epochSeconds)
    val now = clock.instant()
    if (issuedAt.isAfter(now.plus(REMEMBER_ME_SKEW_LEEWAY))) return null
    if (issuedAt.isBefore(now.minus(maxAge))) return null
    return RememberMeToken(userId, epoch, issuedAt, bytes)
  }
}

private const val REMEMBER_ME_MAC_ALGORITHM = "HmacSHA256"
private const val REMEMBER_ME_PREFIX_SIZE = 16
private const val REMEMBER_ME_MAC_SIZE = 16
private const val REMEMBER_ME_TOKEN_SIZE = REMEMBER_ME_PREFIX_SIZE + REMEMBER_ME_MAC_SIZE

private val REMEMBER_ME_SKEW_LEEWAY: Duration = Duration.ofSeconds(30)

private fun buildRememberMe(
    userId: Int,
    epoch: Int,
    epochSecond: Long,
    key: SecretKeySpec,
): ByteArray {
  val out = ByteArray(REMEMBER_ME_TOKEN_SIZE)
  val prefix = ByteBuffer.wrap(out, 0, REMEMBER_ME_PREFIX_SIZE)
  prefix.putInt(userId)
  prefix.putInt(epoch)
  prefix.putLong(epochSecond)
  val mac = Mac.getInstance(REMEMBER_ME_MAC_ALGORITHM)
  mac.init(key)
  mac.update(out, 0, REMEMBER_ME_PREFIX_SIZE)
  System.arraycopy(mac.doFinal(), 0, out, REMEMBER_ME_PREFIX_SIZE, REMEMBER_ME_MAC_SIZE)
  return out
}
