package de.fiereu.network.handlers

import de.fiereu.network.ChecksumMismatchException
import de.fiereu.network.checksum.Crc16Checksum
import de.fiereu.network.checksum.NoOpChecksum
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel

private fun EmbeddedChannel.drainOutbound(): ByteBuf {
  val out = Unpooled.buffer()
  while (true) {
    val obj = readOutbound<Any>() ?: break
    if (obj is ByteBuf) {
      out.writeBytes(obj)
      obj.release()
    }
  }
  return out
}

private fun rootCause(t: Throwable): Throwable {
  var c: Throwable = t
  while (c.cause != null && c.cause !== c) c = c.cause!!
  return c
}

class FrameAndChecksumTest :
    FunSpec({
      test("PacketFrameEncoder writes LE length prefix including itself") {
        val channel = EmbeddedChannel(PacketFrameEncoder())
        channel.writeOutbound(
            Unpooled.wrappedBuffer(byteArrayOf(0xAA.toByte(), 0xBB.toByte(), 0xCC.toByte())))
        val out = channel.drainOutbound()
        out.readableBytes() shouldBe 5
        out.readUnsignedShortLE() shouldBe 5
        val rest = ByteArray(3)
        out.readBytes(rest)
        rest shouldBe byteArrayOf(0xAA.toByte(), 0xBB.toByte(), 0xCC.toByte())
      }

      test("PacketFrameDecoder strips the length prefix") {
        val encoder = EmbeddedChannel(PacketFrameEncoder())
        encoder.writeOutbound(Unpooled.wrappedBuffer(byteArrayOf(1, 2, 3, 4)))
        val framed = encoder.drainOutbound()

        val decoder = EmbeddedChannel(PacketFrameDecoder())
        decoder.writeInbound(framed)
        val out = decoder.readInbound<ByteBuf>()
        out.readableBytes() shouldBe 4
      }

      test("ChecksumFrameEncoder appends tag; decoder strips it") {
        val enc = EmbeddedChannel(ChecksumFrameEncoder(Crc16Checksum()))
        enc.writeOutbound(Unpooled.wrappedBuffer(byteArrayOf(1, 2, 3, 4)))
        val withTag = enc.drainOutbound()
        withTag.readableBytes() shouldBe 6

        val dec = EmbeddedChannel(ChecksumFrameDecoder(Crc16Checksum()))
        dec.writeInbound(withTag)
        val out = dec.readInbound<ByteBuf>()
        out.readableBytes() shouldBe 4
      }

      test("ChecksumFrameDecoder throws on mismatch") {
        val dec = EmbeddedChannel(ChecksumFrameDecoder(Crc16Checksum()))
        val thrown =
            shouldThrow<Throwable> {
              dec.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(1, 2, 3, 4, 0, 0)))
            }
        (rootCause(thrown) is ChecksumMismatchException) shouldBe true
      }

      test("NoOpChecksum decoder passes payload through") {
        val dec = EmbeddedChannel(ChecksumFrameDecoder(NoOpChecksum))
        dec.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(9, 9, 9)))
        val out = dec.readInbound<ByteBuf>()
        out.readableBytes() shouldBe 3
      }
    })
