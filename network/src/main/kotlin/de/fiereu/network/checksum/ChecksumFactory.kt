package de.fiereu.network.checksum

object ChecksumFactory {
  fun create(size: Int, key: ByteArray = ByteArray(0)): Checksum =
      when (size) {
        0 -> NoOpChecksum
        2 -> Crc16Checksum()
        in 4..32 -> HmacSha256Checksum(size, key)
        else -> throw IllegalArgumentException("Unsupported checksum size: $size")
      }
}
