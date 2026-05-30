package de.fiereu.network.checksum

import io.netty.buffer.ByteBuf

object NoOpChecksum : Checksum {
  override val size: Int = 0

  private val EMPTY = ByteArray(0)

  override fun calculate(data: ByteBuf): ByteArray = EMPTY

  override fun verify(data: ByteBuf, expected: ByteArray): Boolean = true
}
