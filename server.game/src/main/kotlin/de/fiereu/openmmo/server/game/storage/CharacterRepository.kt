package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.Skin
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.SkinSlot
import de.fiereu.openmmo.db.game.tables.records.CharacterFlagsRecord
import de.fiereu.openmmo.db.game.tables.records.CharacterItemsRecord
import de.fiereu.openmmo.db.game.tables.records.CharacterSkinsRecord
import de.fiereu.openmmo.db.game.tables.records.CharacterVarsRecord
import de.fiereu.openmmo.db.game.tables.records.CharactersRecord
import de.fiereu.openmmo.db.game.tables.records.PokemonRecord
import de.fiereu.openmmo.db.game.tables.references.CHARACTERS
import de.fiereu.openmmo.db.game.tables.references.CHARACTER_FLAGS
import de.fiereu.openmmo.db.game.tables.references.CHARACTER_ITEMS
import de.fiereu.openmmo.db.game.tables.references.CHARACTER_SKINS
import de.fiereu.openmmo.db.game.tables.references.CHARACTER_VARS
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

  /** A null [previous] writes every row. */
  suspend fun saveChanges(previous: StoredCharacter?, current: StoredCharacter)

  /** Deletes a character owned by the user. */
  suspend fun deleteById(userId: Int, id: Long): Boolean
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

  override suspend fun saveChanges(previous: StoredCharacter?, current: StoredCharacter) =
      withContext(dispatcher) {
        dsl.transaction { cfg -> writeChanges(cfg.dsl(), previous, current) }
      }

  override suspend fun deleteById(userId: Int, id: Long): Boolean =
      withContext(dispatcher) {
        dsl.deleteFrom(CHARACTERS)
            .where(CHARACTERS.ID.eq(id))
            .and(CHARACTERS.USER_ID.eq(userId))
            .execute() == 1
      }

  private fun writeChanges(
      tx: DSLContext,
      previous: StoredCharacter?,
      current: StoredCharacter,
  ) {
    val id = current.info.id
    tx.writeDelta(
        CHARACTERS,
        rowDelta(previous?.let { mapOf(id to it.info) }.orEmpty(), mapOf(id to current.info)),
        { CHARACTERS.ID.eq(it) },
        { _, info -> info.toRecord() },
    )
    tx.writeDelta(
        POKEMON,
        rowDelta(previous.monstersById(), current.monstersById()),
        { POKEMON.ID.eq(it) },
        { _, monster -> monster.toRecord() },
    )
    tx.writeDelta(
        CHARACTER_ITEMS,
        rowDelta(previous?.items.orEmpty(), current.items),
        { CHARACTER_ITEMS.CHARACTER_ID.eq(id).and(CHARACTER_ITEMS.ITEM_ID.eq(it)) },
        { itemId, quantity -> CharacterItemsRecord(id, itemId, quantity) },
    )
    tx.writeDelta(
        CHARACTER_FLAGS,
        rowDelta(previous?.storyFlags.orEmpty(), current.storyFlags),
        { CHARACTER_FLAGS.CHARACTER_ID.eq(id).and(CHARACTER_FLAGS.FLAG_KEY.eq(it)) },
        { key, _ -> CharacterFlagsRecord(id, key) },
    )
    tx.writeDelta(
        CHARACTER_VARS,
        rowDelta(previous?.storyVars.orEmpty(), current.storyVars),
        { CHARACTER_VARS.CHARACTER_ID.eq(id).and(CHARACTER_VARS.VAR_KEY.eq(it)) },
        { key, value -> CharacterVarsRecord(id, key, value) },
    )
    tx.writeDelta(
        CHARACTER_SKINS,
        rowDelta(previous?.skins.orEmpty(), current.skins),
        { CHARACTER_SKINS.CHARACTER_ID.eq(id).and(CHARACTER_SKINS.SLOT.eq(it.name)) },
        { slot, skin -> skin.toRecord(id, slot) },
    )
  }

  private fun StoredCharacter?.monstersById(): Map<Long, Pokemon> =
      this?.let { (it.pokemon + it.pcStorage).associateBy { monster -> monster.id } }.orEmpty()

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
    for (flag in stored.storyFlags) {
      tx.insertInto(CHARACTER_FLAGS)
          .set(CHARACTER_FLAGS.CHARACTER_ID, stored.info.id)
          .set(CHARACTER_FLAGS.FLAG_KEY, flag)
          .execute()
    }
    for ((key, value) in stored.storyVars) {
      tx.insertInto(CHARACTER_VARS)
          .set(CHARACTER_VARS.CHARACTER_ID, stored.info.id)
          .set(CHARACTER_VARS.VAR_KEY, key)
          .set(CHARACTER_VARS.VAR_VALUE, value)
          .execute()
    }
    if (stored.skins.isNotEmpty()) {
      tx.batchInsert(stored.skins.map { (slot, skin) -> skin.toRecord(stored.info.id, slot) })
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
    val flagsByOwner: Map<Long, Set<String>> =
        dsl.selectFrom(CHARACTER_FLAGS)
            .where(CHARACTER_FLAGS.CHARACTER_ID.`in`(ids))
            .fetch()
            .groupBy({ it.characterId }, { it.flagKey })
            .mapValues { (_, keys) -> keys.toSet() }
    val varsByOwner: Map<Long, Map<String, Int>> =
        dsl.selectFrom(CHARACTER_VARS)
            .where(CHARACTER_VARS.CHARACTER_ID.`in`(ids))
            .fetch()
            .groupBy({ it.characterId }, { it.varKey to it.varValue })
            .mapValues { (_, pairs) -> pairs.toMap() }
    val skinsByOwner: Map<Long, Map<SkinSlot, Skin>> =
        dsl.selectFrom(CHARACTER_SKINS)
            .where(CHARACTER_SKINS.CHARACTER_ID.`in`(ids))
            .fetch()
            .groupBy({ it.characterId }, { it.toSkin() })
            .mapValues { (_, skins) -> skins.associateBy { it.slot } }
    return rows.map { row ->
      val monsters = monstersByOwner[row.id].orEmpty()
      val (party, pc) = monsters.partition { it.container == PokemonContainer.PARTY }
      StoredCharacter(
          info = row.toInfo(),
          pokemon = party.toMutableList(),
          pcStorage = pc.toMutableList(),
          items = itemsByOwner[row.id].orEmpty().toMutableMap(),
          storyFlags = flagsByOwner[row.id].orEmpty().toMutableSet(),
          storyVars = varsByOwner[row.id].orEmpty().toMutableMap(),
          skins = skinsByOwner[row.id].orEmpty(),
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
          skinRegionSelectionIndex = skinRegionSelectionIndex.toShort(),
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
          positionFacing = positionFacing.ordinal.toShort(),
          repelLeft = repelLeft,
          repelItemId = repelItemId,
          lureLeft = lureLeft,
          lureItemId = lureItemId,
          dynamicWarpRegion = dynamicWarp?.regionId?.toShort(),
          dynamicWarpBank = dynamicWarp?.bankId?.toShort(),
          dynamicWarpMap = dynamicWarp?.mapId?.toShort(),
          dynamicWarpX = dynamicWarp?.x,
          dynamicWarpY = dynamicWarp?.y,
          dynamicWarpFacing = dynamicWarp?.facing?.ordinal?.toShort(),
      )

  private fun CharactersRecord.toInfo(): CharacterInfo =
      CharacterInfo(
          id = id,
          name = name,
          namePrefix = namePrefix ?: "",
          userId = userId,
          rivalSex = rivalSex.toByte(),
          skinRegionSelectionIndex = skinRegionSelectionIndex?.toInt() ?: 0,
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
          positionFacing =
              Direction.entries.getOrElse(positionFacing?.toInt() ?: 0) { Direction.DOWN },
          repelLeft = repelLeft,
          repelItemId = repelItemId,
          lureLeft = lureLeft,
          lureItemId = lureItemId,
          dynamicWarp = toDynamicWarp(),
      )

  private fun CharactersRecord.toDynamicWarp(): DynamicWarp? {
    val region = dynamicWarpRegion ?: return null
    val bank = dynamicWarpBank ?: return null
    val map = dynamicWarpMap ?: return null
    val x = dynamicWarpX ?: return null
    val y = dynamicWarpY ?: return null
    val facing = dynamicWarpFacing ?: return null
    return DynamicWarp(
        regionId = region.toByte(),
        bankId = bank.toByte(),
        mapId = map.toByte(),
        x = x,
        y = y,
        facing = Direction.entries[facing.toInt()],
    )
  }

  private fun Skin.toRecord(characterId: Long, slot: SkinSlot): CharacterSkinsRecord =
      CharacterSkinsRecord(
          characterId = characterId,
          slot = slot.name,
          skinType = type?.toShort(),
          skinColor = color?.toShort(),
      )

  private fun CharacterSkinsRecord.toSkin(): Skin =
      Skin(
          slot = SkinSlot.valueOf(slot),
          type = skinType?.toUShort(),
          color = skinColor?.toUByte(),
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

  private fun PokemonRecord.hydrateEvs(): EVs =
      EVs().also {
        it.hp = (evHp ?: 0).toInt()
        it.atk = (evAtk ?: 0).toInt()
        it.def = (evDef ?: 0).toInt()
        it.spAtk = (evSpAtk ?: 0).toInt()
        it.spDef = (evSpDef ?: 0).toInt()
        it.spd = (evSpd ?: 0).toInt()
      }

  private fun PokemonRecord.hydrateIvs(): IVs =
      IVs().also {
        it.hp = (ivHp ?: 0).toInt()
        it.atk = (ivAtk ?: 0).toInt()
        it.def = (ivDef ?: 0).toInt()
        it.spAtk = (ivSpAtk ?: 0).toInt()
        it.spDef = (ivSpDef ?: 0).toInt()
        it.spd = (ivSpd ?: 0).toInt()
      }
}
