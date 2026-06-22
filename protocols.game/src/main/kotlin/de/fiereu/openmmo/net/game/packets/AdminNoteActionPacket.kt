package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class AdminNoteActionPacket(
    val actionType: Byte,
    val targetEntityId: Long?,
    val noteEntityId: Long?,
    val noteText: String?,
)

object AdminNoteActionPacketCodec : PacketCodec<AdminNoteActionPacket>() {
    override fun CodecScope<AdminNoteActionPacket>.body(): AdminNoteActionPacket {
        val actionType = field(S8) { it.actionType }
        return when (actionType.toInt()) {
            1 -> {
                val targetEntityId =
                    field(S64LE) {
                        it.targetEntityId ?: throw MalformedPacketException("targetEntityId required")
                    }
                val noteText =
                    field(Utf16LeNullTerminated) {
                        it.noteText ?: throw MalformedPacketException("noteText required")
                    }
                AdminNoteActionPacket(actionType, targetEntityId, null, noteText)
            }

            2 -> {
                val targetEntityId =
                    field(S64LE) {
                        it.targetEntityId ?: throw MalformedPacketException("targetEntityId required")
                    }
                val noteEntityId =
                    field(S64LE) {
                        it.noteEntityId ?: throw MalformedPacketException("noteEntityId required")
                    }
                AdminNoteActionPacket(actionType, targetEntityId, noteEntityId, null)
            }

            else -> AdminNoteActionPacket(actionType, null, null, null)
        }
    }
}
