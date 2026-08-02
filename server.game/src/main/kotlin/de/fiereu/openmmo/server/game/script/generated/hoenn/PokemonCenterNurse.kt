package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.common.dialog.DialogLine
import de.fiereu.openmmo.server.game.script.ScriptContext

/** Shared Emerald nurse text, resolved directly against the configured retail ROM. */
private enum class PokemonCenterNurseDialog(override val textId: Int) : DialogLine {
  WouldYouLikeToRestYourPokemon(0x102726EB),
  IllTakeYourPokemon(0x10272768),
  RestoredPokemonToFullHealth(0x10272798),
  WeHopeToSeeYouAgain(0x10272640),
}

internal suspend fun healAtPokemonCenter(ctx: ScriptContext) {
  if (!ctx.askYesNo(PokemonCenterNurseDialog.WouldYouLikeToRestYourPokemon)) {
    ctx.say(PokemonCenterNurseDialog.WeHopeToSeeYouAgain)
    return
  }
  ctx.say(PokemonCenterNurseDialog.IllTakeYourPokemon)
  ctx.healParty()
  ctx.say(PokemonCenterNurseDialog.RestoredPokemonToFullHealth)
  ctx.say(PokemonCenterNurseDialog.WeHopeToSeeYouAgain)
}
