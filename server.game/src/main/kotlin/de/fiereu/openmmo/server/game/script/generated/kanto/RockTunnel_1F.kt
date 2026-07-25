package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.RockTunnel_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_DANA, RockTunnel_1F_Text_DanaIntro, RockTunnel_1F_Text_DanaDefeat
 * msgbox RockTunnel_1F_Text_DanaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_Dana : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_Dana")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_ARIANA, RockTunnel_1F_Text_ArianaIntro, RockTunnel_1F_Text_ArianaDefeat
 * msgbox RockTunnel_1F_Text_ArianaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_Ariana : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_Ariana")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_LEAH, RockTunnel_1F_Text_LeahIntro, RockTunnel_1F_Text_LeahDefeat
 * msgbox RockTunnel_1F_Text_LeahPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_Leah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_Leah")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_LUCAS, RockTunnel_1F_Text_LucasIntro, RockTunnel_1F_Text_LucasDefeat
 * msgbox RockTunnel_1F_Text_LucasPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_Lucas : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_Lucas")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_OLIVER, RockTunnel_1F_Text_OliverIntro, RockTunnel_1F_Text_OliverDefeat
 * msgbox RockTunnel_1F_Text_OliverPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_Oliver : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_Oliver")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_LENNY, RockTunnel_1F_Text_LennyIntro, RockTunnel_1F_Text_LennyDefeat
 * msgbox RockTunnel_1F_Text_LennyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_Lenny : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_Lenny")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_ASHTON, RockTunnel_1F_Text_AshtonIntro, RockTunnel_1F_Text_AshtonDefeat
 * msgbox RockTunnel_1F_Text_AshtonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_Ashton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_Ashton")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_REPEL
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_ItemRepel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_ItemRepel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PEARL
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_ItemPearl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_1F_EventScript_ItemPearl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RockTunnel_1F_EventScript_ItemEscapeRope")
}

internal object RockTunnel_1F_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RockTunnel_1F.RouteSign)
}

internal val RockTunnel_1FScripts: Map<String, Script> =
    mapOf(
        "RockTunnel_1F_EventScript_Dana" to RockTunnel_1F_EventScript_Dana,
        "RockTunnel_1F_EventScript_Ariana" to RockTunnel_1F_EventScript_Ariana,
        "RockTunnel_1F_EventScript_Leah" to RockTunnel_1F_EventScript_Leah,
        "RockTunnel_1F_EventScript_Lucas" to RockTunnel_1F_EventScript_Lucas,
        "RockTunnel_1F_EventScript_Oliver" to RockTunnel_1F_EventScript_Oliver,
        "RockTunnel_1F_EventScript_Lenny" to RockTunnel_1F_EventScript_Lenny,
        "RockTunnel_1F_EventScript_Ashton" to RockTunnel_1F_EventScript_Ashton,
        "RockTunnel_1F_EventScript_ItemRepel" to RockTunnel_1F_EventScript_ItemRepel,
        "RockTunnel_1F_EventScript_ItemPearl" to RockTunnel_1F_EventScript_ItemPearl,
        "RockTunnel_1F_EventScript_ItemEscapeRope" to RockTunnel_1F_EventScript_ItemEscapeRope,
        "RockTunnel_1F_EventScript_RouteSign" to RockTunnel_1F_EventScript_RouteSign,
    )
