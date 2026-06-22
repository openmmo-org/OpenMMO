package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldSessionStatePacket(
    val worldState: Int,
    val payloadBlocks: List<ByteArray>?,
) {
  override fun equals(other: Any?): Boolean {
    if (other !is WorldSessionStatePacket) return false
    if (worldState != other.worldState) return false
    val a = payloadBlocks
    val b = other.payloadBlocks
    if (a == null) return b == null
    if (b == null || a.size != b.size) return false
    for (i in a.indices) if (!a[i].contentEquals(b[i])) return false
    return true
  }

  override fun hashCode(): Int {
    var r = worldState
    payloadBlocks?.forEach { r = r * 31 + it.contentHashCode() }
    return r
  }
}

object WorldSessionStatePacketCodec : PacketCodec<WorldSessionStatePacket>() {
  override fun CodecScope<WorldSessionStatePacket>.body(): WorldSessionStatePacket {
    val worldState = field(U8) { it.worldState }
    val payloadBlocks =
        if (worldState == 11) {
          field(bytesPrefixed(U8).listPrefixed(U8)) { it.payloadBlocks!! }
        } else null
    return WorldSessionStatePacket(worldState, payloadBlocks)
  }
}
