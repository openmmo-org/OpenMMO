package de.fiereu.openmmo.net.game.packets.gtl

/** A filter whose body has not been decoded yet. */
data class GtlRawFilter(override val kind: GtlFilterKind, val payload: ByteArray) :
    GtlSearchFilter {
  override fun equals(other: Any?): Boolean =
      other is GtlRawFilter && kind == other.kind && payload.contentEquals(other.payload)

  override fun hashCode(): Int = kind.hashCode() * 31 + payload.contentHashCode()
}
