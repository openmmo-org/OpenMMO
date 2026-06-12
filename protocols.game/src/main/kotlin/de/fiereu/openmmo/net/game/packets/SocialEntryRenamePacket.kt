package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SocialEntryRenamePacket(
    val playerId: Long,
    val errorCode: Byte,
    val name: String?,
)

object SocialEntryRenamePacketCodec : PacketCodec<SocialEntryRenamePacket>() {
    override fun CodecScope<SocialEntryRenamePacket>.body(): SocialEntryRenamePacket {
        val playerId = field(S64LE) { it.playerId }
        val errorCode = field(S8) { it.errorCode }
        val name = if (errorCode.toInt() == 0) field(Utf16LeNullTerminated) { it.name!! } else null
        return SocialEntryRenamePacket(playerId, errorCode, name)
    }
}
