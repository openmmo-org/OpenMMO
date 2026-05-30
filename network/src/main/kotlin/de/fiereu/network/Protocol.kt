package de.fiereu.network

import de.fiereu.bytecodec.Codec
import kotlin.reflect.KClass

abstract class Protocol {

  open val compressed: Boolean = false

  data class Registration<T : Any>(
      val opcode: UByte,
      val direction: Direction,
      val codec: Codec<T>,
      val type: KClass<T>,
  )

  private val mutableRegistrations = mutableListOf<Registration<*>>()
  private val byOpcode = mutableMapOf<Pair<UByte, Direction>, Registration<*>>()
  private val byType = mutableMapOf<Pair<KClass<*>, Direction>, Registration<*>>()

  val registrations: List<Registration<*>>
    get() = mutableRegistrations

  fun <T : Any> c2s(opcode: UByte, codec: Codec<T>, type: KClass<T>) {
    register(Registration(opcode, Direction.C2S, codec, type))
  }

  fun <T : Any> s2c(opcode: UByte, codec: Codec<T>, type: KClass<T>) {
    register(Registration(opcode, Direction.S2C, codec, type))
  }

  fun <T : Any> bidi(opcode: UByte, codec: Codec<T>, type: KClass<T>) {
    register(Registration(opcode, Direction.C2S, codec, type))
    register(Registration(opcode, Direction.S2C, codec, type))
  }

  private fun register(reg: Registration<*>) {
    val opKey = reg.opcode to reg.direction
    check(byOpcode.put(opKey, reg) == null) {
      "Duplicate registration for opcode 0x${reg.opcode.toString(16)} ${reg.direction}"
    }
    val typeKey = reg.type to reg.direction
    check(byType.put(typeKey, reg) == null) {
      "Duplicate registration for ${reg.type.simpleName} ${reg.direction}"
    }
    mutableRegistrations += reg
  }

  fun incomingCodec(side: Side, opcode: UByte): Codec<*>? =
      incomingRegistration(side, opcode)?.codec

  fun incomingRegistration(side: Side, opcode: UByte): Registration<*>? {
    val direction = if (side == Side.SERVER) Direction.C2S else Direction.S2C
    return byOpcode[opcode to direction]
  }

  fun outgoingRegistration(side: Side, type: KClass<*>): Registration<*>? {
    val direction = if (side == Side.SERVER) Direction.S2C else Direction.C2S
    return byType[type to direction]
  }
}

inline fun <reified T : Any> Protocol.c2s(opcode: UByte, codec: Codec<T>) {
  c2s(opcode, codec, T::class)
}

inline fun <reified T : Any> Protocol.s2c(opcode: UByte, codec: Codec<T>) {
  s2c(opcode, codec, T::class)
}

inline fun <reified T : Any> Protocol.bidi(opcode: UByte, codec: Codec<T>) {
  bidi(opcode, codec, T::class)
}
