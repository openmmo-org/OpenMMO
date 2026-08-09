package de.fiereu.openmmo.server.game.services.command

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HelpCommand @Inject constructor() : ChatCommand {
  override val name = "help"
  override val usage = "/help"
  override val description = "lists the commands you can run"

  override suspend fun run(ctx: CommandContext) {
    ctx.commands.sortedBy { it.name }.forEach { ctx.reply("${it.usage} - ${it.description}") }
  }
}
