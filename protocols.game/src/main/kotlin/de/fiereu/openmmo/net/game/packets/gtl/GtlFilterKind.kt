package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.enumBy

/**
 * A search filter. [payloadSize] is how many bytes follow the id, so a filter list can be walked
 * without knowing what each one means. [SPECIES] sizes itself instead.
 */
enum class GtlFilterKind(val id: Int, val payloadSize: Int) {
  SPECIES(0, VARIABLE),
  GENDER(1, 1),
  NATURE(2, 1),
  MIN_LEVEL(3, 1),
  MAX_LEVEL(4, 1),
  IV(5, 3),
  EGG_TYPE(7, 1),
  SHINY(8, 1),
  MIN_PRICE(9, 4),
  MAX_PRICE(10, 4),
  TIER(11, 1),
  UNKNOWN_12(12, 1),
  UNKNOWN_13(13, 1),
}

private const val VARIABLE = -1

val GtlFilterKindCodec: Codec<GtlFilterKind> =
    enumBy(U8, lookup = { id -> GtlFilterKind.entries.first { it.id == id } }, keyOf = { it.id })
