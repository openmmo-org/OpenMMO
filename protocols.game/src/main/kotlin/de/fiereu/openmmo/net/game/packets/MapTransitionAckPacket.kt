package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.imap

data class MapTransitionAckPacket(val kind: MapTransitionKind)

val MapTransitionAckPacketCodec: Codec<MapTransitionAckPacket> =
    MapTransitionKindCodec.imap(
        decode = { MapTransitionAckPacket(it) },
        encode = { it.kind },
    )
