package de.fiereu.openmmo.server.game.session

import de.fiereu.network.SessionContext
import io.netty.channel.Channel
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRegistry @Inject constructor() {
  private val sessionsByChannel = ConcurrentHashMap<Channel, SessionContext>()
  private val sessionsByCharacter = ConcurrentHashMap<Long, SessionContext>()

  fun register(ctx: SessionContext) {
    sessionsByChannel[ctx.channel] = ctx
  }

  fun unregister(ctx: SessionContext) {
    sessionsByChannel.remove(ctx.channel)
    val state = ctx.attributes[PLAYER_STATE]
    val charId = state?.characterId
    if (charId != null) {
      sessionsByCharacter.remove(charId)
    }
  }

  fun bindCharacter(ctx: SessionContext, characterId: Long) {
    sessionsByCharacter[characterId] = ctx
  }

  fun unbindCharacter(characterId: Long) {
    sessionsByCharacter.remove(characterId)
  }

  fun getByCharacterId(id: Long): SessionContext? = sessionsByCharacter[id]

  fun getOthersInMap(
      excludeCharacterId: Long,
      regionId: Int,
      bankId: Int,
      mapId: Int,
  ): List<SessionContext> =
      sessionsByCharacter.values.filter { ctx ->
        val state = ctx.attributes[PLAYER_STATE] ?: return@filter false
        state.characterId != excludeCharacterId &&
            state.regionId == regionId &&
            state.bankId == bankId &&
            state.mapId == mapId
      }

  fun onlineCharacterIds(): Set<Long> = sessionsByCharacter.keys

  fun isOnline(id: Long): Boolean = sessionsByCharacter.containsKey(id)
}
