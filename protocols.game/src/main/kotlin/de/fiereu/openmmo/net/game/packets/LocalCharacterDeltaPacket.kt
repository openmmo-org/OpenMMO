package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

private const val MONEY = 0x1
private const val MAP = 0x2
private const val VALUE_4 = 0x4
private const val VALUE_8 = 0x8
private const val VALUE_16 = 0x10
private const val VALUE_32 = 0x20
private const val VALUE_64 = 0x40
private const val STATUS_CONDITIONS = 0x80
private const val VALUE_256 = 0x100

data class MapLocation(val mapId: Short, val mapFlag: Byte)

data class Value8Group(val a: Byte, val b: Byte, val c: Byte)

data class Value16Group(val a: Short, val b: Short)

/** [a] and [b] are only on the wire when [kind] is not zero. */
data class Value64Group(val kind: Byte, val a: Short?, val b: Short?)

/** A partial update to the player's own character. A null group is left off the wire. */
data class LocalCharacterDeltaPacket(
    val money: Int? = null,
    val map: MapLocation? = null,
    val value4: Short? = null,
    val value8: Value8Group? = null,
    val value16: Value16Group? = null,
    val value32: Int? = null,
    val value64: Value64Group? = null,
    val statusConditions: List<Byte>? = null,
    val value256: Byte? = null,
)

private fun LocalCharacterDeltaPacket.mask(): Short {
  var m = 0
  if (money != null) m = m or MONEY
  if (map != null) m = m or MAP
  if (value4 != null) m = m or VALUE_4
  if (value8 != null) m = m or VALUE_8
  if (value16 != null) m = m or VALUE_16
  if (value32 != null) m = m or VALUE_32
  if (value64 != null) m = m or VALUE_64
  if (statusConditions != null) m = m or STATUS_CONDITIONS
  if (value256 != null) m = m or VALUE_256
  return m.toShort()
}

object LocalCharacterDeltaPacketCodec : PacketCodec<LocalCharacterDeltaPacket>() {
  override fun CodecScope<LocalCharacterDeltaPacket>.body(): LocalCharacterDeltaPacket {
    val m = field(S16LE) { it.mask() }.toInt()

    val money = optionalField(m and MONEY != 0, S32LE) { it.money }
    val map =
        if (m and MAP != 0)
            MapLocation(field(S16LE) { it.map!!.mapId }, field(S8) { it.map!!.mapFlag })
        else null
    val value4 = optionalField(m and VALUE_4 != 0, S16LE) { it.value4 }
    val value8 =
        if (m and VALUE_8 != 0)
            Value8Group(
                field(S8) { it.value8!!.a },
                field(S8) { it.value8!!.b },
                field(S8) { it.value8!!.c },
            )
        else null
    val value16 =
        if (m and VALUE_16 != 0)
            Value16Group(field(S16LE) { it.value16!!.a }, field(S16LE) { it.value16!!.b })
        else null
    val value32 = optionalField(m and VALUE_32 != 0, S32LE) { it.value32 }
    val value64 =
        if (m and VALUE_64 != 0) {
          val kind = field(S8) { it.value64!!.kind }
          if (kind.toInt() != 0)
              Value64Group(
                  kind,
                  field(S16LE) { it.value64!!.a!! },
                  field(S16LE) { it.value64!!.b!! },
              )
          else Value64Group(kind, null, null)
        } else null
    val statusConditions =
        optionalField(m and STATUS_CONDITIONS != 0, S8.listPrefixed(U8)) { it.statusConditions }
    val value256 = optionalField(m and VALUE_256 != 0, S8) { it.value256 }

    return LocalCharacterDeltaPacket(
        money = money,
        map = map,
        value4 = value4,
        value8 = value8,
        value16 = value16,
        value32 = value32,
        value64 = value64,
        statusConditions = statusConditions,
        value256 = value256,
    )
  }
}
