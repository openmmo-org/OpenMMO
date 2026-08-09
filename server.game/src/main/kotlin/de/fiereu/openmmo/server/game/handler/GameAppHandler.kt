package de.fiereu.openmmo.server.game.handler

import de.fiereu.network.PacketEvent
import de.fiereu.network.Side
import de.fiereu.network.coroutines.CoroutineProtocolHandler
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.net.game.GameProtocol
import de.fiereu.openmmo.net.game.packets.AddFriendPacket
import de.fiereu.openmmo.net.game.packets.BlockPlayerPacket
import de.fiereu.openmmo.net.game.packets.CancelSocialInteractionPacket
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.ChatMessageSendPacket
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.DeleteCharacterPacket
import de.fiereu.openmmo.net.game.packets.DialogChoicePacket
import de.fiereu.openmmo.net.game.packets.EntityInteractPacket
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.JoinPacket
import de.fiereu.openmmo.net.game.packets.KeepAlivePacket
import de.fiereu.openmmo.net.game.packets.MapLoadedAckPacket
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.net.game.packets.NullPacket
import de.fiereu.openmmo.net.game.packets.RemoveFriendPacket
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.net.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.net.game.packets.RequestSocialProfilePacket
import de.fiereu.openmmo.net.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.net.game.packets.TileInteractPacket
import de.fiereu.openmmo.net.game.packets.UnblockPlayerPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSubmitPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleAppearancePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleCancelRequestPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleChatMessagePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleLeavePacket
import de.fiereu.openmmo.net.game.packets.battle.BattlePartySlotSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattlePartySwitchPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleReadyPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleRewardSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSequencePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSimulationRequestPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSlotActionPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchSelectionsPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTargetPickPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTeamPreviewConfirmPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTierSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTransitionReadyPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleUseItemPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnReplyPacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionResponsePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildActivityLogPageRequestPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildCreatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildDisbandPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildInvitePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildLeavePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberKickPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberRankAssignPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMotdUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildRankLabelUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildRankPermissionUpdatePacket
import de.fiereu.openmmo.server.game.script.ScriptRunner
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.GuildService
import de.fiereu.openmmo.server.game.services.InteractionService
import de.fiereu.openmmo.server.game.services.LoginService
import de.fiereu.openmmo.server.game.services.MovementService
import de.fiereu.openmmo.server.game.services.MultiplayerService
import de.fiereu.openmmo.server.game.services.PresenceService
import de.fiereu.openmmo.server.game.services.SocialService
import de.fiereu.openmmo.server.game.services.command.ChatCommandService
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SCRIPT_SCOPE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

private val log = KotlinLogging.logger {}

class GameAppHandler
@Inject
constructor(
    private val loginService: LoginService,
    private val movementService: MovementService,
    private val dialogService: DialogService,
    private val interactionService: InteractionService,
    private val multiplayerService: MultiplayerService,
    private val presenceService: PresenceService,
    private val socialService: SocialService,
    private val guildService: GuildService,
    private val battleService: BattleService,
    private val chatCommandService: ChatCommandService,
    private val scriptRunner: ScriptRunner,
    private val sessionRegistry: SessionRegistry,
    private val characterStore: CharacterStore,
    scope: CoroutineScope,
) : CoroutineProtocolHandler<GameProtocol>(GameProtocol, Side.SERVER, scope) {

  init {
    onSuspend<JoinPacket> { event -> loginService.onJoinGame(event) }
    onSuspend<CreateCharacterPacket> { event -> loginService.onCreateCharacter(event) }
    onSuspend<RequestCharactersPacket> { event -> loginService.onCharacterRequest(event) }
    onSuspend<SelectCharacterPacket> { event -> loginService.onCharacterSelected(event) }
    onSuspend<DeleteCharacterPacket> { event -> loginService.onDeleteCharacter(event) }
    onSuspend<RequestPlayerPacket> { event -> loginService.onRequestPlayer(event) }

    onSuspend<MovementPacket> { event -> movementService.onMovement(event) }
    onSuspend<FaceDirectionPacket> { event -> movementService.onFaceDirection(event) }

    onSuspend<EntityInteractPacket> { event -> interactionService.onEntityInteract(event) }
    onSuspend<TileInteractPacket> { event -> interactionService.onTileInteract(event) }
    onSuspend<DialogActionResponsePacket> { event -> dialogService.onInteractive(event) }
    onSuspend<DialogChoicePacket> { event -> dialogService.onDialogChoice(event) }

    on<AddFriendPacket> { event -> socialService.onAddFriend(event) }
    on<RemoveFriendPacket> { event -> socialService.onRemoveFriend(event) }
    on<BlockPlayerPacket> { event -> socialService.onBlockPlayer(event) }
    on<UnblockPlayerPacket> { event -> socialService.onUnblockPlayer(event) }
    on<RequestSocialProfilePacket> { event -> socialService.onRequestSocialProfile(event) }
    on<CancelSocialInteractionPacket> { event -> socialService.onCancelSocialInteraction(event) }

    onSuspend<GuildCreatePacket> { event -> guildService.onCreateGuild(event) }
    onSuspend<GuildInvitePacket> { event -> guildService.onGuildInvite(event) }
    onSuspend<GuildRankPermissionUpdatePacket> { event ->
      guildService.onRankPermissionUpdate(event)
    }
    onSuspend<GuildMemberRankAssignPacket> { event -> guildService.onRankAssign(event) }
    onSuspend<GuildMemberKickPacket> { event -> guildService.onKick(event) }
    onSuspend<GuildLeavePacket> { event -> guildService.onLeave(event) }
    onSuspend<GuildDisbandPacket> { event -> guildService.onDisband(event) }
    onSuspend<GuildMotdUpdatePacket> { event -> guildService.onMotdUpdate(event) }
    onSuspend<GuildRankLabelUpdatePacket> { event -> guildService.onRankLabelUpdate(event) }
    onSuspend<GuildActivityLogPageRequestPacket> { event ->
      guildService.onActivityLogPageRequest(event)
    }

    onSuspend<MoveLearnReplyPacket> { event -> battleService.onMoveLearnReply(event) }
    on<BattlePartySwitchPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleActionPacket> { event -> battleService.onBattlePacket(event) }
    onSuspend<BattleActionSelectPacket> { event -> battleService.onBattleAction(event) }
    on<BattleLeavePacket> { event -> battleService.onBattlePacket(event) }
    on<BattleSequencePacket> { event -> battleService.onBattlePacket(event) }
    on<BattleSlotActionPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleSwitchSelectionsPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleUseItemPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleAppearancePacket> { event -> battleService.onBattlePacket(event) }
    on<BattleCancelRequestPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleSimulationRequestPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleReadyPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleActionSubmitPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleTierSelectPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleChatMessagePacket> { event -> battleService.onBattlePacket(event) }
    on<BattlePartySlotSelectPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleTargetPickPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleTransitionReadyPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleTeamPreviewConfirmPacket> { event -> battleService.onBattlePacket(event) }
    on<BattleRewardSelectPacket> { event -> battleService.onBattlePacket(event) }
    onSuspend<MapLoadedAckPacket> { event -> battleService.onClientReady(event) }

    // The client sends an empty heartbeat packet.
    on<NullPacket> {}
    on<KeepAlivePacket> { event -> event.session.send(event.packet) }
    onSuspend<ChatMessagePacket> { event -> onChatMessage(event) }
    // What the client sends when the player types. The text rides in target unless the mode
    // carries a message of its own.
    onSuspend<ChatMessageSendPacket> { event ->
      chatCommandService.tryHandle(event.session, event.packet.message ?: event.packet.target)
    }
  }

  override fun onInactive() {
    // A cancelled scope left behind would make every later launch a silent no-op.
    session.attributes.remove(SCRIPT_SCOPE)?.cancel()
    val state = session.attributes[PLAYER_STATE] ?: return
    log.info { "Player ${state.characterId} disconnected." }
    val charId = state.characterId
    if (charId != null) {
      // The battle flush must land before the unload evicts the character from the cache, and
      // before the rollback, which would otherwise be overwritten by the party it persists.
      battleService.onDisconnect(session)
      // Undo the interrupted script here rather than leaving it to the coroutine's own cleanup,
      // which runs on another thread and would race the flush below.
      scriptRunner.rollBack(session, state, entityId = -1)
      state.inDialog = false
      presenceService.leave(session)
      sessionRegistry.unbindCharacter(charId)
      characterStore.unloadCharacterAsync(charId)
    }
    multiplayerService.broadcastMessage(
        ChatMessagePacket(
            type = ChatType.GAME_NOTIFICATIONS,
            language = Language.EN,
            message = "A player left the game.",
            sender = "",
        ),
    )
  }

  private suspend fun onChatMessage(event: PacketEvent<ChatMessagePacket>) {
    val state = event.session.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "Chat message from session without PlayerState" }
      return
    }
    val charId = state.characterId ?: return
    val msg = event.packet
    val sender = characterStore.getCharacter(charId)?.info?.name ?: "Unknown"
    log.info { "Chat [${msg.type}] $sender: ${msg.message}" }
    multiplayerService.broadcastMessage(
        ChatMessagePacket(
            type = msg.type,
            language = msg.language,
            message = msg.message,
            sender = sender,
        ),
    )
  }
}
