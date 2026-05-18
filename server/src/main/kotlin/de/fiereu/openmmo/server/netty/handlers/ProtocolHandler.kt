package de.fiereu.openmmo.server.netty.handlers

import de.fiereu.openmmo.protocols.PacketDirection
import de.fiereu.openmmo.protocols.Protocol
import de.fiereu.openmmo.protocols.getDeserializer
import de.fiereu.openmmo.server.config.ServerConfig
import de.fiereu.openmmo.server.netty.Compressor
import de.fiereu.openmmo.server.protocol.PacketEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise

private val log = KotlinLogging.logger {}

abstract class ProtocolHandler(
  val protocol: Protocol,
  serverConfig: ServerConfig
) : ChannelDuplexHandler() {

  protected val compressor = Compressor(serverConfig)

  override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
    if (msg !is ByteBuf) {
      log.warn { "Received non-ByteBuf message: ${msg::class.simpleName}" }
      return
    }

    try {
      if (!msg.isReadable) {
        log.warn { "Empty packet received" }
        return
      }
      val opcodeValue = msg.readUnsignedByte().toUByte()
      val remainingBytes = msg.readableBytes()

      log.debug { "<< RX $opcodeValue (${remainingBytes}b) from ${ctx.channel().remoteAddress()}" }

      val deserializer = protocol.getDeserializer(opcodeValue)
      if (deserializer == null) {
        log.warn { "No deserializer found for ${PacketDirection.CLIENT_TO_SERVER} opcode $opcodeValue (${remainingBytes}b remaining)" }
        return
      }

      val packetData = deserializer.deserialize(msg)

      /*
       * We cant call the onPacketReceived function asynchronously directly here,
       * because the TLS protocol expects a synchronous flow. Meaning that the client already sends the next packet
       * after the ClientReady packet, so if we would process the ClientReady packet asynchronously,
       * the next packet would be processed before the ClientReady packet is fully processed.
       * -> It would be processed by the TLS protocol before and not by the next protocol (e.g. login protocol).
       */
      onPacketReceived(PacketEvent(ctx, packetData))
    } catch (e: Exception) {
      log.error(e) { "Error processing packet: ${e.message}" }
    } finally {
      msg.release()
    }
  }

  override fun write(
    ctx: ChannelHandlerContext,
    msg: Any,
    promise: ChannelPromise
  ) {
    try {
      val opcode = protocol.getOpcode(msg::class)
      if (opcode == null) {
        log.warn { "No opcode found for packet type: ${msg::class.simpleName}" }
        return
      }
      val serializer = protocol.getSerializer(opcode)
      if (serializer == null) {
        log.warn { "No serializer found for ${PacketDirection.SERVER_TO_CLIENT} opcode $opcode" }
        return
      }

      val buffer = ctx.alloc().buffer()
      buffer.writeByte(opcode.value.toInt())

      /*
       * If the protocol requires compression (e.g. Game protocol),
       * we need to compress the serialized packet before adding the opcode.
       */
      if (!protocol.compressed) {
        serializer.serializeObject(msg, buffer)
        log.debug { ">> TX $opcode (${buffer.readableBytes() - 1}b payload)" }
      } else {
        val uncompressedBuffer = Unpooled.buffer()
        serializer.serializeObject(msg, uncompressedBuffer)
        val uncompressedSize = uncompressedBuffer.readableBytes()
        val compressed = compressor.compress(uncompressedBuffer)
        buffer.writeBytes(compressed)
        log.debug { ">> TX $opcode (payload: ${uncompressedSize}b -> ${compressed.readableBytes()}b compressed)" }
      }

      ctx.write(buffer, promise)
    } catch (e: Exception) {
      log.error(e) { "Error processing packet: ${e.message}" }
    }
  }

  override fun channelActive(ctx: ChannelHandlerContext) {
    onActive(ctx)
  }

  protected fun sendRaw(ctx: ChannelHandlerContext, opcode: UByte, data: ByteArray) {
    val buffer = ctx.alloc().buffer()
    buffer.writeByte(opcode.toInt())
    val dataBuf = Unpooled.wrappedBuffer(data)
    buffer.writeBytes(compressor.compress(dataBuf))
    ctx.write(buffer)
  }

  protected fun sendRawAndFlush(ctx: ChannelHandlerContext, opcode: UByte, data: ByteArray) {
    val buffer = ctx.alloc().buffer()
    buffer.writeByte(opcode.toInt())
    val dataBuf = Unpooled.wrappedBuffer(data)
    buffer.writeBytes(compressor.compress(dataBuf))
    ctx.writeAndFlush(buffer)
  }

  abstract fun onPacketReceived(event: PacketEvent<*>)
  abstract fun onActive(ctx: ChannelHandlerContext)
}