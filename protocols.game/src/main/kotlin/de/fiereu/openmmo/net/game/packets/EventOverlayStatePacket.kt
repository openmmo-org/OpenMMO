package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

private const val GRID_OVERLAY_TYPE: Int = 1

data class OverlayGridPoint(val x: Short, val y: Short)

data class OverlayRegion(
    val regionId: Short,
    val secondary: Short,
    val gridPoints: List<OverlayGridPoint>?,
    val value: Int?,
)

data class OverlayHole(val regionId: Short, val secondary: Short)

data class EventOverlayStatePacket(
    val active: Boolean,
    val overlayType: Byte,
    val flagBits: Byte,
    val ignoredEnum: Byte,
    val timeRemaining: Int?,
    val valueA: Int?,
    val valueB: Int?,
    val anchorEntityId: Long?,
    val regions: List<OverlayRegion>,
    val holes: List<OverlayHole>,
)

object EventOverlayStatePacketCodec : PacketCodec<EventOverlayStatePacket>() {
  override fun CodecScope<EventOverlayStatePacket>.body(): EventOverlayStatePacket {
    val codec = EventOverlayStateCodec
    return field(codec) { it }
  }
}

private val EventOverlayStateCodec: Codec<EventOverlayStatePacket> =
    object : Codec<EventOverlayStatePacket> {
      override fun read(buf: ReadBuffer): EventOverlayStatePacket {
        val typeByte = S8.read(buf)
        val active = typeByte.toInt() != -1
        if (!active) {
          return EventOverlayStatePacket(
              active = false,
              overlayType = typeByte,
              flagBits = 0,
              ignoredEnum = 0,
              timeRemaining = null,
              valueA = null,
              valueB = null,
              anchorEntityId = null,
              regions = emptyList(),
              holes = emptyList(),
          )
        }
        val grid = typeByte.toInt() == GRID_OVERLAY_TYPE
        val flagBits = S8.read(buf)
        val fb = flagBits.toInt()
        val ignoredEnum = S8.read(buf)
        val timeRemaining = if (fb and 32 != 0) S32LE.read(buf) else null
        val valueA = if (fb and 8 != 0) S32LE.read(buf) else null
        val valueB = if (fb and 16 != 0) S32LE.read(buf) else null
        val anchorEntityId = if (fb and 64 != 0) S64LE.read(buf) else null
        val regionCount = U16LE.read(buf)
        val regions = ArrayList<OverlayRegion>(regionCount)
        repeat(regionCount) {
          val regionId = S16LE.read(buf)
          val secondary = S16LE.read(buf)
          if (grid) {
            val n = U8.read(buf)
            val points = ArrayList<OverlayGridPoint>(n)
            repeat(n) { points.add(OverlayGridPoint(S16LE.read(buf), S16LE.read(buf))) }
            regions.add(OverlayRegion(regionId, secondary, points, null))
          } else {
            val v = S32LE.read(buf)
            regions.add(OverlayRegion(regionId, secondary, null, v))
          }
        }
        val holes = ArrayList<OverlayHole>()
        if (grid && fb and 4 != 0) {
          val holeCount = U16LE.read(buf)
          repeat(holeCount) { holes.add(OverlayHole(S16LE.read(buf), S16LE.read(buf))) }
        }
        return EventOverlayStatePacket(
            active = true,
            overlayType = typeByte,
            flagBits = flagBits,
            ignoredEnum = ignoredEnum,
            timeRemaining = timeRemaining,
            valueA = valueA,
            valueB = valueB,
            anchorEntityId = anchorEntityId,
            regions = regions,
            holes = holes,
        )
      }

      override fun write(buf: WriteBuffer, value: EventOverlayStatePacket) {
        S8.write(buf, value.overlayType)
        if (!value.active) return
        val grid = value.overlayType.toInt() == GRID_OVERLAY_TYPE
        S8.write(buf, value.flagBits)
        val fb = value.flagBits.toInt()
        S8.write(buf, value.ignoredEnum)
        if (fb and 32 != 0) S32LE.write(buf, value.timeRemaining!!)
        if (fb and 8 != 0) S32LE.write(buf, value.valueA!!)
        if (fb and 16 != 0) S32LE.write(buf, value.valueB!!)
        if (fb and 64 != 0) S64LE.write(buf, value.anchorEntityId!!)
        U16LE.write(buf, value.regions.size)
        for (r in value.regions) {
          S16LE.write(buf, r.regionId)
          S16LE.write(buf, r.secondary)
          if (grid) {
            val points =
                r.gridPoints ?: throw MalformedPacketException("grid overlay region missing points")
            U8.write(buf, points.size)
            for (p in points) {
              S16LE.write(buf, p.x)
              S16LE.write(buf, p.y)
            }
          } else {
            S32LE.write(buf, r.value!!)
          }
        }
        if (grid && fb and 4 != 0) {
          U16LE.write(buf, value.holes.size)
          for (h in value.holes) {
            S16LE.write(buf, h.regionId)
            S16LE.write(buf, h.secondary)
          }
        }
      }
    }
