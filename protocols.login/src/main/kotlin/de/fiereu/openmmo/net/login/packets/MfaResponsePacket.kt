package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.imap

data class MfaResponsePacket(val mfaCode: String)

val MfaResponsePacketCodec: Codec<MfaResponsePacket> =
    Utf16LeNullTerminated.imap(
        decode = { MfaResponsePacket(it) },
        encode = { it.mfaCode },
    )
