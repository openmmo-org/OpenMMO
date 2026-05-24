package de.fiereu.bytecodec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer

class BuffersTest :
    FunSpec({
      test("ByteArrayReadBuffer sequential read") {
        val buf = ByteArrayReadBuffer(byteArrayOf(1, 2, 3, 4))
        buf.readByte() shouldBe 1
        buf.remaining() shouldBe 3
        val dst = ByteArray(2)
        buf.readBytes(dst)
        dst shouldBe byteArrayOf(2, 3)
        buf.skip(1)
        buf.remaining() shouldBe 0
        shouldThrow<IndexOutOfBoundsException> { buf.readByte() }
      }

      test("ByteArrayReadBuffer respects offset and length") {
        val buf = ByteArrayReadBuffer(byteArrayOf(9, 1, 2, 3, 9), offset = 1, length = 3)
        buf.remaining() shouldBe 3
        val arr = ByteArray(3)
        buf.readBytes(arr)
        arr shouldBe byteArrayOf(1, 2, 3)
      }

      test("GrowableWriteBuffer grows on demand") {
        val buf = GrowableWriteBuffer(initialCapacity = 2)
        repeat(10) { buf.writeByte(it.toByte()) }
        buf.size() shouldBe 10
        buf.toByteArray() shouldBe byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
      }

      test("NioReadBuffer / NioWriteBuffer interoperate") {
        val nio = ByteBuffer.allocate(8)
        val write = NioWriteBuffer(nio)
        write.writeByte(0x11)
        write.writeBytes(byteArrayOf(0x22, 0x33))
        nio.flip()
        val read = NioReadBuffer(nio)
        read.readByte() shouldBe 0x11
        val arr = ByteArray(2)
        read.readBytes(arr)
        arr shouldBe byteArrayOf(0x22, 0x33)
        read.remaining() shouldBe 0
      }
    })
