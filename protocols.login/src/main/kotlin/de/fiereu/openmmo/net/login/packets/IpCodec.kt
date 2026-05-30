package de.fiereu.openmmo.net.login.packets

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.github.maltalex.ineter.base.IPv6Address
import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.MalformedPacketException
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.WriteBuffer

object TaggedIpCodec : Codec<IPAddress> {
  override fun read(buf: ReadBuffer): IPAddress {
    val type = U8.read(buf)
    return when (type) {
      4 -> IPv4Address.of(S32LE.read(buf))
      6 -> {
        val upper = S64LE.read(buf)
        val lower = S64LE.read(buf)
        IPv6Address.of(upper, lower)
      }
      else -> throw MalformedPacketException("Unknown IP address type: $type")
    }
  }

  override fun write(buf: WriteBuffer, value: IPAddress) {
    when (value) {
      is IPv4Address -> {
        U8.write(buf, 4)
        S32LE.write(buf, value.toInt())
      }
      is IPv6Address -> {
        U8.write(buf, 6)
        S64LE.write(buf, value.upper)
        S64LE.write(buf, value.lower)
      }
      else -> throw MalformedPacketException("Unknown IP address type: ${value.javaClass}")
    }
  }
}
