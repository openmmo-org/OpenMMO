package de.fiereu.openmmo.server.game.protocol.game

import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.protocols.Protocol
import de.fiereu.openmmo.protocols.game.packets.ChatMessagePacket
import de.fiereu.openmmo.protocols.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.protocols.game.packets.DialogChoicePacket
import de.fiereu.openmmo.protocols.game.packets.EntityInteractPacket
import de.fiereu.openmmo.protocols.game.packets.EntityLeavePacket
import de.fiereu.openmmo.protocols.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.protocols.game.packets.InteractiveResponsePacket
import de.fiereu.openmmo.protocols.game.packets.JoinGamePacket
import de.fiereu.openmmo.protocols.game.packets.KeepAlivePacket
import de.fiereu.openmmo.protocols.game.packets.MovementPacket
import de.fiereu.openmmo.protocols.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.protocols.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.protocols.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.server.config.ServerConfig
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.InteractionService
import de.fiereu.openmmo.server.game.services.LoginService
import de.fiereu.openmmo.server.game.services.MapLoadService
import de.fiereu.openmmo.server.game.services.MovementService
import de.fiereu.openmmo.server.game.services.MultiplayerService
import de.fiereu.openmmo.server.game.services.NpcService
import de.fiereu.openmmo.server.game.services.PacketSender
import de.fiereu.openmmo.server.game.services.WarpService
import de.fiereu.openmmo.server.game.session.SessionManager
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.netty.handlers.ProtocolHandler
import de.fiereu.openmmo.server.protocol.PacketEvent
import de.fiereu.openmmo.server.protocol.respond
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.channel.ChannelHandlerContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val log = KotlinLogging.logger {}

class GameProtocolHandler(
    protocol: Protocol,
    serverConfig: ServerConfig,
    private val coroutineScope: CoroutineScope,
) : ProtocolHandler(protocol, serverConfig) {

  private val packetSender = PacketSender(compressor)
  private val mapLoadService = MapLoadService(packetSender)
  private val warpService = WarpService(mapLoadService)
  private val npcService = NpcService()
  private val multiplayerService = MultiplayerService()
  private val movementService =
      MovementService(warpService, mapLoadService, npcService, multiplayerService)
  private val dialogService = DialogService(packetSender)
  private val interactionService = InteractionService(npcService, dialogService, packetSender)
  private val loginService =
      LoginService(mapLoadService, npcService, multiplayerService, packetSender)

  override fun onActive(ctx: ChannelHandlerContext) {
    log.info { "Client ${ctx.channel().remoteAddress()} connected to game server." }
  }

  override fun channelInactive(ctx: ChannelHandlerContext) {
    val session = SessionManager.getSessionByChannel(ctx.channel())
    if (session != null) {
      log.info { "Player ${session.characterId} disconnected." }

      session.characterId?.let { charId ->
        multiplayerService.broadcastExcept(ctx.channel(), EntityLeavePacket(charId))
      }

      multiplayerService.broadcastMessage(
          ChatMessagePacket(
              ChatType.GAME_NOTIFICATIONS,
              Language.EN,
              "A player left the game.",
              "",
          ))
      SessionManager.removeSession(ctx.channel())
    }
    super.channelInactive(ctx)
  }

  @Suppress("unchecked_cast")
  override fun onPacketReceived(event: PacketEvent<*>) {
    coroutineScope.launch {
      when (event.packet) {
        is JoinGamePacket -> loginService.onJoinGame(event as PacketEvent<JoinGamePacket>)
        is CreateCharacterPacket ->
            loginService.onCreateCharacter(event as PacketEvent<CreateCharacterPacket>)
        is RequestCharactersPacket ->
            loginService.onCharacterRequest(event as PacketEvent<RequestCharactersPacket>)
        is SelectCharacterPacket ->
            loginService.onCharacterSelected(event as PacketEvent<SelectCharacterPacket>)
        is RequestPlayerPacket ->
            loginService.onRequestPlayer(event as PacketEvent<RequestPlayerPacket>)
        is MovementPacket -> movementService.onMovement(event as PacketEvent<MovementPacket>)
        is FaceDirectionPacket ->
            movementService.onFaceDirection(event as PacketEvent<FaceDirectionPacket>)
        is KeepAlivePacket -> onKeepAlive(event as PacketEvent<KeepAlivePacket>)
        is EntityInteractPacket ->
            interactionService.onEntityInteract(event as PacketEvent<EntityInteractPacket>)
        is InteractiveResponsePacket ->
            dialogService.onInteractive(event as PacketEvent<InteractiveResponsePacket>)
        is DialogChoicePacket ->
            dialogService.onDialogChoice(event as PacketEvent<DialogChoicePacket>)
        is ChatMessagePacket -> onChatMessage(event as PacketEvent<ChatMessagePacket>)
        else -> log.warn { "Unhandled game packet type: ${event.packet::class.simpleName}" }
      }
    }
  }

  private fun onKeepAlive(event: PacketEvent<KeepAlivePacket>) {
    event.respond(event.packet)
  }

  private fun onChatMessage(event: PacketEvent<ChatMessagePacket>) {
    val msg = event.packet
    val sender = SessionManager.getSessionByChannel(event.ctx.channel())

    if (sender == null) {
      log.warn { "Chat message from unknown sender" }
      return
    }

    val charId = sender.characterId ?: return
    val stored = CharacterStore.getCharacter(charId)
    val senderName = stored?.info?.name ?: "Unknown"

    log.info { "Chat [${msg.type}] $senderName: ${msg.message}" }

    multiplayerService.broadcastMessage(
        ChatMessagePacket(
            type = msg.type,
            language = msg.language,
            message = msg.message,
            sender = senderName,
        ))
  }
}
