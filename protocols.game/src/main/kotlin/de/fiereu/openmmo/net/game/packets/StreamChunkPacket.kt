package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class StreamChunkPacket(
    val streamId: Long,
    val finalChunk: Boolean,
    val chunk: ByteArray,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is StreamChunkPacket) return false
    return streamId == other.streamId &&
        finalChunk == other.finalChunk &&
        chunk.contentEquals(other.chunk)
  }

  override fun hashCode(): Int {
    var result = streamId.hashCode()
    result = 31 * result + finalChunk.hashCode()
    result = 31 * result + chunk.contentHashCode()
    return result
  }
}

object StreamChunkPacketCodec : PacketCodec<StreamChunkPacket>() {
  override fun CodecScope<StreamChunkPacket>.body(): StreamChunkPacket {
    val streamId = field(S64LE, StreamChunkPacket::streamId)
    val finalChunk = field(Bool, StreamChunkPacket::finalChunk)
    val chunk = field(bytesPrefixed(U16LE), StreamChunkPacket::chunk)
    return StreamChunkPacket(streamId, finalChunk, chunk)
  }
}
