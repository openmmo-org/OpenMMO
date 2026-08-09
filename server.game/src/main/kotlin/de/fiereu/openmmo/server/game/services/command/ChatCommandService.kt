package de.fiereu.openmmo.server.game.services.command

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.hasPermission
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val PREFIX = '/'

@Singleton
class ChatCommandService
@Inject
constructor(
    private val characterStore: CharacterStore,
    registered: Set<@JvmSuppressWildcards ChatCommand>,
) {

  private val commands = registered.associateBy { it.name.lowercase() }

  /** Handles a chat line. Returns false when it is ordinary chat the caller should broadcast. */
  fun tryHandle(session: SessionContext, message: String): Boolean {
    val text = message.trim()
    if (!text.startsWith(PREFIX)) return false

    // TODO: Support quoted arguments, for a command that takes a name with a space in it.
    val parts = text.drop(1).split(Regex("\\s+")).filter { it.isNotEmpty() }
    val name = parts.firstOrNull()?.lowercase()
    if (name == null) {
      session.send(notice("Type /help to see what you can run."))
      return true
    }

    val state = session.attributes[PLAYER_STATE]
    val character = state?.characterId?.let(characterStore::getCharacter)
    if (state == null || character == null) {
      session.send(notice("You are not in the world yet."))
      return true
    }

    val command = commands[name]
    if (command == null || !character.info.hasPermission(command.permission)) {
      if (command != null) {
        log.info { "char=${character.info.id} may not run /$name" }
      }
      session.send(notice("Unknown command: $name. Try /help."))
      return true
    }

    val ctx =
        CommandContext(
            session = session,
            state = state,
            character = character,
            args = parts.drop(1),
            commands = commands.values.filter { character.info.hasPermission(it.permission) },
        )
    try {
      command.run(ctx)
    } catch (e: Exception) {
      log.error(e) { "/$name failed for char=${character.info.id}" }
      ctx.reply("/$name failed.")
    }
    return true
  }
}
