package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.WriteBuffer

sealed interface GtlSearchFilter {
  val kind: GtlFilterKind
}

internal val GtlSearchFilterCodec: Codec<GtlSearchFilter> =
    object : Codec<GtlSearchFilter> {
      override fun read(buf: ReadBuffer): GtlSearchFilter {
        val kind = GtlFilterKindCodec.read(buf)
        if (kind == GtlFilterKind.SPECIES) {
          return GtlSpeciesFilter(List(U16LE.read(buf)) { S16LE.read(buf) })
        }
        val payload = ByteArray(kind.payloadSize)
        buf.readBytes(payload)
        return GtlRawFilter(kind, payload)
      }

      override fun write(buf: WriteBuffer, value: GtlSearchFilter) {
        GtlFilterKindCodec.write(buf, value.kind)
        when (value) {
          is GtlSpeciesFilter -> {
            U16LE.write(buf, value.speciesIds.size)
            value.speciesIds.forEach { S16LE.write(buf, it) }
          }
          is GtlRawFilter -> buf.writeBytes(value.payload)
        }
      }
    }
