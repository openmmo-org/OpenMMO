@import de.fiereu.openmmo.codegen.maps.ParsedMap
@import de.fiereu.openmmo.codegen.maps.RenderUtil

@param String packageName
@param String objectName
@param ParsedMap map
package ${packageName}

import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.MovementType
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.maps.BgEventDef
import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.maps.NpcDef
import de.fiereu.openmmo.maps.WarpTile
import de.fiereu.openmmo.net.game.packets.MapData

internal val ${objectName}: MapDef =
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
        blockData = "${map.getBlockData()}",
        lighting = ${map.getLighting()},
        weather = ${map.getWeather()},
        mapType = ${map.getMapType()},
        encounterType = ${map.getEncounterType()},
        connections = ${RenderUtil.INSTANCE.connections(map.getConnections())},
        warps = ${RenderUtil.INSTANCE.warps(map.getWarps())},
        npcs = ${RenderUtil.INSTANCE.npcs(map.getVisibleNpcs())},
        bgEvents = ${RenderUtil.INSTANCE.bgEvents(map.getBgEvents())},
    )
