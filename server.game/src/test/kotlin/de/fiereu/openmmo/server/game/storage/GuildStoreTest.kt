package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.GuildRank
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GuildStoreTest :
    FunSpec({
      test("creating a guild registers the leader as a member and binds the lookup") {
        val store = GuildStore()
        store.getGuildForChar(100L) shouldBe null

        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        guild.name shouldBe "Knights"
        guild.tag shouldBe "KNT"
        guild.members.map { it.id } shouldBe listOf(100L)
        guild.members.single().rank shouldBe GuildRank.BOSS
        store.getGuildForChar(100L) shouldBe guild
      }

      test("invited members append to the roster") {
        val store = GuildStore()
        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.addMember(guild, GuildMember(200L, "Grunt", GuildRank.GRUNT, leader = false))
        guild.members.map { it.name } shouldBe listOf("Leader", "Grunt")
      }

      test("rank assign updates a member and kick removes them") {
        val store = GuildStore()
        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.addMember(guild, GuildMember(200L, "Recruit", GuildRank.GRUNT, leader = false))

        store.setMemberRank(guild, 200L, GuildRank.OFFICER)
        guild.members.single { it.id == 200L }.rank shouldBe GuildRank.OFFICER

        store.removeMember(guild, 200L)
        guild.members.map { it.id } shouldBe listOf(100L)
      }

      test("transferring leadership promotes the target and demotes the old Boss to Executive") {
        val store = GuildStore()
        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.addMember(guild, GuildMember(200L, "Heir", GuildRank.OFFICER, leader = false))

        store.transferLeadership(guild, 200L)

        val byId = guild.members.associateBy { it.id }
        byId.getValue(200L).rank shouldBe GuildRank.BOSS
        byId.getValue(200L).leader shouldBe true
        byId.getValue(100L).rank shouldBe GuildRank.EXECUTIVE
        byId.getValue(100L).leader shouldBe false
      }

      test("leave unbinds the leaver; disband removes the guild") {
        val store = GuildStore()
        store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.leaveGuild(100L)
        store.getGuildForChar(100L) shouldBe null

        store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.disbandGuild(100L)
        store.getGuildForChar(100L) shouldBe null
      }
    })
