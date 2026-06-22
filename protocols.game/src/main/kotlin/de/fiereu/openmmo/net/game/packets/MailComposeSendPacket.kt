package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

sealed class MailAttachment {
    abstract val typeByte: Byte
}

data class ItemMailAttachment(
    override val typeByte: Byte,
    val itemId: Int,
) : MailAttachment()

data class PokemonQuantityMailAttachment(
    override val typeByte: Byte,
    val pokemonEntityId: Long,
    val quantity: Short,
) : MailAttachment()

data class PokemonMailAttachment(
    override val typeByte: Byte,
    val pokemonEntityId: Long,
) : MailAttachment()

private val MailAttachmentCodec: Codec<MailAttachment> =
    object : Codec<MailAttachment> {
        override fun read(buf: ReadBuffer): MailAttachment {
            throw MalformedPacketException("MailComposeSendPacket is a client-encoded packet")
        }

        override fun write(buf: WriteBuffer, value: MailAttachment) {
            S8.write(buf, value.typeByte)
            when (value) {
                is ItemMailAttachment -> S32LE.write(buf, value.itemId)
                is PokemonQuantityMailAttachment -> {
                    S64LE.write(buf, value.pokemonEntityId)
                    S16LE.write(buf, value.quantity)
                }

                is PokemonMailAttachment -> S64LE.write(buf, value.pokemonEntityId)
            }
        }
    }

data class MailComposeSendPacket(
    val recipientName: String,
    val subject: String,
    val body: String,
    val attachments: List<MailAttachment>,
)

object MailComposeSendPacketCodec : PacketCodec<MailComposeSendPacket>() {
    override fun CodecScope<MailComposeSendPacket>.body(): MailComposeSendPacket {
        val recipientName = field(Utf16LeNullTerminated) { it.recipientName }
        val subject = field(Utf16LeNullTerminated) { it.subject }
        val body = field(Utf16LeNullTerminated) { it.body }
        val attachments = field(MailAttachmentCodec.listPrefixed(U8)) { it.attachments }
        return MailComposeSendPacket(recipientName, subject, body, attachments)
    }
}
