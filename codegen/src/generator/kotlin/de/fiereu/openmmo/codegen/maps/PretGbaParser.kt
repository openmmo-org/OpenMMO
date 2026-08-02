package de.fiereu.openmmo.codegen.maps

import de.fiereu.openmmo.codegen.pokemon.NationalDex
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Base64
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class PretGbaParser(
    private val rootDir: File,
    private val region: RegionConstants,
    private val movementTypes: MovementTypes,
    private val metatileBehaviors: MetatileBehaviors,
) {

  private val json = Json { ignoreUnknownKeys = true }

  private data class Address(val groupIndex: Int, val mapIndex: Int, val mapName: String)

  private class MapGroups(val order: List<String>, val entries: Map<String, List<String>>)

  // The wild tables keyed by map constant, plus the shared per-slot weights for each method.
  private class EncounterData(
      val weightsByType: Map<String, List<Int>>,
      val byMap: Map<String, JsonObject>,
  )

  private class Context(
      val layouts: Map<String, JsonObject>,
      val musicIds: Map<String, Int>,
      val gfxIds: Map<String, Int>,
      val mapsecIds: Map<String, Int>,
      val tilesetPaletteIds: Map<String, Int>,
      val addresses: Map<String, Address>,
      val mapJsons: Map<String, JsonObject>,
      val speciesIds: Map<String, Int>,
      val nationalDex: Map<Int, Int>,
      val encounters: EncounterData,
  )

  fun parseAll(): List<ParsedMap> {
    val groups = readMapGroups()
    val context = buildContext(groups)

    return buildList {
      for ((groupIndex, groupName) in groups.order.withIndex()) {
        val maps = groups.entries[groupName] ?: continue
        for ((mapIndex, mapDirName) in maps.withIndex()) {
          parseMap(groupIndex, groupName, mapIndex, mapDirName, context)?.let { add(it) }
        }
      }
    }
  }

  private fun readMapGroups(): MapGroups {
    val file = File(rootDir, "data/maps/map_groups.json")
    require(file.exists()) {
      "Decomp not initialized at $rootDir (missing ${file.path}). " +
          "Run: git submodule update --init --recursive"
    }
    val mapGroups = readJson(file).jsonObject
    val order =
        (mapGroups["group_order"] ?: error("group_order missing")).jsonArray.map {
          it.jsonPrimitive.content
        }
    val entries =
        order.associateWith { group ->
          (mapGroups[group]?.jsonArray ?: error("group $group missing")).map {
            it.jsonPrimitive.content
          }
        }
    return MapGroups(order, entries)
  }

  private fun readLayouts(): Map<String, JsonObject> =
      (readJson(File(rootDir, "data/layouts/layouts.json")).jsonObject["layouts"]
              ?: error("layouts missing"))
          .jsonArray
          .mapNotNull { it as? JsonObject }
          .filter { it["id"]?.jsonPrimitive?.contentOrNull != null }
          .associateBy { it["id"]?.jsonPrimitive?.content ?: error("layout id") }

  private fun buildContext(groups: MapGroups): Context {
    // Pre-parse every map.json once so warp lookups don't re-read files.
    val addresses = mutableMapOf<String, Address>()
    val mapJsons = mutableMapOf<String, JsonObject>()
    for ((groupIndex, groupName) in groups.order.withIndex()) {
      val maps = groups.entries[groupName] ?: continue
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

    return Context(
        layouts = readLayouts(),
        musicIds = readDefineTable(File(rootDir, "include/constants/songs.h"), "MUS_"),
        gfxIds = readGfxIds(File(rootDir, "include/constants/event_objects.h")),
        mapsecIds = readMapsecIds(),
        tilesetPaletteIds = readTilesetPaletteIds(),
        addresses = addresses,
        mapJsons = mapJsons,
        speciesIds = readDefineTable(File(rootDir, "include/constants/species.h"), "SPECIES_"),
        nationalDex = NationalDex.read(rootDir),
        encounters = readEncounters(),
    )
  }

  private fun readEncounters(): EncounterData {
    val file = File(rootDir, "src/data/wild_encounters.json")
    if (!file.exists()) return EncounterData(emptyMap(), emptyMap())
    val group =
        readJson(file)
            .jsonObject["wild_encounter_groups"]
            ?.jsonArrayOrNull()
            ?.firstOrNull()
            ?.jsonObject ?: return EncounterData(emptyMap(), emptyMap())
    val weights =
        group["fields"]?.jsonArrayOrNull().orEmpty().mapNotNull { field ->
          val obj = field.jsonObject
          val type = obj["type"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
          val rates =
              obj["encounter_rates"]?.jsonArrayOrNull()?.mapNotNull { it.jsonPrimitive.intOrNull }
                  ?: return@mapNotNull null
          type to rates
        }
    val byMap =
        group["encounters"]?.jsonArrayOrNull().orEmpty().mapNotNull { entry ->
          val obj = entry.jsonObject
          val mapId = obj["map"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
          mapId to obj
        }
    return EncounterData(weights.toMap(), byMap.toMap())
  }

  private fun parseMap(
      groupIndex: Int,
      groupName: String,
      mapIndex: Int,
      mapDirName: String,
      ctx: Context,
  ): ParsedMap? {
    val mapJson = ctx.mapJsons[mapDirName] ?: return null
    val layoutId = mapJson["layout"]?.jsonPrimitive?.contentOrNull ?: return null
    val layout = ctx.layouts[layoutId] ?: return null

    val mapType = mapJson["map_type"]?.jsonPrimitive?.contentOrNull ?: "MAP_TYPE_INDOOR"
    val musicName = mapJson["music"]?.jsonPrimitive?.contentOrNull ?: "MUS_NONE"
    val mapsecName = mapJson["region_map_section"]?.jsonPrimitive?.contentOrNull ?: "MAPSEC_NONE"

    return ParsedMap(
        regionName = region.name,
        sourceName = mapDirName,
        groupName = groupName,
        mapId = mapJson["id"]?.jsonPrimitive?.contentOrNull ?: mapDirName,
        region = region.regionId,
        bank = wireBank(groupIndex),
        index = mapIndex,
        width = layout["width"]?.jsonPrimitive?.intOrNull ?: 20,
        height = layout["height"]?.jsonPrimitive?.intOrNull ?: 15,
        paletteIdx1 =
            ctx.tilesetPaletteIds[layout["primary_tileset"]?.jsonPrimitive?.contentOrNull] ?: 80,
        paletteIdx2 =
            ctx.tilesetPaletteIds[layout["secondary_tileset"]?.jsonPrimitive?.contentOrNull] ?: 82,
        musicId = ctx.musicIds[musicName] ?: 405,
        mapsecId = ctx.mapsecIds[mapsecName] ?: 0,
        borderTiles = parseBorderTiles(layout),
        blockData = parseBlockData(layout),
        behaviorData = parseBehaviorData(layout),
        encounters = parseEncounters(mapJson, ctx),
        lighting = "Lighting.REGULAR",
        weather =
            region.weatherMap[mapJson["weather"]?.jsonPrimitive?.contentOrNull]
                ?: "Weather.REGULAR_WEATHER",
        mapType = region.mapTypeMap[mapType] ?: "MapType.INSIDE",
        encounterType = region.encounterMap[mapType] ?: "EncounterType.RANDOM",
        connections = parseConnections(mapJson, ctx),
        warps = parseWarps(mapJson, ctx),
        visibleNpcs = parseNpcs(mapJson, mapDirName, ctx),
        bgEvents = parseBgEvents(mapJson),
        onTransitionScript = parseOnTransitionScript(mapDirName),
        onFrameScripts = parseOnFrameScripts(mapDirName),
        coordScripts = parseCoordScripts(mapJson),
    )
  }

  // The map's ON_TRANSITION script runs whenever the player enters the map. It sits in the map's
  // scripts.inc _MapScripts table as a direct label, unlike the ON_FRAME/ON_WARP conditional
  // tables.
  private fun parseOnTransitionScript(mapDirName: String): String {
    val file = File(rootDir, "data/maps/$mapDirName/scripts.inc")
    if (!file.exists()) return ""
    val re = Regex("""map_script\s+MAP_SCRIPT_ON_TRANSITION\s*,\s*(\w+)""")
    return re.find(file.readText())?.groupValues?.get(1) ?: ""
  }

  private fun parseConnections(mapJson: JsonObject, ctx: Context): List<ParsedConnection> =
      mapJson["connections"]?.jsonArrayOrNull()?.mapNotNull { conn ->
        val obj = conn.jsonObject
        val dirName = obj["direction"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
        val dir = region.dirMap[dirName] ?: error("Unknown connection direction '$dirName'")
        val mapName = obj["map"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
        val addr = ctx.addresses[mapName] ?: return@mapNotNull null
        ParsedConnection(
            direction = dir,
            offset = obj["offset"]?.jsonPrimitive?.intOrNull ?: 0,
            targetBank = wireBank(addr.groupIndex),
            targetMap = addr.mapIndex,
        )
      } ?: emptyList()

  private fun parseWarps(mapJson: JsonObject, ctx: Context): List<ParsedWarp> =
      mapJson["warp_events"]?.jsonArrayOrNull()?.mapNotNull { warp ->
        val obj = warp.jsonObject
        val destName = obj["dest_map"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
        val x = obj["x"]?.jsonPrimitive?.intOrNull ?: 0
        val y = obj["y"]?.jsonPrimitive?.intOrNull ?: 0
        // A MAP_DYNAMIC warp has no static destination, the runtime resolves it from the player's
        // dynamic warp (setdynamicwarp). Emit the tile so it still triggers, with dynamic = true.
        if (destName == "MAP_DYNAMIC") {
          return@mapNotNull ParsedWarp(
              x = x,
              y = y,
              elevation = ((obj["elevation"]?.jsonPrimitive?.intOrNull ?: 0) - 1).coerceAtLeast(0),
              targetRegion = 0,
              targetBank = 0,
              targetMap = 0,
              targetX = 0,
              targetY = 0,
              targetElevation = 0,
              dynamic = true,
          )
        }
        val destAddr = ctx.addresses[destName] ?: return@mapNotNull null
        val srcElevation = (obj["elevation"]?.jsonPrimitive?.intOrNull ?: 0)
        val destWarpId =
            obj["dest_warp_id"]?.jsonPrimitive?.let { it.intOrNull ?: it.content.toIntOrNull() }
                ?: 0
        val destWarps =
            ctx.mapJsons[destAddr.mapName]?.get("warp_events")?.jsonArrayOrNull().orEmpty()
        val target = destWarps.getOrNull(destWarpId)?.jsonObject
        ParsedWarp(
            x = x,
            y = y,
            elevation = (srcElevation - 1).coerceAtLeast(0),
            targetRegion = region.regionId,
            targetBank = wireBank(destAddr.groupIndex),
            targetMap = destAddr.mapIndex,
            targetX = target?.get("x")?.jsonPrimitive?.intOrNull ?: x,
            targetY = target?.get("y")?.jsonPrimitive?.intOrNull ?: y,
            targetElevation =
                ((target?.get("elevation")?.jsonPrimitive?.intOrNull ?: 0) - 1).coerceAtLeast(0),
        )
      } ?: emptyList()

  // Every object event is emitted. A npc hidden by a story flag keeps the flag in hideFlag so the
  // server can skip it at spawn and a script can show it later, the always-visible ones get "".
  private fun parseNpcs(mapJson: JsonObject, mapDirName: String, ctx: Context): List<ParsedNpc> {
    val visibleOverride = region.defaultVisibleNpcs[mapDirName]
    return mapJson["object_events"]?.jsonArrayOrNull().orEmpty().mapIndexed { idx, npcElement ->
      val npc = npcElement.jsonObject
      val flag = npc["flag"]?.jsonPrimitive?.contentOrNull ?: "0"
      val shownByDefault = if (visibleOverride != null) idx in visibleOverride else flag == "0"
      val gfxName = npc["graphics_id"]?.jsonPrimitive?.contentOrNull ?: "OBJ_EVENT_GFX_BOY_1"
      val movementName = npc["movement_type"]?.jsonPrimitive?.contentOrNull ?: "MOVEMENT_TYPE_NONE"
      ParsedNpc(
          entityIdx = idx,
          graphicsId = ctx.gfxIds[gfxName] ?: 0,
          x = npc["x"]?.jsonPrimitive?.intOrNull ?: 0,
          y = npc["y"]?.jsonPrimitive?.intOrNull ?: 0,
          elevation = npc["elevation"]?.jsonPrimitive?.intOrNull ?: 3,
          movementType = movementTypes.ref(movementName),
          movementRangeX = npc["movement_range_x"]?.jsonPrimitive?.intOrNull ?: 0,
          movementRangeY = npc["movement_range_y"]?.jsonPrimitive?.intOrNull ?: 0,
          trainerType =
              if (npc["trainer_type"]?.jsonPrimitive?.contentOrNull == "TRAINER_TYPE_NONE") 0
              else 1,
          facing = movementTypes.facingRef(movementName),
          script = npc["script"]?.jsonPrimitive?.contentOrNull ?: "0x0",
          hideFlag = if (shownByDefault) "" else "${region.name}/$flag",
      )
    }
  }

  // Parses the map's ON_FRAME table: each map_script_2 runs its script once the var equals the
  // value
  // when the player enters the map. Values that are not plain integers (rare) are skipped.
  private fun parseOnFrameScripts(mapDirName: String): List<ParsedFrameScript> {
    val file = File(rootDir, "data/maps/$mapDirName/scripts.inc")
    if (!file.exists()) return emptyList()
    val text = file.readText()
    val tableLabel =
        Regex("""map_script\s+MAP_SCRIPT_ON_FRAME_TABLE\s*,\s*(\w+)""")
            .find(text)
            ?.groupValues
            ?.get(1) ?: return emptyList()
    // Emerald writes the table label with one colon, FireRed with two.
    val block =
        Regex("""(?m)^$tableLabel:+[^\n]*\n(.*?)(?=\n\s*\.2byte)""", RegexOption.DOT_MATCHES_ALL)
            .find(text)
            ?.groupValues
            ?.get(1) ?: return emptyList()
    val entry = Regex("""map_script_2\s+(\w+)\s*,\s*(\d+)\s*,\s*(\w+)""")
    return entry
        .findAll(block)
        .map {
          ParsedFrameScript(
              varKey = "${region.name}/${it.groupValues[1]}",
              value = it.groupValues[2].toInt(),
              script = it.groupValues[3],
          )
        }
        .toList()
  }

  private fun parseBgEvents(mapJson: JsonObject): List<ParsedBgEvent> =
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

  /** Scripts fired on map trigger coordinates. */
  private fun parseCoordScripts(mapJson: JsonObject): List<ParsedCoordScript> =
      mapJson["coord_events"]?.jsonArrayOrNull()?.mapNotNull { event ->
        val obj = event.jsonObject
        if (obj["type"]?.jsonPrimitive?.contentOrNull != "trigger") return@mapNotNull null
        val varName = obj["var"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
        val value =
            obj["var_value"]?.jsonPrimitive?.contentOrNull?.toIntOrNull() ?: return@mapNotNull null
        val script = obj["script"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
        ParsedCoordScript(
            x = obj["x"]?.jsonPrimitive?.intOrNull ?: 0,
            y = obj["y"]?.jsonPrimitive?.intOrNull ?: 0,
            elevation = obj["elevation"]?.jsonPrimitive?.intOrNull ?: 0,
            varKey = "${region.name}/$varName",
            value = value,
            script = script,
        )
      } ?: emptyList()

  private fun parseBorderTiles(layout: JsonObject): List<Int> =
      layout["border_filepath"]?.jsonPrimitive?.contentOrNull?.let {
        readBorderTiles(File(rootDir, it))
      } ?: emptyList()

  private fun parseBlockData(layout: JsonObject): String {
    val path = layout["blockdata_filepath"]?.jsonPrimitive?.contentOrNull ?: return ""
    val file = File(rootDir, path)
    if (!file.exists()) return ""
    return Base64.getEncoder().encodeToString(file.readBytes())
  }

  // One behavior byte per tile, resolved from the layout's tilesets. Empty when there is no block
  // data to align with.
  private fun parseBehaviorData(layout: JsonObject): String {
    val path = layout["blockdata_filepath"]?.jsonPrimitive?.contentOrNull ?: return ""
    val file = File(rootDir, path)
    if (!file.exists()) return ""
    val primary = layout["primary_tileset"]?.jsonPrimitive?.contentOrNull
    val secondary = layout["secondary_tileset"]?.jsonPrimitive?.contentOrNull
    return metatileBehaviors.behaviorData(primary, secondary, file.readBytes())
  }

  private fun parseEncounters(mapJson: JsonObject, ctx: Context): List<ParsedEncounterTable> {
    val mapId = mapJson["id"]?.jsonPrimitive?.contentOrNull ?: return emptyList()
    val entry = ctx.encounters.byMap[mapId] ?: return emptyList()
    return ENCOUNTER_METHODS.mapNotNull { (jsonKey, methodRef) ->
      val table = (entry[jsonKey] as? JsonObject) ?: return@mapNotNull null
      val rate = table["encounter_rate"]?.jsonPrimitive?.intOrNull ?: 0
      val weights = ctx.encounters.weightsByType[jsonKey].orEmpty()
      val slots =
          table["mons"]?.jsonArrayOrNull().orEmpty().mapIndexedNotNull { i, mon ->
            val obj = mon.jsonObject
            val speciesName =
                obj["species"]?.jsonPrimitive?.contentOrNull ?: return@mapIndexedNotNull null
            val internalId = ctx.speciesIds[speciesName] ?: return@mapIndexedNotNull null
            val speciesId = ctx.nationalDex[internalId] ?: return@mapIndexedNotNull null
            ParsedEncounterSlot(
                speciesId = speciesId,
                minLevel = obj["min_level"]?.jsonPrimitive?.intOrNull ?: 1,
                maxLevel = obj["max_level"]?.jsonPrimitive?.intOrNull ?: 1,
                weight = weights.getOrElse(i) { 1 },
            )
          }
      if (slots.isEmpty()) null
      else ParsedEncounterTable(method = methodRef, encounterRate = rate, slots = slots)
    }
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

  private fun readIntDefine(file: File, name: String): Int? {
    if (!file.exists()) return null
    val pattern = Regex("""^#define\s+$name\s+(\d+)""")
    return file.readLines().firstNotNullOfOrNull {
      pattern.find(it.trim())?.groupValues?.get(1)?.toInt()
    }
  }

  private fun readGfxIds(file: File): Map<String, Int> {
    val gfx = readDefineTable(file, "OBJ_EVENT_GFX_").toMutableMap()
    val varsBase =
        gfx["OBJ_EVENT_GFX_VARS"] ?: readIntDefine(file, "NUM_OBJ_EVENT_GFX")?.plus(1) ?: return gfx
    val varPattern =
        Regex(
            """^#define\s+(OBJ_EVENT_GFX_VAR_[0-9A-Fa-f]+)\s+\(OBJ_EVENT_GFX_VARS\s*\+\s*0x([0-9A-Fa-f]+)\)""")
    file.forEachLine { line ->
      val m = varPattern.find(line.trim()) ?: return@forEachLine
      gfx[m.groupValues[1]] = varsBase + m.groupValues[2].toInt(16)
    }
    return gfx
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
    var index = region.gbaPaletteOffset
    val out = mutableMapOf<String, Int>()
    file.forEachLine { line ->
      val m = pattern.find(line.trim()) ?: return@forEachLine
      out[m.groupValues[1]] = index
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

  private fun wireBank(groupIndex: Int): Int = groupIndex + region.gbaBankOffset

  private fun JsonElement.jsonArrayOrNull() = (this as? JsonArray)
}
