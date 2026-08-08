package de.fiereu.openmmo.server.login.handler

import de.fiereu.network.PacketEvent
import de.fiereu.network.Side
import de.fiereu.network.coroutines.CoroutineProtocolHandler
import de.fiereu.openmmo.common.auth.RememberMeTokenIssuer
import de.fiereu.openmmo.common.auth.RememberMeTokenVerifier
import de.fiereu.openmmo.common.auth.SessionTokenIssuer
import de.fiereu.openmmo.common.enums.LoginState
import de.fiereu.openmmo.net.login.LoginProtocol
import de.fiereu.openmmo.net.login.packets.GameServerData
import de.fiereu.openmmo.net.login.packets.GameServerListPacket
import de.fiereu.openmmo.net.login.packets.GameServerNodesPacket
import de.fiereu.openmmo.net.login.packets.JoinGameServerPacket
import de.fiereu.openmmo.net.login.packets.LoginRequestPacket
import de.fiereu.openmmo.net.login.packets.LoginResponsePacket
import de.fiereu.openmmo.net.login.packets.PasswordLogin
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacket
import de.fiereu.openmmo.net.login.packets.SentCredentialsPacket
import de.fiereu.openmmo.net.login.packets.TokenLogin
import de.fiereu.openmmo.server.login.auth.UserService
import de.fiereu.openmmo.server.login.catalog.GameServerCatalog
import de.fiereu.openmmo.server.login.session.AUTHED_USER_ID
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

private val log = KotlinLogging.logger {}

class LoginAppHandler
@Inject
constructor(
    private val users: UserService,
    private val catalog: GameServerCatalog,
    private val tokenIssuer: SessionTokenIssuer,
    private val rememberMeIssuer: RememberMeTokenIssuer,
    private val rememberMeVerifier: RememberMeTokenVerifier,
    scope: CoroutineScope,
) : CoroutineProtocolHandler<LoginProtocol>(LoginProtocol, Side.SERVER, scope) {

  init {
    onSuspend<LoginRequestPacket> { event -> onLoginRequest(event) }
    onSuspend<RequestGameServerListPacket> { event -> onServerListRequest(event) }
    onSuspend<JoinGameServerPacket> { event -> onJoinGameServer(event) }
  }

  internal suspend fun onLoginRequest(event: PacketEvent<LoginRequestPacket>) {
    when (val method = event.packet.method) {
      is PasswordLogin -> onPasswordLogin(event, method)
      is TokenLogin -> onTokenLogin(event, method)
    }
  }

  private suspend fun onPasswordLogin(
      event: PacketEvent<LoginRequestPacket>,
      method: PasswordLogin,
  ) {
    val username = event.packet.username
    val result = users.authenticate(username, method.password)
    log.info { "Login attempt for $username: ${result.state}" }
    if (result.state != LoginState.AUTHED || result.userId == null) {
      event.session.send(LoginResponsePacket(result.state))
      return
    }
    event.session.attributes[AUTHED_USER_ID] = result.userId
    if (method.stayLoggedIn) {
      sendRememberMeToken(event, result.userId, result.tokenEpoch, username)
    }
    event.session.send(LoginResponsePacket(result.state))
  }

  private suspend fun onTokenLogin(event: PacketEvent<LoginRequestPacket>, method: TokenLogin) {
    val username = event.packet.username
    val token = rememberMeVerifier.verify(method.token)
    val user = token?.let { users.findForToken(it.userId) }
    if (token == null ||
        user == null ||
        user.tokenEpoch != token.epoch ||
        !user.username.equals(username, ignoreCase = true)) {
      log.warn { "Rejected token login for $username" }
      event.session.send(LoginResponsePacket(LoginState.INVALID_SAVED_CREDENTIALS))
      return
    }
    log.info { "Token login for ${user.displayName}: AUTHED" }
    event.session.attributes[AUTHED_USER_ID] = user.id
    // Sliding expiry, so a player who keeps logging in never has to type a password again.
    sendRememberMeToken(event, user.id, user.tokenEpoch, user.displayName)
    event.session.send(LoginResponsePacket(LoginState.AUTHED))
  }

  private fun sendRememberMeToken(
      event: PacketEvent<LoginRequestPacket>,
      userId: Int,
      epoch: Int,
      displayName: String,
  ) {
    val issued = rememberMeIssuer.issue(userId, epoch)
    event.session.send(SentCredentialsPacket(displayName, issued.bytes))
  }

  private fun onServerListRequest(event: PacketEvent<RequestGameServerListPacket>) {
    event.session.send(GameServerListPacket(catalog.list()))
  }

  private fun onJoinGameServer(event: PacketEvent<JoinGameServerPacket>) {
    val entry = catalog.find(event.packet.gameServerId)
    if (entry == null) {
      log.warn { "Unknown game server id ${event.packet.gameServerId}" }
      event.session.send(GameServerNodesPacket(LoginState.NO_GS_AVAILABLE))
      return
    }
    val userId = event.session.attributes[AUTHED_USER_ID]
    if (userId == null) {
      log.warn { "Join game server without a completed login" }
      event.session.send(GameServerNodesPacket(LoginState.INVALID_SAVED_CREDENTIALS))
      return
    }
    val token = tokenIssuer.issue(userId = userId.toLong())
    val data =
        GameServerData(
            gameServerId = entry.server.id,
            userId = userId,
            sessionToken = token.bytes,
            localAddress = entry.localAddress,
            localHostname = entry.localHostname,
            port = entry.node.port,
        )
    event.session.send(
        GameServerNodesPacket(
            loginState = LoginState.AUTHED,
            gameServerData = data,
            nodes = listOf(entry.node),
        ),
    )
  }
}
