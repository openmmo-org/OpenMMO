package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

// A starter party monster for the dev test character. Its uid, species, HP, and moves match the
// field state emitted by the test battle so the client can resolve the player's monster.
const val STARTER_SNIVY_UID = 0x000000000001C000L
const val SNIVY_DEX_ID = 495

// A second party monster so the switch action is selectable in the test battle.
const val STARTER_PATRAT_UID = 0x000000000002C000L
const val PATRAT_DEX_ID = 504

data class StoredCharacter(
    val info: CharacterInfo,
    val pokemon: MutableList<Pokemon>,
    val pcStorage: MutableList<Pokemon>,
    val items: MutableMap<Int, Int>,
)

@Singleton
class CharacterStore @Inject constructor() {
  private val characters = ConcurrentHashMap<Long, StoredCharacter>()
  private val charactersByUser = ConcurrentHashMap<Int, MutableList<Long>>()
  private val nextCharId = AtomicLong(1)

  init {
    ensureTestCharacter()
  }

  private fun ensureTestCharacter() {
    if (characters.isEmpty()) {
      val test = createCharacter(userId = 1, name = "Test")
      test.pokemon.add(starterSnivy(test.info.id, test.info.name))
      test.pokemon.add(starterPatrat(test.info.id, test.info.name))
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
  }

  private fun starterSnivy(ownerId: Long, ot: String): Pokemon =
      Pokemon(
          id = STARTER_SNIVY_UID,
          ownerId = ownerId,
          container = PokemonContainer.PARTY,
          containerSlot = 0,
          dexId = SNIVY_DEX_ID,
          seed = 0,
          ot = ot,
          nickname = "",
          level = 5,
          hp = 20,
          xp = 165,
          eVs = EVs(),
          iVs = IVs(),
          moves =
              listOf(
                  PokemonMove(33, 35),
                  PokemonMove(43, 30),
                  PokemonMove(0, 0),
                  PokemonMove(0, 0),
              ),
          isShiny = false,
          hasHiddenAbility = false,
          isAlpha = false,
          isSecret = false,
          isFatefulEncounter = false,
          isRaidEncounter = false,
          caughtAt = LocalDateTime.now(),
      )

  private fun starterPatrat(ownerId: Long, ot: String): Pokemon =
      Pokemon(
          id = STARTER_PATRAT_UID,
          ownerId = ownerId,
          container = PokemonContainer.PARTY,
          containerSlot = 1,
          dexId = PATRAT_DEX_ID,
          seed = 0,
          ot = ot,
          nickname = "",
          level = 3,
          hp = 12,
          xp = 27,
          eVs = EVs(),
          iVs = IVs(),
          moves =
              listOf(
                  PokemonMove(33, 35),
                  PokemonMove(0, 0),
                  PokemonMove(0, 0),
                  PokemonMove(0, 0),
              ),
          isShiny = false,
          hasHiddenAbility = false,
          isAlpha = false,
          isSecret = false,
          isFatefulEncounter = false,
          isRaidEncounter = false,
          caughtAt = LocalDateTime.now(),
      )

  fun addPokemon(characterId: Long, pokemon: Pokemon) {
    characters[characterId]?.pokemon?.add(pokemon)
  }

  fun addMoney(characterId: Long, amount: Int) {
    val stored = characters[characterId] ?: return
    val newInfo = stored.info.copy(money = stored.info.money + amount)
    characters[characterId] = stored.copy(info = newInfo)
  }
}
