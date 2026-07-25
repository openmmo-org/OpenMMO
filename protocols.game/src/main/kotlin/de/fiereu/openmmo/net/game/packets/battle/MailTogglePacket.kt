package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class MailTogglePacket(
    val shown: Boolean,
)

object MailTogglePacketCodec : PacketCodec<MailTogglePacket>() {
  override fun CodecScope<MailTogglePacket>.body(): MailTogglePacket {
    val shown = field(Bool) { it.shown }
    return MailTogglePacket(shown)
  }
}
