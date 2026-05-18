package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

data class StoredCharacter(
    val info: CharacterInfo,
    val pokemon: MutableList<Pokemon>,
    val pcStorage: MutableList<Pokemon>,
    val items: MutableMap<Int, Int>,
)

object CharacterStore {
  private val characters = ConcurrentHashMap<Long, StoredCharacter>()
  private val charactersByUser = ConcurrentHashMap<Int, MutableList<Long>>()
  private val nextCharId = AtomicLong(1)
  private val nextPokemonId = AtomicLong(1)

  init {
    ensureTestCharacter()
  }

  private fun ensureTestCharacter() {
    if (characters.isEmpty()) {
      createCharacter(userId = 1, name = "Test")
    }
  }

  private fun generateEntityId(): Long {
    val uniqueId = nextCharId.getAndIncrement()
    return (uniqueId shl 16) or 0x9000L
  }

  fun createCharacter(
      userId: Int,
      name: String,
  ): StoredCharacter {
    val id = generateEntityId()
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
            money = 3000,
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
            positionY = 2,
            repelLeft = 0,
            repelItemId = 0,
            lureLeft = 0,
            lureItemId = 0,
        )
    val stored = StoredCharacter(info, mutableListOf(), mutableListOf(), mutableMapOf())
    characters[id] = stored
    charactersByUser.getOrPut(userId) { mutableListOf() }.add(id)
    return stored
  }

  fun getCharacter(id: Long): StoredCharacter? = characters[id]

  fun getCharactersByUser(userId: Int): List<StoredCharacter> {
    return charactersByUser[userId]?.mapNotNull { characters[it] } ?: emptyList()
  }

  fun updateCharacter(info: CharacterInfo) {
    val stored = characters[info.id] ?: return
    characters[info.id] = stored.copy(info = info)
  }

  fun updatePosition(
      characterId: Long,
      x: Short,
      y: Short,
  ) {
    val stored = characters[characterId] ?: return
    val oldInfo = stored.info
    val newInfo =
        oldInfo.copy(
            positionX = x,
            positionY = y,
        )
    characters[characterId] = stored.copy(info = newInfo)
  }

  fun addPokemon(characterId: Long, pokemon: Pokemon) {
    characters[characterId]?.pokemon?.add(pokemon)
  }

  fun addMoney(characterId: Long, amount: Int) {
    val stored = characters[characterId] ?: return
    val newInfo = stored.info.copy(money = stored.info.money + amount)
    characters[characterId] = stored.copy(info = newInfo)
  }
}
