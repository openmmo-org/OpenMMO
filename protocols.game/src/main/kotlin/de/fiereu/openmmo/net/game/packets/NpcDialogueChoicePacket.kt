package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class NpcDialogueChoicePacket(
    val dialogueType: Byte,
    val choiceIndex: Byte,
    val subOption: Short?,
)

object NpcDialogueChoicePacketCodec : PacketCodec<NpcDialogueChoicePacket>() {
    override fun CodecScope<NpcDialogueChoicePacket>.body(): NpcDialogueChoicePacket {
        val dialogueType = field(S8) { it.dialogueType }
        val choiceIndex = field(S8) { it.choiceIndex }
        val subOption =
            if (choiceIndex != 0.toByte() && dialogueType == BRANCHING_DIALOGUE_TYPE)
                field(S16LE) { it.subOption ?: 0 }
            else null
        return NpcDialogueChoicePacket(dialogueType, choiceIndex, subOption)
    }

    private const val BRANCHING_DIALOGUE_TYPE: Byte = 1
}
