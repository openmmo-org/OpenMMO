package de.fiereu.bytecodec

import java.nio.charset.Charset

fun <T, R> Codec<T>.imap(decode: (T) -> R, encode: (R) -> T): Codec<R> {
  val inner = this
  return object : Codec<R> {
    override fun read(buf: ReadBuffer): R = decode(inner.read(buf))

    override fun write(buf: WriteBuffer, value: R) {
      inner.write(buf, encode(value))
    }
  }
}

fun <T, R> Codec<T>.imapCatching(
    decode: (T) -> R,
    encode: (R) -> T,
    onError: (T, Throwable) -> String = { v, _ -> "Cannot decode $v" },
): Codec<R> {
  val inner = this
  return object : Codec<R> {
    override fun read(buf: ReadBuffer): R {
      val raw = inner.read(buf)
      return try {
        decode(raw)
      } catch (e: CodecException) {
        throw e
      } catch (e: Throwable) {
        throw MalformedPacketException(onError(raw, e), e)
      }
    }

    override fun write(buf: WriteBuffer, value: R) {
      inner.write(buf, encode(value))
    }
  }
}

fun <T> Codec<T>.listPrefixed(prefix: Codec<Int>): Codec<List<T>> {
  val inner = this
  return object : Codec<List<T>> {
    override fun read(buf: ReadBuffer): List<T> {
      val n = prefix.read(buf)
      if (n < 0) throw MalformedPacketException("negative list length: $n")
      val list = ArrayList<T>(n.coerceAtMost(1024))
      repeat(n) { list.add(inner.read(buf)) }
      return list
    }

    override fun write(buf: WriteBuffer, value: List<T>) {
      prefix.write(buf, value.size)
      for (item in value) inner.write(buf, item)
    }
  }
}

fun <T> Codec<T>.repeat(count: Int): Codec<List<T>> {
  require(count >= 0) { "repeat negative count: $count" }
  val inner = this
  return object : Codec<List<T>> {
    override fun read(buf: ReadBuffer): List<T> = List(count) { inner.read(buf) }

    override fun write(buf: WriteBuffer, value: List<T>) {
      if (value.size != count) {
        throw MalformedPacketException("repeat($count) got ${value.size}")
      }
      for (item in value) inner.write(buf, item)
    }
  }
}

fun bytesPrefixed(prefix: Codec<Int>): Codec<ByteArray> =
    object : Codec<ByteArray> {
      override fun read(buf: ReadBuffer): ByteArray {
        val n = prefix.read(buf)
        if (n < 0) throw MalformedPacketException("negative byte length: $n")
        val arr = ByteArray(n)
        if (n > 0) buf.readBytes(arr)
        return arr
      }

      override fun write(buf: WriteBuffer, value: ByteArray) {
        prefix.write(buf, value.size)
        if (value.isNotEmpty()) buf.writeBytes(value)
      }
    }

fun stringPrefixed(prefix: Codec<Int>, charset: Charset): Codec<String> =
    object : Codec<String> {
      override fun read(buf: ReadBuffer): String {
        val n = prefix.read(buf)
        if (n < 0) throw MalformedPacketException("negative string length: $n")
        val arr = ByteArray(n)
        if (n > 0) buf.readBytes(arr)
        return String(arr, charset)
      }

      override fun write(buf: WriteBuffer, value: String) {
        val arr = value.toByteArray(charset)
        prefix.write(buf, arr.size)
        if (arr.isNotEmpty()) buf.writeBytes(arr)
      }
    }

inline fun <reified E : Enum<E>> enumByOrdinalByte(): Codec<E> {
  val values = enumValues<E>()
  val name = E::class.simpleName ?: "enum"
  return U8.imapCatching(
      decode = { ord ->
        if (ord !in values.indices) {
          throw MalformedPacketException("ordinal $ord out of range for $name")
        }
        values[ord]
      },
      encode = { it.ordinal },
  )
}

inline fun <reified E : Enum<E>> enumByOrdinalU16LE(): Codec<E> {
  val values = enumValues<E>()
  val name = E::class.simpleName ?: "enum"
  return U16LE.imapCatching(
      decode = { ord ->
        if (ord !in values.indices) {
          throw MalformedPacketException("ordinal $ord out of range for $name")
        }
        values[ord]
      },
      encode = { it.ordinal },
  )
}

fun <E : Enum<E>> enumBy(
    prefix: Codec<Int>,
    lookup: (Int) -> E,
    keyOf: (E) -> Int,
): Codec<E> =
    object : Codec<E> {
      override fun read(buf: ReadBuffer): E {
        val key = prefix.read(buf)
        return try {
          lookup(key)
        } catch (e: CodecException) {
          throw e
        } catch (e: Throwable) {
          throw MalformedPacketException("no enum value for key $key", e)
        }
      }

      override fun write(buf: WriteBuffer, value: E) {
        prefix.write(buf, keyOf(value))
      }
    }

fun <T : Any> choose(
    tag: Codec<Int>,
    identify: (T) -> Int,
    vararg branches: Pair<Int, Codec<out T>>,
): Codec<T> {
  val byTag: Map<Int, Codec<out T>> = branches.toMap()
  require(byTag.size == branches.size) { "duplicate branch tags in choose" }
  return object : Codec<T> {
    override fun read(buf: ReadBuffer): T {
      val t = tag.read(buf)
      val branch = byTag[t] ?: throw UnknownTagException(t)
      return branch.read(buf)
    }

    override fun write(buf: WriteBuffer, value: T) {
      val t = identify(value)
      val branch =
          byTag[t]
              ?: throw MalformedPacketException("identify returned unknown tag 0x${t.toString(16)}")
      tag.write(buf, t)
      @Suppress("UNCHECKED_CAST") (branch as Codec<T>).write(buf, value)
    }
  }
}

fun <T : Any> Codec<T>.optional(flag: Codec<Boolean> = Bool): Codec<T?> {
  val inner = this
  return object : Codec<T?> {
    override fun read(buf: ReadBuffer): T? {
      val present = flag.read(buf)
      return if (present) inner.read(buf) else null
    }

    override fun write(buf: WriteBuffer, value: T?) {
      flag.write(buf, value != null)
      if (value != null) inner.write(buf, value)
    }
  }
}

fun <A, B> Codec<A>.then(other: Codec<B>): Codec<Pair<A, B>> {
  val first = this
  return object : Codec<Pair<A, B>> {
    override fun read(buf: ReadBuffer): Pair<A, B> = first.read(buf) to other.read(buf)

    override fun write(buf: WriteBuffer, value: Pair<A, B>) {
      first.write(buf, value.first)
      other.write(buf, value.second)
    }
  }
}

interface BitsBuilder {
  fun bit(index: Int, name: String)
}

class BitSet32
internal constructor(
    internal val raw: Int,
    internal val byName: Map<String, Int>,
) {
  operator fun get(index: Int): Boolean {
    require(index in 0..31) { "bit index out of range: $index" }
    return (raw ushr index) and 1 != 0
  }

  operator fun get(name: String): Boolean {
    val i = byName[name] ?: error("unknown bit name: $name")
    return get(i)
  }

  fun toRaw(): Int = raw

  override fun equals(other: Any?): Boolean = other is BitSet32 && other.raw == raw

  override fun hashCode(): Int = raw
}

class BitsCodec
internal constructor(
    private val backing: Codec<Int>,
    private val byName: Map<String, Int>,
) : Codec<BitSet32> {
  override fun read(buf: ReadBuffer): BitSet32 = BitSet32(backing.read(buf), byName)

  override fun write(buf: WriteBuffer, value: BitSet32) {
    backing.write(buf, value.raw)
  }

  fun of(vararg bits: Pair<String, Boolean>): BitSet32 {
    var raw = 0
    for ((name, set) in bits) {
      val i = byName[name] ?: error("unknown bit name: $name")
      if (set) raw = raw or (1 shl i)
    }
    return BitSet32(raw, byName)
  }
}

fun bitsLE(backing: Codec<Int>, builder: BitsBuilder.() -> Unit): BitsCodec {
  val map = LinkedHashMap<String, Int>()
  val b =
      object : BitsBuilder {
        override fun bit(index: Int, name: String) {
          require(index in 0..31) { "bit index out of range: $index" }
          require(name !in map) { "duplicate bit name: $name" }
          map[name] = index
        }
      }
  b.builder()
  return BitsCodec(backing, map)
}
