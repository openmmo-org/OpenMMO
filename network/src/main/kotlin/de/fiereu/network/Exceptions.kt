package de.fiereu.network

import kotlin.reflect.KClass

open class NetworkException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)

class UnknownOpcodeException(val opcode: UByte, val side: Side) :
    NetworkException("No incoming codec for opcode 0x${opcode.toString(16)} on $side")

class UnknownPacketTypeException(val type: KClass<*>, val side: Side) :
    NetworkException("No outgoing registration for ${type.simpleName} on $side")

class ChecksumMismatchException : NetworkException("Checksum verification failed")

class TrailingBytesException(val opcode: UByte, val remaining: Int) :
    NetworkException(
        "Codec for opcode 0x${opcode.toString(16)} did not consume $remaining trailing byte(s)")

class EmptyFrameException : NetworkException("Received empty frame")

open class HandshakeException(message: String, cause: Throwable? = null) :
    NetworkException(message, cause)

class StaleClientHelloException(val skewSeconds: Long) :
    HandshakeException("ClientHello timestamp drift: ${skewSeconds}s")

class InvalidServerSignatureException :
    HandshakeException("ServerHello signature does not match trusted root key")

class HandlerRegistrationException(message: String) : NetworkException(message)
