package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_TrainerTower
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PSYCHIC_DARIO, SevenIsland_TrainerTower_Text_DarioIntro, SevenIsland_TrainerTower_Text_DarioDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_TrainerTower_EventScript_DarioRematch
 * msgbox SevenIsland_TrainerTower_Text_DarioPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_TrainerTower_EventScript_Dario : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_TrainerTower_EventScript_Dario")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PSYCHIC_RODETTE, SevenIsland_TrainerTower_Text_RodetteIntro, SevenIsland_TrainerTower_Text_RodetteDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_TrainerTower_EventScript_RodetteRematch
 * msgbox SevenIsland_TrainerTower_Text_RodettePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_TrainerTower_EventScript_Rodette : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_TrainerTower_EventScript_Rodette")
}

internal object SevenIsland_TrainerTower_EventScript_TrainerTowerSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SevenIsland_TrainerTower.TrainerTowerSign)
}

internal object SevenIsland_TrainerTower_EventScript_TrainerTowerAheadSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SevenIsland_TrainerTower.TrainerTowerAhead)
}

internal val SevenIsland_TrainerTowerScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_TrainerTower_EventScript_Dario" to SevenIsland_TrainerTower_EventScript_Dario,
        "SevenIsland_TrainerTower_EventScript_Rodette" to
            SevenIsland_TrainerTower_EventScript_Rodette,
        "SevenIsland_TrainerTower_EventScript_TrainerTowerSign" to
            SevenIsland_TrainerTower_EventScript_TrainerTowerSign,
        "SevenIsland_TrainerTower_EventScript_TrainerTowerAheadSign" to
            SevenIsland_TrainerTower_EventScript_TrainerTowerAheadSign,
    )
