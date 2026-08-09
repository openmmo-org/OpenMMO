package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val PALLET_TOWN_BANK = 3
private const val PALLET_TOWN_MAP = 0
private const val INDOOR_PALLET_BANK = 4
private const val PLAYERS_HOUSE_2F_MAP = 1
private const val OAKS_LAB_MAP = 3
private const val VIRIDIAN_MART_BANK = 5
private const val VIRIDIAN_MART_MAP = 3

private val BULBASAUR = CheckpointMon(dexId = 1, level = 5, moveIds = listOf(33, 45))

// Clearing the hide flag is what makes NpcService spawn Oak in his lab.
private val AFTER_GRASS_SCENE = KantoFlags.initiallySet - KantoFlags.FLAG_HIDE_OAK_IN_HIS_LAB

// The scripts despawn these without persisting a flag, so a skipped scene sets them by hand.
private val TAKEN_BALLS =
    setOf(KantoFlags.FLAG_HIDE_BULBASAUR_BALL, KantoFlags.FLAG_HIDE_CHARMANDER_BALL)

private val AFTER_STARTER =
    AFTER_GRASS_SCENE +
        setOf(
            KantoFlags.FLAG_WORLD_MAP_PALLET_TOWN,
            KantoFlags.FLAG_VISITED_OAKS_LAB,
            KantoFlags.FLAG_SYS_POKEMON_GET,
            KantoFlags.FLAG_PALLET_LADY_NOT_BLOCKING_SIGN,
        )

private val AFTER_RIVAL =
    AFTER_STARTER +
        TAKEN_BALLS +
        setOf(
            KantoFlags.FLAG_BEAT_RIVAL_IN_OAKS_LAB,
            KantoFlags.FLAG_HIDE_RIVAL_IN_LAB,
            KantoFlags.FLAG_OPENED_START_MENU,
            KantoFlags.FLAG_WORLD_MAP_VIRIDIAN_CITY,
            KantoFlags.FLAG_GOT_POTION_ON_ROUTE_1,
        )

val KANTO_CHECKPOINTS: List<StoryCheckpoint> =
    listOf(
        StoryCheckpoint(
            name = "new-game",
            description = "the bedroom, exactly as a fresh character starts",
            region = Region.KANTO,
            bankId = INDOOR_PALLET_BANK,
            mapId = PLAYERS_HOUSE_2F_MAP,
            x = 6,
            y = 6,
            facing = Direction.DOWN,
            storyFlags = KantoFlags.initiallySet,
        ),
        StoryCheckpoint(
            name = "oak-grass",
            description = "on the trigger where Oak stops you leaving Pallet Town",
            region = Region.KANTO,
            bankId = PALLET_TOWN_BANK,
            mapId = PALLET_TOWN_MAP,
            x = 12,
            y = 1,
            facing = Direction.UP,
            storyFlags = KantoFlags.initiallySet + KantoFlags.FLAG_WORLD_MAP_PALLET_TOWN,
        ),
        StoryCheckpoint(
            name = "starter-choice",
            description = "inside Oak's lab, the starter scene about to play",
            region = Region.KANTO,
            bankId = INDOOR_PALLET_BANK,
            mapId = OAKS_LAB_MAP,
            x = 6,
            y = 12,
            facing = Direction.UP,
            storyFlags =
                AFTER_GRASS_SCENE +
                    setOf(
                        KantoFlags.FLAG_WORLD_MAP_PALLET_TOWN,
                        KantoFlags.FLAG_VISITED_OAKS_LAB,
                        KantoFlags.FLAG_DONT_TRANSITION_MUSIC,
                    ),
            storyVars =
                mapOf(
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_OAK to 1,
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB to 1,
                ),
        ),
        StoryCheckpoint(
            name = "rival-battle",
            description = "on the lab exit tile that starts the rival battle",
            region = Region.KANTO,
            bankId = INDOOR_PALLET_BANK,
            mapId = OAKS_LAB_MAP,
            x = 6,
            y = 8,
            facing = Direction.DOWN,
            storyFlags = AFTER_STARTER + TAKEN_BALLS,
            storyVars =
                mapOf(
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_OAK to 1,
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB to 3,
                    // Picks the rival's monster, so it has to match the party below.
                    KantoVars.VAR_STARTER_MON to 0,
                ),
            party = listOf(BULBASAUR),
        ),
        StoryCheckpoint(
            name = "oaks-parcel",
            description = "walking into the Viridian mart for Oak's parcel",
            region = Region.KANTO,
            bankId = VIRIDIAN_MART_BANK,
            mapId = VIRIDIAN_MART_MAP,
            x = 4,
            y = 7,
            facing = Direction.UP,
            storyFlags = AFTER_RIVAL,
            storyVars =
                mapOf(
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_OAK to 1,
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB to 4,
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY to 2,
                    KantoVars.VAR_STARTER_MON to 0,
                ),
            party = listOf(BULBASAUR),
        ),
        StoryCheckpoint(
            name = "pokedex",
            description = "in front of Oak with the parcel, ready for the Pokedex",
            region = Region.KANTO,
            bankId = INDOOR_PALLET_BANK,
            mapId = OAKS_LAB_MAP,
            x = 6,
            y = 4,
            facing = Direction.UP,
            storyFlags = AFTER_RIVAL,
            storyVars =
                mapOf(
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_OAK to 1,
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB to 5,
                    KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_MART to 1,
                    KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY to 2,
                    KantoVars.VAR_STARTER_MON to 0,
                ),
            party = listOf(BULBASAUR),
            items = mapOf(Items.OAKS_PARCEL to 1),
        ),
    )
