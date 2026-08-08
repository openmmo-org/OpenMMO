package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.maps.WarpTile
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.services.WarpService
import de.fiereu.openmmo.server.game.services.WorldStateService
import de.fiereu.openmmo.server.game.storage.CharacterStore
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
) : ChatCommand {
  override val name = "story"
  override val usage = "/story [checkpoint]"
  override val description = "jumps to a story scene, or lists them with no argument"
  override val permission = CharacterPermissions.DEVELOPER

  override fun run(ctx: CommandContext) {
    val wanted = ctx.args.firstOrNull()
    if (wanted == null) {
      KANTO_CHECKPOINTS.forEach { ctx.reply("${it.name} - ${it.description}") }
      return
    }
    val checkpoint = KANTO_CHECKPOINTS.find { it.name.equals(wanted, ignoreCase = true) }
    if (checkpoint == null) {
      ctx.reply("No checkpoint called $wanted. Run /story to list them.")
      return
    }
    apply(ctx, checkpoint)
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
    // block goes again before the warp shows any of it.
    val stored = characterStore.getCharacter(charId) ?: return
    worldStateService.send(ctx.session, stored, fullVars = true)

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
