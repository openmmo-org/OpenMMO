package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionAttribute
import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.openmmo.net.game.packets.BagInventoryPacket
import de.fiereu.openmmo.net.game.packets.BagOpenRequestPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.embedded.EmbeddedChannel
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.time.Instant

class BagServiceTest :
    FunSpec({
      test("bag open emits validated deferred-large, main, and small container payloads") {
        val store = CharacterStore()
        val character = store.getCharactersByUser(1).first()
        character.items.clear()
        character.items[17] = 3
        val session = CapturingBagSessionContext()
        session.attributes[PLAYER_STATE] =
            PlayerState(userId = character.info.userId, characterId = character.info.id)

        BagService(store).onBagOpen(PacketEvent(BagOpenRequestPacket(), session))

        session.sent.shouldHaveSize(3)
        val deferredLarge = session.sent[0] as BagInventoryPacket
        deferredLarge.containerId shouldBe BagService.CONTAINER_DEFERRED_LARGE
        deferredLarge.entries.shouldHaveSize(0)

        val main = session.sent[1] as BagInventoryPacket
        main.containerId shouldBe BagService.CONTAINER_MAIN
        main.entries.shouldHaveSize(1)
        main.entries.first().categoryFlags shouldBe 1
        main.entries.first().itemId shouldBe 17
        main.entries.first().quantity shouldBe 3
        main.entries.first().slotDuplicate shouldBe main.entries.first().slot

        val small = session.sent[2] as BagInventoryPacket
        small.containerId shouldBe BagService.CONTAINER_SMALL
        small.entries.shouldHaveSize(1)
        small.entries.first().itemId shouldBe 17
      }

      test("new characters are seeded with small bag items") {
        val store = CharacterStore()
        val character = store.getCharactersByUser(1).first()
        val session = CapturingBagSessionContext()
        session.attributes[PLAYER_STATE] =
            PlayerState(userId = character.info.userId, characterId = character.info.id)

        BagService(store).onBagOpen(PacketEvent(BagOpenRequestPacket(), session))

        val small = session.sent[2] as BagInventoryPacket
        small.containerId shouldBe BagService.CONTAINER_SMALL
        small.entries.shouldHaveSize(3)
        small.entries.map { it.itemId } shouldBe listOf(1025, 1026, 1027)
      }

      test("bag open with empty inventory still emits empty valid containers") {
        // Regression test for the 2026-07-09 bag-render client crash (Fatal Render Error, NPE in
        // the client's item lookup). Root cause: we never sent a response for container 0x0001 at
        // all. Golden capture (2026-07-07-232143-first-manual.log) shows the real server always
        // sends 0x0001 (20 entries there, sent first) before 0x0000 -- the client expects *a*
        // response for every container it renders, even an empty one.
        val store = CharacterStore()
        val character = store.getCharactersByUser(1).first()
        character.items.clear()
        val session = CapturingBagSessionContext()
        session.attributes[PLAYER_STATE] =
            PlayerState(userId = character.info.userId, characterId = character.info.id)

        BagService(store).onBagOpen(PacketEvent(BagOpenRequestPacket(), session))

        session.sent shouldBe
            listOf(
                BagInventoryPacket(BagService.CONTAINER_DEFERRED_LARGE, emptyList()),
                BagInventoryPacket(BagService.CONTAINER_MAIN, emptyList()),
                BagInventoryPacket(BagService.CONTAINER_SMALL, emptyList()),
            )
      }
    })

private class CapturingBagSessionContext : SessionContext {
  val sent = mutableListOf<Any>()

  override val side: Side = Side.SERVER
  override val channel: Channel = EmbeddedChannel()
  override val remoteAddress: SocketAddress = InetSocketAddress("127.0.0.1", 0)
  override val phase: SessionPhase = SessionPhase.ESTABLISHED
  override val handshakeCompletedAt: Instant? = Instant.now()
  override val attributes: SessionAttributes = MapSessionAttributes()
  override val diagnosticsCaptureEnabled: Boolean = false
  override val diagnosticsCaptureDir: String = "captures"

  override fun send(packet: Any): ChannelFuture {
    sent.add(packet)
    return channel.newSucceededFuture()
  }

  override fun close(reason: () -> String) {}

  override fun onPhase(phase: SessionPhase, listener: () -> Unit) {}
}

private class MapSessionAttributes : SessionAttributes {
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
