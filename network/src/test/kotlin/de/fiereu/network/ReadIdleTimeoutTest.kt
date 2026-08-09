package de.fiereu.network

import de.fiereu.network.handlers.CloseOnReaderIdleHandler
import de.fiereu.network.handshake.EcKeys
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.handler.timeout.IdleStateEvent
import io.netty.handler.timeout.IdleStateHandler
import java.security.interfaces.ECPrivateKey
import kotlin.time.Duration.Companion.seconds

private object EmptyProtocol : Protocol()

class ReadIdleTimeoutTest :
    FunSpec({
      test("reader idle closes the channel") {
        var inactivePropagated = false
        val channel =
            EmbeddedChannel(
                CloseOnReaderIdleHandler(),
                object : ChannelInboundHandlerAdapter() {
                  override fun channelInactive(ctx: ChannelHandlerContext) {
                    inactivePropagated = true
                    ctx.fireChannelInactive()
                  }
                },
            )

        channel.pipeline().fireUserEventTriggered(IdleStateEvent.READER_IDLE_STATE_EVENT)

        channel.isOpen shouldBe false
        inactivePropagated shouldBe true
      }

      test("other idle events do not close the channel") {
        val channel = EmbeddedChannel(CloseOnReaderIdleHandler())

        channel.pipeline().fireUserEventTriggered(IdleStateEvent.WRITER_IDLE_STATE_EVENT)
        channel.pipeline().fireUserEventTriggered(IdleStateEvent.ALL_IDLE_STATE_EVENT)

        channel.isOpen shouldBe true
      }

      test("installPipeline registers reader idle handlers in protocol order") {
        val rootPrivate = EcKeys.generateEphemeralKeyPair().private as ECPrivateKey
        val channel =
            EmbeddedChannel(
                object : ChannelInitializer<Channel>() {
                  override fun initChannel(ch: Channel) {
                    installPipeline(
                        pipeline = ch.pipeline(),
                        side = Side.SERVER,
                        identity = SessionIdentity.ServerRoot(rootPrivate),
                        applicationProtocol = EmptyProtocol,
                        applicationHandlerFactory = { error("handshake not completed") },
                        options = PipelineOptions(readerIdleTimeout = 45.seconds),
                    )
                  }
                },
            )

        channel
            .pipeline()
            .get(PipelineNames.READER_IDLE_STATE)
            .shouldBeInstanceOf<IdleStateHandler>()
        channel
            .pipeline()
            .get(PipelineNames.READER_IDLE_CLOSE)
            .shouldBeInstanceOf<CloseOnReaderIdleHandler>()
        val names = channel.pipeline().names()
        names.indexOf(PipelineNames.CIPHER_ENCODER) shouldBeLessThan
            names.indexOf(PipelineNames.READER_IDLE_STATE)
        names.indexOf(PipelineNames.READER_IDLE_STATE) shouldBeLessThan
            names.indexOf(PipelineNames.READER_IDLE_CLOSE)
        names.indexOf(PipelineNames.READER_IDLE_CLOSE) shouldBeLessThan
            names.indexOf(PipelineNames.PROTOCOL_HANDLER)
      }
    })
