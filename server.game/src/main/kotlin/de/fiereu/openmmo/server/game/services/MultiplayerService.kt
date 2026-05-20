package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.server.game.session.SessionManager
import io.netty.channel.Channel

class MultiplayerService {

  fun broadcastMessage(packet: Any) {
    for (characterId in SessionManager.getOnlineCharacterIds()) {
      val s = SessionManager.getSessionByCharacterId(characterId) ?: continue
      if (s.channel.isActive) {
        s.channel.writeAndFlush(packet)
      }
    }
  }

  fun broadcastExcept(channel: Channel, packet: Any) {
    for (characterId in SessionManager.getOnlineCharacterIds()) {
      val s = SessionManager.getSessionByCharacterId(characterId) ?: continue
      if (s.channel.isActive && s.channel != channel) {
        s.channel.writeAndFlush(packet)
      }
    }
  }
}
