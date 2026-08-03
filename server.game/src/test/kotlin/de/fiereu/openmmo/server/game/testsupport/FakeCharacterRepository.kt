package de.fiereu.openmmo.server.game.testsupport

import de.fiereu.openmmo.server.game.storage.CharacterRepository
import de.fiereu.openmmo.server.game.storage.StoredCharacter
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class FakeCharacterRepository : CharacterRepository {
  val saved = ConcurrentHashMap<Long, StoredCharacter>()
  val saveCount = AtomicInteger(0)
  var failNextSave = false
  val lastPrevious = ConcurrentHashMap<Long, StoredCharacter>()

  override suspend fun loadByUser(userId: Int): List<StoredCharacter> =
      saved.values.filter { it.info.userId == userId }

  override suspend fun loadById(id: Long): StoredCharacter? = saved[id]

  override suspend fun insertAggregate(stored: StoredCharacter) {
    saved[stored.info.id] = stored
  }

  override suspend fun saveChanges(previous: StoredCharacter?, current: StoredCharacter) {
    if (failNextSave) {
      failNextSave = false
      throw IllegalStateException("simulated save failure")
    }
    saveCount.incrementAndGet()
    previous?.let { lastPrevious[current.info.id] = it }
    saved[current.info.id] = current
  }

  override suspend fun deleteById(userId: Int, id: Long): Boolean {
    val stored = saved[id] ?: return false
    if (stored.info.userId != userId) return false
    return saved.remove(id, stored)
  }
}
