package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.WarpTile
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.services.WarpService
import de.fiereu.openmmo.server.game.services.WorldStateService
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.NewGameStarts
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryCommand
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val worldStateService: WorldStateService,
    private val storyPlayerService: StoryPlayerService,
    private val warpService: WarpService,
    private val battleService: BattleService,
) : ChatCommand {
  override val name = "story"
  override val usage = "/story [checkpoint|reset]"
  override val description = "jumps to a story scene, or lists the scenes with no argument"
  override val permission = CharacterPermissions.DEVELOPER

  override fun run(ctx: CommandContext) {
    val wanted = ctx.args.firstOrNull()
    if (wanted == null) {
      KANTO_CHECKPOINTS.forEach { ctx.reply("${it.name} - ${it.description}") }
      ctx.reply("reset - starts the region's story over, keeping money and the counters")
      return
    }
    // Both paths warp and rewrite the character underneath whatever is running. A parked script
    // would never be woken by the client it just lost, and a battle would keep playing against
    // monsters that no longer exist.
    if (ctx.state.inDialog) {
      ctx.reply("Finish what you are talking to first.")
      return
    }
    if (battleService.inBattle(ctx.characterId)) {
      ctx.reply("Finish the battle first.")
      return
    }
    if (wanted.equals("reset", ignoreCase = true)) {
      reset(ctx)
      return
    }
    val checkpoint = KANTO_CHECKPOINTS.find { it.name.equals(wanted, ignoreCase = true) }
    if (checkpoint == null) {
      ctx.reply("No checkpoint called $wanted. Run /story for the list.")
      return
    }
    apply(ctx, checkpoint)
  }

  private fun reset(ctx: CommandContext) {
    val charId = ctx.characterId
    val stored = characterStore.getCharacter(charId) ?: return
    val region = Region.byWireValue(stored.info.positionRegionId)
    if (region == null) {
      ctx.reply("That region has no known start, so there is nothing to reset to.")
      return
    }
    val female = stored.info.rivalSex == CharacterGender.FEMALE.wireValue
    val start = NewGameStarts.forRegion(region, female)

    characterStore.replaceProgress(
        characterId = charId,
        party = emptyList(),
        items = emptyMap(),
        storyFlags = start.storyFlags,
        storyVars = start.storyVars,
    )
    // Hoenn opens in the moving truck, whose exit reads the dynamic warp, so the reset has to put
    // that back as well or the first scene has nowhere to go.
    characterStore.setDynamicWarp(charId, start.dynamicWarp)

    val refreshed = characterStore.getCharacter(charId) ?: return
    worldStateService.send(ctx.session, refreshed, fullVars = true)
    warpService.executeWarp(
        ctx.session,
        charId,
        WarpTile(
            x = 0,
            y = 0,
            targetRegionId = region.wireValue,
            targetBankId = start.bankId,
            targetMapId = start.mapId,
            targetX = start.x.toInt(),
            targetY = start.y.toInt(),
            exitFacing = Direction.DOWN,
        ),
    )
    characterStore.flushCharacterAsync(charId)
    ctx.reply("Reset to the ${region.displayName} start. Your party, PC and bag are empty.")
  }

  private fun apply(ctx: CommandContext, checkpoint: StoryCheckpoint) {
    val charId = ctx.characterId
    characterStore.replaceProgress(
        characterId = charId,
        party = emptyList(),
        items = checkpoint.items,
        storyFlags = checkpoint.storyFlags,
        storyVars = checkpoint.storyVars,
    )
    checkpoint.party.forEach {
      storyPlayerService.givePokemon(ctx.session, ctx.state, it.dexId, it.level, it.moveIds)
    }

    // The client caches story vars from login and has no packet for a single one, so the whole
    // block goes again before the warp shows any of it. Flag and var ids resolve per region, so
    // the block has to go out under the region the checkpoint is in, not the one still on the
    // character until the warp lands.
    val stored = characterStore.getCharacter(charId) ?: return
    val aimed = stored.copy(info = stored.info.copy(positionRegionId = checkpoint.region.wireValue))
    worldStateService.send(ctx.session, aimed, fullVars = true)

    // Warping rather than moving the player, so the destination runs its entry scripts on arrival
    // and the scene the checkpoint aims at actually fires.
    warpService.executeWarp(
        ctx.session,
        charId,
        WarpTile(
            x = 0,
            y = 0,
            targetRegionId = checkpoint.region.wireValue,
            targetBankId = checkpoint.bankId.toByte(),
            targetMapId = checkpoint.mapId.toByte(),
            targetX = checkpoint.x,
            targetY = checkpoint.y,
            exitFacing = checkpoint.facing,
        ),
    )
    characterStore.flushCharacterAsync(charId)
    ctx.reply("Jumped to ${checkpoint.name}.")
  }
}
