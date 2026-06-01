package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

data class StoredCharacter(
    val info: CharacterInfo,
    val pokemon: MutableList<Pokemon>,
    val pcStorage: MutableList<Pokemon>,
    val items: MutableMap<Int, Int>,
)

@Singleton
class CharacterStore @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
  private val cache = ConcurrentHashMap<Long, StoredCharacter>()

  fun createCharacter(
      userId: Int,
      name: String,
      gender: Byte = 0,
      cosmetics: ByteArray? = null,
  ): StoredCharacter {
    val info = characterRepository.create(userId, name, gender, cosmetics)
    val stored = StoredCharacter(info, mutableListOf(), mutableListOf(), mutableMapOf())
    cache[info.id] = stored
    return stored
  }

  fun getCharacter(id: Long): StoredCharacter? {
    val cached = cache[id]
    if (cached != null) return cached
    val info = characterRepository.getById(id) ?: return null
    val stored = StoredCharacter(info, mutableListOf(), mutableListOf(), mutableMapOf())
    cache[id] = stored
    return stored
  }

  fun getCharactersByUser(userId: Int): List<StoredCharacter> {
    return characterRepository.getByUserId(userId).map { info ->
      cache.getOrPut(info.id) { StoredCharacter(info, mutableListOf(), mutableListOf(), mutableMapOf()) }
    }
  }

  fun updateCharacter(info: CharacterInfo) {
    characterRepository.update(info.id, info)
    cache[info.id]?.let { cache[info.id] = it.copy(info = info) }
  }

  fun updatePosition(
      characterId: Long,
      x: Short,
      y: Short,
      bankId: Byte? = null,
      mapId: Byte? = null,
  ) {
    characterRepository.updatePosition(characterId, x, y, bankId, mapId)
    val stored = cache[characterId] ?: return
    val oldInfo = stored.info
    val newInfo =
        oldInfo.copy(
            positionX = x,
            positionY = y,
            positionBankId = bankId ?: oldInfo.positionBankId,
            positionMapId = mapId ?: oldInfo.positionMapId,
        )
    cache[characterId] = stored.copy(info = newInfo)
  }

  fun addPokemon(characterId: Long, pokemon: Pokemon) {
    cache[characterId]?.pokemon?.add(pokemon)
  }

  fun addMoney(characterId: Long, amount: Int) {
    val stored = cache[characterId] ?: return
    val newInfo = stored.info.copy(money = stored.info.money + amount)
    characterRepository.update(characterId, newInfo)
    cache[characterId] = stored.copy(info = newInfo)
  }
}
