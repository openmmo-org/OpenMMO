package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.NpcSpawnPacket
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
) {

  private val npcEntityIdCounter = AtomicLong(0x1A69000000000000L)
  private val npcEntityIds = mutableMapOf<String, Long>()

  fun getNpcEntityId(bankId: Int, mapId: Int, entityIdx: Int): Long? {
    return npcEntityIds["$bankId:$mapId:$entityIdx"]
  }

  fun spawnNpcsForMap(ctx: SessionContext, bankId: Int, mapId: Int) {
    val map = mapManager.getMap(1, bankId, mapId) ?: return

    if (bankId == 74 && mapId == 11) {
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
      val key = "$bankId:$mapId:${npc.entityIdx}"
      val entityId = npcEntityIds.getOrPut(key) { npcEntityIdCounter.incrementAndGet() }
      val movementId = npc.movementType.forRegion(Region.HOENN).id
      val unk3 = ((movementId and 0xFF) shl 8) or 0x02
      val unk4 =
          if (movementId in 1..6 || (movementId in 25..52)) {
            ((npc.movementRangeX and 0xFF) shl 8) or (npc.movementRangeY and 0xFF)
          } else {
            0
          }
      val spawnPacket =
          NpcSpawnPacket(
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
      ctx.send(spawnPacket)
    }
  }
}
