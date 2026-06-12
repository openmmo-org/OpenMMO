package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class FieldMapTilePaletteApplyPacket(
    val layer: Byte,
    val keys: ByteArray,
    val values: ByteArray,
    val tiles: List<Short>,
) {
    override fun equals(other: Any?): Boolean =
        other is FieldMapTilePaletteApplyPacket &&
                layer == other.layer &&
                keys.contentEquals(other.keys) &&
                values.contentEquals(other.values) &&
                tiles == other.tiles

    override fun hashCode(): Int {
        var r = layer.toInt()
        r = r * 31 + keys.contentHashCode()
        r = r * 31 + values.contentHashCode()
        r = r * 31 + tiles.hashCode()
        return r
    }
}

object FieldMapTilePaletteApplyPacketCodec : PacketCodec<FieldMapTilePaletteApplyPacket>() {
    override fun CodecScope<FieldMapTilePaletteApplyPacket>.body(): FieldMapTilePaletteApplyPacket {
        val layer = field(S8) { it.layer }
        val keys = field(bytesPrefixed(U8)) { it.keys }
        val values = field(bytesPrefixed(U8)) { it.values }
        val tiles = field(S16LE.listPrefixed(U8)) { it.tiles }
        return FieldMapTilePaletteApplyPacket(layer, keys, values, tiles)
    }
}
