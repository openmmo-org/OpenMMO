package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_DepartmentStoreRooftop
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * getpokenewsactive POKENEWS_LILYCOVE
 * call_if_eq VAR_RESULT, TRUE, LilycoveCity_DepartmentStoreRooftop_EventScript_ManClearOutSale
 * msgbox LilycoveCity_DepartmentStoreRooftop_Text_SetDatesForClearOutSales, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStoreRooftop_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStoreRooftop_EventScript_Man")
}

internal object LilycoveCity_DepartmentStoreRooftop_EventScript_ThirstyMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStoreRooftop.BoneDryThirsty)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration LilycoveCity_DepartmentStoreRooftop_PokemartDecor_ClearOutSale
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStoreRooftop_EventScript_SaleWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStoreRooftop_EventScript_SaleWoman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MOVE_TUTOR_TAUGHT_SUBSTITUTE, MoveTutor_EventScript_SubstituteTaught
 * msgbox MoveTutor_Text_SubstituteTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_SubstituteDeclined
 * call MoveTutor_EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_SubstituteDeclined
 * msgbox MoveTutor_Text_SubstituteWhichMon, MSGBOX_DEFAULT
 * setvar VAR_0x8005, TUTOR_MOVE_SUBSTITUTE
 * call MoveTutor_EventScript_OpenPartyMenu
 * goto_if_eq VAR_RESULT, 0, MoveTutor_EventScript_SubstituteDeclined
 * setflag FLAG_MOVE_TUTOR_TAUGHT_SUBSTITUTE
 * goto MoveTutor_EventScript_SubstituteTaught
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStoreRooftop_EventScript_SubstituteTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStoreRooftop_EventScript_SubstituteTutor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * message LilycoveCity_DepartmentStoreRooftop_Text_WhichDrinkWouldYouLike
 * waitmessage
 * showmoneybox 0, 0
 * goto LilycoveCity_DepartmentStoreRooftop_EventScript_ChooseDrink
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStoreRooftop_EventScript_VendingMachine : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStoreRooftop_EventScript_VendingMachine")
}

internal val LilycoveCity_DepartmentStoreRooftopScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_DepartmentStoreRooftop_EventScript_Man" to
            LilycoveCity_DepartmentStoreRooftop_EventScript_Man,
        "LilycoveCity_DepartmentStoreRooftop_EventScript_ThirstyMan" to
            LilycoveCity_DepartmentStoreRooftop_EventScript_ThirstyMan,
        "LilycoveCity_DepartmentStoreRooftop_EventScript_SaleWoman" to
            LilycoveCity_DepartmentStoreRooftop_EventScript_SaleWoman,
        "LilycoveCity_DepartmentStoreRooftop_EventScript_SubstituteTutor" to
            LilycoveCity_DepartmentStoreRooftop_EventScript_SubstituteTutor,
        "LilycoveCity_DepartmentStoreRooftop_EventScript_VendingMachine" to
            LilycoveCity_DepartmentStoreRooftop_EventScript_VendingMachine,
    )
