package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.imap

data class ToSConfirmationPacket(val confirmationKey: Byte)

val ToSConfirmationPacketCodec: Codec<ToSConfirmationPacket> =
    S8.imap(
        decode = { ToSConfirmationPacket(it) },
        encode = { it.confirmationKey },
    )
