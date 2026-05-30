package de.fiereu.network.handlers

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel

class CompressionTest :
    FunSpec({
      test("payload below threshold preserves opcode and writes flag=0") {
        val enc = EmbeddedChannel(CompressionEncoder(threshold = 16))
        enc.writeOutbound(Unpooled.wrappedBuffer(byteArrayOf(0xAA.toByte(), 1, 2, 3, 4)))
        val out = enc.readOutbound<ByteBuf>()
        out.readableBytes() shouldBe 6
        out.readByte() shouldBe 0xAA.toByte()
        out.readByte() shouldBe 0
        val rest = ByteArray(4)
        out.readBytes(rest)
        rest shouldBe byteArrayOf(1, 2, 3, 4)
      }

      test("encoder + decoder roundtrip for raw payload") {
        val enc = EmbeddedChannel(CompressionEncoder(threshold = 16))
        enc.writeOutbound(Unpooled.wrappedBuffer(byteArrayOf(0xAA.toByte(), 1, 2, 3, 4)))
        val compressed = enc.readOutbound<ByteBuf>()
        val dec = EmbeddedChannel(CompressionDecoder())
        dec.writeInbound(compressed)
        val out = dec.readInbound<ByteBuf>()
        out.readableBytes() shouldBe 5
        out.readByte() shouldBe 0xAA.toByte()
        val rest = ByteArray(4)
        out.readBytes(rest)
        rest shouldBe byteArrayOf(1, 2, 3, 4)
      }

      test("encoder + decoder roundtrip for compressed payload") {
        val payload = ByteArray(512) { (it and 0x0F).toByte() }
        val enc = EmbeddedChannel(CompressionEncoder(threshold = 64))
        enc.writeOutbound(Unpooled.wrappedBuffer(byteArrayOf(0xBB.toByte()) + payload))
        val compressed = enc.readOutbound<ByteBuf>()
        compressed.getByte(0) shouldBe 0xBB.toByte()
        compressed.getByte(1) shouldBe 1
        val dec = EmbeddedChannel(CompressionDecoder())
        dec.writeInbound(compressed)
        val out = dec.readInbound<ByteBuf>()
        out.readByte() shouldBe 0xBB.toByte()
        val read = ByteArray(out.readableBytes())
        out.readBytes(read)
        read shouldBe payload
      }
    })
