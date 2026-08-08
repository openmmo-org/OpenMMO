package de.fiereu.openmmo.server.login.handler

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionAttribute
import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.openmmo.common.auth.RememberMeTokenIssuer
import de.fiereu.openmmo.common.auth.RememberMeTokenVerifier
import de.fiereu.openmmo.common.auth.SessionTokenIssuer
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.common.enums.LoginState
import de.fiereu.openmmo.net.login.packets.JoinGameServerPacket
import de.fiereu.openmmo.net.login.packets.LoginRequestPacket
import de.fiereu.openmmo.net.login.packets.LoginResponsePacket
import de.fiereu.openmmo.net.login.packets.PasswordLogin
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacket
import de.fiereu.openmmo.net.login.packets.SentCredentialsPacket
import de.fiereu.openmmo.net.login.packets.TokenLogin
import de.fiereu.openmmo.server.login.auth.InMemoryUserStore
import de.fiereu.openmmo.server.login.auth.sha1Hex
import de.fiereu.openmmo.server.login.catalog.GameServerCatalog
import de.fiereu.openmmo.server.login.config.LoginServerConfig
import de.fiereu.openmmo.server.login.di.DaggerLoginServerComponent
import de.fiereu.openmmo.server.login.session.AUTHED_USER_ID
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.netty.channel.ChannelFuture
import io.netty.channel.embedded.EmbeddedChannel
import java.net.SocketAddress
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.util.Base64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

private class RecordingSession : SessionContext {
  val sent = mutableListOf<Any>()
  private val backing = EmbeddedChannel()

  override val side: Side = Side.SERVER
  override val channel = backing
  override val remoteAddress: SocketAddress
    get() = backing.remoteAddress() ?: java.net.InetSocketAddress(0)

  override val phase: SessionPhase = SessionPhase.ESTABLISHED
  override val handshakeCompletedAt: Instant? = Instant.now()

  override val attributes: SessionAttributes =
      object : SessionAttributes {
        private val map = HashMap<SessionAttribute<*>, Any>()

        @Suppress("UNCHECKED_CAST")
        override fun <T : Any> get(key: SessionAttribute<T>): T? = map[key] as T?

        override fun <T : Any> set(key: SessionAttribute<T>, value: T) {
          map[key] = value
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : Any> remove(key: SessionAttribute<T>): T? = map.remove(key) as T?

        @Suppress("UNCHECKED_CAST")
        override fun <T : Any> getOrPut(key: SessionAttribute<T>, default: () -> T): T =
            map.getOrPut(key) { default() } as T

        override fun contains(key: SessionAttribute<*>): Boolean = map.containsKey(key)
      }

  override fun send(packet: Any): ChannelFuture {
    sent += packet
    return backing.newSucceededFuture()
  }

  override fun close(reason: () -> String) {
    backing.close()
  }

  override fun onPhase(phase: SessionPhase, listener: () -> Unit) = listener()
}

private fun loginRequest(
    username: String,
    method: de.fiereu.openmmo.net.login.packets.LoginMethod
) =
    LoginRequestPacket(
        username = username,
        manualLogin = true,
        hwid = ByteArray(0),
        method = method,
        language = Language.EN,
        clientRevision = 0,
        installationRevision = 0,
        os = 0u,
        hardwareInfoCache = ByteArray(0),
    )

class LoginAppHandlerTest :
    FunSpec({
      val secret = ByteArray(32) { it.toByte() }

      val maxAge: Duration = Duration.ofDays(30)

      fun newHandler(
          users: InMemoryUserStore,
          clock: Clock = Clock.systemUTC(),
      ): LoginAppHandler =
          LoginAppHandler(
              users = users,
              catalog = GameServerCatalog(),
              tokenIssuer = SessionTokenIssuer(secret, clock),
              rememberMeIssuer = RememberMeTokenIssuer(secret, clock),
              rememberMeVerifier = RememberMeTokenVerifier(secret, maxAge, clock),
              scope = CoroutineScope(Job()),
          )

      test("dagger component constructs the handler with all three packet types registered") {
        val config =
            LoginServerConfig(
                host = "127.0.0.1",
                port = 0,
                checksumSize = 16,
                rootKeyResource = "game.private.pem",
                sessionSecret = "test-secret-for-unit-tests".toByteArray(),
            )
        val component = DaggerLoginServerComponent.factory().create(config)
        val handler = component.handlerProvider().get()
        handler.isRegistered(LoginRequestPacket::class) shouldBe true
        handler.isRegistered(RequestGameServerListPacket::class) shouldBe true
        handler.isRegistered(JoinGameServerPacket::class) shouldBe true
      }

      test("password login without stay-logged-in sends only the AUTHED response") {
        val users = InMemoryUserStore()
        users.addUser("test", "test")
        val handler = newHandler(users)
        val session = RecordingSession()
        runBlocking {
          handler.onLoginRequest(
              PacketEvent(loginRequest("test", PasswordLogin(sha1Hex("test"), false)), session))
        }
        session.sent.map { it::class } shouldBe listOf(LoginResponsePacket::class)
        (session.sent.single() as LoginResponsePacket).state shouldBe LoginState.AUTHED
      }

      test("password login with stay-logged-in sends a token that verifies to the user id") {
        val users = InMemoryUserStore()
        val userId = users.addUser("stay", "secret")
        val handler = newHandler(users)
        val session = RecordingSession()
        runBlocking {
          handler.onLoginRequest(
              PacketEvent(loginRequest("stay", PasswordLogin(sha1Hex("secret"), true)), session))
        }
        session.sent.map { it::class } shouldBe
            listOf(SentCredentialsPacket::class, LoginResponsePacket::class)
        val credentials = session.sent[0] as SentCredentialsPacket
        credentials.key.isNotBlank() shouldBe true
        val tokenBytes = Base64.getDecoder().decode(credentials.key)
        tokenBytes.size shouldBe 32
        val decoded = RememberMeTokenVerifier(secret, maxAge).verify(tokenBytes)
        decoded.shouldNotBeNull()
        decoded.userId shouldBe userId
        (session.sent[1] as LoginResponsePacket).state shouldBe LoginState.AUTHED
        session.attributes[AUTHED_USER_ID] shouldBe userId
      }

      test("token login with a matching token authenticates the user and refreshes the token") {
        val users = InMemoryUserStore()
        val userId = users.addUser("tokenuser", "secret")
        val handler = newHandler(users)
        val token = RememberMeTokenIssuer(secret).issue(userId, 0)
        val session = RecordingSession()
        runBlocking {
          handler.onLoginRequest(
              PacketEvent(loginRequest("tokenuser", TokenLogin(token.bytes)), session))
        }
        session.sent.map { it::class } shouldBe
            listOf(SentCredentialsPacket::class, LoginResponsePacket::class)
        val refreshed = session.sent[0] as SentCredentialsPacket
        val refreshedBytes = Base64.getDecoder().decode(refreshed.key)
        RememberMeTokenVerifier(secret, maxAge)
            .verify(refreshedBytes)
            .shouldNotBeNull()
            .userId shouldBe userId
        (session.sent[1] as LoginResponsePacket).state shouldBe LoginState.AUTHED
        session.attributes[AUTHED_USER_ID] shouldBe userId
      }

      test("token login with an expired token is rejected") {
        val users = InMemoryUserStore()
        val userId = users.addUser("tokenuser", "secret")
        val issuedAt = Instant.ofEpochSecond(1_700_000_000)
        val token =
            RememberMeTokenIssuer(secret, Clock.fixed(issuedAt, ZoneOffset.UTC)).issue(userId, 0)
        val laterClock = Clock.fixed(issuedAt.plus(Duration.ofDays(31)), ZoneOffset.UTC)
        val handler = newHandler(users, clock = laterClock)
        val session = RecordingSession()
        runBlocking {
          handler.onLoginRequest(
              PacketEvent(loginRequest("tokenuser", TokenLogin(token.bytes)), session))
        }
        (session.sent.single() as LoginResponsePacket).state shouldBe
            LoginState.INVALID_SAVED_CREDENTIALS
        session.attributes.contains(AUTHED_USER_ID) shouldBe false
      }

      test("token login with invalid bytes is rejected") {
        val users = InMemoryUserStore()
        users.addUser("tokenuser", "secret")
        val handler = newHandler(users)
        val session = RecordingSession()
        runBlocking {
          handler.onLoginRequest(
              PacketEvent(loginRequest("tokenuser", TokenLogin(ByteArray(24) { 1 })), session))
        }
        (session.sent.single() as LoginResponsePacket).state shouldBe
            LoginState.INVALID_SAVED_CREDENTIALS
        session.attributes.contains(AUTHED_USER_ID) shouldBe false
      }

      test("token login for a username that does not exist is rejected") {
        val users = InMemoryUserStore()
        val token = RememberMeTokenIssuer(secret).issue(999, 0)
        val handler = newHandler(users)
        val session = RecordingSession()
        runBlocking {
          handler.onLoginRequest(
              PacketEvent(loginRequest("ghost", TokenLogin(token.bytes)), session))
        }
        (session.sent.single() as LoginResponsePacket).state shouldBe
            LoginState.INVALID_SAVED_CREDENTIALS
        session.attributes.contains(AUTHED_USER_ID) shouldBe false
      }

      test("token login for a user id that does not match the username is rejected") {
        val users = InMemoryUserStore()
        val otherId = users.addUser("other", "secret")
        users.addUser("victim", "secret")
        val handler = newHandler(users)
        val token = RememberMeTokenIssuer(secret).issue(otherId, 0)
        val session = RecordingSession()
        runBlocking {
          handler.onLoginRequest(
              PacketEvent(loginRequest("victim", TokenLogin(token.bytes)), session))
        }
        (session.sent.single() as LoginResponsePacket).state shouldBe
            LoginState.INVALID_SAVED_CREDENTIALS
        session.attributes.contains(AUTHED_USER_ID) shouldBe false
      }
    })
