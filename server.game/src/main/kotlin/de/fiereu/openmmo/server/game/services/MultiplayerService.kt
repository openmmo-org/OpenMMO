package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.server.game.session.SessionRegistry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MultiplayerService
@Inject
constructor(
    private val sessionRegistry: SessionRegistry,
) {

  fun broadcastMessage(packet: Any) {
    for (characterId in sessionRegistry.onlineCharacterIds()) {
      val ctx = sessionRegistry.getByCharacterId(characterId) ?: continue
      if (ctx.channel.isActive) {
        ctx.send(packet)
      }
    }
  }

  fun broadcastExcept(exclude: SessionContext, packet: Any) {
    for (characterId in sessionRegistry.onlineCharacterIds()) {
      val ctx = sessionRegistry.getByCharacterId(characterId) ?: continue
      if (ctx.channel.isActive && ctx.channel != exclude.channel) {
        ctx.send(packet)
      }
    }
  }
}
