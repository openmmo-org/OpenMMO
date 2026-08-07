package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.enumBy

enum class MapTransitionKind(val id: Int) {
  WARP(1),
  WORLD_ENTRY(2),
}

val MapTransitionKindCodec: Codec<MapTransitionKind> =
    enumBy(
        U8, lookup = { id -> MapTransitionKind.entries.first { it.id == id } }, keyOf = { it.id })
