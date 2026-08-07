package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

private val MoveSlotCodec: Codec<Pair<Short, Byte>> = S16LE.then(S8)

// One bit per group, set when the group is.
private const val EXPERIENCE = 0x1
private const val STATS = 0x2
private const val MOVES = 0x4
private const val CURRENT_HP = 0x8
private const val FAINT = 0x10
private const val SPECIES = 0x20
private const val LISTING = 0x40
private const val EVS = 0x80
private const val LEVEL = 0x100
private const val HAPPINESS = 0x200
private const val POSITION = 0x400
private const val STATUS_FLAGS = 0x800
private const val MAP_POSITION = 0x1000
private const val NATURE = 0x2000
private const val VALUE_64 = 0x4000
private const val ORIGIN = 0x8000
private const val SHININESS_TYPE = 0x10000
private const val GENDER = 0x20000
private const val IVS = 0x40000
private const val CAUGHT_BALL = 0x80000
private const val STATUS_FLAGS_2 = 0x100000
private const val PACKED_IVS = 0x200000
private const val STATUS_LIST = 0x400000
private const val STATUS = 0x800000

data class Experience(val level: Byte, val points: Int)

data class MoveSlots(val slots: List<Pair<Short, Byte>>, val ppUps: Byte)

data class Species(val speciesId: Short, val forme: Byte)

data class Listing(val listType: Byte, val sortKey: Short)

data class Position(val x: Int, val y: Int, val flagA: Byte, val flagB: Byte, val flagC: Byte)

/** Map square the client turns into a location name. */
data class MapPosition(val mapX: Short, val mapY: Short)

data class Origin(
    val effortValues: List<Short>,
    val species: Long,
    val trainerName: String,
    val trainerId: Int,
)

/** A partial update to one entity. A null group is left off the wire. */
data class BattleEntityDeltaPacket(
    val entityId: Long,
    val experience: Experience? = null,
    val statValues: List<Short>? = null,
    val moves: MoveSlots? = null,
    // A capture shows this matching the monster's hp. The client calls it a level.
    val currentHp: Short? = null,
    val faintFlag: Byte? = null,
    val species: Species? = null,
    // The client reads these as a facing and a heading. No capture either way yet.
    val listing: Listing? = null,
    val evValues: List<Short>? = null,
    val level: Short? = null,
    val happiness: Short? = null,
    val position: Position? = null,
    val statusFlagsValue: Short? = null,
    val mapPosition: MapPosition? = null,
    val natureId: Byte? = null,
    val value64: Long? = null,
    val origin: Origin? = null,
    val shininessType: Byte? = null,
    val gender: Byte? = null,
    val ivs: List<Short>? = null,
    val caughtBall: Byte? = null,
    val statusFlags2: Short? = null,
    val packedIvs: Int? = null,
    val statusList: List<Byte>? = null,
    val status: Byte? = null,
)

private fun BattleEntityDeltaPacket.mask(): Int {
  var m = 0
  if (experience != null) m = m or EXPERIENCE
  if (statValues != null) m = m or STATS
  if (moves != null) m = m or MOVES
  if (currentHp != null) m = m or CURRENT_HP
  if (faintFlag != null) m = m or FAINT
  if (species != null) m = m or SPECIES
  if (listing != null) m = m or LISTING
  if (evValues != null) m = m or EVS
  if (level != null) m = m or LEVEL
  if (happiness != null) m = m or HAPPINESS
  if (position != null) m = m or POSITION
  if (statusFlagsValue != null) m = m or STATUS_FLAGS
  if (mapPosition != null) m = m or MAP_POSITION
  if (natureId != null) m = m or NATURE
  if (value64 != null) m = m or VALUE_64
  if (origin != null) m = m or ORIGIN
  if (shininessType != null) m = m or SHININESS_TYPE
  if (gender != null) m = m or GENDER
  if (ivs != null) m = m or IVS
  if (caughtBall != null) m = m or CAUGHT_BALL
  if (statusFlags2 != null) m = m or STATUS_FLAGS_2
  if (packedIvs != null) m = m or PACKED_IVS
  if (statusList != null) m = m or STATUS_LIST
  if (status != null) m = m or STATUS
  return m
}

object BattleEntityDeltaPacketCodec : PacketCodec<BattleEntityDeltaPacket>() {
  override fun CodecScope<BattleEntityDeltaPacket>.body(): BattleEntityDeltaPacket {
    val entityId = field(S64LE) { it.entityId }
    val m = field(S32LE) { it.mask() }

    val experience =
        if (m and EXPERIENCE != 0)
            Experience(
                field(S8) { it.experience!!.level },
                field(S32LE) { it.experience!!.points },
            )
        else null
    val statValues = optionalField(m and STATS != 0, S16LE.repeat(6)) { it.statValues }
    val moves =
        if (m and MOVES != 0)
            MoveSlots(
                field(MoveSlotCodec.repeat(4)) { it.moves!!.slots },
                field(S8) { it.moves!!.ppUps },
            )
        else null
    val currentHp = optionalField(m and CURRENT_HP != 0, S16LE) { it.currentHp }
    val faintFlag = optionalField(m and FAINT != 0, S8) { it.faintFlag }
    val species =
        if (m and SPECIES != 0)
            Species(field(S16LE) { it.species!!.speciesId }, field(S8) { it.species!!.forme })
        else null
    val listing =
        if (m and LISTING != 0)
            Listing(field(S8) { it.listing!!.listType }, field(S16LE) { it.listing!!.sortKey })
        else null
    val evValues = optionalField(m and EVS != 0, S16LE.repeat(6)) { it.evValues }
    val level = optionalField(m and LEVEL != 0, S16LE) { it.level }
    val happiness = optionalField(m and HAPPINESS != 0, S16LE) { it.happiness }
    val position =
        if (m and POSITION != 0)
            Position(
                field(S32LE) { it.position!!.x },
                field(S32LE) { it.position!!.y },
                field(S8) { it.position!!.flagA },
                field(S8) { it.position!!.flagB },
                field(S8) { it.position!!.flagC },
            )
        else null
    val statusFlagsValue = optionalField(m and STATUS_FLAGS != 0, S16LE) { it.statusFlagsValue }
    val mapPosition =
        if (m and MAP_POSITION != 0)
            MapPosition(
                field(S16LE) { it.mapPosition!!.mapX },
                field(S16LE) { it.mapPosition!!.mapY },
            )
        else null
    val natureId = optionalField(m and NATURE != 0, S8) { it.natureId }
    val value64 = optionalField(m and VALUE_64 != 0, S64LE) { it.value64 }
    // Read out of bit order, between 0x4000 and 0x8000. Confirmed against the client.
    val packedIvs = optionalField(m and PACKED_IVS != 0, S32LE) { it.packedIvs }

    val origin =
        if (m and ORIGIN != 0)
            Origin(
                field(S16LE.repeat(4)) { it.origin!!.effortValues },
                field(S64LE) { it.origin!!.species },
                field(Utf16LeNullTerminated) { it.origin!!.trainerName },
                field(S32LE) { it.origin!!.trainerId },
            )
        else null

    val shininessType = optionalField(m and SHININESS_TYPE != 0, S8) { it.shininessType }
    val gender = optionalField(m and GENDER != 0, S8) { it.gender }
    val ivs = optionalField(m and IVS != 0, S16LE.repeat(5)) { it.ivs }
    val caughtBall = optionalField(m and CAUGHT_BALL != 0, S8) { it.caughtBall }
    val statusFlags2 = optionalField(m and STATUS_FLAGS_2 != 0, S16LE) { it.statusFlags2 }
    val statusList = optionalField(m and STATUS_LIST != 0, S8.listPrefixed(U8)) { it.statusList }
    val status = optionalField(m and STATUS != 0, S8) { it.status }

    return BattleEntityDeltaPacket(
        entityId = entityId,
        experience = experience,
        statValues = statValues,
        moves = moves,
        currentHp = currentHp,
        faintFlag = faintFlag,
        species = species,
        listing = listing,
        evValues = evValues,
        level = level,
        happiness = happiness,
        position = position,
        statusFlagsValue = statusFlagsValue,
        mapPosition = mapPosition,
        natureId = natureId,
        value64 = value64,
        origin = origin,
        shininessType = shininessType,
        gender = gender,
        ivs = ivs,
        caughtBall = caughtBall,
        statusFlags2 = statusFlags2,
        packedIvs = packedIvs,
        statusList = statusList,
        status = status,
    )
  }
}
