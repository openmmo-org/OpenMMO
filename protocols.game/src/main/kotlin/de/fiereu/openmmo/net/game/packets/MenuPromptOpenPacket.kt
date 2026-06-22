package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityRelationStat(
    val stat: Byte,
    val value: Byte,
    val relatedIds: List<Long>,
)

data class EntityRelation(
    val entityId: Long,
    val stats: List<EntityRelationStat>,
)

data class MenuPromptOpenPacket(
    val menuKind: Byte,
    val promptType: Byte?,
    val value: Short?,
    val relations: List<EntityRelation>?,
)

private val EntityRelationStatCodec: Codec<EntityRelationStat> =
    object : PacketCodec<EntityRelationStat>() {
      override fun CodecScope<EntityRelationStat>.body(): EntityRelationStat {
        val stat = field(S8) { it.stat }
        val value = field(S8) { it.value }
        val relatedIds = field(S64LE.listPrefixed(U8)) { it.relatedIds }
        return EntityRelationStat(stat, value, relatedIds)
      }
    }

private val EntityRelationCodec: Codec<EntityRelation> =
    object : PacketCodec<EntityRelation>() {
      override fun CodecScope<EntityRelation>.body(): EntityRelation {
        val entityId = field(S64LE) { it.entityId }
        val stats = field(EntityRelationStatCodec.listPrefixed(U8)) { it.stats }
        return EntityRelation(entityId, stats)
      }
    }

object MenuPromptOpenPacketCodec : PacketCodec<MenuPromptOpenPacket>() {
  override fun CodecScope<MenuPromptOpenPacket>.body(): MenuPromptOpenPacket {
    val menuKind = field(S8) { it.menuKind }
    var promptType: Byte? = null
    var value: Short? = null
    var relations: List<EntityRelation>? = null
    when {
      menuKind.toInt() == 6 -> {
        relations = field(EntityRelationCodec.listPrefixed(U8)) { it.relations!! }
      }

      menuKind.toInt() in 3..5 -> {
        promptType = field(S8) { it.promptType!! }
        value = field(S16LE) { it.value!! }
      }

      menuKind.toInt() in 1..2 -> {
        promptType = field(S8) { it.promptType!! }
      }
    }
    return MenuPromptOpenPacket(menuKind, promptType, value, relations)
  }
}
