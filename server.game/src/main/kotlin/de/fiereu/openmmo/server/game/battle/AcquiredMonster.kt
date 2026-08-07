package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.pokemon.SpeciesDef

// The real server follows the record on 0x14 with this. Its mask 0x21B also carries a happiness
// we do not model.
private const val ACQUIRED_MASK = 0x1 or 0x2 or 0x8 or 0x10

/** Tells the client about a monster the player just obtained. */
fun acquiredMonsterDelta(mon: Pokemon, species: SpeciesDef): BattleEntityDeltaPacket {
  val stats = StatCalculator.computeAll(species, mon)
  return entityDelta(
      entityId = mon.id,
      mask = ACQUIRED_MASK,
      experienceLevel = mon.level,
      experiencePoints = mon.xp,
      statValues = stats.asWireList(),
      currentHp = mon.hp,
      faintFlag = 0,
  )
}

fun entityDelta(
    entityId: Long,
    mask: Int,
    experienceLevel: Byte? = null,
    experiencePoints: Int? = null,
    statValues: List<Short>? = null,
    currentHp: Short? = null,
    faintFlag: Byte? = null,
): BattleEntityDeltaPacket =
    BattleEntityDeltaPacket(
        entityId = entityId,
        fieldMask = mask,
        experienceLevel = experienceLevel,
        experiencePoints = experiencePoints,
        statValues = statValues,
        moveSlots = null,
        ppUps = null,
        currentHp = currentHp,
        faintFlag = faintFlag,
        speciesId = null,
        forme = null,
        listType = null,
        sortKey = null,
        evValues = null,
        level = null,
        happiness = null,
        shininessSeed = null,
        experience = null,
        flagA = null,
        flagB = null,
        encounterType = null,
        statusFlagsValue = null,
        warnA = null,
        warnB = null,
        natureId = null,
        ribbons = null,
        packedIvs = null,
        effortValues = null,
        originalSpecies = null,
        originalTrainerName = null,
        originalTrainerId = null,
        shininessType = null,
        gender = null,
        ivs = null,
        caughtBall = null,
        statusFlags2 = null,
        statusList = null,
        status = null,
    )
