package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class NpcPanelTogglePacket(
    val npcId: Short,
    val npcType: Byte?,
    val visible: Boolean?,
)

object NpcPanelTogglePacketCodec : PacketCodec<NpcPanelTogglePacket>() {
    override fun CodecScope<NpcPanelTogglePacket>.body(): NpcPanelTogglePacket {
        val npcId = field(S16LE) { it.npcId }
        val npcType: Byte?
        val visible: Boolean?
        if (npcId > 0) {
            npcType = field(S8) { it.npcType!! }
            visible = field(Bool) { it.visible!! }
        } else {
            npcType = null
            visible = null
        }
        return NpcPanelTogglePacket(npcId, npcType, visible)
    }
}
