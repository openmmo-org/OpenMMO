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

      test("encoder + decoder roundtrip for real captured id=2 316-byte payload") {
        // Exact payload captured off the live game server for CharactersListPacket
        // (captures/server-s2c-capture.log, id=2 len=316) -- the packet CEO flagged as
        // implicated in the client's "invalid stored block lengths" DataFormatException.
        val payloadHex =
            "0100900100000000005400650073007400000000000100000000268f4e6a268f4e6a00000000" +
                "0000000000b80b00000000000000000800000000000000000000000000000000000000000000" +
                "000000000001330300040004000000000000000000000000000000ff0f000000000000000000" +
                "000000000000000000000000000000ff0f000000000000000000000000000000000000000000" +
                "0000000001285d6a8ee5a9ba1000000000000000000000000000000000000100000100010000" +
                "00000000000000000054006500730074000000420075006c0062006100730061007500720000" +
                "0000000519000000000000000000002100000000000000230000000000000000000000000000" +
                "00000000000000000000000000004a29a5140000000000000000000000278f4e6a0000000000" +
                "000000000000000000000000"
        val payload = payloadHex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        payload.size shouldBe 316
        val enc = EmbeddedChannel(CompressionEncoder(threshold = 256))
        enc.writeOutbound(Unpooled.wrappedBuffer(byteArrayOf(0x02.toByte()) + payload))
        val compressed = enc.readOutbound<ByteBuf>()
        compressed.getByte(0) shouldBe 0x02.toByte()
        compressed.getByte(1) shouldBe 1
        val dec = EmbeddedChannel(CompressionDecoder())
        dec.writeInbound(compressed)
        val out = dec.readInbound<ByteBuf>()
        out.readByte() shouldBe 0x02.toByte()
        val read = ByteArray(out.readableBytes())
        out.readBytes(read)
        read shouldBe payload
      }
    })
