package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldNoticeArg(
    val argId: Byte,
    val type: Int,
    val hasExtra: Boolean,
    val extra: Byte,
    val longValue: Long?,
    val intValue: Int?,
    val stringValue: String?,
    val shortValues: List<Short>?,
)

private val WorldNoticeArgCodec: Codec<WorldNoticeArg> =
    object : PacketCodec<WorldNoticeArg>() {
      override fun CodecScope<WorldNoticeArg>.body(): WorldNoticeArg {
        val argId = field(S8) { it.argId }
        val rawType = field(U8) { if (it.hasExtra) it.type or 128 else it.type }
        val hasExtra = rawType and 128 != 0
        val type = if (hasExtra) rawType and 127 else rawType
        val extra = if (hasExtra) field(S8) { it.extra } else 0
        var longValue: Long? = null
        var intValue: Int? = null
        var stringValue: String? = null
        var shortValues: List<Short>? = null
        when (type) {
          28 -> {}
          30 -> longValue = field(S64LE) { it.longValue!! }
          9,
          10,
          17 -> intValue = field(S32LE) { it.intValue!! }
          5,
          18 -> stringValue = field(Utf16LeNullTerminated) { it.stringValue!! }
          else -> {
            val count = field(U8) { it.shortValues!!.size }
            shortValues = (0 until count).map { i -> field(S16LE) { it.shortValues!![i] } }
          }
        }
        return WorldNoticeArg(
            argId, type, hasExtra, extra, longValue, intValue, stringValue, shortValues)
      }
    }

data class WorldNoticeSlotPacket(
    val slot: Byte,
    val flags: Byte,
    val severity: Byte,
    val stringId: Int?,
    val args: List<WorldNoticeArg>,
    val text: String?,
)

object WorldNoticeSlotPacketCodec : PacketCodec<WorldNoticeSlotPacket>() {
  override fun CodecScope<WorldNoticeSlotPacket>.body(): WorldNoticeSlotPacket {
    val slot = field(S8) { it.slot }
    val flags = field(S8) { it.flags }
    if (flags.toInt() != 0) {
      field(S8) { 0 }
      val severity = field(S8) { it.severity }
      val stringId = if (flags.toInt() and 1 != 0) field(S32LE) { it.stringId!! } else null
      val args =
          if (flags.toInt() and 2 != 0) {
            val count = field(S8) { it.args.size.toByte() }.toInt()
            (0 until count).map { i -> field(WorldNoticeArgCodec) { it.args[i] } }
          } else emptyList()
      val text = if (flags.toInt() and 4 != 0) field(Utf16LeNullTerminated) { it.text!! } else null
      return WorldNoticeSlotPacket(slot, flags, severity, stringId, args, text)
    }
    return WorldNoticeSlotPacket(slot, flags, 0, null, emptyList(), null)
  }
}
