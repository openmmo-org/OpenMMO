package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class MenuPromptClosePacket(
    val promptType: Byte,
)

object MenuPromptClosePacketCodec : PacketCodec<MenuPromptClosePacket>() {
  override fun CodecScope<MenuPromptClosePacket>.body(): MenuPromptClosePacket {
    val promptType = field(S8) { it.promptType }
    return MenuPromptClosePacket(promptType)
  }
}
