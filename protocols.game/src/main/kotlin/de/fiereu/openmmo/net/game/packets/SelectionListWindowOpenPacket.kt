package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

sealed class ListWindowMessageArg {
    abstract val argId: Byte
    abstract val typeTag: Byte
    abstract val hasExtra: Boolean
    abstract val extra: Byte
}

data class ListWindowByteArg(
    override val argId: Byte,
    override val typeTag: Byte,
    override val hasExtra: Boolean,
    override val extra: Byte,
) : ListWindowMessageArg()

data class ListWindowLongArg(
    override val argId: Byte,
    override val typeTag: Byte,
    override val hasExtra: Boolean,
    override val extra: Byte,
    val value: Long,
) : ListWindowMessageArg()

data class ListWindowIntArg(
    override val argId: Byte,
    override val typeTag: Byte,
    override val hasExtra: Boolean,
    override val extra: Byte,
    val value: Int,
) : ListWindowMessageArg()

data class ListWindowStringArg(
    override val argId: Byte,
    override val typeTag: Byte,
    override val hasExtra: Boolean,
    override val extra: Byte,
    val text: String,
) : ListWindowMessageArg()

data class ListWindowShortsArg(
    override val argId: Byte,
    override val typeTag: Byte,
    override val hasExtra: Boolean,
    override val extra: Byte,
    val values: List<Short>,
) : ListWindowMessageArg()

internal val ListWindowMessageArgCodec: Codec<ListWindowMessageArg> =
    object : PacketCodec<ListWindowMessageArg>() {
        override fun CodecScope<ListWindowMessageArg>.body(): ListWindowMessageArg {
            val argId = field(S8) { it.argId }
            val rawType =
                field(S8) { v -> if (v.hasExtra) (v.typeTag.toInt() or 128).toByte() else v.typeTag }
            val hasExtra = (rawType.toInt() and 128) != 0
            val typeTag = if (hasExtra) (rawType.toInt() and 127).toByte() else rawType
            val extra: Byte = if (hasExtra) field(S8) { it.extra } else 0
            return when (typeTag.toInt()) {
                28 -> ListWindowByteArg(argId, typeTag, hasExtra, extra)
                30 -> {
                    val value = field(S64LE) { (it as ListWindowLongArg).value }
                    ListWindowLongArg(argId, typeTag, hasExtra, extra, value)
                }

                9,
                10,
                17 -> {
                    val value = field(S32LE) { (it as ListWindowIntArg).value }
                    ListWindowIntArg(argId, typeTag, hasExtra, extra, value)
                }

                5,
                18 -> {
                    val text = field(Utf16LeNullTerminated) { (it as ListWindowStringArg).text }
                    ListWindowStringArg(argId, typeTag, hasExtra, extra, text)
                }

                else -> {
                    val count = field(U8) { (it as ListWindowShortsArg).values.size }
                    val values =
                        (0 until count).map { i -> field(S16LE) { (it as ListWindowShortsArg).values[i] } }
                    ListWindowShortsArg(argId, typeTag, hasExtra, extra, values)
                }
            }
        }
    }

data class ListWindowEntry(
    val id: Int,
    val value: Int,
    val arg: ListWindowMessageArg?,
)

data class SelectionListWindowOpenPacket(
    val windowId: Int,
    val type: Byte,
    val flag: Boolean,
    val entries: List<ListWindowEntry>,
)

object SelectionListWindowOpenPacketCodec : PacketCodec<SelectionListWindowOpenPacket>() {
    override fun CodecScope<SelectionListWindowOpenPacket>.body(): SelectionListWindowOpenPacket {
        val windowId = field(S32LE) { it.windowId }
        val type = field(S8) { it.type }
        val flag = field(U8) { if (it.flag) 1 else 0 } == 1
        val count = field(S8) { it.entries.size.toByte() }.toInt()
        val entries =
            (0 until count).map { i ->
                val id = field(S32LE) { it.entries[i].id }
                val value = field(S32LE) { it.entries[i].value }
                val present = field(U8) { if (it.entries[i].arg != null) 1 else 0 } == 1
                val arg = if (present) field(ListWindowMessageArgCodec) { it.entries[i].arg!! } else null
                ListWindowEntry(id, value, arg)
            }
        return SelectionListWindowOpenPacket(windowId, type, flag, entries)
    }
}
