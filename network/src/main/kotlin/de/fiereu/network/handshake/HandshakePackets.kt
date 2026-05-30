package de.fiereu.network.handshake

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.bytesPrefixed
import java.security.interfaces.ECPublicKey
import kotlin.random.Random

private const val XOR_KEY_RANDOM = 3214621489648854472L
private const val XOR_KEY_TIMESTAMP = -4214651440992349575L

data class ClientHelloPacket(val timestamp: Long)

data class ServerHelloPacket(
    val ephemeralPublic: ECPublicKey,
    val signature: ByteArray,
    val checksumSize: Int,
) {
  override fun equals(other: Any?): Boolean =
      other is ServerHelloPacket &&
          ephemeralPublic == other.ephemeralPublic &&
          signature.contentEquals(other.signature) &&
          checksumSize == other.checksumSize

  override fun hashCode(): Int =
      (ephemeralPublic.hashCode() * 31 + signature.contentHashCode()) * 31 + checksumSize
}

data class ClientReadyPacket(val ephemeralPublic: ECPublicKey)

object ClientHelloCodec : PacketCodec<ClientHelloPacket>() {
  override fun CodecScope<ClientHelloPacket>.body(): ClientHelloPacket {
    var random = 0L
    val xoredRandom =
        field(S64LE) {
          random = Random.nextLong()
          random xor XOR_KEY_RANDOM
        }
    val xoredTimestamp = field(S64LE) { it.timestamp xor XOR_KEY_TIMESTAMP xor random }
    val readRandom = xoredRandom xor XOR_KEY_RANDOM
    val timestamp = xoredTimestamp xor XOR_KEY_TIMESTAMP xor readRandom
    return ClientHelloPacket(timestamp)
  }
}

private val LengthPrefixedBytes = bytesPrefixed(U16LE)

object ServerHelloCodec : PacketCodec<ServerHelloPacket>() {
  override fun CodecScope<ServerHelloPacket>.body(): ServerHelloPacket {
    val pubKey = field(LengthPrefixedBytes) { EcKeys.toUncompressedPoint(it.ephemeralPublic) }
    val signature = field(LengthPrefixedBytes, ServerHelloPacket::signature)
    val checksumSize = field(U8, ServerHelloPacket::checksumSize)
    return ServerHelloPacket(
        ephemeralPublic = EcKeys.fromUncompressedPoint(pubKey),
        signature = signature,
        checksumSize = checksumSize,
    )
  }
}

object ClientReadyCodec : PacketCodec<ClientReadyPacket>() {
  override fun CodecScope<ClientReadyPacket>.body(): ClientReadyPacket {
    val bytes = field(LengthPrefixedBytes) { EcKeys.toUncompressedPoint(it.ephemeralPublic) }
    return ClientReadyPacket(EcKeys.fromUncompressedPoint(bytes))
  }
}
