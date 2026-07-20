package de.fiereu.openmmo.server.game.world.interest

/**
 * Identifies an interest group a session can belong to. A session may be in several groups at once
 * (its map, its guild, a battle) so the same manager can drive overworld presence, guild chat, or
 * battle updates. Add a variant to introduce a new group kind.
 */
sealed interface InterestKey

data class MapInterestKey(val regionId: Int, val bankId: Int, val mapId: Int) : InterestKey

data class GuildInterestKey(val guildId: Long) : InterestKey

data class BattleInterestKey(val battleId: Long) : InterestKey
