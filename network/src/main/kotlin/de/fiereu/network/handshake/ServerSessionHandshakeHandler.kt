package de.fiereu.network.handshake

import de.fiereu.network.PipelineNames
import de.fiereu.network.PipelineOptions
import de.fiereu.network.Protocol
import de.fiereu.network.ProtocolHandler
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.network.StaleClientHelloException
import de.fiereu.network.TypedProtocolHandler
import de.fiereu.network.cipher.AesCtrSessionCipher
import de.fiereu.network.handlers.ChecksumFrameDecoder
import de.fiereu.network.handlers.ChecksumFrameEncoder
import de.fiereu.network.handlers.CipherDecoder
import de.fiereu.network.handlers.CipherEncoder
import de.fiereu.network.handlers.CompressionEncoder
import de.fiereu.network.internal.MutableSessionContext
import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.interfaces.ECPrivateKey
import kotlin.math.abs

private val log = KotlinLogging.logger {}

internal class ServerSessionHandshakeHandler(
    private val rootPrivate: ECPrivateKey,
    private val applicationProtocol: Protocol,
    private val applicationHandlerFactory: () -> ProtocolHandler,
    private val options: PipelineOptions,
) : TypedProtocolHandler<SessionHandshakeProtocol>(SessionHandshakeProtocol, Side.SERVER) {

  private val ephemeralKeyPair = EcKeys.generateEphemeralKeyPair()

  init {
    on<ClientHelloPacket> { event ->
      val skew = abs(System.currentTimeMillis() - event.packet.timestamp)
      if (skew > options.maxHelloSkew.inWholeMilliseconds) {
        throw StaleClientHelloException(skew)
      }
      val publicBytes =
          EcKeys.toUncompressedPoint(
              ephemeralKeyPair.public as java.security.interfaces.ECPublicKey,
          )
      val signature = EcKeys.sign(rootPrivate, publicBytes)
      event.session.send(
          ServerHelloPacket(
              ephemeralPublic = ephemeralKeyPair.public as java.security.interfaces.ECPublicKey,
              signature = signature,
              checksumSize = options.checksumSize,
          ),
      )
    }

    on<ClientReadyPacket> { event ->
      val crypto =
          SessionCryptoState.derive(
              localPrivate = ephemeralKeyPair.private,
              remotePublic = event.packet.ephemeralPublic,
              role = AesCtrSessionCipher.Role.SERVER,
              checksumSize = options.checksumSize,
          )
      val pipeline = event.session.channel.pipeline()
      (pipeline.get(PipelineNames.CHECKSUM_DECODER) as ChecksumFrameDecoder).checksum =
          crypto.incomingChecksum
      (pipeline.get(PipelineNames.CHECKSUM_ENCODER) as ChecksumFrameEncoder).checksum =
          crypto.outgoingChecksum
      (pipeline.get(PipelineNames.CIPHER_DECODER) as CipherDecoder).cipher = crypto.cipher
      (pipeline.get(PipelineNames.CIPHER_ENCODER) as CipherEncoder).cipher = crypto.cipher
      if (applicationProtocol.compressed) {
        pipeline.addBefore(
            PipelineNames.PROTOCOL_HANDLER,
            PipelineNames.COMPRESSION_ENCODER,
            CompressionEncoder(options.compressionThreshold),
        )
      }
      val appHandler = applicationHandlerFactory()
      pipeline.replace(
          PipelineNames.PROTOCOL_HANDLER,
          PipelineNames.PROTOCOL_HANDLER,
          appHandler,
      )
      (event.session as MutableSessionContext).transitionTo(SessionPhase.ESTABLISHED)
      log.debug { "Handshake complete for ${event.session.remoteAddress}" }
    }
  }
}
