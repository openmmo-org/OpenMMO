package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.imap

data class MapTransitionAckPacket(val unk: Int = 0)

val MapTransitionAckPacketCodec: Codec<MapTransitionAckPacket> =
    U8.imap(
        decode = { MapTransitionAckPacket(it) },
        encode = { it.unk },
    )
