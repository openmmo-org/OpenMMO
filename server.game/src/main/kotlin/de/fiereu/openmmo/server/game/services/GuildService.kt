package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank
import de.fiereu.openmmo.net.game.packets.guild.GuildActivityLogEntry
import de.fiereu.openmmo.net.game.packets.guild.GuildActivityLogPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildActivityLogPageRequestPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildDepartPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildDisbandTogglePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildInvitePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberEntry
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberExpelPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberRankAssignPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMembershipPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMotdUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildProfileData
import de.fiereu.openmmo.net.game.packets.guild.GuildRankLabelUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildRankPermissionUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.SyncGuildMembersPacket
import de.fiereu.openmmo.net.game.packets.guild.TeamFoundPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.Guild
import de.fiereu.openmmo.server.game.storage.GuildMember
import de.fiereu.openmmo.server.game.storage.GuildStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val GUILD_FOUND_COST = 15000

// The entries list is length-prefixed with a single byte, so a page holds at most 255 entries.
private const val MAX_ACTIVITY_LOG_ENTRIES = 255

@Singleton
class GuildService
@Inject
constructor(
    private val guildStore: GuildStore,
    private val characterStore: CharacterStore,
) {

  fun onFoundTeam(event: PacketEvent<TeamFoundPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val stored = characterStore.getCharacter(charId) ?: return
    val packet = event.packet
    log.info {
      "FoundTeam name='${packet.teamName}' tag='${packet.teamTag}' char=$charId money=${stored.info.money}"
    }
    if (stored.info.money < GUILD_FOUND_COST) {
      log.info { "Insufficient funds to found a guild (need $GUILD_FOUND_COST)" }
      return
    }
    characterStore.addMoney(charId, -GUILD_FOUND_COST)
    val guild = guildStore.createGuild(packet.teamName, packet.teamTag, charId, stored.info.name)
    ctx.send(buildMembership(guild))
    ctx.send(buildMemberSync(guild))
  }

  fun onActivityLogPageRequest(event: PacketEvent<GuildActivityLogPageRequestPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val page = event.packet.pageIndex.toInt()
    log.info { "Guild activity log page=$page requested." }
    val packet = buildActivityLog(guild)
    log.info {
      "Sending guild activity log guild=${guild.id} sent=${packet.entries.size} total=${packet.totalCount}"
    }
    ctx.send(packet)
  }

  fun onGuildInvite(event: PacketEvent<GuildInvitePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val target = event.packet.targetName
    log.info { "GuildInvite char=$charId target='$target'" }
    guildStore.addMember(
        guild,
        GuildMember(syntheticId(target), target, GuildRank.GRUNT, leader = false),
    )
    ctx.send(buildMemberSync(guild))
  }

  fun onRankAssign(event: PacketEvent<GuildMemberRankAssignPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val rank = GuildRank.entries.getOrNull(event.packet.rankOrdinal) ?: return
    if (rank == GuildRank.BOSS) {
      guildStore.transferLeadership(guild, event.packet.memberEntityId)
      log.info { "Leadership transferred char=$charId newLeader=${event.packet.memberEntityId}" }
    } else {
      guildStore.setMemberRank(guild, event.packet.memberEntityId, rank)
      log.info { "RankAssign char=$charId member=${event.packet.memberEntityId} rank=$rank" }
    }
    ctx.send(buildMemberSync(guild))
  }

  fun onExpel(event: PacketEvent<GuildMemberExpelPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    guildStore.removeMember(guild, event.packet.targetEntityId)
    log.info { "Expel char=$charId member=${event.packet.targetEntityId}" }
    ctx.send(buildMemberSync(guild))
  }

  fun onDepart(event: PacketEvent<GuildDepartPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    guildStore.departGuild(charId)
    log.info { "GuildDepart char=$charId" }
    ctx.send(GuildMembershipPacket(inGuild = false, profile = null))
  }

  fun onDisbandToggle(event: PacketEvent<GuildDisbandTogglePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val initiate = event.packet.initiate
    val guild = guildStore.getGuildForChar(charId)
    log.info { "GuildDisbandToggle char=$charId guild=${guild?.id} initiate=$initiate" }
    if (guild == null) return
    // We disband immediately on initiate, so a follow-up cancel has no pending state to undo.
    if (!initiate) return
    guildStore.disbandGuild(charId)
    log.info { "Guild ${guild.id} disbanded by char=$charId" }
    // TODO: The guild window does not close after disbanding. Sending
    // GuildMembershipPacket(inGuild = false) updates the state (reopening the
    // window shows the create-guild screen) but does not dismiss the currently
    // open window. Check against the real game to see what packet closes it.
    ctx.send(GuildMembershipPacket(inGuild = false, profile = null))
  }

  fun onMotdUpdate(event: PacketEvent<GuildMotdUpdatePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    log.info { "GuildMotdUpdate char=$charId guild=${guild.id} motd='${event.packet.motdText}'" }
  }

  fun onRankLabelUpdate(event: PacketEvent<GuildRankLabelUpdatePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val rank = GuildRank.entries.getOrNull(event.packet.rankOrdinal)
    log.info {
      "GuildRankLabelUpdate char=$charId guild=${guild.id} rank=$rank label='${event.packet.rankLabel}'"
    }
  }

  fun onRankPermissionUpdate(event: PacketEvent<GuildRankPermissionUpdatePacket>) {
    val state = event.session.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val sanitized =
        event.packet.permissions.mapValues { (rank, perms) ->
          if (rank == GuildRank.GRUNT && GuildPermission.KICK in perms) {
            log.warn { "Rejecting KICK permission for GRUNT" }
            perms - GuildPermission.KICK
          } else {
            perms
          }
        }
    guild.permissions.clear()
    guild.permissions.putAll(sanitized)
    log.info { "RankPermUpdate char=$charId perms=$sanitized" }
  }

  private fun buildMembership(guild: Guild): GuildMembershipPacket =
      GuildMembershipPacket(
          inGuild = true,
          profile =
              GuildProfileData(
                  guildId = guild.id,
                  name = guild.name,
                  tag = guild.tag,
                  foundedAt = 0,
                  message = "Your Team has been successfully created!",
                  updatedAt = 0,
                  value1 = 5,
                  value2 = 5,
                  value3 = 5,
                  value4 = 0,
                  value5 = 0,
                  unk1 = 0,
                  rankCount = GuildRank.entries.size,
                  unk2 = 0,
                  unk3 = 0,
                  flag = 0,
              ),
      )

  private fun buildMemberSync(guild: Guild): SyncGuildMembersPacket =
      SyncGuildMembersPacket(
          replace = true,
          members =
              guild.members.map { member ->
                GuildMemberEntry(
                    entityId = member.id,
                    rank = member.rank.ordinal.toByte(),
                    joinedAt = 0,
                    name = member.name,
                    online = true,
                    lastSeen = 0,
                    appearance = List(5) { 0 },
                    leader = member.leader,
                )
              },
      )

  private fun buildActivityLog(guild: Guild): GuildActivityLogPacket =
      GuildActivityLogPacket(
          totalCount = guild.activityLog.size.toShort(),
          entries =
              guild.activityLog.takeLast(MAX_ACTIVITY_LOG_ENTRIES).map { entry ->
                GuildActivityLogEntry(
                    type = entry.type.code,
                    actor = entry.actor,
                    target = entry.target,
                    timestamp = entry.timestamp,
                )
              },
      )

  private fun syntheticId(name: String): Long = (name.hashCode().toLong() shl 16) or 0x9000L
}
