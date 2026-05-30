package de.fiereu.network.handlers

import de.fiereu.network.cipher.SessionCipher
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class CipherEncoder(@Volatile var cipher: SessionCipher) : MessageToByteEncoder<ByteBuf>() {

  override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
    cipher.encrypt(msg, out)
  }
}
