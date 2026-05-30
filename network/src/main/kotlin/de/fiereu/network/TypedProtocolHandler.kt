package de.fiereu.network

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.reflect.KClass

private val log = KotlinLogging.logger {}

abstract class TypedProtocolHandler<P : Protocol>(
    protocol: P,
    side: Side,
) : ProtocolHandler(protocol, side) {

  @Volatile private var sealed = false

  private val handlers = mutableMapOf<KClass<*>, (PacketEvent<*>) -> Unit>()

  protected inline fun <reified T : Any> on(noinline handler: (PacketEvent<T>) -> Unit) {
    register(T::class, handler)
  }

  @PublishedApi
  internal fun <T : Any> register(type: KClass<T>, handler: (PacketEvent<T>) -> Unit) {
    if (sealed) {
      throw HandlerRegistrationException(
          "Cannot register handler for ${type.simpleName} after the first packet",
      )
    }
    if (handlers.containsKey(type)) {
      throw HandlerRegistrationException(
          "Duplicate handler registration for ${type.simpleName}",
      )
    }
    @Suppress("UNCHECKED_CAST")
    handlers[type] = handler as (PacketEvent<*>) -> Unit
  }

  open fun isRegistered(type: KClass<*>): Boolean = handlers.containsKey(type)

  protected open fun onUnhandled(event: PacketEvent<*>) {
    log.error { "Unhandled packet ${event.packet::class.simpleName} on $side" }
  }

  final override fun onPacket(event: PacketEvent<*>) {
    sealed = true
    val handler = handlers[event.packet::class]
    if (handler != null) {
      handler(event)
    } else if (!tryHandleAlternate(event)) {
      onUnhandled(event)
    }
  }

  protected open fun tryHandleAlternate(event: PacketEvent<*>): Boolean = false
}
