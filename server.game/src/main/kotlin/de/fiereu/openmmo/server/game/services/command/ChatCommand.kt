package de.fiereu.openmmo.server.game.services.command

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.StoredCharacter

/** One chat command, matched on [name] without the slash and without case. */
interface ChatCommand {
  val name: String
  val usage: String
  val description: String

  val permission: Int
    get() = 0

  // Runs on the thread the packet arrived on. Anything that waits for the client needs its own
  // scope first.
  suspend fun run(ctx: CommandContext)
}

class CommandContext(
    val session: SessionContext,
    val state: PlayerState,
    val character: StoredCharacter,
    val args: List<String>,
    /** Already filtered to what this caller may run. */
    val commands: List<ChatCommand>,
) {
  val characterId: Long
    get() = character.info.id

  fun reply(message: String) {
    session.send(notice(message))
  }
}
