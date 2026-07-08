package de.fiereu.network.handshake

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.fixedBytes
import de.fiereu.network.PipelineOptions
import de.fiereu.network.Protocol
import de.fiereu.network.ProtocolHandler
import de.fiereu.network.SessionIdentity
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.network.TypedProtocolHandler
import de.fiereu.network.bidi
import de.fiereu.network.installPipeline
import de.fiereu.network.internal.SESSION_KEY
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.ByteBuf
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.embedded.EmbeddedChannel
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

private data class Echo(val value: Int)

private object EchoCodec : PacketCodec<Echo>() {
  override fun CodecScope<Echo>.body() = Echo(field(U16LE, Echo::value))
}

private object EchoProtocol : Protocol() {
  init {
    bidi<Echo>(0x55u, EchoCodec)
  }
}

private class BigEcho(val marker: Int, val payload: ByteArray) {
  override fun equals(other: Any?) =
      other is BigEcho && marker == other.marker && payload.contentEquals(other.payload)

  override fun hashCode() = marker * 31 + payload.contentHashCode()
}

private object BigEchoCodec : PacketCodec<BigEcho>() {
  override fun CodecScope<BigEcho>.body() =
      BigEcho(field(U16LE, BigEcho::marker), field(fixedBytes(300), BigEcho::payload))
}

// Mirrors GameProtocol: the only protocol that turns compression on.
private object BigEchoProtocol : Protocol() {
  override val compressed = true

  init {
    bidi<BigEcho>(0x66u, BigEchoCodec)
  }
}

private class BigCollectingHandler(side: Side) :
    TypedProtocolHandler<BigEchoProtocol>(BigEchoProtocol, side) {
  val received = mutableListOf<BigEcho>()

  init {
    on<BigEcho> { event -> received += event.packet }
  }
}

private class CollectingHandler(side: Side) :
    TypedProtocolHandler<EchoProtocol>(EchoProtocol, side) {
  val received = mutableListOf<Echo>()

  init {
    on<Echo> { event -> received += event.packet }
  }
}

private fun drain(from: EmbeddedChannel, into: EmbeddedChannel) {
  while (true) {
    val obj = from.readOutbound<ByteBuf>() ?: break
    into.writeInbound(obj)
  }
}

class EndToEndHandshakeTest :
    FunSpec({
      test("server and client exchange a full handshake and an application packet") {
        val rootKeyPair = EcKeys.generateEphemeralKeyPair()
        val rootPrivate = rootKeyPair.private as ECPrivateKey
        val rootPublic = rootKeyPair.public as ECPublicKey

        val serverApp = CollectingHandler(Side.SERVER)
        val clientApp = CollectingHandler(Side.CLIENT)

        val options = PipelineOptions(checksumSize = 8)

        val serverChannel =
            EmbeddedChannel(
                object : ChannelInitializer<Channel>() {
                  override fun initChannel(ch: Channel) {
                    installPipeline(
                        pipeline = ch.pipeline(),
                        side = Side.SERVER,
                        identity = SessionIdentity.ServerRoot(rootPrivate),
                        applicationProtocol = EchoProtocol,
                        applicationHandlerFactory = { serverApp as ProtocolHandler },
                        options = options,
                    )
                  }
                },
            )

        val clientChannel =
            EmbeddedChannel(
                object : ChannelInitializer<Channel>() {
                  override fun initChannel(ch: Channel) {
                    installPipeline(
                        pipeline = ch.pipeline(),
                        side = Side.CLIENT,
                        identity = SessionIdentity.ClientTrust(rootPublic),
                        applicationProtocol = EchoProtocol,
                        applicationHandlerFactory = { clientApp as ProtocolHandler },
                        options = options,
                    )
                  }
                },
            )

        // Client sends ClientHello on channelActive (called during init for EmbeddedChannel)
        drain(clientChannel, serverChannel)
        // Server replies ServerHello
        drain(serverChannel, clientChannel)
        // Client replies ClientReady
        drain(clientChannel, serverChannel)

        serverChannel.attr(SESSION_KEY).get().phase shouldBe SessionPhase.ESTABLISHED
        clientChannel.attr(SESSION_KEY).get().phase shouldBe SessionPhase.ESTABLISHED

        // Client sends an application packet through the encrypted pipeline
        clientChannel.attr(SESSION_KEY).get().send(Echo(0xCAFE))
        drain(clientChannel, serverChannel)
        serverApp.received shouldBe listOf(Echo(0xCAFE))

        // Server responds
        serverChannel.attr(SESSION_KEY).get().send(Echo(0xBABE))
        drain(serverChannel, clientChannel)
        clientApp.received shouldBe listOf(Echo(0xBABE))
      }

      test(
          "server and client exchange a >256B application packet through the full compressed " +
              "pipeline (frame + checksum + cipher + compression)") {
            val rootKeyPair = EcKeys.generateEphemeralKeyPair()
            val rootPrivate = rootKeyPair.private as ECPrivateKey
            val rootPublic = rootKeyPair.public as ECPublicKey

            val serverApp = BigCollectingHandler(Side.SERVER)
            val clientApp = BigCollectingHandler(Side.CLIENT)

            val options = PipelineOptions(checksumSize = 8)

            val serverChannel =
                EmbeddedChannel(
                    object : ChannelInitializer<Channel>() {
                      override fun initChannel(ch: Channel) {
                        installPipeline(
                            pipeline = ch.pipeline(),
                            side = Side.SERVER,
                            identity = SessionIdentity.ServerRoot(rootPrivate),
                            applicationProtocol = BigEchoProtocol,
                            applicationHandlerFactory = { serverApp as ProtocolHandler },
                            options = options,
                        )
                      }
                    },
                )

            val clientChannel =
                EmbeddedChannel(
                    object : ChannelInitializer<Channel>() {
                      override fun initChannel(ch: Channel) {
                        installPipeline(
                            pipeline = ch.pipeline(),
                            side = Side.CLIENT,
                            identity = SessionIdentity.ClientTrust(rootPublic),
                            applicationProtocol = BigEchoProtocol,
                            applicationHandlerFactory = { clientApp as ProtocolHandler },
                            options = options,
                        )
                      }
                    },
                )

            drain(clientChannel, serverChannel) // ClientHello
            drain(serverChannel, clientChannel) // ServerHello
            drain(clientChannel, serverChannel) // ClientReady -- compression turns on here

            serverChannel.attr(SESSION_KEY).get().phase shouldBe SessionPhase.ESTABLISHED
            clientChannel.attr(SESSION_KEY).get().phase shouldBe SessionPhase.ESTABLISHED

            // Several >256B (compressed) packets in a row, with varying content each time, to
            // rule out the AES-CTR session cipher's keystream (which is never doFinal()'d -- it
            // advances continuously for the life of the connection) drifting out of sync across
            // repeated compress+encrypt cycles.
            val expected = mutableListOf<BigEcho>()
            repeat(6) { i ->
              val body = ByteArray(300) { ((it * 7 + i * 13) xor 0x5A).toByte() }
              val echo = BigEcho(0xC0DE + i, body)
              expected += echo
              serverChannel.attr(SESSION_KEY).get().send(echo)
              serverChannel.checkException()
              drain(serverChannel, clientChannel)
              clientChannel.checkException()
            }
            clientApp.received shouldBe expected
          }
    })
