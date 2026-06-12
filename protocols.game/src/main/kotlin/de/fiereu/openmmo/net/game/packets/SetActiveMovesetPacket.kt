package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SetActiveMovesetPacket(
    val partySlot: Short,
    val pokemonEntityId: Long,
    val moveIds: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is SetActiveMovesetPacket &&
                partySlot == other.partySlot &&
                pokemonEntityId == other.pokemonEntityId &&
                moveIds.contentEquals(other.moveIds)

    override fun hashCode(): Int {
        var r = partySlot.toInt()
        r = r * 31 + pokemonEntityId.hashCode()
        r = r * 31 + moveIds.contentHashCode()
        return r
    }
}

private val MoveIdsCodec: Codec<ByteArray> =
    object : Codec<ByteArray> {
        override fun read(buf: ReadBuffer): ByteArray {
            val data = ByteArray(buf.remaining())
            if (data.isNotEmpty()) buf.readBytes(data)
            return data
        }

        override fun write(buf: WriteBuffer, value: ByteArray) {
            if (value.isNotEmpty()) buf.writeBytes(value)
        }
    }

object SetActiveMovesetPacketCodec : PacketCodec<SetActiveMovesetPacket>() {
    override fun CodecScope<SetActiveMovesetPacket>.body(): SetActiveMovesetPacket {
        val partySlot = field(S16LE) { it.partySlot }
        val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
        val moveIds = field(MoveIdsCodec) { it.moveIds }
        return SetActiveMovesetPacket(partySlot, pokemonEntityId, moveIds)
    }
}
