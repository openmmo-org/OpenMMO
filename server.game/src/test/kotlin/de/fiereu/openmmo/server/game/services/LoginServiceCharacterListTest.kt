package de.fiereu.openmmo.server.game.services

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionAttribute
import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.CharactersListPacket
import de.fiereu.openmmo.net.game.packets.CharactersListPacketCodec
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.SocialStore
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.embedded.EmbeddedChannel
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.time.Instant

/**
 * Regression test for the 2026-07-09 relogin hang: a character created with real (non-empty)
 * create-cosmetics bytes corrupted every field after its skin entry in the character list, so any
 * account with such a character stuck the client on "Loading..." for every session after the one
 * that created it. Root-cause evidence: server.game/captures/server-s2c-capture.log's session-2
 * CharactersListPacket decoded (via our own codec) with the second entry's partySize read as 0 and
 * ~186 trailing bytes unread -- DefaultSkinSetCodec's write() special-cases opaque/raw cosmetics
 * bytes (no length marker) but its read() always expects the structured mask+per-slot layout.
 */
class LoginServiceCharacterListTest :
    FunSpec({
      test("character list decodes cleanly when a character has real create cosmetics") {
        val characterStore = CharacterStore()
        // Mirrors a real captured C2S 0x03 create-cosmetics tail -- any non-empty bytes reproduce
        // the bug, since the point is DefaultSkinSetCodec's write/read asymmetry, not this value.
        characterStore.createCharacter(
            userId = 1,
            name = "retestbagfix",
            gender = 0,
            cosmetics =
                byteArrayOf(
                    0x01,
                    0x02,
                    0x02,
                    0x4c,
                    0x03,
                    0x0b,
                    0x34,
                    0x0a,
                    0x00,
                    0x03,
                    0x40,
                    0x00,
                    0x74,
                    0x02,
                    0xa0.toByte()),
        )
        val mapManager = MapManager()
        val sessionRegistry = SessionRegistry()
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
        val session = CharacterListSessionContext()
        session.attributes[PLAYER_STATE] = PlayerState(userId = 1)

        service.onCharacterRequest(PacketEvent(RequestCharactersPacket(), session))

        val sent = session.sent.filterIsInstance<CharactersListPacket>().single()
        val bytes = CharactersListPacketCodec.encodeToBytes(sent)
        val decoded = CharactersListPacketCodec.decodeBytes(bytes)

        decoded.characters.map { it.characterInfo.name } shouldBe listOf("Test", "retestbagfix")
        decoded.characters.map { it.pokemon.size } shouldBe listOf(1, 1)
      }
    })

private class CharacterListSessionContext : SessionContext {
  val sent = mutableListOf<Any>()

  override val side: Side = Side.SERVER
  override val channel: Channel = EmbeddedChannel()
  override val remoteAddress: SocketAddress = InetSocketAddress("127.0.0.1", 0)
  override val phase: SessionPhase = SessionPhase.ESTABLISHED
  override val handshakeCompletedAt: Instant? = Instant.now()
  override val attributes: SessionAttributes = CharacterListSessionAttributes()
  override val diagnosticsCaptureEnabled: Boolean = false
  override val diagnosticsCaptureDir: String = "captures"

  override fun send(packet: Any): ChannelFuture {
    sent.add(packet)
    return channel.newSucceededFuture()
  }

  override fun close(reason: () -> String) {}

  override fun onPhase(phase: SessionPhase, listener: () -> Unit) {}
}

private class CharacterListSessionAttributes : SessionAttributes {
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
