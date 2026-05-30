package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.imap

data class DialogStatePacket(val active: Boolean)

val DialogStatePacketCodec: Codec<DialogStatePacket> =
    Bool.imap(
        decode = { DialogStatePacket(it) },
        encode = { it.active },
    )
