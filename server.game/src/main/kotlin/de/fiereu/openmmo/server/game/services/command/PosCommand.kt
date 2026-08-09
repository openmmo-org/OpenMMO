package de.fiereu.openmmo.server.game.services.command

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PosCommand @Inject constructor() : ChatCommand {
  override val name = "pos"
  override val usage = "/pos"
  override val description = "shows the map and tile you are standing on"

  override suspend fun run(ctx: CommandContext) {
    val s = ctx.state
    ctx.reply(
        "region ${s.regionId} bank ${s.bankId} map ${s.mapId} " +
            "(${s.x}, ${s.y}) z ${s.elevation} facing ${s.facingDirection}")
  }
}
