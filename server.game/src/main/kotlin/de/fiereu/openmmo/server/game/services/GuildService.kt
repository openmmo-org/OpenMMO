package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank
import de.fiereu.openmmo.net.game.packets.GuildDepartPacket
import de.fiereu.openmmo.net.game.packets.GuildDisbandTogglePacket
import de.fiereu.openmmo.net.game.packets.GuildInvitePacket
import de.fiereu.openmmo.net.game.packets.GuildMemberExpelPacket
import de.fiereu.openmmo.net.game.packets.GuildMemberRankAssignPacket
import de.fiereu.openmmo.net.game.packets.GuildMembershipPacket
import de.fiereu.openmmo.net.game.packets.GuildProfileData
import de.fiereu.openmmo.net.game.packets.GuildRankPermissionUpdatePacket
import de.fiereu.openmmo.net.game.packets.GuildMemberEntry
import de.fiereu.openmmo.net.game.packets.SyncGuildMembersPacket
import de.fiereu.openmmo.net.game.packets.TeamFoundPacket
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
    val state = event.session.attributes[PLAYER_STATE] ?: return
    log.info { "GuildDisbandToggle char=${state.characterId} initiate=${event.packet.initiate}" }
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

  private fun syntheticId(name: String): Long = (name.hashCode().toLong() shl 16) or 0x9000L
}
