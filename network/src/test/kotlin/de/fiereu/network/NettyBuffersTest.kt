package de.fiereu.network

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.Unpooled

class NettyBuffersTest :
    FunSpec({
      test("NettyReadBuffer matches ByteBuf state") {
        val buf = Unpooled.wrappedBuffer(byteArrayOf(1, 2, 3, 4))
        val read = NettyReadBuffer(buf)
        read.readByte() shouldBe 1
        read.remaining() shouldBe 3
        val dst = ByteArray(2)
        read.readBytes(dst)
        dst shouldBe byteArrayOf(2, 3)
        read.skip(1)
        read.remaining() shouldBe 0
      }

      test("NettyWriteBuffer writes through to ByteBuf") {
        val buf = Unpooled.buffer()
        val write = NettyWriteBuffer(buf)
        write.writeByte(0xAB.toByte())
        write.writeBytes(byteArrayOf(0xCD.toByte(), 0xEF.toByte()))
        val read = ByteArray(3)
        buf.readBytes(read)
        read shouldBe byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte())
      }
    })
