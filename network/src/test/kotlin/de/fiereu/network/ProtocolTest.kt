package de.fiereu.network

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private data class Hello(val n: Int)

private data class World(val n: Int)

private object HelloCodec : PacketCodec<Hello>() {
  override fun CodecScope<Hello>.body() = Hello(field(U16LE, Hello::n))
}

private object WorldCodec : PacketCodec<World>() {
  override fun CodecScope<World>.body() = World(field(U8, World::n))
}

private data class Beep(val n: Int)

private object BeepCodec : PacketCodec<Beep>() {
  override fun CodecScope<Beep>.body() = Beep(field(U16LE, Beep::n))
}

private object SampleProtocol : Protocol() {
  init {
    c2s<Hello>(0x10u, HelloCodec)
    s2c<World>(0x20u, WorldCodec)
    bidi<Beep>(0x30u, BeepCodec)
  }
}

class ProtocolTest :
    FunSpec({
      test("incoming lookup depends on side") {
        SampleProtocol.incomingCodec(Side.SERVER, 0x10u) shouldBe HelloCodec
        SampleProtocol.incomingCodec(Side.CLIENT, 0x10u) shouldBe null
        SampleProtocol.incomingCodec(Side.CLIENT, 0x20u) shouldBe WorldCodec
      }

      test("outgoing lookup depends on side") {
        SampleProtocol.outgoingRegistration(Side.CLIENT, Hello::class)?.opcode shouldBe 0x10u
        SampleProtocol.outgoingRegistration(Side.SERVER, World::class)?.opcode shouldBe 0x20u
      }

      test("bidi registers both directions") {
        SampleProtocol.incomingCodec(Side.SERVER, 0x30u) shouldBe BeepCodec
        SampleProtocol.incomingCodec(Side.CLIENT, 0x30u) shouldBe BeepCodec
        SampleProtocol.outgoingRegistration(Side.SERVER, Beep::class)?.opcode shouldBe 0x30u
        SampleProtocol.outgoingRegistration(Side.CLIENT, Beep::class)?.opcode shouldBe 0x30u
      }

      test("duplicate registration throws") {
        shouldThrow<IllegalStateException> {
          object : Protocol() {
            init {
              c2s<Hello>(0x01u, HelloCodec)
              c2s<Hello>(0x02u, HelloCodec)
            }
          }
        }
      }

      test("Direction.isIncomingFor") {
        Direction.C2S.isIncomingFor(Side.SERVER) shouldBe true
        Direction.C2S.isIncomingFor(Side.CLIENT) shouldBe false
        Direction.S2C.isIncomingFor(Side.CLIENT) shouldBe true
      }
    })
