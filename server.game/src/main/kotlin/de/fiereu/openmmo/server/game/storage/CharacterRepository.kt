package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.PokemonStats
import de.fiereu.openmmo.db.game.tables.records.CharactersRecord
import de.fiereu.openmmo.db.game.tables.records.PokemonRecord
import de.fiereu.openmmo.db.game.tables.references.CHARACTERS
import de.fiereu.openmmo.db.game.tables.references.CHARACTER_ITEMS
import de.fiereu.openmmo.db.game.tables.references.POKEMON
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

interface CharacterRepository {
  suspend fun loadByUser(userId: Int): List<StoredCharacter>

  suspend fun loadById(id: Long): StoredCharacter?

  suspend fun insertAggregate(stored: StoredCharacter)

  suspend fun saveAggregate(stored: StoredCharacter)
}

@Singleton
class JooqCharacterRepository
@Inject
constructor(
    private val dsl: DSLContext,
    @param:Named("db") private val dispatcher: CoroutineDispatcher,
) : CharacterRepository {

  override suspend fun loadByUser(userId: Int): List<StoredCharacter> =
      withContext(dispatcher) {
        hydrate(dsl.selectFrom(CHARACTERS).where(CHARACTERS.USER_ID.eq(userId)).fetch())
      }

  override suspend fun loadById(id: Long): StoredCharacter? =
      withContext(dispatcher) {
        val row = dsl.selectFrom(CHARACTERS).where(CHARACTERS.ID.eq(id)).fetchOne()
        row?.let { hydrate(listOf(it)).single() }
      }

  override suspend fun insertAggregate(stored: StoredCharacter) =
      withContext(dispatcher) { dsl.transaction { cfg -> insert(cfg.dsl(), stored) } }

  /** Replaces the whole aggregate. The character delete cascades to pokemon and items. */
  // TODO Split the dirty tracking so a character-only change updates just the characters row
  //  instead of rewriting every pokemon and item row.
  override suspend fun saveAggregate(stored: StoredCharacter) =
      withContext(dispatcher) {
        dsl.transaction { cfg ->
          val tx = cfg.dsl()
          tx.deleteFrom(CHARACTERS).where(CHARACTERS.ID.eq(stored.info.id)).execute()
          insert(tx, stored)
        }
      }

  private fun insert(tx: DSLContext, stored: StoredCharacter) {
    tx.insertInto(CHARACTERS).set(stored.info.toRecord()).execute()
    val monsters = stored.pokemon + stored.pcStorage
    if (monsters.isNotEmpty()) {
      tx.batchInsert(monsters.map { it.toRecord() }).execute()
    }
    for ((itemId, quantity) in stored.items) {
      tx.insertInto(CHARACTER_ITEMS)
          .set(CHARACTER_ITEMS.CHARACTER_ID, stored.info.id)
          .set(CHARACTER_ITEMS.ITEM_ID, itemId)
          .set(CHARACTER_ITEMS.QUANTITY, quantity)
          .execute()
    }
  }

  /** Loads pokemon and items for all rows in one query each instead of per character. */
  private fun hydrate(rows: List<CharactersRecord>): List<StoredCharacter> {
    if (rows.isEmpty()) return emptyList()
    val ids = rows.map { it.id }
    val monstersByOwner: Map<Long, List<Pokemon>> =
        dsl.selectFrom(POKEMON)
            .where(POKEMON.OWNER_ID.`in`(ids))
            .orderBy(POKEMON.OWNER_ID, POKEMON.CONTAINER, POKEMON.CONTAINER_SLOT)
            .fetch()
            .map { it.toPokemon() }
            .groupBy { it.ownerId }
    val itemsByOwner: Map<Long, Map<Int, Int>> =
        dsl.selectFrom(CHARACTER_ITEMS)
            .where(CHARACTER_ITEMS.CHARACTER_ID.`in`(ids))
            .fetch()
            .groupBy({ it.characterId }, { it.itemId to it.quantity })
            .mapValues { (_, pairs) -> pairs.toMap() }
    return rows.map { row ->
      val monsters = monstersByOwner[row.id].orEmpty()
      val (party, pc) = monsters.partition { it.container == PokemonContainer.PARTY }
      StoredCharacter(
          info = row.toInfo(),
          pokemon = party.toMutableList(),
          pcStorage = pc.toMutableList(),
          items = itemsByOwner[row.id].orEmpty().toMutableMap(),
      )
    }
  }

  private fun CharacterInfo.toRecord(): CharactersRecord =
      CharactersRecord(
          id = id,
          userId = userId,
          name = name,
          namePrefix = namePrefix,
          rivalSex = rivalSex.toShort(),
          lastLogin = lastLogin,
          createdAt = createdAt,
          money = money,
          permissions = permissions,
          remainingSafariSteps = remainingSafariSteps,
          remainingSafariBalls = remainingSafariBalls.toShort(),
          pcExtraSlots = pcExtraSlots.toShort(),
          battleBoxExtraSlots = battleBoxExtraSlots.toShort(),
          templateAmount = templateAmount.toShort(),
          positionRegionId = positionRegionId.toShort(),
          positionBankId = positionBankId.toShort(),
          positionMapId = positionMapId.toShort(),
          positionX = positionX,
          positionY = positionY,
          repelLeft = repelLeft,
          repelItemId = repelItemId,
          lureLeft = lureLeft,
          lureItemId = lureItemId,
      )

  private fun CharactersRecord.toInfo(): CharacterInfo =
      CharacterInfo(
          id = id,
          name = name,
          namePrefix = namePrefix ?: "",
          userId = userId,
          rivalSex = rivalSex.toByte(),
          lastLogin = lastLogin,
          createdAt = createdAt,
          money = money,
          permissions = permissions,
          remainingSafariSteps = remainingSafariSteps,
          remainingSafariBalls = remainingSafariBalls.toByte(),
          pcExtraSlots = pcExtraSlots.toByte(),
          battleBoxExtraSlots = battleBoxExtraSlots.toByte(),
          templateAmount = templateAmount.toByte(),
          positionRegionId = positionRegionId.toByte(),
          positionBankId = positionBankId.toByte(),
          positionMapId = positionMapId.toByte(),
          positionX = positionX,
          positionY = positionY,
          repelLeft = repelLeft,
          repelItemId = repelItemId,
          lureLeft = lureLeft,
          lureItemId = lureItemId,
      )

  private fun Pokemon.toRecord(): PokemonRecord =
      PokemonRecord(
          id = id,
          ownerId = ownerId,
          container = container.name,
          containerSlot = containerSlot,
          dexId = dexId,
          seed = seed,
          ot = ot,
          nickname = nickname,
          pokemonLevel = level.toShort(),
          hp = hp,
          xp = xp,
          evHp = eVs.hp.toShort(),
          evAtk = eVs.atk.toShort(),
          evDef = eVs.def.toShort(),
          evSpAtk = eVs.spAtk.toShort(),
          evSpDef = eVs.spDef.toShort(),
          evSpd = eVs.spd.toShort(),
          ivHp = iVs.hp.toShort(),
          ivAtk = iVs.atk.toShort(),
          ivDef = iVs.def.toShort(),
          ivSpAtk = iVs.spAtk.toShort(),
          ivSpDef = iVs.spDef.toShort(),
          ivSpd = iVs.spd.toShort(),
          move1Id = moves.getOrNull(0)?.id ?: 0,
          move1Pp = (moves.getOrNull(0)?.pp ?: 0).toShort(),
          move2Id = moves.getOrNull(1)?.id ?: 0,
          move2Pp = (moves.getOrNull(1)?.pp ?: 0).toShort(),
          move3Id = moves.getOrNull(2)?.id ?: 0,
          move3Pp = (moves.getOrNull(2)?.pp ?: 0).toShort(),
          move4Id = moves.getOrNull(3)?.id ?: 0,
          move4Pp = (moves.getOrNull(3)?.pp ?: 0).toShort(),
          isShiny = isShiny,
          hasHiddenAbility = hasHiddenAbility,
          isAlpha = isAlpha,
          isSecret = isSecret,
          isFatefulEncounter = isFatefulEncounter,
          isRaidEncounter = isRaidEncounter,
          isEgg = isEgg,
          caughtAt = caughtAt,
      )

  private fun PokemonRecord.toPokemon(): Pokemon =
      Pokemon(
          id = id,
          ownerId = ownerId,
          container = PokemonContainer.valueOf(container),
          containerSlot = containerSlot,
          dexId = dexId,
          seed = seed,
          ot = ot,
          nickname = nickname ?: "",
          level = pokemonLevel.toByte(),
          hp = hp,
          xp = xp,
          eVs = hydrateEvs(),
          iVs = hydrateIvs(),
          moves =
              listOf(
                  PokemonMove(move1Id ?: 0, (move1Pp ?: 0).toByte()),
                  PokemonMove(move2Id ?: 0, (move2Pp ?: 0).toByte()),
                  PokemonMove(move3Id ?: 0, (move3Pp ?: 0).toByte()),
                  PokemonMove(move4Id ?: 0, (move4Pp ?: 0).toByte()),
              ),
          isShiny = isShiny ?: false,
          hasHiddenAbility = hasHiddenAbility ?: false,
          isAlpha = isAlpha ?: false,
          isSecret = isSecret ?: false,
          isFatefulEncounter = isFatefulEncounter ?: false,
          isRaidEncounter = isRaidEncounter ?: false,
          isEgg = isEgg ?: false,
          caughtAt = caughtAt,
      )

  // The stat setters reject zero, so only set stats that are above it.
  private fun PokemonStats.setPositive(
      hp: Short?,
      atk: Short?,
      def: Short?,
      spAtk: Short?,
      spDef: Short?,
      spd: Short?,
  ) {
    hp?.toInt()?.let { if (it > 0) this.hp = it }
    atk?.toInt()?.let { if (it > 0) this.atk = it }
    def?.toInt()?.let { if (it > 0) this.def = it }
    spAtk?.toInt()?.let { if (it > 0) this.spAtk = it }
    spDef?.toInt()?.let { if (it > 0) this.spDef = it }
    spd?.toInt()?.let { if (it > 0) this.spd = it }
  }

  private fun PokemonRecord.hydrateEvs(): EVs =
      EVs().also { it.setPositive(evHp, evAtk, evDef, evSpAtk, evSpDef, evSpd) }

  private fun PokemonRecord.hydrateIvs(): IVs =
      IVs().also { it.setPositive(ivHp, ivAtk, ivDef, ivSpAtk, ivSpDef, ivSpd) }
}
