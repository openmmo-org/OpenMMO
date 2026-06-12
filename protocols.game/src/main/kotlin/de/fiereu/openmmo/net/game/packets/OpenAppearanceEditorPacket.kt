package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class OpenAppearanceEditorPacket(
    val entityId: Long,
)

object OpenAppearanceEditorPacketCodec : PacketCodec<OpenAppearanceEditorPacket>() {
    override fun CodecScope<OpenAppearanceEditorPacket>.body(): OpenAppearanceEditorPacket {
        val entityId = field(S64LE) { it.entityId }
        return OpenAppearanceEditorPacket(entityId)
    }
}
