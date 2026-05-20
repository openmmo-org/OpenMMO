package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.server.netty.Compressor
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext

class PacketSender(private val compressor: Compressor) {

  fun sendRaw(ctx: ChannelHandlerContext, opcode: UByte, data: ByteArray) {
    val buffer = ctx.alloc().buffer()
    buffer.writeByte(opcode.toInt())
    val dataBuf = Unpooled.wrappedBuffer(data)
    buffer.writeBytes(compressor.compress(dataBuf))
    ctx.write(buffer)
  }

  fun sendRawAndFlush(ctx: ChannelHandlerContext, opcode: UByte, data: ByteArray) {
    val buffer = ctx.alloc().buffer()
    buffer.writeByte(opcode.toInt())
    val dataBuf = Unpooled.wrappedBuffer(data)
    buffer.writeBytes(compressor.compress(dataBuf))
    ctx.writeAndFlush(buffer)
  }
}
