package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.imap

data class JoinGameServerPacket(val gameServerId: UByte)

val JoinGameServerPacketCodec: Codec<JoinGameServerPacket> =
    U8.imap(
        decode = { JoinGameServerPacket(it.toUByte()) },
        encode = { it.gameServerId.toInt() },
    )
