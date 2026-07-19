package de.fiereu.network

import de.fiereu.network.handlers.CompressionDecoder
import de.fiereu.network.handlers.CompressionEncoder
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel

private fun frame(opcode: Int, payload: ByteArray): ByteBuf =
    Unpooled.buffer().apply {
      writeByte(opcode)
      writeBytes(payload)
    }

private fun body(buf: ByteBuf): ByteArray {
  val bytes = ByteArray(buf.readableBytes())
  buf.readBytes(bytes)
  return bytes.copyOfRange(1, bytes.size)
}

class CompressionRoundtripTest :
    FunSpec({
      test("compressed payload above threshold round-trips byte for byte") {
        val payload = ByteArray(300) { (it * 7).toByte() }
        val enc = EmbeddedChannel(CompressionEncoder(threshold = 256))
        val dec = EmbeddedChannel(CompressionDecoder())

        enc.writeOutbound(frame(0x13, payload))
        dec.writeInbound(enc.readOutbound<ByteBuf>())

        body(dec.readInbound()) shouldBe payload
      }

      test("two compressed packets share one continuous deflate stream") {
        val first = ByteArray(300) { (it * 3).toByte() }
        val second = ByteArray(300) { (it * 11 + 5).toByte() }
        val enc = EmbeddedChannel(CompressionEncoder(threshold = 256))
        val dec = EmbeddedChannel(CompressionDecoder())

        enc.writeOutbound(frame(0x13, first))
        enc.writeOutbound(frame(0x13, second))
        dec.writeInbound(enc.readOutbound<ByteBuf>())
        dec.writeInbound(enc.readOutbound<ByteBuf>())

        body(dec.readInbound()) shouldBe first
        body(dec.readInbound()) shouldBe second
      }
    })
