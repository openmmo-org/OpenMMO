package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class FriendRosterEntry(
    val name: String,
    val online: Boolean,
)

data class FriendRosterPacket(
    val friends: List<FriendRosterEntry>,
)

private val FriendRosterEntryCodec: Codec<FriendRosterEntry> =
    object : PacketCodec<FriendRosterEntry>() {
        override fun CodecScope<FriendRosterEntry>.body(): FriendRosterEntry {
            field(S64LE) { 0L }
            val name = field(Utf16LeNullTerminated) { it.name }
            val online = field(Bool) { it.online }
            return FriendRosterEntry(name, online)
        }
    }

object FriendRosterPacketCodec : PacketCodec<FriendRosterPacket>() {
    override fun CodecScope<FriendRosterPacket>.body(): FriendRosterPacket {
        val count = field(U8) { it.friends.size }
        val friends = (0 until count).map { i -> field(FriendRosterEntryCodec) { it.friends[i] } }
        return FriendRosterPacket(friends)
    }
}
