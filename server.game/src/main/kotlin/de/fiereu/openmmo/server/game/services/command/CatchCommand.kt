package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.server.game.services.BattleService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatchCommand @Inject constructor(private val battleService: BattleService) : ChatCommand {
  override val name = "catch"
  override val usage = "/catch"
  override val description = "throws a ball at the monster you are fighting"
  override val permission = CharacterPermissions.DEVELOPER

  override fun run(ctx: CommandContext) {
    if (!battleService.catchActiveWild(ctx.characterId)) {
      ctx.reply("You are not in a battle.")
    }
  }
}
