package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.openmmo.net.game.packets.SinglePokemonAddPacket
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.PokemonDepositTarget
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.embedded.EmbeddedChannel
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.time.Instant

class PokemonPartyServiceTest :
    FunSpec({
      test("addCaughtPokemon persists and emits S2C 0x14 single Pokemon add") {
        val store = CharacterStore()
        val registry = SessionRegistry()
        val service = PokemonPartyService(store, registry)
        val character = store.getCharactersByUser(1).first()
        val caught =
            store.getParty(character.info.id).first().copy(uid = "caught-service", speciesId = 504)
        val session = CapturingSessionContext()
        registry.bindCharacter(session, character.info.id)

        service.addCaughtPokemon(character.info.id, caught) shouldBe PokemonDepositTarget.PARTY

        store.getParty(character.info.id).map { it.uid }.contains("caught-service") shouldBe true
        session.sent.shouldHaveSize(1)
        val packet = session.sent.single() as SinglePokemonAddPacket
        packet.pokemon.dexId shouldBe 504
        packet.pokemon.containerSlot shouldBe 1
      }
    })

private class CapturingSessionContext : SessionContext {
  val sent = mutableListOf<Any>()

  override val side: Side = Side.SERVER
  override val channel: Channel = EmbeddedChannel()
  override val remoteAddress: SocketAddress = InetSocketAddress("127.0.0.1", 0)
  override val phase: SessionPhase = SessionPhase.ESTABLISHED
  override val handshakeCompletedAt: Instant? = Instant.now()
  override val attributes: SessionAttributes
    get() = throw NotImplementedError()

  override fun send(packet: Any): ChannelFuture {
    sent.add(packet)
    return channel.newSucceededFuture()
  }

  override fun close(reason: () -> String) {}

  override fun onPhase(phase: SessionPhase, listener: () -> Unit) {}
}
