package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class LocalCharacterDeltaPacket(
    val mask: Short,
    val money: Int?,
    val mapId: Short?,
    val mapFlag: Byte?,
    val value4: Short?,
    val value8a: Byte?,
    val value8b: Byte?,
    val value8c: Byte?,
    val value16a: Short?,
    val value16b: Short?,
    val value32: Int?,
    val value64Enum: Byte?,
    val value64a: Short?,
    val value64b: Short?,
    val statusConditions: List<Byte>?,
    val value256: Byte?,
)

object LocalCharacterDeltaPacketCodec : PacketCodec<LocalCharacterDeltaPacket>() {
  override fun CodecScope<LocalCharacterDeltaPacket>.body(): LocalCharacterDeltaPacket {
    val mask = field(S16LE) { it.mask }
    val m = mask.toInt()
    val money = if (m and 1 != 0) field(S32LE) { it.money!! } else null
    val mapId = if (m and 2 != 0) field(S16LE) { it.mapId!! } else null
    val mapFlag = if (m and 2 != 0) field(S8) { it.mapFlag!! } else null
    val value4 = if (m and 4 != 0) field(S16LE) { it.value4!! } else null
    val value8a = if (m and 8 != 0) field(S8) { it.value8a!! } else null
    val value8b = if (m and 8 != 0) field(S8) { it.value8b!! } else null
    val value8c = if (m and 8 != 0) field(S8) { it.value8c!! } else null
    val value16a = if (m and 16 != 0) field(S16LE) { it.value16a!! } else null
    val value16b = if (m and 16 != 0) field(S16LE) { it.value16b!! } else null
    val value32 = if (m and 32 != 0) field(S32LE) { it.value32!! } else null
    val value64Enum: Byte?
    val value64a: Short?
    val value64b: Short?
    if (m and 64 != 0) {
      value64Enum = field(S8) { it.value64Enum!! }
      if (value64Enum.toInt() != 0) {
        value64a = field(S16LE) { it.value64a!! }
        value64b = field(S16LE) { it.value64b!! }
      } else {
        value64a = null
        value64b = null
      }
    } else {
      value64Enum = null
      value64a = null
      value64b = null
    }
    val statusConditions =
        if (m and 128 != 0) field(S8.listPrefixed(U8)) { it.statusConditions!! } else null
    val value256 = if (m and 256 != 0) field(S8) { it.value256!! } else null
    return LocalCharacterDeltaPacket(
        mask = mask,
        money = money,
        mapId = mapId,
        mapFlag = mapFlag,
        value4 = value4,
        value8a = value8a,
        value8b = value8b,
        value8c = value8c,
        value16a = value16a,
        value16b = value16b,
        value32 = value32,
        value64Enum = value64Enum,
        value64a = value64a,
        value64b = value64b,
        statusConditions = statusConditions,
        value256 = value256,
    )
  }
}
