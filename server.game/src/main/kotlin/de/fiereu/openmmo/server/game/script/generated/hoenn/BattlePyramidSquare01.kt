package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_PYRAMID, TRAINER_PHILLIP, LOCALID_NONE, BattleFacility_TrainerBattle_PlaceholderText, BattleFacility_TrainerBattle_PlaceholderText
 * pyramid_showhint
 * waitmessage
 * waitbuttonpress
 * closemessage
 * releaseall
 * end
 * ```
 */
internal object BattlePyramid_TrainerBattle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BattlePyramid_TrainerBattle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * pyramid_setitem
 * callstd STD_FIND_ITEM
 * goto_if_eq VAR_0x8007, 0, BattlePyramid_FindItemBallEnd
 * pyramid_hideitem
 * end
 * ```
 */
internal object BattlePyramid_FindItemBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BattlePyramid_FindItemBall")
}

internal val BattlePyramidSquare01Scripts: Map<String, Script> =
    mapOf(
        "BattlePyramid_TrainerBattle" to BattlePyramid_TrainerBattle,
        "BattlePyramid_FindItemBall" to BattlePyramid_FindItemBall,
    )
