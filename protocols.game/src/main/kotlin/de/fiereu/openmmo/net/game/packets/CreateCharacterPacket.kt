package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.WriteBuffer

data class CreateCharacterPacket(val name: String, val gender: Byte, val cosmetics: ByteArray)

object RemainingBytesCodec : Codec<ByteArray> {
  override fun read(buf: ReadBuffer): ByteArray {
    val n = buf.remaining()
    val arr = ByteArray(n)
    buf.readBytes(arr)
    return arr
  }

  override fun write(buf: WriteBuffer, value: ByteArray) {
    buf.writeBytes(value)
  }
}

object CreateCharacterPacketCodec : PacketCodec<CreateCharacterPacket>() {
  override fun CodecScope<CreateCharacterPacket>.body(): CreateCharacterPacket {
    val name = field(Utf16LeNullTerminated) { it.name }
    val gender = field(S8) { it.gender }
    val cosmetics = field(RemainingBytesCodec) { it.cosmetics }
    return CreateCharacterPacket(name, gender, cosmetics)
  }
}
