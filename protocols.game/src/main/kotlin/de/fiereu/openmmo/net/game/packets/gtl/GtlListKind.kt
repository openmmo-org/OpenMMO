package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.enumBy

/** The board a request or a page belongs to. It also decides how a listing row is laid out. */
enum class GtlListKind(val id: Int) {
  POKEMON(0),
  ITEM(1),
  /** Every captured page of these is empty, so the row layout is unverified. */
  OWN_LISTINGS(2),
}

val GtlListKindCodec: Codec<GtlListKind> =
    enumBy(U8, lookup = { id -> GtlListKind.entries.first { it.id == id } }, keyOf = { it.id })
