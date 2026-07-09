package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.EntityStatus
import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.codecs.SkinSet
import de.fiereu.openmmo.net.game.codecs.opaqueSkinSet
import de.fiereu.openmmo.net.game.packets.LoadEntityPacket
import de.fiereu.openmmo.net.game.packets.MapData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapLoadService
@Inject
constructor(
    private val mapManager: MapManager,
) {

  fun createLoadEntity(
      info: CharacterInfo,
      facing: Direction = Direction.DOWN,
      z: Int = 0,
      hasFollower: Boolean = false,
      followerDexId: Short = 0,
  ): LoadEntityPacket {
    return LoadEntityPacket(
        entityId = info.id,
        skin = if (info.cosmetics.isNotEmpty()) opaqueSkinSet(info.cosmetics) else SkinSet(),
        name = info.name,
        regionId = info.positionRegionId.toInt(),
        bankId = info.positionBankId.toInt(),
        mapId = info.positionMapId.toInt(),
        x = info.positionX.toInt(),
        y = info.positionY.toInt(),
        z = z,
        facing = facing,
        status = EntityStatus.NONE,
        hasFollower = hasFollower,
        followerDexId = followerDexId,
    )
  }

  fun preloadConnectedMaps(
      ctx: SessionContext,
      map: MapDef,
      depth: Int = 2,
      reloadPlayer: Boolean = false,
  ) {
    val loaded = mutableSetOf<String>()
    loaded.add("${map.bankId}:${map.mapId}")
    fun preload(connections: List<MapData.GbaConnection>, remaining: Int) {
      if (remaining <= 0) return
      for (conn in connections) {
        val key = "${conn.targetBank}:${conn.targetMap}"
        if (!loaded.add(key)) continue
        val connected = mapManager.getMap(1, conn.targetBank, conn.targetMap)
        if (connected != null) {
          ctx.send(
              mapManager.createLoadMapPacket(
                  connected,
                  reloadPlayer = reloadPlayer,
                  deleteCache = false,
              ))
          preload(connected.connections, remaining - 1)
        }
      }
    }
    preload(map.connections, depth)
  }
}
