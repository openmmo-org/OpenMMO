package de.fiereu.openmmo.net.game.codecs

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.enums.SkinSlot
import java.util.*

data class Skin(val slot: SkinSlot, val type: UShort?, val color: UByte?)

class SkinSet(val rawBytes: ByteArray? = null) : EnumMap<SkinSlot, Skin>(SkinSlot::class.java) {
  fun put(skin: Skin) {
    this[skin.slot] = skin
  }

  override fun put(key: SkinSlot, value: Skin): Skin? {
    require(key == value.slot) { "Slot mismatch: $key != ${value.slot}" }
    return super.put(key, value)
  }
}

class SkinSetCodec(
    private val slots: List<SkinSlot> = SkinSlot.entries,
    private val withLeadingByte: Boolean = true,
) : PacketCodec<SkinSet>() {
  override fun CodecScope<SkinSet>.body(): SkinSet {
    if (withLeadingByte) field(U8) { 0 }
    val mask = field(U16LE) { slots.fold(0) { acc, slot -> acc or (1 shl slot.ordinal) } }
    val skins = SkinSet()
    slots.forEach { slot ->
      if ((mask and (1 shl slot.ordinal)) != 0) {
        val compressed =
            field(U16LE) {
              val skin = it[slot] ?: Skin(slot, 0u, 0u)
              val type = skin.type ?: 0x3FFFu
              val color = skin.color ?: 0x3Fu
              require(type <= 0x3FFFu) { "Skin type too large: ${skin.type}" }
              require(color <= 0x3Fu) { "Skin color too large: ${skin.color}" }
              (type and 0x3FFFu).toInt() or ((color and 0x3Fu).toInt() shl 10)
            }
        val type = (compressed and 0x3FFF).toUShort()
        val color = ((compressed shr 10) and 0x3F).toUByte()
        skins.put(Skin(slot, type, color))
      }
    }
    return skins
  }
}

private object RawSkinBytesCodec : Codec<ByteArray> {
  override fun read(buf: ReadBuffer): ByteArray {
    val bytes = ByteArray(buf.remaining())
    buf.readBytes(bytes)
    return bytes
  }

  override fun write(buf: WriteBuffer, value: ByteArray) {
    buf.writeBytes(value)
  }
}

fun opaqueSkinSet(bytes: ByteArray): SkinSet = SkinSet(bytes.copyOf())

private val ParsedDefaultSkinSetCodec: Codec<SkinSet> = SkinSetCodec()

val DefaultSkinSetCodec: Codec<SkinSet> =
    object : Codec<SkinSet> {
      override fun read(buf: ReadBuffer): SkinSet = ParsedDefaultSkinSetCodec.read(buf)

      override fun write(buf: WriteBuffer, value: SkinSet) {
        val rawBytes = value.rawBytes
        if (rawBytes != null) {
          RawSkinBytesCodec.write(buf, rawBytes)
        } else {
          ParsedDefaultSkinSetCodec.write(buf, value)
        }
      }
    }
val SkinSetCodecNoLeading: Codec<SkinSet> = SkinSetCodec(withLeadingByte = false)
