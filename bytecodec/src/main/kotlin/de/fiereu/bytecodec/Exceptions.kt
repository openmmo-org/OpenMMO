package de.fiereu.bytecodec

open class CodecException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)

class MalformedPacketException(message: String, cause: Throwable? = null) :
    CodecException(message, cause)

class UnknownTagException(val tag: Int) : CodecException("No branch for tag 0x${tag.toString(16)}")

class ReservedByteMismatchException(val expected: Int, val actual: Int) :
    CodecException(
        "Expected reserved 0x${expected.toString(16)}, got 0x${actual.toString(16)}",
    )
