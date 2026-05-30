package de.fiereu.network

import de.fiereu.network.checksum.NoOpChecksum
import de.fiereu.network.cipher.NoOpSessionCipher
import de.fiereu.network.handlers.ChecksumFrameDecoder
import de.fiereu.network.handlers.ChecksumFrameEncoder
import de.fiereu.network.handlers.CipherDecoder
import de.fiereu.network.handlers.CipherEncoder
import de.fiereu.network.handlers.PacketFrameDecoder
import de.fiereu.network.handlers.PacketFrameEncoder
import de.fiereu.network.handshake.ClientSessionHandshakeHandler
import de.fiereu.network.handshake.ServerSessionHandshakeHandler
import de.fiereu.network.internal.MutableSessionContext
import de.fiereu.network.internal.SESSION_KEY
import io.netty.channel.ChannelPipeline
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import java.util.concurrent.TimeUnit

fun installPipeline(
    pipeline: ChannelPipeline,
    side: Side,
    identity: SessionIdentity,
    applicationProtocol: Protocol,
    applicationHandlerFactory: () -> ProtocolHandler,
    options: PipelineOptions = PipelineOptions(),
) {
  val channel = pipeline.channel()
  val session = MutableSessionContext(side, channel, applicationProtocol)
  channel.attr(SESSION_KEY).set(session)

  val handshakeHandler: ProtocolHandler =
      when (side) {
        Side.SERVER -> {
          require(identity is SessionIdentity.ServerRoot) {
            "Server side requires SessionIdentity.ServerRoot"
          }
          ServerSessionHandshakeHandler(
              rootPrivate = identity.rootPrivate,
              applicationProtocol = applicationProtocol,
              applicationHandlerFactory = applicationHandlerFactory,
              options = options,
          )
        }
        Side.CLIENT -> {
          require(identity is SessionIdentity.ClientTrust) {
            "Client side requires SessionIdentity.ClientTrust"
          }
          ClientSessionHandshakeHandler(
              rootPublic = identity.rootPublic,
              applicationProtocol = applicationProtocol,
              applicationHandlerFactory = applicationHandlerFactory,
              options = options,
          )
        }
      }

  pipeline.addLast(
      PipelineNames.WRITE_TIMEOUT,
      WriteTimeoutHandler(options.writeTimeout.inWholeSeconds, TimeUnit.SECONDS),
  )
  if (options.frameLogging) {
    pipeline.addLast(PipelineNames.FRAME_LOGGER, LoggingHandler(LogLevel.TRACE))
  }
  pipeline.addLast(PipelineNames.FRAME_DECODER, PacketFrameDecoder(options.maxFrameLength))
  pipeline.addLast(PipelineNames.FRAME_ENCODER, PacketFrameEncoder())
  pipeline.addLast(PipelineNames.CHECKSUM_DECODER, ChecksumFrameDecoder(NoOpChecksum))
  pipeline.addLast(PipelineNames.CHECKSUM_ENCODER, ChecksumFrameEncoder(NoOpChecksum))
  pipeline.addLast(PipelineNames.CIPHER_DECODER, CipherDecoder(NoOpSessionCipher))
  pipeline.addLast(PipelineNames.CIPHER_ENCODER, CipherEncoder(NoOpSessionCipher))
  if (options.frameLogging) {
    pipeline.addLast(PipelineNames.PROTOCOL_LOGGER, LoggingHandler(LogLevel.TRACE))
  }
  pipeline.addLast(PipelineNames.PROTOCOL_HANDLER, handshakeHandler)
}
