package de.fiereu.openmmo.maps.generator

import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class PokeemeraldParser(private val rootDir: File) {

  private val json = Json { ignoreUnknownKeys = true }

  fun parseAll(): List<ParsedMap> {
    val mapGroups = readJson(File(rootDir, "data/maps/map_groups.json")).jsonObject
    val groupOrder =
        (mapGroups["group_order"] ?: error("group_order missing")).jsonArray.map {
          it.jsonPrimitive.content
        }
    val groupEntries: Map<String, List<String>> =
        groupOrder.associateWith { group ->
          (mapGroups[group]?.jsonArray ?: error("group $group missing")).map {
            it.jsonPrimitive.content
          }
        }

    val layouts: Map<String, JsonObject> =
        (readJson(File(rootDir, "data/layouts/layouts.json")).jsonObject["layouts"]
                ?: error("layouts missing"))
            .jsonArray
            .mapNotNull { it as? JsonObject }
            .filter { it["id"]?.jsonPrimitive?.contentOrNull != null }
            .associateBy { it["id"]?.jsonPrimitive?.content ?: error("layout id") }

    val musicIds = readDefineTable(File(rootDir, "include/constants/songs.h"), "MUS_")
    val mapsecIds = readMapsecIds()
    val tilesetPaletteIds = readTilesetPaletteIds()

    // Pre-parse every map.json once so warp lookups don't re-read files.
    data class Address(val groupIndex: Int, val mapIndex: Int, val mapName: String)
    val addresses = mutableMapOf<String, Address>()
    val mapJsons = mutableMapOf<String, JsonObject>()
    for ((groupIndex, groupName) in groupOrder.withIndex()) {
      val maps = groupEntries[groupName] ?: continue
      for ((mapIndex, mapDirName) in maps.withIndex()) {
        val mapJsonFile = File(rootDir, "data/maps/$mapDirName/map.json")
        if (!mapJsonFile.exists()) continue
        val parsed = readJson(mapJsonFile).jsonObject
        mapJsons[mapDirName] = parsed
        addresses[mapDirName] = Address(groupIndex, mapIndex, mapDirName)
        val mapId = parsed["id"]?.jsonPrimitive?.contentOrNull
        if (mapId != null) addresses[mapId] = Address(groupIndex, mapIndex, mapDirName)
      }
    }

    val results = mutableListOf<ParsedMap>()
    for ((groupIndex, groupName) in groupOrder.withIndex()) {
      val maps = groupEntries[groupName] ?: continue
      for ((mapIndex, mapDirName) in maps.withIndex()) {
        val mapJson = mapJsons[mapDirName] ?: continue
        val layoutId = mapJson["layout"]?.jsonPrimitive?.contentOrNull ?: continue
        val layout = layouts[layoutId] ?: continue

        val mapType = mapJson["map_type"]?.jsonPrimitive?.contentOrNull ?: "MAP_TYPE_INDOOR"
        val primaryTileset = layout["primary_tileset"]?.jsonPrimitive?.contentOrNull
        val secondaryTileset = layout["secondary_tileset"]?.jsonPrimitive?.contentOrNull
        val pal1 = tilesetPaletteIds[primaryTileset] ?: 80
        val pal2 = tilesetPaletteIds[secondaryTileset] ?: 82
        val musicName = mapJson["music"]?.jsonPrimitive?.contentOrNull ?: "MUS_NONE"
        val mapsecName =
            mapJson["region_map_section"]?.jsonPrimitive?.contentOrNull ?: "MAPSEC_NONE"

        val connections =
            mapJson["connections"]?.jsonArrayOrNull()?.mapNotNull { conn ->
              val obj = conn.jsonObject
              val dirName = obj["direction"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
              val dir = dirMap[dirName] ?: error("Unknown connection direction '$dirName'")
              val mapName = obj["map"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
              val addr = addresses[mapName] ?: return@mapNotNull null
              val offset = obj["offset"]?.jsonPrimitive?.intOrNull ?: 0
              ParsedConnection(
                  direction = dir,
                  offset = offset,
                  targetBank = addr.groupIndex + BANK_GROUP_OFFSET,
                  targetMap = addr.mapIndex,
              )
            } ?: emptyList()

        val warps =
            mapJson["warp_events"]?.jsonArrayOrNull()?.mapNotNull { warp ->
              val obj = warp.jsonObject
              val destName = obj["dest_map"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
              val destAddr = addresses[destName] ?: return@mapNotNull null
              val x = obj["x"]?.jsonPrimitive?.intOrNull ?: 0
              val y = obj["y"]?.jsonPrimitive?.intOrNull ?: 0
              val srcElevation = obj["elevation"]?.jsonPrimitive?.intOrNull ?: 0
              val destWarpId =
                  obj["dest_warp_id"]?.jsonPrimitive?.let {
                    it.intOrNull ?: it.content.toIntOrNull()
                  } ?: 0
              val destWarps =
                  mapJsons[destAddr.mapName]?.get("warp_events")?.jsonArrayOrNull().orEmpty()
              val target =
                  destWarps.getOrNull(destWarpId)?.jsonObject
                      ?: return@mapNotNull ParsedWarp(
                          x = x,
                          y = y,
                          elevation = (srcElevation - 1).coerceAtLeast(0),
                          targetBank = destAddr.groupIndex + BANK_GROUP_OFFSET,
                          targetMap = destAddr.mapIndex,
                          targetX = x,
                          targetY = y,
                          targetElevation = 0,
                      )
              val tx = target["x"]?.jsonPrimitive?.intOrNull ?: x
              val ty = target["y"]?.jsonPrimitive?.intOrNull ?: y
              val targetElevation =
                  ((target["elevation"]?.jsonPrimitive?.intOrNull ?: 0) - 1).coerceAtLeast(0)
              ParsedWarp(
                  x = x,
                  y = y,
                  elevation = (srcElevation - 1).coerceAtLeast(0),
                  targetBank = destAddr.groupIndex + BANK_GROUP_OFFSET,
                  targetMap = destAddr.mapIndex,
                  targetX = tx,
                  targetY = ty,
                  targetElevation = targetElevation,
              )
            } ?: emptyList()

        val rawNpcs = mapJson["object_events"]?.jsonArrayOrNull().orEmpty()
        val visibleOverride = defaultVisibleNpcs[mapDirName]
        val visibleNpcs =
            rawNpcs.mapIndexedNotNull { idx, npcElement ->
              val npc = npcElement.jsonObject
              val isVisible =
                  if (visibleOverride != null) idx in visibleOverride
                  else npc["flag"]?.jsonPrimitive?.contentOrNull == "0"
              if (!isVisible) return@mapIndexedNotNull null
              val gfxName =
                  npc["graphics_id"]?.jsonPrimitive?.contentOrNull ?: "OBJ_EVENT_GFX_BOY_1"
              val movementName =
                  npc["movement_type"]?.jsonPrimitive?.contentOrNull ?: "MOVEMENT_TYPE_NONE"
              val movement = movementTypes[movementName] ?: 0
              val trainerType =
                  if (npc["trainer_type"]?.jsonPrimitive?.contentOrNull == "TRAINER_TYPE_NONE") 0
                  else 1
              ParsedNpc(
                  entityIdx = idx,
                  graphicsId = gfxIds[gfxName] ?: 0,
                  x = npc["x"]?.jsonPrimitive?.intOrNull ?: 0,
                  y = npc["y"]?.jsonPrimitive?.intOrNull ?: 0,
                  elevation = npc["elevation"]?.jsonPrimitive?.intOrNull ?: 3,
                  movementType = movement,
                  movementRangeX = npc["movement_range_x"]?.jsonPrimitive?.intOrNull ?: 0,
                  movementRangeY = npc["movement_range_y"]?.jsonPrimitive?.intOrNull ?: 0,
                  trainerType = trainerType,
                  facing = movementToFacingDir(movement),
                  script = npc["script"]?.jsonPrimitive?.contentOrNull ?: "0x0",
              )
            }

        val bgEvents =
            mapJson["bg_events"]?.jsonArrayOrNull()?.map { be ->
              val obj = be.jsonObject
              ParsedBgEvent(
                  x = obj["x"]?.jsonPrimitive?.intOrNull ?: 0,
                  y = obj["y"]?.jsonPrimitive?.intOrNull ?: 0,
                  facingDir =
                      obj["player_facing_dir"]?.jsonPrimitive?.contentOrNull
                          ?: "BG_EVENT_PLAYER_FACING_ANY",
                  script = obj["script"]?.jsonPrimitive?.contentOrNull ?: "0x0",
              )
            } ?: emptyList()

        val borderTiles =
            layout["border_filepath"]?.jsonPrimitive?.contentOrNull?.let {
              readBorderTiles(File(rootDir, it))
            } ?: emptyList()

        results +=
            ParsedMap(
                sourceName = mapDirName,
                mapId = mapJson["id"]?.jsonPrimitive?.contentOrNull ?: mapDirName,
                region = 1,
                bank = groupIndex + BANK_GROUP_OFFSET,
                index = mapIndex,
                width = layout["width"]?.jsonPrimitive?.intOrNull ?: 20,
                height = layout["height"]?.jsonPrimitive?.intOrNull ?: 15,
                paletteIdx1 = pal1,
                paletteIdx2 = pal2,
                musicId = musicIds[musicName] ?: 405,
                mapsecId = mapsecIds[mapsecName] ?: 0,
                borderTiles = borderTiles,
                lighting = "Lighting.REGULAR",
                weather =
                    weatherMap[mapJson["weather"]?.jsonPrimitive?.contentOrNull]
                        ?: "Weather.REGULAR_WEATHER",
                mapType = mapTypeMap[mapType] ?: "MapType.INSIDE",
                encounterType = encounterMap[mapType] ?: "EncounterType.RANDOM",
                connections = connections,
                warps = warps,
                visibleNpcs = visibleNpcs,
                bgEvents = bgEvents,
            )
      }
    }
    return results
  }

  private fun readJson(file: File): JsonElement = json.parseToJsonElement(file.readText())

  private fun readDefineTable(file: File, prefix: String): Map<String, Int> {
    if (!file.exists()) return emptyMap()
    val pattern = Regex("""^#define\s+($prefix\w+)\s+(\d+)""")
    return file
        .readLines()
        .mapNotNull { pattern.find(it.trim()) }
        .associate { it.groupValues[1] to it.groupValues[2].toInt() }
  }

  private fun readMapsecIds(): Map<String, Int> {
    val file = File(rootDir, "src/data/region_map/region_map_sections.json")
    if (!file.exists()) return emptyMap()
    val sections = readJson(file).jsonObject["map_sections"]?.jsonArray ?: return emptyMap()
    return sections
        .mapIndexedNotNull { i, el ->
          el.jsonObject["id"]?.jsonPrimitive?.contentOrNull?.let { it to i }
        }
        .toMap()
  }

  private fun readTilesetPaletteIds(): Map<String, Int> {
    val file = File(rootDir, "src/data/tilesets/headers.h")
    if (!file.exists()) return emptyMap()
    val pattern = Regex("""^const struct Tileset\s+(gTileset_\w+)\s*=""")
    var index = 0
    val out = mutableMapOf<String, Int>()
    file.forEachLine { line ->
      val m = pattern.find(line.trim()) ?: return@forEachLine
      out[m.groupValues[1]] = 100 + index
      index++
    }
    return out
  }

  private fun readBorderTiles(file: File): List<Int> {
    if (!file.exists()) return emptyList()
    val bytes = file.readBytes()
    val buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
    val out = mutableListOf<Int>()
    while (buf.remaining() >= 2) out += buf.short.toInt() and 0xFFFF
    return out
  }

  private fun JsonElement.jsonArrayOrNull() = (this as? kotlinx.serialization.json.JsonArray)
}
