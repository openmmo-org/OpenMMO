package de.fiereu.network.cipher

import io.netty.buffer.ByteBuf

interface SessionCipher {
  fun encrypt(input: ByteBuf, output: ByteBuf)

  fun decrypt(input: ByteBuf, output: ByteBuf)
}

object NoOpSessionCipher : SessionCipher {
  override fun encrypt(input: ByteBuf, output: ByteBuf) {
    output.writeBytes(input)
  }

  override fun decrypt(input: ByteBuf, output: ByteBuf) {
    output.writeBytes(input)
  }
}
