package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityChecklistItem(
    val entityId: Long,
    val checked: Boolean,
)

data class EntityChecklistPromptPacket(
    val titleId: Int,
    val kind: Byte?,
    val items: List<EntityChecklistItem>?,
)

private val EntityChecklistItemCodec: Codec<EntityChecklistItem> =
    object : PacketCodec<EntityChecklistItem>() {
        override fun CodecScope<EntityChecklistItem>.body(): EntityChecklistItem {
            val entityId = field(S64LE) { it.entityId }
            val checked = field(U8) { if (it.checked) 1 else 0 } == 1
            return EntityChecklistItem(entityId, checked)
        }
    }

object EntityChecklistPromptPacketCodec : PacketCodec<EntityChecklistPromptPacket>() {
    override fun CodecScope<EntityChecklistPromptPacket>.body(): EntityChecklistPromptPacket {
        val titleId = field(S32LE) { it.titleId }
        val kind = if (titleId > 0) field(S8) { it.kind!! } else null
        val items =
            if (titleId > 0) field(EntityChecklistItemCodec.listPrefixed(U8)) { it.items!! } else null
        return EntityChecklistPromptPacket(titleId, kind, items)
    }
}
