package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank

data class GuildRankPermissionUpdatePacket(
    val permissions: Map<GuildRank, Set<GuildPermission>>,
)

private val EDITABLE_RANKS: List<GuildRank> =
    GuildRank.entries.filter { it != GuildRank.BOSS }.reversed()

private fun maskToPermissions(mask: Short): Set<GuildPermission> =
    GuildPermission.entries.filterTo(mutableSetOf()) { (mask.toInt() and (1 shl it.ordinal)) != 0 }

private fun permissionsToMask(perms: Set<GuildPermission>): Short =
    perms.fold(0) { acc, p -> acc or (1 shl p.ordinal) }.toShort()

object GuildRankPermissionUpdatePacketCodec : PacketCodec<GuildRankPermissionUpdatePacket>() {
  override fun CodecScope<GuildRankPermissionUpdatePacket>.body(): GuildRankPermissionUpdatePacket {
    val permissions = LinkedHashMap<GuildRank, Set<GuildPermission>>()
    for (rank in EDITABLE_RANKS) {
      val mask = field(S16LE) { permissionsToMask(it.permissions[rank].orEmpty()) }
      permissions[rank] = maskToPermissions(mask)
    }
    return GuildRankPermissionUpdatePacket(permissions)
  }
}
