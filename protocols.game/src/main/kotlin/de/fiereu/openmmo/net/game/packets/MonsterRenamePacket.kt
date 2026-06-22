package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class MonsterRenamePacket(val pokemonEntityId: Long, val nickname: String)

object MonsterRenamePacketCodec : PacketCodec<MonsterRenamePacket>() {
    override fun CodecScope<MonsterRenamePacket>.body(): MonsterRenamePacket {
        val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
        val nickname = field(Utf16LeNullTerminated) { it.nickname }
        return MonsterRenamePacket(pokemonEntityId, nickname)
    }
}
