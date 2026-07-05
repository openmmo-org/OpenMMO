package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

data class GuildMember(
    val id: Long,
    val name: String,
    val rank: GuildRank,
    val leader: Boolean,
)

// Wire codes are provisional and unverified against the real client.
enum class GuildActivityType(val code: Int) {
  FOUNDED(0),
  JOINED(1),
}

data class GuildLogEntry(
    val type: GuildActivityType,
    val actor: String,
    val target: String,
    val timestamp: Int,
)

data class Guild(
    val id: Long,
    val name: String,
    val tag: String,
    val members: MutableList<GuildMember>,
    val permissions: MutableMap<GuildRank, Set<GuildPermission>> = mutableMapOf(),
    val activityLog: MutableList<GuildLogEntry> = mutableListOf(),
)

@Singleton
class GuildStore @Inject constructor() {
  private val guilds = ConcurrentHashMap<Long, Guild>()
  private val guildByChar = ConcurrentHashMap<Long, Long>()
  private val nextId = AtomicLong(1)

  fun createGuild(name: String, tag: String, leaderId: Long, leaderName: String): Guild {
    val leader = GuildMember(leaderId, leaderName, GuildRank.BOSS, leader = true)
    val guild = Guild(nextId.getAndIncrement(), name, tag, mutableListOf(leader))
    guild.activityLog.add(GuildLogEntry(GuildActivityType.FOUNDED, leaderName, "", now()))
    guilds[guild.id] = guild
    guildByChar[leaderId] = guild.id
    return guild
  }

  fun getGuildForChar(charId: Long): Guild? = guildByChar[charId]?.let { guilds[it] }

  fun addMember(guild: Guild, member: GuildMember) {
    guild.members.add(member)
    guild.activityLog.add(GuildLogEntry(GuildActivityType.JOINED, member.name, "", now()))
  }

  fun setMemberRank(guild: Guild, entityId: Long, rank: GuildRank) {
    val index = guild.members.indexOfFirst { it.id == entityId }
    if (index >= 0) {
      guild.members[index] = guild.members[index].copy(rank = rank)
    }
  }

  fun removeMember(guild: Guild, entityId: Long) {
    guild.members.removeAll { it.id == entityId }
  }

  fun transferLeadership(guild: Guild, newLeaderId: Long) {
    for (i in guild.members.indices) {
      val member = guild.members[i]
      guild.members[i] =
          when {
            member.id == newLeaderId -> member.copy(rank = GuildRank.BOSS, leader = true)
            member.leader || member.rank == GuildRank.BOSS ->
                member.copy(rank = GuildRank.EXECUTIVE, leader = false)
            else -> member
          }
    }
  }

  fun departGuild(charId: Long) {
    val guildId = guildByChar.remove(charId) ?: return
    guilds[guildId]?.members?.removeAll { it.id == charId }
  }

  fun disbandGuild(charId: Long) {
    val guildId = guildByChar[charId] ?: return
    guilds.remove(guildId)
    guildByChar.entries.removeIf { it.value == guildId }
  }

  private fun now(): Int = Instant.now().epochSecond.toInt()
}
