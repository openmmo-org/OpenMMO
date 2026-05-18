package de.fiereu.openmmo.server.game.session

import de.fiereu.openmmo.common.enums.Direction
import io.netty.channel.Channel
import java.util.concurrent.ConcurrentHashMap

data class ScriptPage(
    val type: Int = 0x04,
    val unk1: Int,
    val unk2: Int,
    val unk3: Int,
)

data class Session(
    val channel: Channel,
    val userId: Int,
    var characterId: Long? = null,
    var justWarped: Boolean = false,
    var facingDirection: Direction = Direction.DOWN,
    var inDialog: Boolean = false,
    var dialogNpcEntityId: Long = 0,
    var dialogSeqId: Int = 0,
    var dialogPages: List<ScriptPage> = emptyList(),
    var dialogPageIndex: Int = 0,
    var regionId: Int = 1,
    var bankId: Int = 51,
    var mapId: Int = 3,
    var x: Short = 4,
    var y: Short = 2,
)

object SessionManager {
  private val sessionsByChannel = ConcurrentHashMap<Channel, Session>()
  private val sessionsByCharacter = ConcurrentHashMap<Long, Session>()

  fun createSession(channel: Channel, userId: Int): Session {
    removeSession(channel)
    val session = Session(channel, userId)
    sessionsByChannel[channel] = session
    return session
  }

  fun getSessionByChannel(channel: Channel): Session? = sessionsByChannel[channel]

  fun getSessionByCharacterId(characterId: Long): Session? = sessionsByCharacter[characterId]

  fun setActiveCharacter(channel: Channel, characterId: Long) {
    val session = sessionsByChannel[channel] ?: return
    sessionsByCharacter[characterId] = session
    session.characterId = characterId
  }

  fun removeSession(channel: Channel) {
    val session = sessionsByChannel.remove(channel)
    if (session != null && session.characterId != null) {
      sessionsByCharacter.remove(session.characterId)
    }
  }

  fun getOnlineCharacterIds(): Set<Long> = sessionsByCharacter.keys

  fun isOnline(characterId: Long): Boolean = sessionsByCharacter.containsKey(characterId)

  fun getOthersInMap(
      excludeCharacterId: Long,
      regionId: Int,
      bankId: Int,
      mapId: Int,
  ): List<Session> =
      sessionsByCharacter.values.filter { session ->
        session.characterId != excludeCharacterId &&
            session.regionId == regionId &&
            session.bankId == bankId &&
            session.mapId == mapId
      }
}
