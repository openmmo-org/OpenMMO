package de.fiereu.openmmo.server.login.handler

import de.fiereu.network.PacketEvent
import de.fiereu.network.Side
import de.fiereu.network.coroutines.CoroutineProtocolHandler
import de.fiereu.openmmo.common.auth.SessionTokenIssuer
import de.fiereu.openmmo.common.auth.SessionTokenVerifier
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
import java.time.Clock
import java.time.Duration
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

private val log = KotlinLogging.logger {}

/** Maximum age of a remember-me token before it is considered expired. */
private val TOKEN_MAX_AGE: Duration = Duration.ofDays(30)

class LoginAppHandler
@Inject
constructor(
    private val users: UserService,
    private val catalog: GameServerCatalog,
    private val tokenIssuer: SessionTokenIssuer,
    private val tokenVerifier: SessionTokenVerifier,
    private val clock: Clock = Clock.systemUTC(),
    scope: CoroutineScope,
) : CoroutineProtocolHandler<LoginProtocol>(LoginProtocol, Side.SERVER, scope) {

  init {
    onSuspend<LoginRequestPacket> { event -> onLoginRequest(event) }
    onSuspend<RequestGameServerListPacket> { event -> onServerListRequest(event) }
    onSuspend<JoinGameServerPacket> { event -> onJoinGameServer(event) }
  }

  internal suspend fun onLoginRequest(event: PacketEvent<LoginRequestPacket>) {
    val packet = event.packet
    when (val method = packet.method) {
      is PasswordLogin -> onPasswordLogin(event, method)
      is TokenLogin -> onTokenLogin(event, method)
    }
  }

  private suspend fun onPasswordLogin(
      event: PacketEvent<LoginRequestPacket>,
      method: PasswordLogin,
  ) {
    val packet = event.packet
    val result = users.authenticate(packet.username, method.password)
    log.info { "Login attempt for ${packet.username}: ${result.state}" }
    if (result.state == LoginState.AUTHED && result.userId != null) {
      event.session.attributes[AUTHED_USER_ID] = result.userId
      if (method.stayLoggedIn) {
        val token = tokenIssuer.issue(result.userId.toLong())
        event.session.send(SentCredentialsPacket(packet.username, token.bytes))
      }
    }
    event.session.send(LoginResponsePacket(result.state))
  }

  private suspend fun onTokenLogin(event: PacketEvent<LoginRequestPacket>, method: TokenLogin) {
    val packet = event.packet
    val token = tokenVerifier.verify(method.token)
    if (token != null && Duration.between(token.issuedAt, clock.instant()) > TOKEN_MAX_AGE) {
      log.info { "Token login rejected for ${packet.username}: token expired" }
      event.session.send(LoginResponsePacket(LoginState.INVALID_SAVED_CREDENTIALS))
      return
    }
    val userId = if (token != null) users.getUserId(packet.username) else null
    if (token == null || userId == null || userId.toLong() != token.userId) {
      log.info { "Token login rejected for ${packet.username}" }
      event.session.send(LoginResponsePacket(LoginState.INVALID_SAVED_CREDENTIALS))
      return
    }
    log.info { "Token login for ${packet.username}: AUTHED" }
    event.session.attributes[AUTHED_USER_ID] = userId
    // Rolling window: refresh the remember-me token on every successful token login so that a user
    // who logs in regularly is never forced to re-enter their password once the original token ages
    // out. Mirrors the stayLoggedIn branch in onPasswordLogin.
    val refreshed = tokenIssuer.issue(token.userId)
    event.session.send(SentCredentialsPacket(packet.username, refreshed.bytes))
    event.session.send(LoginResponsePacket(LoginState.AUTHED))
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
