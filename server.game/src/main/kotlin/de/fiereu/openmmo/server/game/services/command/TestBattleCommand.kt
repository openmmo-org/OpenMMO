package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.server.game.services.BattleService
import javax.inject.Inject
import javax.inject.Singleton

private const val DEFAULT_WILD_DEX = 19
private const val DEFAULT_WILD_LEVEL = 3
private const val MIN_WILD_LEVEL = 2
private const val MAX_WILD_LEVEL = 100

@Singleton
class TestBattleCommand @Inject constructor(private val battleService: BattleService) :
    ChatCommand {
  override val name = "testbattle"
  override val usage = "/testbattle [dexId] [level]"
  override val description = "starts a wild battle against the given species"
  override val permission = CharacterPermissions.DEVELOPER

  override suspend fun run(ctx: CommandContext) {
    val dexId = ctx.args.getOrNull(0)?.toIntOrNull() ?: DEFAULT_WILD_DEX
    val level =
        (ctx.args.getOrNull(1)?.toIntOrNull() ?: DEFAULT_WILD_LEVEL).coerceIn(
            MIN_WILD_LEVEL, MAX_WILD_LEVEL)
    battleService.startWildBattle(ctx.session, dexId, level)
  }
}
