package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

/**
 * Where a fresh character starts and the story state its source game would already have set. Every
 * region needs its own entry, so a new region is one function here rather than another branch in
 * [CharacterStore].
 */
internal data class NewGameStart(
    val bankId: Byte,
    val mapId: Byte,
    val x: Short,
    val y: Short,
    val dynamicWarp: DynamicWarp? = null,
    val storyFlags: Set<String> = emptySet(),
    val storyVars: Map<String, Int> = emptyMap(),
)

internal object NewGameStarts {

  fun forRegion(region: Region, female: Boolean): NewGameStart =
      when (region) {
        Region.HOENN -> hoenn(female)
        Region.KANTO -> kanto()
      }

  /** Emerald opens in the moving truck, whose exit goes through the player's dynamic warp. */
  private fun hoenn(female: Boolean): NewGameStart =
      NewGameStart(
          bankId = 75,
          mapId = 40,
          x = 2,
          y = 2,
          dynamicWarp =
              DynamicWarp(
                  Region.HOENN.wireValue,
                  50,
                  9,
                  if (female) 12 else 3,
                  10,
                  Direction.RIGHT,
              ),
          storyFlags =
              HoennFlags.initiallySet +
                  (if (female) HoennFlags.femaleIntro else HoennFlags.maleIntro) +
                  HoennFlags.FLAG_HIDE_MAP_NAME_POPUP,
          storyVars =
              mapOf(
                  HoennVars.VAR_LITTLEROOT_INTRO_STATE to if (female) 2 else 1,
                  (if (female) HoennVars.VAR_LITTLEROOT_HOUSES_STATE_MAY
                  else HoennVars.VAR_LITTLEROOT_HOUSES_STATE_BRENDAN) to 1,
              ),
      )

  /**
   * FireRed opens in the player's bedroom above their house in Pallet Town, at the coordinates its
   * new game code warps to. Nothing about the opening depends on the player's gender.
   */
  private fun kanto(): NewGameStart =
      NewGameStart(
          bankId = 4,
          mapId = 1,
          x = 6,
          y = 6,
          storyFlags = KantoFlags.initiallySet,
      )
}
