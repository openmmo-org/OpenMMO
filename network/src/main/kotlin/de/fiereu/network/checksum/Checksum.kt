package de.fiereu.network.checksum

import io.netty.buffer.ByteBuf

interface Checksum {
  val size: Int

  fun calculate(data: ByteBuf): ByteArray

  fun verify(data: ByteBuf, expected: ByteArray): Boolean
}
