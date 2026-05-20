const fs = require('fs');
const path = require('path');

const POKEEMERALD = 'D:/openmmo/pokeemerald';
const OUTPUT = 'D:/openmmo/server.game/src/main/kotlin/de/fiereu/openmmo/server/game/world/MapManager.kt';

// ---- Load data ----
const mapGroups = JSON.parse(fs.readFileSync(`${POKEEMERALD}/data/maps/map_groups.json`, 'utf-8'));
const layoutsJson = JSON.parse(fs.readFileSync(`${POKEEMERALD}/data/layouts/layouts.json`, 'utf-8'));
const layouts = layoutsJson.layouts;

// Build layout lookup by name (used by map JSON as "layout": "LAYOUT_xxx")
const layoutByName = {};
for (const l of layouts) {
  layoutByName[l.id] = l;
}

// Build map group -> [map names] lookup, track index within group
const groupOrder = mapGroups.group_order;
const mapGroupEntries = {};
for (const group of groupOrder) {
  mapGroupEntries[group] = mapGroups[group] || [];
}

// Map name -> { groupIndex, mapIndexWithinGroup }
const mapToAddress = {};
// Secondary lookup that normalizes MAP_LITTLEROOT_TOWN -> LittlerootTown
const mapNameLookup = {};
function normalizeMapName(name) {
  return name.replace(/^MAP_/, '').replace(/_/g, '').toLowerCase();
}
for (let gi = 0; gi < groupOrder.length; gi++) {
  const group = groupOrder[gi];
  const maps = mapGroupEntries[group];
  for (let mi = 0; mi < maps.length; mi++) {
    mapToAddress[maps[mi]] = { groupIndex: gi, mapIndex: mi, mapName: maps[mi] };
    mapNameLookup[normalizeMapName(maps[mi])] = mapToAddress[maps[mi]];
  }
}
function resolveMap(name) {
  return mapToAddress[name] || mapNameLookup[normalizeMapName(name)];
}

// ---- Music ID lookup (from songs.h) ----
const musicIds = {};
(function buildMusicTable() {
  const songsH = fs.readFileSync(`${POKEEMERALD}/include/constants/songs.h`, 'utf-8');
  for (const line of songsH.split('\n')) {
    const m = line.match(/^#define\s+(MUS_\w+)\s+(\d+)/);
    if (m) musicIds[m[1]] = parseInt(m[2], 10);
  }
})();

// ---- MAPSEC index lookup (from region_map_sections.json) ----
const mapsecIds = {};
(function buildMapsecTable() {
  const sectionsJson = JSON.parse(fs.readFileSync(`${POKEEMERALD}/src/data/region_map/region_map_sections.json`, 'utf-8'));
  const sections = sectionsJson.map_sections || [];
  for (let i = 0; i < sections.length; i++) {
    mapsecIds[sections[i].id] = i;
  }
})();

// ---- Tileset palette index lookup (from headers.h order, starting at 100) ----
const tilesetPaletteIds = {};
(function buildTilesetPaletteTable() {
  const headersH = fs.readFileSync(`${POKEEMERALD}/src/data/tilesets/headers.h`, 'utf-8');
  let index = 0;
  for (const line of headersH.split('\n')) {
    const m = line.match(/^const struct Tileset\s+(gTileset_\w+)\s*=/);
    if (m) {
      tilesetPaletteIds[m[1]] = 100 + index;
      index++;
    }
  }
})();

function getPalette(primaryTileset, secondaryTileset) {
  const pal1 = tilesetPaletteIds[primaryTileset];
  const pal2 = tilesetPaletteIds[secondaryTileset];
  if (pal1 !== undefined && pal2 !== undefined) return [pal1, pal2];
  // Fallback for unknown tilesets
  return [80, 82];
}

// Map type -> default palette ranges (fallback only)
function guessPalette(primaryTileset, secondaryTileset, mapType) {
  return getPalette(primaryTileset, secondaryTileset);
}

// ---- Direction mapping ----
const dirMap = { down: 1, up: 2, left: 3, right: 4, dive: 5, emerge: 6 };

// Movement type name -> value lookup
const movementTypes = {
  'MOVEMENT_TYPE_NONE': 0,
  'MOVEMENT_TYPE_LOOK_AROUND': 1,
  'MOVEMENT_TYPE_WANDER_AROUND': 2,
  'MOVEMENT_TYPE_WANDER_UP_AND_DOWN': 3,
  'MOVEMENT_TYPE_WANDER_DOWN_AND_UP': 4,
  'MOVEMENT_TYPE_WANDER_LEFT_AND_RIGHT': 5,
  'MOVEMENT_TYPE_WANDER_RIGHT_AND_LEFT': 6,
  'MOVEMENT_TYPE_FACE_UP': 7,
  'MOVEMENT_TYPE_FACE_DOWN': 8,
  'MOVEMENT_TYPE_FACE_LEFT': 9,
  'MOVEMENT_TYPE_FACE_RIGHT': 10,
  'MOVEMENT_TYPE_PLAYER': 11,
  'MOVEMENT_TYPE_BERRY_TREE_GROWTH': 12,
  'MOVEMENT_TYPE_FACE_DOWN_AND_UP': 13,
  'MOVEMENT_TYPE_FACE_LEFT_AND_RIGHT': 14,
  'MOVEMENT_TYPE_FACE_UP_AND_LEFT': 15,
  'MOVEMENT_TYPE_FACE_UP_AND_RIGHT': 16,
  'MOVEMENT_TYPE_FACE_DOWN_AND_LEFT': 17,
  'MOVEMENT_TYPE_FACE_DOWN_AND_RIGHT': 18,
  'MOVEMENT_TYPE_FACE_DOWN_UP_AND_LEFT': 19,
  'MOVEMENT_TYPE_FACE_DOWN_UP_AND_RIGHT': 20,
  'MOVEMENT_TYPE_FACE_UP_LEFT_AND_RIGHT': 21,
  'MOVEMENT_TYPE_FACE_DOWN_LEFT_AND_RIGHT': 22,
  'MOVEMENT_TYPE_ROTATE_COUNTERCLOCKWISE': 23,
  'MOVEMENT_TYPE_ROTATE_CLOCKWISE': 24,
  'MOVEMENT_TYPE_WALK_UP_AND_DOWN': 25,
  'MOVEMENT_TYPE_WALK_DOWN_AND_UP': 26,
  'MOVEMENT_TYPE_WALK_LEFT_AND_RIGHT': 27,
  'MOVEMENT_TYPE_WALK_RIGHT_AND_LEFT': 28,
  'MOVEMENT_TYPE_WALK_IN_PLACE_DOWN': 64,
  'MOVEMENT_TYPE_WALK_IN_PLACE_UP': 65,
  'MOVEMENT_TYPE_WALK_IN_PLACE_LEFT': 66,
  'MOVEMENT_TYPE_WALK_IN_PLACE_RIGHT': 67,
  'MOVEMENT_TYPE_JOG_IN_PLACE_DOWN': 68,
  'MOVEMENT_TYPE_JOG_IN_PLACE_UP': 69,
  'MOVEMENT_TYPE_JOG_IN_PLACE_LEFT': 70,
  'MOVEMENT_TYPE_JOG_IN_PLACE_RIGHT': 71,
  'MOVEMENT_TYPE_RUN_IN_PLACE_DOWN': 72,
  'MOVEMENT_TYPE_RUN_IN_PLACE_UP': 73,
  'MOVEMENT_TYPE_RUN_IN_PLACE_LEFT': 74,
  'MOVEMENT_TYPE_RUN_IN_PLACE_RIGHT': 75,
  'MOVEMENT_TYPE_INVISIBLE': 76,
};

// Graphics ID name -> value lookup (from event_objects.h)
const gfxIds = {};
(function buildGfxTable() {
  const entries = [
    ['OBJ_EVENT_GFX_BRENDAN_NORMAL',0],['OBJ_EVENT_GFX_BRENDAN_MACH_BIKE',1],
    ['OBJ_EVENT_GFX_BRENDAN_SURFING',2],['OBJ_EVENT_GFX_BRENDAN_FIELD_MOVE',3],
    ['OBJ_EVENT_GFX_QUINTY_PLUMP',4],['OBJ_EVENT_GFX_NINJA_BOY',5],
    ['OBJ_EVENT_GFX_TWIN',6],['OBJ_EVENT_GFX_BOY_1',7],
    ['OBJ_EVENT_GFX_GIRL_1',8],['OBJ_EVENT_GFX_BOY_2',9],
    ['OBJ_EVENT_GFX_GIRL_2',10],['OBJ_EVENT_GFX_LITTLE_BOY',11],
    ['OBJ_EVENT_GFX_LITTLE_GIRL',12],['OBJ_EVENT_GFX_BOY_3',13],
    ['OBJ_EVENT_GFX_GIRL_3',14],['OBJ_EVENT_GFX_RICH_BOY',15],
    ['OBJ_EVENT_GFX_WOMAN_1',16],['OBJ_EVENT_GFX_FAT_MAN',17],
    ['OBJ_EVENT_GFX_POKEFAN_F',18],['OBJ_EVENT_GFX_MAN_1',19],
    ['OBJ_EVENT_GFX_WOMAN_2',20],['OBJ_EVENT_GFX_EXPERT_M',21],
    ['OBJ_EVENT_GFX_EXPERT_F',22],['OBJ_EVENT_GFX_MAN_2',23],
    ['OBJ_EVENT_GFX_WOMAN_3',24],['OBJ_EVENT_GFX_POKEFAN_M',25],
    ['OBJ_EVENT_GFX_WOMAN_4',26],['OBJ_EVENT_GFX_COOK',27],
    ['OBJ_EVENT_GFX_LINK_RECEPTIONIST',28],['OBJ_EVENT_GFX_OLD_MAN',29],
    ['OBJ_EVENT_GFX_OLD_WOMAN',30],['OBJ_EVENT_GFX_CAMPER',31],
    ['OBJ_EVENT_GFX_PICNICKER',32],['OBJ_EVENT_GFX_MAN_3',33],
    ['OBJ_EVENT_GFX_WOMAN_5',34],['OBJ_EVENT_GFX_YOUNGSTER',35],
    ['OBJ_EVENT_GFX_BUG_CATCHER',36],['OBJ_EVENT_GFX_PSYCHIC_M',37],
    ['OBJ_EVENT_GFX_SCHOOL_KID_M',38],['OBJ_EVENT_GFX_MANIAC',39],
    ['OBJ_EVENT_GFX_HEX_MANIAC',40],['OBJ_EVENT_GFX_RAYQUAZA_STILL',41],
    ['OBJ_EVENT_GFX_SWIMMER_M',42],['OBJ_EVENT_GFX_SWIMMER_F',43],
    ['OBJ_EVENT_GFX_BLACK_BELT',44],['OBJ_EVENT_GFX_BEAUTY',45],
    ['OBJ_EVENT_GFX_SCIENTIST_1',46],['OBJ_EVENT_GFX_LASS',47],
    ['OBJ_EVENT_GFX_GENTLEMAN',48],['OBJ_EVENT_GFX_SAILOR',49],
    ['OBJ_EVENT_GFX_FISHERMAN',50],['OBJ_EVENT_GFX_RUNNING_TRIATHLETE_M',51],
    ['OBJ_EVENT_GFX_RUNNING_TRIATHLETE_F',52],['OBJ_EVENT_GFX_TUBER_F',53],
    ['OBJ_EVENT_GFX_TUBER_M',54],['OBJ_EVENT_GFX_HIKER',55],
    ['OBJ_EVENT_GFX_CYCLING_TRIATHLETE_M',56],['OBJ_EVENT_GFX_CYCLING_TRIATHLETE_F',57],
    ['OBJ_EVENT_GFX_NURSE',58],['OBJ_EVENT_GFX_ITEM_BALL',59],
    ['OBJ_EVENT_GFX_BERRY_TREE',60],['OBJ_EVENT_GFX_BERRY_TREE_EARLY_STAGES',61],
    ['OBJ_EVENT_GFX_BERRY_TREE_LATE_STAGES',62],['OBJ_EVENT_GFX_BRENDAN_ACRO_BIKE',63],
    ['OBJ_EVENT_GFX_PROF_BIRCH',64],['OBJ_EVENT_GFX_MAN_4',65],
    ['OBJ_EVENT_GFX_MAN_5',66],['OBJ_EVENT_GFX_REPORTER_M',67],
    ['OBJ_EVENT_GFX_REPORTER_F',68],['OBJ_EVENT_GFX_BARD',69],
    ['OBJ_EVENT_GFX_ANABEL',70],['OBJ_EVENT_GFX_TUCKER',71],
    ['OBJ_EVENT_GFX_GRETA',72],['OBJ_EVENT_GFX_SPENSER',73],
    ['OBJ_EVENT_GFX_NOLAND',74],['OBJ_EVENT_GFX_LUCY',75],
    ['OBJ_EVENT_GFX_MART_EMPLOYEE',83],['OBJ_EVENT_GFX_SCIENTIST_2',115],
    ['OBJ_EVENT_GFX_MOM',215],['OBJ_EVENT_GFX_PROF_BIRCH',64],
    ['OBJ_EVENT_GFX_NORMAN',129],['OBJ_EVENT_GFX_VIGOROTH_CARRYING_BOX',95],
    ['OBJ_EVENT_GFX_VIGOROTH_FACING_AWAY',96],['OBJ_EVENT_GFX_RIVAL_BRENDAN_NORMAL',100],
    ['OBJ_EVENT_GFX_RIVAL_MAY_NORMAL',105],['OBJ_EVENT_GFX_TRUCK',94],
    ['OBJ_EVENT_GFX_VAR_0',240],
  ];
  for (const [k, v] of entries) gfxIds[k] = v;
})();

const facingDirection = { south: 0, north: 1, west: 2, east: 3 };
function movementToFaceDir(movementType) {
  // Extract initial facing direction from movement type.
  // GBA facing values: DOWN=0, UP=1, LEFT=2, RIGHT=3
  if (movementType >= 7 && movementType <= 10) {
    // FACE_UP=7→UP(1), FACE_DOWN=8→DOWN(0), FACE_LEFT=9→LEFT(2), FACE_RIGHT=10→RIGHT(3)
    if (movementType == 7) return 1;  // UP → north
    if (movementType == 8) return 0;  // DOWN → south
    return movementType - 7;          // 9→2 (west), 10→3 (east)
  }
  if (movementType >= 64 && movementType <= 67) return movementType - 64; // WALK_IN_PLACE_DOWN=64→0, etc.
  if (movementType >= 68 && movementType <= 71) return movementType - 68; // JOG_IN_PLACE_DOWN=68→0, etc.
  if (movementType >= 72 && movementType <= 75) return movementType - 72; // RUN_IN_PLACE_DOWN=72→0, etc.
  return 0; // default DOWN
}

// ---- Border tile reader ----
function readBorderTiles(borderFilepath) {
  const fullPath = `${POKEEMERALD}/${borderFilepath}`;
  if (!fs.existsSync(fullPath)) return null;
  const buf = fs.readFileSync(fullPath);
  const tiles = [];
  for (let i = 0; i < buf.length; i += 2) {
    if (i + 1 < buf.length) {
      const val = buf.readUInt16LE(i);
      tiles.push(val);
    }
  }
  return tiles;
}

// ---- Weather/lighting/mapType mapping ----
const weatherMap = {
  WEATHER_NONE: 'Weather.IN_HOUSE_WEATHER',
  WEATHER_SUNNY: 'Weather.REGULAR_WEATHER',
  WEATHER_RAIN: 'Weather.RAINY_WEATHER',
  WEATHER_SNOW: 'Weather.THREE_SNOW_FLAKES',
  WEATHER_FOG_HORIZONTAL: 'Weather.STEADY_MIST',
  WEATHER_FOG_DIAGONAL: 'Weather.STEADY_MIST',
  WEATHER_SHADE: 'Weather.CLOUDY',
  WEATHER_UNDERWATER_BUBBLES: 'Weather.UNDERWATER_MIST',
  WEATHER_VOLCANIC_ASH: 'Weather.DENSE_BRIGHT_MIST',
};
const lightingMap = {
  MAP_TYPE_INDOOR: 'Lighting.REGULAR',
  MAP_TYPE_TOWN: 'Lighting.REGULAR',
  MAP_TYPE_ROUTE: 'Lighting.REGULAR',
};
const mapTypeMap = {
  MAP_TYPE_INDOOR: 'MapType.INSIDE',
  MAP_TYPE_TOWN: 'MapType.UNKNOWN_0x01',
  MAP_TYPE_CITY: 'MapType.CITY',
  MAP_TYPE_ROUTE: 'MapType.ROUTE',
  MAP_TYPE_UNDERGROUND: 'MapType.UNDERGROUND',
  MAP_TYPE_UNDERWATER: 'MapType.UNDERWATER',
  MAP_TYPE_OCEAN_ROUTE: 'MapType.ROUTE',
  MAP_TYPE_SECRET_BASE: 'MapType.SECRET_BASE',
};
const encounterMap = {
  MAP_TYPE_INDOOR: 'EncounterType.RANDOM',
  MAP_TYPE_TOWN: 'EncounterType.RANDOM',
  MAP_TYPE_CITY: 'EncounterType.RANDOM',
  MAP_TYPE_ROUTE: 'EncounterType.RANDOM',
  MAP_TYPE_UNDERGROUND: 'EncounterType.RANDOM',
  MAP_TYPE_UNDERWATER: 'EncounterType.UNKNOWN_0x03',
  MAP_TYPE_OCEAN_ROUTE: 'EncounterType.RANDOM',
  MAP_TYPE_SECRET_BASE: 'EncounterType.UNKNOWN_0x03',
};

// ---- Generate ----
const allEntries = [];

// For each map in each group
for (let gi = 0; gi < groupOrder.length; gi++) {
  const group = groupOrder[gi];
  const maps = mapGroupEntries[group];
  if (!maps) continue;

  for (let mi = 0; mi < maps.length; mi++) {
    const mapName = maps[mi];
    const mapDir = `${POKEEMERALD}/data/maps/${mapName}`;
    const mapJsonPath = `${mapDir}/map.json`;
    if (!fs.existsSync(mapJsonPath)) continue;

    const mapJson = JSON.parse(fs.readFileSync(mapJsonPath, 'utf-8'));
    const layoutId = mapJson.layout;
    const layout = layoutByName[layoutId];
    if (!layout) {
      console.warn(`  Layout ${layoutId} not found for map ${mapName}`);
      continue;
    }

    const width = layout.width;
    const height = layout.height;
    const primaryTileset = layout.primary_tileset;
    const secondaryTileset = layout.secondary_tileset;
    const mapType = mapJson.map_type || 'MAP_TYPE_INDOOR';
    const mapTypeStr = mapTypeMap[mapType] || 'MapType.INSIDE';
    const weatherStr = weatherMap[mapJson.weather] || 'Weather.REGULAR_WEATHER';
    const encounterStr = encounterMap[mapType] || 'EncounterType.RANDOM';

    // Palette
    const [pal1, pal2] = guessPalette(primaryTileset, secondaryTileset, mapType);

    // Music → unknownShort, MAPSEC → unknownByte
    const musicStr = mapJson.music || 'MUS_NONE';
    const mapsecStr = mapJson.region_map_section || 'MAPSEC_NONE';
    const mapUnknownShort = musicIds[musicStr] !== undefined ? musicIds[musicStr] : 405;
    const mapUnknownByte = mapsecIds[mapsecStr] !== undefined ? mapsecIds[mapsecStr] : 0;

    // Border tiles
    let borderTilesStr = 'listOf(Tile2D(8, 0), Tile2D(8, 0), Tile2D(8, 0), Tile2D(8, 0))';
    if (layout.border_filepath) {
      const borderVals = readBorderTiles(layout.border_filepath);
      if (borderVals && borderVals.length >= 4) {
        borderTilesStr = `listOf(Tile2D(0x${borderVals[0].toString(16).padStart(4, '0').toUpperCase()}, 0), Tile2D(0x${borderVals[1].toString(16).padStart(4, '0').toUpperCase()}, 0), Tile2D(0x${borderVals[2].toString(16).padStart(4, '0').toUpperCase()}, 0), Tile2D(0x${borderVals[3].toString(16).padStart(4, '0').toUpperCase()}, 0))`;
      }
    }

    // Connections
    const connections = mapJson.connections || [];
    const connStrs = connections.map(c => {
      const dir = dirMap[c.direction];
      if (dir === undefined) {
        throw new Error(`Unknown connection direction "${c.direction}" in ${mapName}`);
      }
      const addr = resolveMap(c.map);
      if (!addr) return null;
      const bank = addr.groupIndex + 50;
      const mapNum = addr.mapIndex;
      const offset = Number.isInteger(c.offset) ? c.offset : parseInt(c.offset || 0, 10) || 0;
      return `GbaConnection(direction = ${dir}, unknown = ${offset}, targetBank = ${bank}, targetMap = ${mapNum})`;
    }).filter(Boolean);

    // Warps
    const warps = mapJson.warp_events || [];
    const warpStrs = warps.map(w => {
      const destAddr = resolveMap(w.dest_map);
      if (!destAddr) return null;
      const destBank = destAddr.groupIndex + 50;
      const destMapNum = destAddr.mapIndex;
      // Find dest warp position in destination map
      const destMapDir = `${POKEEMERALD}/data/maps/${destAddr.mapName}`;
      let destX = w.x, destY = w.y; // fallback
      try {
        const destMapJson = JSON.parse(fs.readFileSync(`${destMapDir}/map.json`, 'utf-8'));
        const destWarps = destMapJson.warp_events || [];
        const targetWarpIdx = parseInt(w.dest_warp_id);
        if (targetWarpIdx >= 0 && targetWarpIdx < destWarps.length) {
          const targetWarp = destWarps[targetWarpIdx];
          destX = targetWarp.x;
          destY = targetWarp.y;
        }
      } catch (e) {
        // use fallback
      }
      return `WarpTile(x = ${w.x}, y = ${w.y}, targetRegionId = 1, targetBankId = ${destBank}, targetMapId = ${destMapNum}, targetX = ${destX}, targetY = ${destY})`;
    }).filter(Boolean);

    // NPCs / object events
    const npcs = mapJson.object_events || [];

    // Per-map override: NPC entity indices that should be visible despite FLAG_HIDE_* being SET at game start
    const defaultVisibleNpcs = {
      "LittlerootTown_MaysHouse_1F": [0],
      "LittlerootTown_BrendansHouse_1F": [3, 5],
      "LittlerootTown_ProfessorBirchsLab": [0],
    };
    const visibleIndices = defaultVisibleNpcs[mapName] || null;

    const npcStrs = [];
    npcs.forEach((npc, idx) => {
      const isVisible = visibleIndices !== null
        ? visibleIndices.includes(idx)
        : npc.flag === "0";
      if (!isVisible) return;
      const gfxName = npc.graphics_id || 'OBJ_EVENT_GFX_BOY_1';
      const gfxId = gfxIds[gfxName] || 0;
      const moveName = npc.movement_type || 'MOVEMENT_TYPE_NONE';
      const moveVal = movementTypes[moveName] || 0;
      const rangeX = parseInt(npc.movement_range_x) || 0;
      const rangeY = parseInt(npc.movement_range_y) || 0;
      const faceDir = movementToFaceDir(moveVal);
      const trainType = npc.trainer_type === 'TRAINER_TYPE_NONE' ? 0 : 1;
      const script = npc.script || "0x0";
      npcStrs.push(`NpcDef(entityIdx = ${idx}, graphicsId = ${gfxId}, x = ${npc.x}, y = ${npc.y}, elevation = ${npc.elevation || 3}, movementType = ${moveVal}, movementRangeX = ${rangeX}, movementRangeY = ${rangeY}, trainerType = ${trainType}, facing = ${faceDir}, script = "${script}")`);
    });
    const npcsStr = npcStrs.length > 0
      ? `listOf(\n${npcStrs.map(s => '                    ' + s).join(',\n')},\n                )`
      : 'emptyList()';

    // bg_events (signs, PCs, TVs, bookshelves, etc.)
    const rawBgEvents = mapJson.bg_events || [];
    const bgEventStrs = rawBgEvents.map(be =>
        `BgEventDef(x = ${be.x}, y = ${be.y}, facingDir = "${be.player_facing_dir || "BG_EVENT_PLAYER_FACING_ANY"}", script = "${be.script}")`
    );
    const bgEventsStr = bgEventStrs.length > 0
      ? `listOf(\n${bgEventStrs.map(s => '                    ' + s).join(',\n')},\n                )`
      : 'emptyList()';

    const warpsStr = warpStrs.length > 0
      ? `listOf(\n${warpStrs.map(s => '                    ' + s).join(',\n')},\n                )`
      : 'emptyList()';

    const connsStr = connStrs.length > 0
      ? `listOf(\n${connStrs.map(s => '                    ' + s).join(',\n')},\n                )`
      : 'emptyList()';

    // Region: always 1 for now
    const region = 1;
    const bank = gi + 50;

    // Generate MapDef
    const comment = `// ${mapName} (${mapJson.id})`;
    const def = `
        MapDef(
            regionId = ${region}, bankId = ${bank}, mapId = ${mi},
            width = ${width}, height = ${height},
            paletteIdx1 = ${pal1}, paletteIdx2 = ${pal2},
            unknownShort = ${mapUnknownShort}, unknownByte = ${mapUnknownByte},
            borderTiles = ${borderTilesStr},
            lighting = Lighting.REGULAR,
            weather = ${weatherStr},
            mapType = ${mapTypeStr},
            encounterType = ${encounterStr},
            connections = ${connsStr},
            warps = ${warpsStr},
            npcs = ${npcsStr},
            bgEvents = ${bgEventsStr},
        )`;

    allEntries.push({ mapName, group, bank, mi, def, comment });
  }
}

// ---- Generate Kotlin file ----
let kt = `package de.fiereu.openmmo.server.game.world

import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.protocols.game.packets.LoadMapPacket
import de.fiereu.openmmo.protocols.game.packets.MapData
import de.fiereu.openmmo.protocols.game.packets.MapData.GbaConnection

class MapDef(
    val regionId: Byte,
    val bankId: Byte,
    val mapId: Byte,
    val width: Int = 20,
    val height: Int = 15,
    val paletteIdx1: Int = 12,
    val paletteIdx2: Int = 14,
    val borderWidth: Int = 2,
    val borderHeight: Int = 2,
    val unknownShort: Int = 0,
    val unknownByte: Int = 0,
    val borderTiles: List<Tile2D> = listOf(Tile2D(8, 0), Tile2D(8, 0), Tile2D(8, 0), Tile2D(8, 0)),
    val lighting: Lighting = Lighting.REGULAR,
    val weather: Weather = Weather.REGULAR_WEATHER,
    val mapType: MapType = MapType.CITY,
    val encounterType: EncounterType = EncounterType.RANDOM,
    val wildEncounters: List<WildEncounterTable> = emptyList(),
    val connections: List<GbaConnection> = emptyList(),
    val warps: List<WarpTile> = emptyList(),
    val npcs: List<NpcDef> = emptyList(),
    val bgEvents: List<BgEventDef> = emptyList(),
)

data class WildEncounterTable(
    val encounterRate: Int,
    val pokemon: List<WildPokemon>,
)

data class WildPokemon(
    val dexId: Int,
    val minLevel: Int,
    val maxLevel: Int,
    val weight: Int,
)

data class WarpTile(
    val x: Int,
    val y: Int,
    val targetRegionId: Byte,
    val targetBankId: Byte,
    val targetMapId: Byte,
    val targetX: Int,
    val targetY: Int,
    val facingDirection: de.fiereu.openmmo.common.enums.Direction? = null,
)

data class NpcDef(
    val entityIdx: Int,
    val graphicsId: Int,
    val x: Int,
    val y: Int,
    val elevation: Int,
    val movementType: Int,
    val movementRangeX: Int,
    val movementRangeY: Int,
    val trainerType: Int,
    val facing: Int,
    val script: String = "0x0",
)

data class BgEventDef(
    val x: Int,
    val y: Int,
    val facingDir: String,
    val script: String,
)

object MapManager {
  private val maps = mutableMapOf<String, MapDef>()

  init {
${Array.from({ length: Math.ceil(allEntries.length / 60) }, (_, i) => `    registerDefaultMaps${i}()`).join('\n')}
  }

${Array.from({ length: Math.ceil(allEntries.length / 60) }, (_, i) => {
  const chunk = allEntries.slice(i * 60, (i + 1) * 60);
  return `  private fun registerDefaultMaps${i}() {
    val allMaps = listOf(
${chunk.map(e => `      ${e.comment}${e.def},`).join('\n')}
    )
    allMaps.forEach { registerMap(it) }
  }`;
}).join('\n\n')}
`;

kt += `
  fun registerMap(map: MapDef) {
    maps[key(map.regionId, map.bankId, map.mapId)] = map
  }

  fun getMap(regionId: Byte, bankId: Byte, mapId: Byte): MapDef? {
    return maps[key(regionId, bankId, mapId)]
  }

  fun createLoadMapPacket(
      map: MapDef,
      reloadPlayer: Boolean = false,
      deleteCache: Boolean = false,
  ): LoadMapPacket {
    return LoadMapPacket(
        reloadPlayer = reloadPlayer,
        deleteCache = deleteCache,
        regionId = map.regionId.toInt(),
        bankId = map.bankId.toInt(),
        mapId = map.mapId.toInt(),
        mapData =
            MapData.GbaMapData(
                width = map.width,
                height = map.height,
                paletteIdx1 = map.paletteIdx1,
                paletteIdx2 = map.paletteIdx2,
                borderWidth = map.borderWidth,
                borderHeight = map.borderHeight,
                unknownShort = map.unknownShort,
                unknownByte = map.unknownByte,
                borderTiles = map.borderTiles,
                lighting = map.lighting,
                weather = map.weather,
                mapType = map.mapType,
                encounterType = map.encounterType,
                connections = map.connections,
            ),
    )
  }

  data class EncounterResult(val dexId: Int, val level: Int)

  fun getEncounter(map: MapDef): EncounterResult? {
    val tables = map.wildEncounters
    if (tables.isEmpty()) return null
    val totalWeight = tables.sumOf { it.encounterRate }
    if (totalWeight <= 0) return null
    var roll = (0 until totalWeight).random()
    for (table in tables) {
      roll -= table.encounterRate
      if (roll < 0) {
        val pokemon = table.pokemon
        val totalPokeWeight = pokemon.sumOf { it.weight }
        if (totalPokeWeight <= 0) return null
        var pokeRoll = (0 until totalPokeWeight).random()
        for (poke in pokemon) {
          pokeRoll -= poke.weight
          if (pokeRoll < 0) {
            val level = (poke.minLevel..poke.maxLevel).random()
            return EncounterResult(poke.dexId, level)
          }
        }
      }
    }
    return null
  }

  private fun key(regionId: Byte, bankId: Byte, mapId: Byte) = "$regionId:$bankId:$mapId"
}
`;

fs.writeFileSync(OUTPUT, kt, 'utf-8');
console.log(`Generated ${allEntries.length} map entries -> ${OUTPUT}`);

// Stats
const groups = {};
for (const e of allEntries) {
  groups[e.group] = (groups[e.group] || 0) + 1;
}
console.log('\nMaps per group:');
for (const [g, c] of Object.entries(groups)) {
  console.log(`  ${g}: ${c}`);
}
