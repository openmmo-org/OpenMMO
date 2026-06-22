package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

sealed class DialogMessageArg

object NullMessageArg : DialogMessageArg()

data class CreatureDataArg(val value: Int, val args: List<DialogMessageArg>) : DialogMessageArg()

data class CreatureMovesArg(val team: Byte, val moveIds: List<Short>) : DialogMessageArg() {
    override fun equals(other: Any?): Boolean =
        other is CreatureMovesArg && team == other.team && moveIds == other.moveIds

    override fun hashCode(): Int = team * 31 + moveIds.hashCode()
}

data class MoveAnimationArg(val sourceSlot: Byte, val targetSlot: Byte, val moveId: Short) :
    DialogMessageArg()

data class TextCreatureArg(
    val slot: Byte,
    val statusId: Byte,
    val value1: Short,
    val value2: Short,
) : DialogMessageArg()

private val DialogMessageArgCodec: Codec<DialogMessageArg> =
    object : Codec<DialogMessageArg> {
        override fun read(buf: ReadBuffer): DialogMessageArg =
            when (val tag = buf.readByte().toInt()) {
                -1 -> NullMessageArg
                0 -> {
                    val value = S32LE.read(buf)
                    val count = U8.read(buf)
                    CreatureDataArg(value, List(count) { read(buf) })
                }

                1 -> {
                    val team = buf.readByte()
                    val count = U8.read(buf)
                    CreatureMovesArg(team, List(count) { S16LE.read(buf) })
                }

                2 -> {
                    val moveId = S16LE.read(buf)
                    MoveAnimationArg(buf.readByte(), buf.readByte(), moveId)
                }

                3 -> TextCreatureArg(buf.readByte(), buf.readByte(), S16LE.read(buf), S16LE.read(buf))
                else -> throw MalformedPacketException("unknown dialog message arg tag $tag")
            }

        override fun write(buf: WriteBuffer, value: DialogMessageArg) {
            when (value) {
                is NullMessageArg -> buf.writeByte((-1).toByte())
                is CreatureDataArg -> {
                    buf.writeByte(0)
                    S32LE.write(buf, value.value)
                    U8.write(buf, value.args.size)
                    value.args.forEach { write(buf, it) }
                }

                is CreatureMovesArg -> {
                    buf.writeByte(1)
                    buf.writeByte(value.team)
                    U8.write(buf, value.moveIds.size)
                    value.moveIds.forEach { S16LE.write(buf, it) }
                }

                is MoveAnimationArg -> {
                    buf.writeByte(2)
                    S16LE.write(buf, value.moveId)
                    buf.writeByte(value.sourceSlot)
                    buf.writeByte(value.targetSlot)
                }

                is TextCreatureArg -> {
                    buf.writeByte(3)
                    buf.writeByte(value.slot)
                    buf.writeByte(value.statusId)
                    S16LE.write(buf, value.value1)
                    S16LE.write(buf, value.value2)
                }
            }
        }
    }

private val DialogActionDetail: Codec<ByteArray> =
    object : Codec<ByteArray> {
        override fun read(buf: ReadBuffer): ByteArray {
            val data = ByteArray(buf.remaining())
            if (data.isNotEmpty()) buf.readBytes(data)
            return data
        }

        override fun write(buf: WriteBuffer, value: ByteArray) {
            if (value.isNotEmpty()) buf.writeBytes(value)
        }
    }

data class DialogActionPacket(
    val flags: Byte,
    val actionType: Byte,
    val textId: Int,
    val entityId: Long,
    val contextValue: Int,
    val messageArgs: List<DialogMessageArg>,
    val detail: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is DialogActionPacket &&
                flags == other.flags &&
                actionType == other.actionType &&
                textId == other.textId &&
                entityId == other.entityId &&
                contextValue == other.contextValue &&
                messageArgs == other.messageArgs &&
                detail.contentEquals(other.detail)

    override fun hashCode(): Int {
        var r = flags.toInt()
        r = r * 31 + actionType
        r = r * 31 + textId
        r = r * 31 + entityId.hashCode()
        r = r * 31 + contextValue
        r = r * 31 + messageArgs.hashCode()
        r = r * 31 + detail.contentHashCode()
        return r
    }
}

object DialogActionPacketCodec : PacketCodec<DialogActionPacket>() {
    override fun CodecScope<DialogActionPacket>.body(): DialogActionPacket {
        val flags = field(S8) { it.flags }
        val actionType = field(S8) { it.actionType }
        val textId = field(S32LE) { it.textId }
        val entityId = field(S64LE) { it.entityId }
        val contextValue = field(S32LE) { it.contextValue }
        val messageArgs = field(DialogMessageArgCodec.listPrefixed(U8)) { it.messageArgs }
        val detail = field(DialogActionDetail) { it.detail }
        return DialogActionPacket(
            flags = flags,
            actionType = actionType,
            textId = textId,
            entityId = entityId,
            contextValue = contextValue,
            messageArgs = messageArgs,
            detail = detail,
        )
    }
}