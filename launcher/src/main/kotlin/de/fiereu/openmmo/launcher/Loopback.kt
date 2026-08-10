package de.fiereu.openmmo.launcher

const val LOOPBACK = "127.0.0.1"

private val GROUPS = listOf("0", "0", "0", "0", "0", "ffff", "7f00", "1")
private const val MAX_GROUP_LENGTH = 4

object Loopback {

  val MIN_WIDTH = GROUPS.sumOf { it.length } + GROUPS.size + 1

  val MAX_WIDTH = GROUPS.size * MAX_GROUP_LENGTH + GROUPS.size + 1

  /**
   * Returns 127.0.0.1 as an IPv4 mapped IPv6 literal of exactly [width] chars.
   *
   * Hostnames cannot be padded because Java rejects NUL and whitespace in them, but IPv6 groups may
   * carry leading zeros.
   */
  fun literal(width: Int): String {
    require(width in MIN_WIDTH..MAX_WIDTH) {
      "Cannot write the loopback address with $width chars, only $MIN_WIDTH to $MAX_WIDTH fit"
    }
    var padding = width - MIN_WIDTH
    val groups =
        GROUPS.map { group ->
          val added = minOf(padding, MAX_GROUP_LENGTH - group.length)
          padding -= added
          group.padStart(group.length + added, '0')
        }
    return groups.joinToString(separator = ":", prefix = "[", postfix = "]")
  }

  /**
   * Returns an origin of exactly [width] chars pointing at [port] on the loopback address.
   *
   * Only the scheme and host of a url are replaced, so the leftover width is padded with slashes
   * that the path absorbs. A mirror whose name is too short to hold the port gets port 1 instead,
   * which fails fast and lets the client move on to the next one.
   */
  fun origin(port: Int, width: Int): String {
    val origin = "https://$LOOPBACK:$port"
    val fitting = if (origin.length <= width) origin else "https://$LOOPBACK:1"
    require(fitting.length <= width) { "Cannot redirect an origin of $width chars" }
    return fitting.padEnd(width, '/')
  }
}
