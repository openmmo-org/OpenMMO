package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.imap

data class DialogOptionPacket(val data: Int)

val DialogOptionPacketCodec: Codec<DialogOptionPacket> =
    S32LE.imap(
        decode = { DialogOptionPacket(it) },
        encode = { it.data },
    )
