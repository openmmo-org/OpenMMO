package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.MovementType
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.maps.NpcDef
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.NpcSpawnPacket
import de.fiereu.openmmo.net.game.packets.NpcUpdatePacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class NpcService
@Inject
constructor(
    private val mapManager: MapManager,
    private val characterStore: CharacterStore,
) {

  private val npcEntityIdCounter = AtomicLong(0x1A69000000000000L)
  private val npcEntityIds = mutableMapOf<String, Long>()

  fun getNpcEntityId(bankId: Int, mapId: Int, entityIdx: Int): Long? {
    return npcEntityIds["$bankId:$mapId:$entityIdx"]
  }

  fun spawnNpcsForMap(ctx: SessionContext, bankId: Int, mapId: Int, regionId: Int = 1) {
    val map = mapManager.getMap(regionId, bankId, mapId) ?: return
    val stored = ctx.attributes[PLAYER_STATE]?.characterId?.let(characterStore::getCharacter)
    val storyFlags = stored?.storyFlags.orEmpty()
    val storyVars = stored?.storyVars.orEmpty()

    // Generated Petalburg data now supplies these NPCs.
    if (regionId == 1 && USE_LEGACY_PETALBURG_SPAWNS && bankId == 74 && mapId == 11) {
      data class NpcEntry(
          val entityIdx: Int,
          val x: Int,
          val y: Int,
          val gfx: Int,
          val unk1: Int,
          val unk3: Int,
          val unk4: Int,
          val facing: Int,
          val unk6: Int,
      )
      val all16 =
          listOf(
              NpcEntry(7, 15, 19, 9, 1, 768, 513, 0, 8),
              NpcEntry(6, 4, 8, 59, 1, 256, 0, 0, 8),
              NpcEntry(5, 35, 20, 59, 1, 2048, 0, 0, 8),
              NpcEntry(4, 45, 7, 59, 1, 2048, 0, 0, 8),
              NpcEntry(3, 26, 20, 23, 1, 256, 0, 0, 0),
              NpcEntry(2, 26, 17, 117, 1, 2563, 0, 3, 0),
              NpcEntry(1, 19, 11, 82, 1, 256, 0, 0, 8),
              NpcEntry(0, 19, 10, 82, 1, 256, 0, 0, 8),
              NpcEntry(15, 16, 24, 0, 10, 25600, 0, 0, 8),
              NpcEntry(14, 15, 24, 600, 10, 0, 0, 0, 0),
              NpcEntry(13, 36, 23, 246, 10, 0, 0, 0, 8),
              NpcEntry(12, 33, 5, 10, 1, 256, 257, 0, 8),
              NpcEntry(11, 4, 26, 59, 1, 256, 257, 0, 8),
              NpcEntry(10, 30, 34, 13, 1, 512, 258, 0, 8),
              NpcEntry(9, 4, 14, 36, 1, 5121, 0, 1, 9),
              NpcEntry(8, 7, 32, 36, 1, 5632, 0, 0, 8),
          )
      val baseEntityId = 0x000000000001E000L
      for (npc in all16) {
        val entityId = baseEntityId or npc.entityIdx.toLong()
        log.info {
          ">> Petalburg NpcSpawn[${npc.entityIdx}] entId=0x${entityId.toString(16)} pos=(${npc.x},${npc.y}) gfx=${npc.gfx}"
        }
        val spawnPacket =
            NpcSpawnPacket(
                entityId = entityId,
                unk1 = npc.unk1,
                unk2 = npc.gfx,
                unk3 = npc.unk3,
                unk4 = npc.unk4,
                regionId = 1,
                bankId = bankId,
                mapId = mapId,
                x = npc.x,
                y = npc.y,
                facing = npc.facing,
                unk5 = 2,
                unk6 = npc.unk6,
            )
        ctx.send(spawnPacket)
      }
      return
    }

    for (npc in map.npcs) {
      // Decoration slots are not normal NPCs.
      if (npc.hideFlag.substringAfter('/').startsWith(DECORATION_FLAG_PREFIX)) continue

      // Only set hide flags suppress NPCs.
      if (shouldHideNpc(bankId, mapId, npc, storyFlags)) continue
      val resolved =
          resolveDynamicGraphics(
              ctx, applyStoryPlacement(bankId, mapId, npc, storyFlags, storyVars))
      ctx.send(buildSpawnPacket(resolved, entityIdFor(bankId, mapId, npc.entityIdx), bankId, mapId))
    }
  }

  /** Allocate (or return) the stable entity id for a map npc by its decomp local id. */
  fun entityIdFor(bankId: Int, mapId: Int, entityIdx: Int): Long =
      npcEntityIds.getOrPut("$bankId:$mapId:$entityIdx") { npcEntityIdCounter.incrementAndGet() }

  /** Spawn a single npc (including a normally hidden one) for one player, for cutscenes. */
  fun spawnNpc(ctx: SessionContext, bankId: Int, mapId: Int, localId: Int) {
    val npc = mapManager.getMap(1, bankId, mapId)?.npcs?.firstOrNull { it.entityIdx == localId }
    if (npc == null) {
      log.warn { "spawnNpc: npc $localId not found on $bankId:$mapId" }
      return
    }
    ctx.send(
        buildSpawnPacket(
            resolveDynamicGraphics(ctx, npc),
            entityIdFor(bankId, mapId, localId),
            bankId,
            mapId,
        ))
  }

  /** Spawns an NPC at a cutscene position. */
  fun spawnNpcAt(
      ctx: SessionContext,
      bankId: Int,
      mapId: Int,
      localId: Int,
      x: Int,
      y: Int,
  ) {
    val npc = mapManager.getMap(1, bankId, mapId)?.npcs?.firstOrNull { it.entityIdx == localId }
    if (npc == null) {
      log.warn { "spawnNpcAt: npc $localId not found on $bankId:$mapId" }
      return
    }
    ctx.send(
        buildSpawnPacket(
            resolveDynamicGraphics(ctx, npc.copy(x = x, y = y)),
            entityIdFor(bankId, mapId, localId),
            bankId,
            mapId,
        ))
  }

  /** Repositions an existing NPC. */
  fun repositionNpc(
      ctx: SessionContext,
      bankId: Int,
      mapId: Int,
      localId: Int,
      x: Int,
      y: Int,
  ) {
    val npc = mapManager.getMap(1, bankId, mapId)?.npcs?.firstOrNull { it.entityIdx == localId }
    if (npc == null) {
      log.warn { "repositionNpc: npc $localId not found on $bankId:$mapId" }
      return
    }
    ctx.send(
        NpcUpdatePacket(
            entityId = entityIdFor(bankId, mapId, localId),
            regionId = 1,
            bankId = bankId,
            mapId = mapId,
            x = x,
            y = y,
            // Captures use 0xF6 followed by the direction for this update packet.
            facing = 0xF6,
            unk = npc.facing.ordinal,
        ))
  }

  /** Removes a cutscene NPC. */
  fun despawnNpc(ctx: SessionContext, bankId: Int, mapId: Int, localId: Int) {
    val entityId = getNpcEntityId(bankId, mapId, localId) ?: return
    ctx.send(EntityLeavePacket(entityId))
  }

  private fun buildSpawnPacket(
      npc: NpcDef,
      entityId: Long,
      bankId: Int,
      mapId: Int,
  ): NpcSpawnPacket {
    val movementId = npc.movementType.forRegion(Region.HOENN).id
    val unk3 = ((movementId and 0xFF) shl 8) or 0x02
    val unk4 =
        if (movementId in 1..6 || (movementId in 25..52)) {
          ((npc.movementRangeX and 0xFF) shl 8) or (npc.movementRangeY and 0xFF)
        } else {
          0
        }
    return NpcSpawnPacket(
        entityId = entityId,
        unk1 = 1,
        unk2 = npc.graphicsId,
        unk3 = unk3,
        unk4 = unk4,
        regionId = 1,
        bankId = bankId,
        mapId = mapId,
        x = npc.x,
        y = npc.y,
        facing = npc.facing.ordinal,
        unk5 = 2,
        unk6 = 8,
    )
  }

  private fun resolveDynamicGraphics(ctx: SessionContext, npc: NpcDef): NpcDef {
    if (npc.graphicsId != DYNAMIC_GFX_VAR_0 ||
        !(npc.script.contains("Rival", ignoreCase = true) ||
            npc.hideFlag.contains("RIVAL", ignoreCase = true))) {
      return npc
    }
    val playerGender =
        ctx.attributes[PLAYER_STATE]?.characterId?.let(characterStore::getCharacter)?.info?.rivalSex
    // The rival is the opposite gender from the player.
    val graphicsId = if (playerGender == FEMALE) RIVAL_BRENDAN_NORMAL else RIVAL_MAY_NORMAL
    return npc.copy(graphicsId = graphicsId)
  }

  private fun applyStoryPlacement(
      bankId: Int,
      mapId: Int,
      npc: NpcDef,
      storyFlags: Set<String>,
      storyVars: Map<String, Int>,
  ): NpcDef {
    if (bankId != LITTLEROOT_BANK || mapId != LITTLEROOT_MAP || npc.entityIdx != LITTLEROOT_TWIN) {
      return npc
    }
    if (RESCUED_BIRCH_FLAG in storyFlags) return npc
    return if (storyVars[LITTLEROOT_STATE_VAR].orZero() == 0) {
      npc.copy(x = 7, y = 2, facing = Direction.DOWN, movementType = MovementType.FACE_DOWN)
    } else {
      npc.copy(x = 10, y = 1, facing = Direction.UP, movementType = MovementType.FACE_UP)
    }
  }

  private fun Int?.orZero(): Int = this ?: 0

  private fun shouldHideNpc(
      bankId: Int,
      mapId: Int,
      npc: NpcDef,
      storyFlags: Set<String>,
  ): Boolean {
    if (npc.hideFlag in storyFlags) return true
    // Completed rescues must not restore Zigzagoon.
    return bankId == ROUTE_101_BANK &&
        mapId == ROUTE_101_MAP &&
        npc.entityIdx == ROUTE_101_ZIGZAGOON &&
        (RESCUED_BIRCH_FLAG in storyFlags || ROUTE_101_RESCUE_HIDDEN_FLAG in storyFlags)
  }

  private companion object {
    const val DECORATION_FLAG_PREFIX = "FLAG_DECORATION_"
    const val USE_LEGACY_PETALBURG_SPAWNS = false
    const val DYNAMIC_GFX_VAR_0 = 240
    const val RIVAL_BRENDAN_NORMAL = 100
    const val RIVAL_MAY_NORMAL = 105
    const val FEMALE: Byte = 1
    const val LITTLEROOT_BANK = 50
    const val LITTLEROOT_MAP = 9
    const val LITTLEROOT_TWIN = 0
    const val RESCUED_BIRCH_FLAG = "hoenn/FLAG_RESCUED_BIRCH"
    const val LITTLEROOT_STATE_VAR = "hoenn/VAR_LITTLEROOT_TOWN_STATE"
    const val ROUTE_101_BANK = 50
    const val ROUTE_101_MAP = 16
    const val ROUTE_101_ZIGZAGOON = 3
    const val ROUTE_101_RESCUE_HIDDEN_FLAG = "hoenn/FLAG_HIDE_ROUTE_101_BIRCH_ZIGZAGOON_BATTLE"
  }
}
