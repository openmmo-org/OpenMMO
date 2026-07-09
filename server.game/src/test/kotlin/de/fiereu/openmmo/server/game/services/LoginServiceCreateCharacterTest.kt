package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionAttribute
import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.CharactersListPacket
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.LoadMapPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.SelectedCharacterPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.SocialStore
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.embedded.EmbeddedChannel
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.time.Instant

/**
 * Regression test for the 2026-07-09 create-character hang: the real client's create flow expects
 * to be dropped into the world (same as selecting an existing character), not just handed a
 * refreshed character list. Root-cause evidence: captures/server-{c2s,s2c}-capture.log showed the
 * server DID send an updated CharactersListPacket after create (no exception), but the client never
 * followed up with its own select and just sat on the New Character screen.
 */
class LoginServiceCreateCharacterTest :
    FunSpec({
      test("create character auto-selects and spawns instead of leaving the client parked") {
        val characterStore = CharacterStore()
        val sessionRegistry = SessionRegistry()
        val mapManager = MapManager()
        val service =
            LoginService(
                mapLoadService = MapLoadService(mapManager),
                npcService = NpcService(mapManager),
                multiplayerService = MultiplayerService(sessionRegistry),
                socialService = SocialService(SocialStore(), sessionRegistry, characterStore),
                sessionRegistry = sessionRegistry,
                mapManager = mapManager,
                characterStore = characterStore,
            )

        val session = CreateCharacterSessionContext()
        val state = PlayerState(userId = 42)
        session.attributes[PLAYER_STATE] = state

        service.onCreateCharacter(
            PacketEvent(
                CreateCharacterPacket(name = "retestbag", gender = 0, cosmetics = ByteArray(0)),
                session,
            ))

        // Old (broken) behavior sent exactly one packet: the refreshed character list, then
        // nothing -- the client had nothing telling it to leave the create screen.
        session.sent.filterIsInstance<CharactersListPacket>().size shouldBe 1
        session.sent.filterIsInstance<SelectedCharacterPacket>().size shouldBe 1
        session.sent.filterIsInstance<PokemonContainerPacket>().size shouldBe 7
        session.sent.filterIsInstance<LoadMapPacket>().size shouldBe 1

        // The list-refresh must come before the auto-select spawn sequence.
        val kinds = session.sent.map { it::class }
        kinds.shouldContainInOrder(
            CharactersListPacket::class,
            SelectedCharacterPacket::class,
        )

        state.characterId.shouldNotBeNull()
        sessionRegistry.getByCharacterId(state.characterId!!).shouldNotBeNull()
      }
    })

private class CreateCharacterSessionContext : SessionContext {
  val sent = mutableListOf<Any>()

  override val side: Side = Side.SERVER
  override val channel: Channel = EmbeddedChannel()
  override val remoteAddress: SocketAddress = InetSocketAddress("127.0.0.1", 0)
  override val phase: SessionPhase = SessionPhase.ESTABLISHED
  override val handshakeCompletedAt: Instant? = Instant.now()
  override val attributes: SessionAttributes = CreateCharacterSessionAttributes()
  override val diagnosticsCaptureEnabled: Boolean = false
  override val diagnosticsCaptureDir: String = "captures"

  override fun send(packet: Any): ChannelFuture {
    sent.add(packet)
    return channel.newSucceededFuture()
  }

  override fun close(reason: () -> String) {}

  override fun onPhase(phase: SessionPhase, listener: () -> Unit) {}
}

private class CreateCharacterSessionAttributes : SessionAttributes {
  private val values = mutableMapOf<SessionAttribute<*>, Any>()

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> get(key: SessionAttribute<T>): T? = values[key] as T?

  override fun <T : Any> set(key: SessionAttribute<T>, value: T) {
    values[key] = value
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> remove(key: SessionAttribute<T>): T? = values.remove(key) as T?

  override fun <T : Any> getOrPut(key: SessionAttribute<T>, default: () -> T): T =
      get(key) ?: default().also { set(key, it) }

  override fun contains(key: SessionAttribute<*>): Boolean = values.containsKey(key)
}
