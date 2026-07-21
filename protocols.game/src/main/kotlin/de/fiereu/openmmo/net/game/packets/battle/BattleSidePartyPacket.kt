package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattlePartyStatusEffect(
    val durationTurns: Int,
    val effectType: Byte,
    val sourceSlot: Byte,
)

data class BattlePartyPokemon(
    val entityId: Long,
    val frontSpriteId: Short,
    val backSpriteId: Short,
    val side: Byte,
    val slot: Byte,
    val partyIndex: Byte,
    val statusEffect: BattlePartyStatusEffect?,
)

private val BattlePartyPokemonCodec: Codec<BattlePartyPokemon> =
    object : PacketCodec<BattlePartyPokemon>() {
      override fun CodecScope<BattlePartyPokemon>.body(): BattlePartyPokemon {
        // Flag bits 1 and 2 exist in the protocol but are never written. Their fields are
        // only skipped over when reading.
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
              BattlePartyStatusEffect(durationTurns, effectType, sourceSlot)
            } else null
        return BattlePartyPokemon(
            entityId, frontSpriteId, backSpriteId, side, slot, partyIndex, statusEffect)
      }
    }

data class BattleSidePartyPacket(
    val side: Byte,
    val replace: Boolean,
    val pokemon: List<BattlePartyPokemon>,
)

/**
 * A single bag item stack carried by opcode 0x40 at join. It shares the wire shape of a battle-side
 * monster and decodes through the same codec. Item object ids end in 0x5000 where monster uids end
 * in 0xC000.
 */
data class ItemStack(val objectId: Long, val itemId: Short, val quantity: Short)

/**
 * Builds the opcode 0x40 item snapshot from bag stacks. The monster fields carry item data: entity
 * id is the stack object id, front sprite the item id, back sprite the quantity.
 */
fun itemStacksPacket(stacks: List<ItemStack>): BattleSidePartyPacket =
    BattleSidePartyPacket(
        side = 1,
        replace = true,
        pokemon =
            stacks.map { stack ->
              BattlePartyPokemon(
                  entityId = stack.objectId,
                  frontSpriteId = stack.itemId,
                  backSpriteId = stack.quantity,
                  side = 1,
                  slot = 0,
                  partyIndex = -1,
                  statusEffect = null,
              )
            },
    )

object BattleSidePartyPacketCodec : PacketCodec<BattleSidePartyPacket>() {
  override fun CodecScope<BattleSidePartyPacket>.body(): BattleSidePartyPacket {
    val side = field(S8) { it.side }
    val replace = field(U8) { if (it.replace) 1 else 0 } == 1
    val pokemon = field(BattlePartyPokemonCodec.listPrefixed(U16LE)) { it.pokemon }
    return BattleSidePartyPacket(side, replace, pokemon)
  }
}
