package de.fiereu.openmmo.server.game.testsupport

import de.fiereu.openmmo.server.game.storage.CharacterRepository
import de.fiereu.openmmo.server.game.storage.StoredCharacter
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class FakeCharacterRepository : CharacterRepository {
  val saved = ConcurrentHashMap<Long, StoredCharacter>()
  val saveCount = AtomicInteger(0)
  var failNextSave = false

  override suspend fun loadByUser(userId: Int): List<StoredCharacter> =
      saved.values.filter { it.info.userId == userId }

  override suspend fun loadById(id: Long): StoredCharacter? = saved[id]

  override suspend fun insertAggregate(stored: StoredCharacter) {
    saved[stored.info.id] = stored
  }

  override suspend fun saveAggregate(stored: StoredCharacter) {
    if (failNextSave) {
      failNextSave = false
      throw IllegalStateException("simulated save failure")
    }
    saveCount.incrementAndGet()
    saved[stored.info.id] = stored
  }
}
