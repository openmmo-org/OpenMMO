package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class MessageArg(
    val argId: Byte,
    val type: Int,
    val hasExtra: Boolean,
    val extra: Byte,
    val longValue: Long?,
    val intValue: Int?,
    val stringValue: String?,
    val shortValues: List<Short>?,
)

internal val MessageArgCodec: Codec<MessageArg> =
    object : PacketCodec<MessageArg>() {
        override fun CodecScope<MessageArg>.body(): MessageArg {
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
                9, 10, 17 -> intValue = field(S32LE) { it.intValue!! }
                5, 18 -> stringValue = field(Utf16LeNullTerminated) { it.stringValue!! }
                else -> {
                    val count = field(U8) { it.shortValues!!.size }
                    shortValues = (0 until count).map { i -> field(S16LE) { it.shortValues!![i] } }
                }
            }
            return MessageArg(argId, type, hasExtra, extra, longValue, intValue, stringValue, shortValues)
        }
    }

data class BattleDisconnectMessagePacket(
    val reason: Byte,
    val args: List<MessageArg>,
)

object BattleDisconnectMessagePacketCodec : PacketCodec<BattleDisconnectMessagePacket>() {
    override fun CodecScope<BattleDisconnectMessagePacket>.body(): BattleDisconnectMessagePacket {
        val reason = field(S8) { it.reason }
        val count = field(U8) { it.args.size }
        val args = (0 until count).map { i -> field(MessageArgCodec) { it.args[i] } }
        return BattleDisconnectMessagePacket(reason, args)
    }
}
