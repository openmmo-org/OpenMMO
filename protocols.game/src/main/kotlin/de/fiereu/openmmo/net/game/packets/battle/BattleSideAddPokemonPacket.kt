package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleAddStatusEffect(
    val durationTurns: Int,
    val effectType: Byte,
    val sourceSlot: Byte,
)

data class BattleAddPokemon(
    val entityId: Long,
    val frontSpriteId: Short,
    val backSpriteId: Short,
    val side: Byte,
    val slot: Byte,
    val partyIndex: Byte,
    val statusEffect: BattleAddStatusEffect?,
)

private val BattleAddPokemonCodec: Codec<BattleAddPokemon> =
    object : PacketCodec<BattleAddPokemon>() {
      override fun CodecScope<BattleAddPokemon>.body(): BattleAddPokemon {
        val flags =
            field(U8) {
              (if (it.slot.toInt() != 0) 4 else 0) or
                  (if (it.partyIndex.toInt() != -1) 8 else 0) or
                  (if (it.statusEffect != null) 16 else 0)
            }
        val entityId = field(S64LE) { it.entityId }
        if (flags and 1 != 0) field(S64LE) { 0L }
        val frontSpriteId = field(S16LE) { it.frontSpriteId }
        val backSpriteId = field(S16LE) { it.backSpriteId }
        val side = field(S8) { it.side }
        if (flags and 2 != 0) field(S8) { 0 }
        val slot = if (flags and 4 != 0) field(S8) { it.slot } else 0
        val partyIndex = if (flags and 8 != 0) field(S8) { it.partyIndex } else -1
        val statusEffect =
            if (flags and 16 != 0) {
              val durationTurns = field(S32LE) { it.statusEffect!!.durationTurns }
              val effectType = field(S8) { it.statusEffect!!.effectType }
              val sourceSlot = field(S8) { it.statusEffect!!.sourceSlot }
              BattleAddStatusEffect(durationTurns, effectType, sourceSlot)
            } else null
        return BattleAddPokemon(
            entityId, frontSpriteId, backSpriteId, side, slot, partyIndex, statusEffect)
      }
    }

data class BattleSideAddPokemonPacket(
    val side: Byte,
    val pokemon: BattleAddPokemon,
)

object BattleSideAddPokemonPacketCodec : PacketCodec<BattleSideAddPokemonPacket>() {
  override fun CodecScope<BattleSideAddPokemonPacket>.body(): BattleSideAddPokemonPacket {
    val side = field(S8) { it.side }
    val pokemon = field(BattleAddPokemonCodec) { it.pokemon }
    return BattleSideAddPokemonPacket(side, pokemon)
  }
}
