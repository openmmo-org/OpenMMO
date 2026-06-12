package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class AppearanceMoveEntry(val slot: Int, val packedMove: Short, val ppUp: Byte?)

data class AppearanceMoveSet(val categoryByte: Byte, val moves: List<AppearanceMoveEntry>)

data class CharacterAppearanceApplyPacket(
    val characterName: String,
    val appearanceTypeA: Byte,
    val appearanceTypeB: Byte,
    val creatureMoves: AppearanceMoveSet,
)

object CharacterAppearanceApplyPacketCodec : PacketCodec<CharacterAppearanceApplyPacket>() {
    override fun CodecScope<CharacterAppearanceApplyPacket>.body(): CharacterAppearanceApplyPacket {
        val characterName = field(Utf16LeNullTerminated) { it.characterName }
        val appearanceTypeA = field(S8) { it.appearanceTypeA }
        val appearanceTypeB = field(S8) { it.appearanceTypeB }

        val categoryByte = field(S8) { it.creatureMoves.categoryByte }
        val bitmask =
            field(S16LE) {
                var m = 0
                for (move in it.creatureMoves.moves) {
                    m = m or (1 shl move.slot)
                    if (move.ppUp != null) m = m or 0x8000
                }
                m.toShort()
            }
        val mask = bitmask.toInt() and 0xFFFF
        val hasPp = (mask and 0x8000) != 0
        val moves = mutableListOf<AppearanceMoveEntry>()
        for (slot in 0 until 15) {
            if (mask and (1 shl slot) != 0) {
                val packed = field(S16LE) { v -> v.creatureMoves.moves.first { it.slot == slot }.packedMove }
                val pp: Byte? =
                    if (hasPp) field(S8) { v -> v.creatureMoves.moves.first { it.slot == slot }.ppUp ?: 0 }
                    else null
                moves.add(AppearanceMoveEntry(slot, packed, pp))
            }
        }
        return CharacterAppearanceApplyPacket(
            characterName,
            appearanceTypeA,
            appearanceTypeB,
            AppearanceMoveSet(categoryByte, moves),
        )
    }
}
