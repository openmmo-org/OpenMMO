package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class FriendListEntry(
    val player: Long,
    val friendsSince: Int,
    val online: Boolean,
    val name: String,
    val unk: Byte,
    val lastSeen: Int,
    val appearance: List<Short>,
)

data class FriendListPacket(
    val mode: Int,
    val entries: List<FriendListEntry>,
)

private val FriendListEntryCodec: Codec<FriendListEntry> =
    object : PacketCodec<FriendListEntry>() {
        override fun CodecScope<FriendListEntry>.body(): FriendListEntry {
            val player = field(S64LE) { it.player }
            val friendsSince = field(S32LE) { it.friendsSince }
            val online = field(Bool) { it.online }
            val name = field(Utf16LeNullTerminated) { it.name }
            val unk = field(S8) { it.unk }
            val lastSeen = field(S32LE) { it.lastSeen }
            val appearance = field(S16LE.repeat(5)) { it.appearance }
            return FriendListEntry(player, friendsSince, online, name, unk, lastSeen, appearance)
        }
    }

object FriendListPacketCodec : PacketCodec<FriendListPacket>() {
    override fun CodecScope<FriendListPacket>.body(): FriendListPacket {
        val mode = field(U8) { it.mode }
        val entries = field(FriendListEntryCodec.listPrefixed(U8)) { it.entries }
        return FriendListPacket(mode, entries)
    }
}
