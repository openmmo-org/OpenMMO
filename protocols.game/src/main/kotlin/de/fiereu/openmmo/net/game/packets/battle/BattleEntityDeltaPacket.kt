package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

private val MoveSlotCodec: Codec<Pair<Short, Byte>> = S16LE.then(S8)

data class BattleEntityDeltaPacket(
    val entityId: Long,
    val fieldMask: Int,
    val experienceLevel: Byte?,
    val experiencePoints: Int?,
    val statValues: List<Short>?,
    val moveSlots: List<Pair<Short, Byte>>?,
    val ppUps: Byte?,
    val currentHp: Short?,
    val faintFlag: Byte?,
    val speciesId: Short?,
    val forme: Byte?,
    val listType: Byte?,
    val sortKey: Short?,
    val evValues: List<Short>?,
    val level: Short?,
    val happiness: Short?,
    val shininessSeed: Int?,
    val experience: Int?,
    val flagA: Byte?,
    val flagB: Byte?,
    val encounterType: Byte?,
    val statusFlagsValue: Short?,
    val warnA: Short?,
    val warnB: Short?,
    val natureId: Byte?,
    val ribbons: Long?,
    val packedIvs: Int?,
    val effortValues: List<Short>?,
    val originalSpecies: Long?,
    val originalTrainerName: String?,
    val originalTrainerId: Int?,
    val shininessType: Byte?,
    val gender: Byte?,
    val ivs: List<Short>?,
    val caughtBall: Byte?,
    val statusFlags2: Short?,
    val statusList: List<Byte>?,
    val status: Byte?,
)

object BattleEntityDeltaPacketCodec : PacketCodec<BattleEntityDeltaPacket>() {
  override fun CodecScope<BattleEntityDeltaPacket>.body(): BattleEntityDeltaPacket {
    val entityId = field(S64LE) { it.entityId }
    val fieldMask = field(S32LE) { it.fieldMask }
    val m = fieldMask

    val experienceLevel = if (m and 1 != 0) field(S8) { it.experienceLevel!! } else null
    val experiencePoints = if (m and 1 != 0) field(S32LE) { it.experiencePoints!! } else null
    val statValues = if (m and 2 != 0) field(S16LE.repeat(6)) { it.statValues!! } else null
    val moveSlots = if (m and 4 != 0) field(MoveSlotCodec.repeat(4)) { it.moveSlots!! } else null
    val ppUps = if (m and 4 != 0) field(S8) { it.ppUps!! } else null
    val currentHp = if (m and 8 != 0) field(S16LE) { it.currentHp!! } else null
    val faintFlag = if (m and 16 != 0) field(S8) { it.faintFlag!! } else null
    val speciesId = if (m and 32 != 0) field(S16LE) { it.speciesId!! } else null
    val forme = if (m and 32 != 0) field(S8) { it.forme!! } else null
    val listType = if (m and 64 != 0) field(S8) { it.listType!! } else null
    val sortKey = if (m and 64 != 0) field(S16LE) { it.sortKey!! } else null
    val evValues = if (m and 128 != 0) field(S16LE.repeat(6)) { it.evValues!! } else null
    val level = if (m and 256 != 0) field(S16LE) { it.level!! } else null
    val happiness = if (m and 512 != 0) field(S16LE) { it.happiness!! } else null
    val shininessSeed = if (m and 1024 != 0) field(S32LE) { it.shininessSeed!! } else null
    val experience = if (m and 1024 != 0) field(S32LE) { it.experience!! } else null
    val flagA = if (m and 1024 != 0) field(S8) { it.flagA!! } else null
    val flagB = if (m and 1024 != 0) field(S8) { it.flagB!! } else null
    val encounterType = if (m and 1024 != 0) field(S8) { it.encounterType!! } else null
    val statusFlagsValue = if (m and 2048 != 0) field(S16LE) { it.statusFlagsValue!! } else null
    val warnA = if (m and 4096 != 0) field(S16LE) { it.warnA!! } else null
    val warnB = if (m and 4096 != 0) field(S16LE) { it.warnB!! } else null
    val natureId = if (m and 8192 != 0) field(S8) { it.natureId!! } else null
    val ribbons = if (m and 16384 != 0) field(S64LE) { it.ribbons!! } else null
    val packedIvs = if (m and 2097152 != 0) field(S32LE) { it.packedIvs!! } else null

    val effortValues = if (m and 32768 != 0) field(S16LE.repeat(4)) { it.effortValues!! } else null
    val originalSpecies = if (m and 32768 != 0) field(S64LE) { it.originalSpecies!! } else null
    val originalTrainerName =
        if (m and 32768 != 0) field(Utf16LeNullTerminated) { it.originalTrainerName!! } else null
    val originalTrainerId = if (m and 32768 != 0) field(S32LE) { it.originalTrainerId!! } else null

    val shininessType = if (m and 65536 != 0) field(S8) { it.shininessType!! } else null
    val gender = if (m and 131072 != 0) field(S8) { it.gender!! } else null
    val ivs = if (m and 262144 != 0) field(S16LE.repeat(5)) { it.ivs!! } else null
    val caughtBall = if (m and 524288 != 0) field(S8) { it.caughtBall!! } else null
    val statusFlags2 = if (m and 1048576 != 0) field(S16LE) { it.statusFlags2!! } else null
    val statusList =
        if (m and 4194304 != 0) field(S8.listPrefixed(U8)) { it.statusList!! } else null
    val status = if (m and 8388608 != 0) field(S8) { it.status!! } else null

    return BattleEntityDeltaPacket(
        entityId = entityId,
        fieldMask = fieldMask,
        experienceLevel = experienceLevel,
        experiencePoints = experiencePoints,
        statValues = statValues,
        moveSlots = moveSlots,
        ppUps = ppUps,
        currentHp = currentHp,
        faintFlag = faintFlag,
        speciesId = speciesId,
        forme = forme,
        listType = listType,
        sortKey = sortKey,
        evValues = evValues,
        level = level,
        happiness = happiness,
        shininessSeed = shininessSeed,
        experience = experience,
        flagA = flagA,
        flagB = flagB,
        encounterType = encounterType,
        statusFlagsValue = statusFlagsValue,
        warnA = warnA,
        warnB = warnB,
        natureId = natureId,
        ribbons = ribbons,
        packedIvs = packedIvs,
        effortValues = effortValues,
        originalSpecies = originalSpecies,
        originalTrainerName = originalTrainerName,
        originalTrainerId = originalTrainerId,
        shininessType = shininessType,
        gender = gender,
        ivs = ivs,
        caughtBall = caughtBall,
        statusFlags2 = statusFlags2,
        statusList = statusList,
        status = status,
    )
  }
}
