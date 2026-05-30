package de.fiereu.network.handlers

import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import java.nio.ByteOrder

class PacketFrameDecoder(maxFrameLength: Int = 0xFFFF) :
    LengthFieldBasedFrameDecoder(
        ByteOrder.LITTLE_ENDIAN,
        maxFrameLength,
        0,
        UShort.SIZE_BYTES,
        -UShort.SIZE_BYTES,
        UShort.SIZE_BYTES,
        false,
    )
