package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

private val log = KotlinLogging.logger {}

private val FLUSH_TICK = 5.seconds
private val FLUSH_DEBOUNCE = 10.seconds

data class StoredCharacter(
    val info: CharacterInfo,
    val pokemon: MutableList<Pokemon>,
    val pcStorage: MutableList<Pokemon>,
    val items: MutableMap<Int, Int>,
    // Story progression. Flags are set/unset booleans, vars are named integers that default to 0.
    // Keys are opaque strings supplied by the content layer, so the store stays game agnostic.
    val storyFlags: MutableSet<String> = mutableSetOf(),
    val storyVars: MutableMap<String, Int> = mutableMapOf(),
)

/**
 * Write-through cache over [CharacterRepository]. Memory is the live version and the database
 * mirrors it. Aggregates enter the database on creation and are written back when marked dirty:
 * after a debounce by the periodic flusher, or immediately through [flushCharacterAsync] on events
 * like warps. A disconnect goes through [unloadCharacterAsync], which also evicts the aggregate
 * from the cache once its last write succeeded, so only connected players stay in memory.
 */
@Singleton
class CharacterStore
@Inject
constructor(
    private val repository: CharacterRepository,
    private val entityIds: EntityIdService,
    scope: CoroutineScope,
) {
  private val flushJob = SupervisorJob()
  private val flushScope = CoroutineScope(scope.coroutineContext + flushJob)
  private var periodicJob: Job? = null

  private val characters = ConcurrentHashMap<Long, StoredCharacter>()
  private val charactersByUser = ConcurrentHashMap<Int, CopyOnWriteArrayList<Long>>()
  private val dirtySince = ConcurrentHashMap<Long, Long>()
  private val pendingUnload = ConcurrentHashMap.newKeySet<Long>()

  /** Create a character with its own entity id and an empty party. */
  suspend fun createCharacter(
      userId: Int,
      name: String,
  ): StoredCharacter {
    val id = entityIds.newCharacterId()
    val now = LocalDateTime.now()
    val info =
        CharacterInfo(
            id = id,
            name = name,
            namePrefix = "",
            userId = userId,
            rivalSex = 0,
            lastLogin = now,
            createdAt = now,
            money = 30000,
            permissions = 8,
            remainingSafariSteps = 0,
            remainingSafariBalls = 0,
            pcExtraSlots = 0,
            battleBoxExtraSlots = 0,
            templateAmount = 0,
            positionRegionId = 1,
            positionBankId = 51,
            positionMapId = 3,
            positionX = 4,
            positionY = 4,
            repelLeft = 0,
            repelItemId = 0,
            lureLeft = 0,
            lureItemId = 0,
        )
    val stored = StoredCharacter(info, mutableListOf(), mutableListOf(), mutableMapOf())
    repository.insertAggregate(stored)
    characters[id] = stored
    charactersByUser.computeIfAbsent(userId) { CopyOnWriteArrayList() }.add(id)
    return stored
  }

  fun getCharacter(id: Long): StoredCharacter? = characters[id]

  /** Like [getCharacter] but falls back to the database when the cache has no entry. */
  suspend fun getOrLoadCharacter(id: Long): StoredCharacter? {
    pendingUnload.remove(id)
    characters[id]?.let {
      return it
    }
    val loaded = repository.loadById(id) ?: return null
    return cache(loaded)
  }

  suspend fun getCharactersByUser(userId: Int): List<StoredCharacter> {
    val cachedIds: List<Long>? = charactersByUser[userId]
    if (cachedIds != null) {
      cachedIds.forEach { pendingUnload.remove(it) }
      return cachedIds.mapNotNull { characters[it] }
    }
    val loaded = repository.loadByUser(userId).map { cache(it) }
    loaded.forEach { pendingUnload.remove(it.info.id) }
    charactersByUser.putIfAbsent(userId, CopyOnWriteArrayList(loaded.map { it.info.id }))
    return loaded
  }

  fun updateCharacter(info: CharacterInfo) {
    val stored = characters[info.id] ?: return
    characters[info.id] = stored.copy(info = info)
    markDirty(info.id)
  }

  fun updatePosition(
      characterId: Long,
      x: Short,
      y: Short,
      bankId: Byte? = null,
      mapId: Byte? = null,
  ) {
    val stored = characters[characterId] ?: return
    val oldInfo = stored.info
    val newInfo =
        oldInfo.copy(
            positionX = x,
            positionY = y,
            positionBankId = bankId ?: oldInfo.positionBankId,
            positionMapId = mapId ?: oldInfo.positionMapId,
        )
    characters[characterId] = stored.copy(info = newInfo)
    markDirty(characterId)
  }

  fun addPokemon(characterId: Long, pokemon: Pokemon) {
    val stored = characters[characterId] ?: return
    // Copy instead of mutating in place, so flusher snapshots never see a half-updated list.
    characters[characterId] = stored.copy(pokemon = (stored.pokemon + pokemon).toMutableList())
    markDirty(characterId)
  }

  /** Replace one party monster by id, for example after a battle changed hp, xp, or level. */
  fun updatePokemon(characterId: Long, updated: Pokemon) {
    val stored = characters[characterId] ?: return
    val party = stored.pokemon.map { if (it.id == updated.id) updated else it }
    characters[characterId] = stored.copy(pokemon = party.toMutableList())
    markDirty(characterId)
  }

  fun addMoney(characterId: Long, amount: Int) {
    val stored = characters[characterId] ?: return
    val newInfo = stored.info.copy(money = stored.info.money + amount)
    characters[characterId] = stored.copy(info = newInfo)
    markDirty(characterId)
  }

  /** Set a story flag. Copies the set so flusher snapshots never see a half-updated collection. */
  fun setStoryFlag(characterId: Long, flag: String) {
    val stored = characters[characterId] ?: return
    if (flag in stored.storyFlags) return
    characters[characterId] = stored.copy(storyFlags = (stored.storyFlags + flag).toMutableSet())
    markDirty(characterId)
  }

  fun clearStoryFlag(characterId: Long, flag: String) {
    val stored = characters[characterId] ?: return
    if (flag !in stored.storyFlags) return
    characters[characterId] = stored.copy(storyFlags = (stored.storyFlags - flag).toMutableSet())
    markDirty(characterId)
  }

  /** Set a story var. A value of 0 is the default, so it drops the row instead of storing it. */
  fun setStoryVar(characterId: Long, key: String, value: Int) {
    val stored = characters[characterId] ?: return
    val newVars = stored.storyVars.toMutableMap()
    if (value == 0) newVars.remove(key) else newVars[key] = value
    if (newVars == stored.storyVars) return
    characters[characterId] = stored.copy(storyVars = newVars)
    markDirty(characterId)
  }

  fun startPeriodicFlush() {
    periodicJob =
        flushScope.launch {
          while (isActive) {
            delay(FLUSH_TICK)
            flushOlderThan(FLUSH_DEBOUNCE.inWholeMilliseconds)
          }
        }
  }

  /** Flush one character soon, skipping the debounce. Safe to call from Netty threads. */
  fun flushCharacterAsync(characterId: Long) {
    flushScope.launch { flush(characterId) }
  }

  /**
   * Persist the character and drop it from the cache once the write succeeded. While the save keeps
   * failing the character stays cached and dirty, and the periodic flusher finishes the eviction on
   * its next successful write. Loading the character again cancels the unload.
   */
  fun unloadCharacterAsync(characterId: Long) {
    pendingUnload.add(characterId)
    flushScope.launch { flush(characterId) }
  }

  suspend fun flushAll() {
    for (id in dirtySince.keys) flush(id)
  }

  /** Stop the periodic loop, wait for in-flight flushes, then persist whatever is still dirty. */
  suspend fun shutdown() {
    periodicJob?.cancel()
    flushJob.children.toList().joinAll()
    flushAll()
  }

  private fun cache(stored: StoredCharacter): StoredCharacter {
    val existing = characters.putIfAbsent(stored.info.id, stored)
    return existing ?: stored
  }

  private fun markDirty(id: Long) {
    dirtySince.putIfAbsent(id, System.currentTimeMillis())
  }

  private suspend fun flushOlderThan(ageMs: Long) {
    val now = System.currentTimeMillis()
    for ((id, since) in dirtySince) {
      if (now - since >= ageMs) flush(id)
    }
  }

  private suspend fun flush(id: Long) {
    val since = dirtySince.remove(id)
    val stored = characters[id]
    if (since != null && stored != null) {
      try {
        repository.saveAggregate(stored)
      } catch (e: Exception) {
        log.warn(e) { "Failed to persist character $id, will retry" }
        dirtySince.putIfAbsent(id, since)
        return
      }
    }
    maybeEvict(id)
  }

  private fun maybeEvict(id: Long) {
    if (!pendingUnload.remove(id)) return
    if (dirtySince.containsKey(id)) {
      pendingUnload.add(id)
      return
    }
    val stored = characters.remove(id) ?: return
    charactersByUser.remove(stored.info.userId)
  }
}
