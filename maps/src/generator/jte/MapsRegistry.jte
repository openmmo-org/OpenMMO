@import de.fiereu.openmmo.maps.generator.ParsedMap
@import de.fiereu.openmmo.maps.generator.RenderUtil
@import java.util.List

@param List<List<ParsedMap>> chunks
package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.net.game.packets.MapData

internal object MapDefaults {
  fun loadInto(mgr: MapManager) {
@for(int i = 0; i < chunks.size(); i++)
    chunk${i}(mgr)
@endfor
  }

@for(int chunkIndex = 0; chunkIndex < chunks.size(); chunkIndex++)
  private fun chunk${chunkIndex}(mgr: MapManager) {
@for(ParsedMap map : chunks.get(chunkIndex))
    // ${map.getSourceName()} (${map.getMapId()})
    mgr.register(
        MapDef(
            regionId = ${map.getRegion()},
            bankId = ${map.getBank()},
            mapId = ${map.getIndex()},
            width = ${map.getWidth()},
            height = ${map.getHeight()},
            paletteIdx1 = ${map.getPaletteIdx1()},
            paletteIdx2 = ${map.getPaletteIdx2()},
            unknownShort = ${map.getMusicId()},
            unknownByte = ${map.getMapsecId()},
            borderTiles = ${RenderUtil.INSTANCE.borderTiles(map.getBorderTiles())},
            lighting = ${map.getLighting()},
            weather = ${map.getWeather()},
            mapType = ${map.getMapType()},
            encounterType = ${map.getEncounterType()},
            connections = ${RenderUtil.INSTANCE.connections(map.getConnections())},
            warps = ${RenderUtil.INSTANCE.warps(map.getWarps())},
            npcs = ${RenderUtil.INSTANCE.npcs(map.getVisibleNpcs())},
            bgEvents = ${RenderUtil.INSTANCE.bgEvents(map.getBgEvents())},
        )
    )
@endfor
  }

@endfor
}
