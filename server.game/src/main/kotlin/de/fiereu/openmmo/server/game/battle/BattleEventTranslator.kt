package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.net.game.packets.BattleEntityDeltaPacket
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Translates a sidecar turn snapshot into the **validated** S2C battle event packets.
 *
 * Implemented now: HP deltas → `0x16` BattleEntityDelta (codec validated byte-exact vs the capture;
 * see BattleEntityDeltaPacketTest + docs/BATTLE-PACKET-MAP.md).
 *
 * GATED (do NOT guess): the move-event `0x33` + full per-turn stream need capture #2 (the captured
 * battle had no attack move). Wiring these into a real battle also needs consistent per-mon
 * **entity ids** from the session/battle-open (currently template-derived) — so [hpDeltas] takes
 * them as inputs and BattleService will thread the ids once the session entity model lands.
 */
object BattleEventTranslator {

  /** BattleEntityDelta.fieldMask bit for a current-HP update (bit 3). */
  private const val MASK_CURRENT_HP = 8

  /** An S2C `0x16` carrying only a current-HP update for [entityId]. */
  fun hpDelta(entityId: Long, currentHp: Int): BattleEntityDeltaPacket =
      BattleEntityDeltaPacket(
          entityId = entityId,
          fieldMask = MASK_CURRENT_HP,
          currentHp = currentHp.toShort(),
          experienceLevel = null,
          experiencePoints = null,
          statValues = null,
          moveSlots = null,
          ppUps = null,
          faintFlag = null,
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

  /** HP deltas for both active mons from a sidecar `sides` snapshot, keyed to their entity ids. */
  fun hpDeltas(
      sides: JsonElement?,
      playerEntityId: Long,
      wildEntityId: Long,
  ): List<BattleEntityDeltaPacket> = buildList {
    activeHp(sides, "player")?.let { add(hpDelta(playerEntityId, it)) }
    activeHp(sides, "wild")?.let { add(hpDelta(wildEntityId, it)) }
  }

  private fun activeHp(sides: JsonElement?, side: String): Int? {
    val arr = sides as? JsonArray ?: return null
    for (entry in arr) {
      val obj = entry.jsonObject
      if (obj["side"]?.jsonPrimitive?.content != side) continue
      val active = obj["active"]?.jsonArray?.firstOrNull()?.jsonObject ?: return null
      return active["hpCurrent"]?.jsonPrimitive?.intOrNull
    }
    return null
  }
}
