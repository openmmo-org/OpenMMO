package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ServerMessageArg(
    val argId: Byte,
    val type: Int,
    val hasExtra: Boolean,
    val extra: Byte,
    val longValue: Long?,
    val intValue: Int?,
    val stringValue: String?,
    val shortValues: List<Short>?,
)

data class ServerMessagePacket(
    val stringId: Int,
    val args: List<ServerMessageArg>,
    val showOnMap: Boolean,
    val mode: Byte?,
)

private val ServerMessageArgCodec: Codec<ServerMessageArg> =
    object : PacketCodec<ServerMessageArg>() {
        override fun CodecScope<ServerMessageArg>.body(): ServerMessageArg {
            val argId = field(S8) { it.argId }
            val typeRaw = field(U8) { if (it.hasExtra) it.type or 128 else it.type }
            val hasExtra = (typeRaw and 128) != 0
            val type = if (hasExtra) typeRaw and 127 else typeRaw
            val extra = if (hasExtra) field(S8) { it.extra } else 0
            var longValue: Long? = null
            var intValue: Int? = null
            var stringValue: String? = null
            var shortValues: List<Short>? = null
            when {
                type == 5 || type == 18 -> stringValue = field(Utf16LeNullTerminated) { it.stringValue!! }
                type == 28 -> {}
                type == 30 -> longValue = field(S64LE) { it.longValue!! }
                type == 9 || type == 10 || type == 17 -> intValue = field(S32LE) { it.intValue!! }
                else -> shortValues = field(S16LE.listPrefixed(U8)) { it.shortValues!! }
            }
            return ServerMessageArg(
                argId, type, hasExtra, extra, longValue, intValue, stringValue, shortValues
            )
        }
    }

object ServerMessagePacketCodec : PacketCodec<ServerMessagePacket>() {
    override fun CodecScope<ServerMessagePacket>.body(): ServerMessagePacket {
        val stringId = field(S32LE) { it.stringId }
        val flags =
            field(U8) {
                (it.args.size and 63) or
                        (if (it.showOnMap) 64 else 0) or
                        (if (it.mode != null) 128 else 0)
            }
        val count = flags and 63
        val args = (0 until count).map { i -> field(ServerMessageArgCodec) { it.args[i] } }
        val showOnMap = (flags and 64) != 0
        val mode = if (flags and 128 != 0) field(S8) { it.mode!! } else null
        return ServerMessagePacket(stringId, args, showOnMap, mode)
    }
}
