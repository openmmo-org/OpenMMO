package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.server.game.domain.Gender
import de.fiereu.openmmo.server.game.domain.IVs
import de.fiereu.openmmo.server.game.domain.Nature
import de.fiereu.openmmo.server.game.domain.OwnedPokemon
import de.fiereu.openmmo.server.game.domain.PokemonMoveSlot
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

data class StoredCharacter(
    val info: CharacterInfo,
    val pokemon: MutableList<OwnedPokemon>,
    val pcStorage: MutableList<OwnedPokemon>,
    val items: MutableMap<Int, Int>,
)

enum class PokemonDepositTarget {
  PARTY,
  PC
}

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
      gender: Byte = 0,
      cosmetics: ByteArray = ByteArray(0),
  ): StoredCharacter {
    val id = generateEntityId()
    val now = LocalDateTime.now()
    val info =
        CharacterInfo(
            id = id,
            name = name,
            namePrefix = "",
            userId = userId,
            // CharacterInfo currently has no separate player-gender field; until the DB/cosmetics
            // model grows one, this field carries the player's actual decoded gender for 0xF3.
            // The v31914 client treats this as a small enum in 0x02/0xF3 and crashes on values
            // outside the observed player-gender domain, so sanitize create bytes before storing.
            rivalSex = normalizePlayerGender(gender),
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
            positionY = 4,
            repelLeft = 0,
            repelItemId = 0,
            lureLeft = 0,
            lureItemId = 0,
        )
    val stored =
        StoredCharacter(
            info = info,
            pokemon =
                mutableListOf(
                    createStarterPokemon(ownerName = name, userId = userId, characterId = id)),
            pcStorage = mutableListOf(),
            items = seededItems(),
        )
    characters[id] = stored
    charactersByUser.getOrPut(userId) { mutableListOf() }.add(id)
    return stored
  }

  private fun seededItems(): MutableMap<Int, Int> =
      mutableMapOf(
          1025 to 500,
          1026 to 1000,
          1027 to 1500,
      )

  private fun normalizePlayerGender(gender: Byte): Byte = if (gender.toInt() == 1) 1 else 0

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

  fun getParty(characterId: Long): List<OwnedPokemon> =
      characters[characterId]?.pokemon ?: emptyList()

  fun addPokemon(characterId: Long, pokemon: OwnedPokemon) {
    addCaughtPokemon(characterId, pokemon)
  }

  fun addCaughtPokemon(characterId: Long, pokemon: OwnedPokemon): PokemonDepositTarget? {
    val stored = characters[characterId] ?: return null
    return if (stored.pokemon.size < 6) {
      stored.pokemon.add(pokemon)
      PokemonDepositTarget.PARTY
    } else {
      stored.pcStorage.add(pokemon)
      PokemonDepositTarget.PC
    }
  }

  fun addMoney(characterId: Long, amount: Int) {
    val stored = characters[characterId] ?: return
    val newInfo = stored.info.copy(money = stored.info.money + amount)
    characters[characterId] = stored.copy(info = newInfo)
  }

  private fun createStarterPokemon(
      ownerName: String,
      userId: Int,
      characterId: Long,
  ): OwnedPokemon =
      OwnedPokemon(
          uid = "starter-$characterId-1",
          speciesId = 1,
          nickname = "Bulbasaur",
          level = 5,
          exp = 0,
          nature = Nature.HARDY,
          ability = "overgrow",
          gender = Gender.MALE,
          shiny = false,
          ivs = IVs(hp = 10, atk = 10, def = 10, spa = 10, spd = 10, spe = 10),
          moves = listOf(PokemonMoveSlot(moveId = 33, ppUp = 0, ppCurrent = 35)),
          friendship = 70,
          otWallet = "user:$userId",
          otName = ownerName,
          pid = userId.toUInt(),
          metLevel = 5,
          metLocation = "starter",
      )
}
