package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

private val LocalIpBytes = fixedBytes(4)

data class ClientDiagnosticsPacket(
    val worldStateFlags: Int,
    val renderMetric0: Byte,
    val renderMetric1: Byte,
    val renderMetric2: Byte,
    val renderMetric3: Byte,
    val knownEntityCount: Short,
    val timestampMillis: Long,
    val timestampNanos: Long,
    val localIp: ByteArray,
    val trackedEntityIds: List<Long>,
) {
  override fun equals(other: Any?): Boolean =
      other is ClientDiagnosticsPacket &&
          worldStateFlags == other.worldStateFlags &&
          renderMetric0 == other.renderMetric0 &&
          renderMetric1 == other.renderMetric1 &&
          renderMetric2 == other.renderMetric2 &&
          renderMetric3 == other.renderMetric3 &&
          knownEntityCount == other.knownEntityCount &&
          timestampMillis == other.timestampMillis &&
          timestampNanos == other.timestampNanos &&
          localIp.contentEquals(other.localIp) &&
          trackedEntityIds == other.trackedEntityIds

  override fun hashCode(): Int {
    var r = worldStateFlags
    r = r * 31 + renderMetric0
    r = r * 31 + renderMetric1
    r = r * 31 + renderMetric2
    r = r * 31 + renderMetric3
    r = r * 31 + knownEntityCount
    r = r * 31 + timestampMillis.hashCode()
    r = r * 31 + timestampNanos.hashCode()
    r = r * 31 + localIp.contentHashCode()
    r = r * 31 + trackedEntityIds.hashCode()
    return r
  }
}

object ClientDiagnosticsPacketCodec : PacketCodec<ClientDiagnosticsPacket>() {
  override fun CodecScope<ClientDiagnosticsPacket>.body(): ClientDiagnosticsPacket {
    val worldStateFlags = field(S32LE) { it.worldStateFlags }
    val renderMetric0 = field(S8) { it.renderMetric0 }
    val renderMetric1 = field(S8) { it.renderMetric1 }
    val renderMetric2 = field(S8) { it.renderMetric2 }
    val renderMetric3 = field(S8) { it.renderMetric3 }
    val knownEntityCount = field(S16LE) { it.knownEntityCount }
    val timestampMillis = field(S64LE) { it.timestampMillis }
    val timestampNanos = field(S64LE) { it.timestampNanos }
    val localIp = field(LocalIpBytes) { it.localIp }
    val trackedEntityIds = field(S64LE.listPrefixed(U8)) { it.trackedEntityIds }
    return ClientDiagnosticsPacket(
        worldStateFlags,
        renderMetric0,
        renderMetric1,
        renderMetric2,
        renderMetric3,
        knownEntityCount,
        timestampMillis,
        timestampNanos,
        localIp,
        trackedEntityIds,
    )
  }
}
