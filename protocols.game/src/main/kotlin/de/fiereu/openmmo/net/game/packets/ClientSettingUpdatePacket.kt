package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE

data class ClientSettingUpdatePacket(val settingsValue: Int)

object ClientSettingUpdatePacketCodec : PacketCodec<ClientSettingUpdatePacket>() {
    override fun CodecScope<ClientSettingUpdatePacket>.body(): ClientSettingUpdatePacket {
        val settingsValue = field(S32LE) { it.settingsValue }
        return ClientSettingUpdatePacket(settingsValue)
    }
}
