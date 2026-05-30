package de.fiereu.network

import kotlin.reflect.KClass

class SessionAttribute<T : Any>(val name: String, val type: KClass<T>) {
  override fun toString(): String = "SessionAttribute($name)"

  companion object {
    inline fun <reified T : Any> of(name: String): SessionAttribute<T> =
        SessionAttribute(name, T::class)
  }
}

interface SessionAttributes {
  operator fun <T : Any> get(key: SessionAttribute<T>): T?

  operator fun <T : Any> set(key: SessionAttribute<T>, value: T)

  fun <T : Any> remove(key: SessionAttribute<T>): T?

  fun <T : Any> getOrPut(key: SessionAttribute<T>, default: () -> T): T

  fun contains(key: SessionAttribute<*>): Boolean
}
