package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class MailAttachmentRequestPacket(
    val targetId: Long,
)

object MailAttachmentRequestPacketCodec : PacketCodec<MailAttachmentRequestPacket>() {
  override fun CodecScope<MailAttachmentRequestPacket>.body(): MailAttachmentRequestPacket {
    val targetId = field(S64LE) { it.targetId }
    return MailAttachmentRequestPacket(targetId)
  }
}
