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
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.DialogChoicePacket
import de.fiereu.openmmo.net.game.packets.EntityInteractPacket
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.FriendProfileRequestPacket
import de.fiereu.openmmo.net.game.packets.GuildDepartPacket
import de.fiereu.openmmo.net.game.packets.GuildDisbandTogglePacket
import de.fiereu.openmmo.net.game.packets.GuildInvitePacket
import de.fiereu.openmmo.net.game.packets.GuildMemberExpelPacket
import de.fiereu.openmmo.net.game.packets.GuildMemberRankAssignPacket
import de.fiereu.openmmo.net.game.packets.GuildRankPermissionUpdatePacket
import de.fiereu.openmmo.net.game.packets.InteractiveResponsePacket
import de.fiereu.openmmo.net.game.packets.JoinPacket
import de.fiereu.openmmo.net.game.packets.KeepAlivePacket
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.net.game.packets.RemoveFriendPacket
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.net.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.net.game.packets.RequestSocialProfilePacket
import de.fiereu.openmmo.net.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.net.game.packets.TeamFoundPacket
import de.fiereu.openmmo.net.game.packets.UnblockPlayerPacket
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.GuildService
import de.fiereu.openmmo.server.game.services.InteractionService
import de.fiereu.openmmo.server.game.services.LoginService
import de.fiereu.openmmo.server.game.services.MovementService
import de.fiereu.openmmo.server.game.services.MultiplayerService
import de.fiereu.openmmo.server.game.services.SocialService
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

private val log = KotlinLogging.logger {}

class GameAppHandler
@Inject
constructor(
    private val loginService: LoginService,
    private val movementService: MovementService,
    private val dialogService: DialogService,
    private val interactionService: InteractionService,
    private val multiplayerService: MultiplayerService,
    private val socialService: SocialService,
    private val guildService: GuildService,
    private val sessionRegistry: SessionRegistry,
    private val characterStore: CharacterStore,
    scope: CoroutineScope,
) : CoroutineProtocolHandler<GameProtocol>(GameProtocol, Side.SERVER, scope) {

  init {
    onSuspend<JoinPacket> { event -> loginService.onJoinGame(event) }
    onSuspend<CreateCharacterPacket> { event -> loginService.onCreateCharacter(event) }
    onSuspend<RequestCharactersPacket> { event -> loginService.onCharacterRequest(event) }
    onSuspend<SelectCharacterPacket> { event -> loginService.onCharacterSelected(event) }
    onSuspend<RequestPlayerPacket> { event -> loginService.onRequestPlayer(event) }

    onSuspend<MovementPacket> { event -> movementService.onMovement(event) }
    onSuspend<FaceDirectionPacket> { event -> movementService.onFaceDirection(event) }

    onSuspend<EntityInteractPacket> { event -> interactionService.onEntityInteract(event) }
    onSuspend<InteractiveResponsePacket> { event -> dialogService.onInteractive(event) }
    onSuspend<DialogChoicePacket> { event -> dialogService.onDialogChoice(event) }

    on<AddFriendPacket> { event -> socialService.onAddFriend(event) }
    on<RemoveFriendPacket> { event -> socialService.onRemoveFriend(event) }
    on<BlockPlayerPacket> { event -> socialService.onBlockPlayer(event) }
    on<UnblockPlayerPacket> { event -> socialService.onUnblockPlayer(event) }
    on<FriendProfileRequestPacket> { event -> socialService.onFriendProfileRequest(event) }
    on<RequestSocialProfilePacket> { event -> socialService.onRequestSocialProfile(event) }
    on<CancelSocialInteractionPacket> { event -> socialService.onCancelSocialInteraction(event) }

    on<TeamFoundPacket> { event -> guildService.onFoundTeam(event) }
    on<GuildInvitePacket> { event -> guildService.onGuildInvite(event) }
    on<GuildRankPermissionUpdatePacket> { event -> guildService.onRankPermissionUpdate(event) }
    on<GuildMemberRankAssignPacket> { event -> guildService.onRankAssign(event) }
    on<GuildMemberExpelPacket> { event -> guildService.onExpel(event) }
    on<GuildDepartPacket> { event -> guildService.onDepart(event) }
    on<GuildDisbandTogglePacket> { event -> guildService.onDisbandToggle(event) }

    on<KeepAlivePacket> { event -> event.session.send(event.packet) }
    on<ChatMessagePacket> { event -> onChatMessage(event) }
  }

  override fun onInactive() {
    val state = session.attributes[PLAYER_STATE] ?: return
    log.info { "Player ${state.characterId} disconnected." }
    val charId = state.characterId
    if (charId != null) {
      multiplayerService.broadcastExcept(session, EntityLeavePacket(charId))
      sessionRegistry.unbindCharacter(charId)
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

  private fun onChatMessage(event: PacketEvent<ChatMessagePacket>) {
    val state = event.session.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "Chat message from session without PlayerState" }
      return
    }
    val charId = state.characterId ?: return
    val sender = characterStore.getCharacter(charId)?.info?.name ?: "Unknown"
    val msg = event.packet
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
