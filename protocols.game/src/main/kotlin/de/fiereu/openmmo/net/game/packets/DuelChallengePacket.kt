package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class DuelItemRestriction(
    val typeId: Byte,
    val extra: Byte?,
)

data class DuelChallengePacket(
    val targetPlayerName: String,
    val battleTypeId: Byte,
    val timed: Boolean,
    val battleFormat: Byte,
    val typeRestriction: Byte,
    val natureRestriction: Byte,
    val allowedFormat: Byte,
    val itemLevelCap: Byte?,
    val natureCap: Byte?,
    val items: List<DuelItemRestriction>?,
    val tier: Byte?,
)

private val DuelItemRestrictionCodec: Codec<DuelItemRestriction> =
    object : Codec<DuelItemRestriction> {
      override fun read(buf: ReadBuffer): DuelItemRestriction {
        val typeId = S8.read(buf)
        return DuelItemRestriction(typeId, null)
      }

      override fun write(buf: WriteBuffer, value: DuelItemRestriction) {
        S8.write(buf, value.typeId)
        if (value.extra != null) S8.write(buf, value.extra)
      }
    }

object DuelChallengePacketCodec : PacketCodec<DuelChallengePacket>() {
  override fun CodecScope<DuelChallengePacket>.body(): DuelChallengePacket {
    val targetPlayerName = field(Utf16LeNullTerminated) { it.targetPlayerName }
    val battleTypeId = field(S8) { it.battleTypeId }
    val flags =
        field(U8) { v ->
          var f = 0
          if (v.timed) f = f or 1
          if (v.itemLevelCap != null) f = f or 8
          if (v.natureCap != null) f = f or 16
          if (v.items != null) f = f or 32
          if (v.tier != null) f = f or 64
          f
        }
    val battleFormat = field(S8) { it.battleFormat }
    val typeRestriction = field(S8) { it.typeRestriction }
    val natureRestriction = field(S8) { it.natureRestriction }
    val allowedFormat = field(S8) { it.allowedFormat }
    val timed = flags and 1 != 0
    val itemLevelCap: Byte? = if (flags and 8 != 0) field(S8) { it.itemLevelCap!! } else null
    val natureCap: Byte? = if (flags and 16 != 0) field(S8) { it.natureCap!! } else null
    val items: List<DuelItemRestriction>? =
        if (flags and 32 != 0) {
          val count = field(U8) { it.items!!.size }
          val list = ArrayList<DuelItemRestriction>(count)
          var i = 0
          while (i < count) {
            list.add(field(DuelItemRestrictionCodec) { v -> v.items!![list.size] })
            i++
          }
          list
        } else null
    val tier: Byte? = if (flags and 64 != 0) field(S8) { it.tier!! } else null
    return DuelChallengePacket(
        targetPlayerName = targetPlayerName,
        battleTypeId = battleTypeId,
        timed = timed,
        battleFormat = battleFormat,
        typeRestriction = typeRestriction,
        natureRestriction = natureRestriction,
        allowedFormat = allowedFormat,
        itemLevelCap = itemLevelCap,
        natureCap = natureCap,
        items = items,
        tier = tier,
    )
  }
}
