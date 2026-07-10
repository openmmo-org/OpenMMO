package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.openmmo.net.game.packets.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.BattleOpenPacket
import de.fiereu.openmmo.server.game.domain.Gender
import de.fiereu.openmmo.server.game.domain.Nature
import de.fiereu.openmmo.server.game.domain.OwnedPokemon
import de.fiereu.openmmo.server.game.domain.PokemonMoveSlot
import de.fiereu.openmmo.server.game.domain.StatBlock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.embedded.EmbeddedChannel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.SocketAddress
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.Collections
import kotlin.concurrent.thread
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class BattleServiceTest :
    FunSpec({
      // Fake sidecar that records received methods and returns canned frames.
      fun startFakeSidecar(received: MutableList<String>): ServerSocket {
        val server = ServerSocket(0)
        thread(isDaemon = true) {
          while (!server.isClosed) {
            val conn =
                try {
                  server.accept()
                } catch (_: Exception) {
                  break
                }
            thread(isDaemon = true) {
              val reader =
                  BufferedReader(InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))
              val out = conn.getOutputStream()
              val json = Json { ignoreUnknownKeys = true }
              while (true) {
                val line = reader.readLine() ?: break
                if (line.isBlank()) continue
                val req = json.parseToJsonElement(line).jsonObject
                val id = req["id"]!!.jsonPrimitive.content
                val method = req["method"]!!.jsonPrimitive.content
                received.add(method)
                val result =
                    when (method) {
                      "create" ->
                          """{"battleId":"b1","turn":{"battleId":"b1","log":[],"finished":false,"sides":[{"side":"player","active":[{"hpCurrent":30,"hpMax":41}],"benchCount":0},{"side":"wild","active":[{"hpCurrent":18,"hpMax":18}],"benchCount":0}]}}"""
                      "catch" ->
                          """{"catch":{"success":true,"shakes":4,"message":"Gotcha!"},"turn":{"battleId":"b1","log":[],"finished":true,"winner":"player"}}"""
                      "run" ->
                          """{"turn":{"battleId":"b1","log":[],"finished":true,"winner":"draw"}}"""
                      else -> """{"ok":true}"""
                    }
                out.write(
                    ("""{"id":"$id","ok":true,"result":$result}""" + "\n").toByteArray(
                        StandardCharsets.UTF_8),
                )
                out.flush()
              }
            }
          }
        }
        return server
      }

      fun ownedMon(speciesId: Int = 25, level: Int = 5) =
          OwnedPokemon(
              uid = "u$speciesId",
              speciesId = speciesId,
              level = level,
              exp = 0,
              nature = Nature.HARDY,
              ability = "static",
              gender = Gender.GENDERLESS,
              shiny = false,
              ivs = StatBlock(31, 31, 31, 31, 31, 31),
              evs = StatBlock.ZERO,
              moves = listOf(PokemonMoveSlot(moveId = 33, ppUp = 0, ppCurrent = 35)),
              friendship = 70,
              otWallet = "wallet",
              otName = "Tester",
              pid = 0u,
              metLevel = level,
              metLocation = "grass",
          )

      test("startWildBattle sends the validated S2C 0x30 battle-open") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val service = BattleService(client, CaughtPokemonSink { _, _ -> })
        val sent = mutableListOf<Any>()
        val session = fakeSession(sent)
        try {
          service.startWildBattle(
              session, characterId = 1L, listOf(ownedMon(25, 7)), ownedMon(504, 4))
          received shouldContain "create"
          val open = sent.filterIsInstance<BattleOpenPacket>().single()
          open.playerSpecies shouldBe 25
          open.playerLevel shouldBe 7
          open.playerCurrentHp shouldBe 30 // from sidecar sides
          open.wildSpecies shouldBe 504
          open.wildLevel shouldBe 4
          open.wildCurrentHp shouldBe 18
        } finally {
          client.close()
          server.close()
        }
      }

      test("startWildBattle stamps session entity ids into the 0x30 open and keys events to them") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val service = BattleService(client, CaughtPokemonSink { _, _ -> })
        val sent = mutableListOf<Any>()
        val session = fakeSession(sent)
        val characterId = 0x79000L // a character entity id: (7 shl 16) or 0x9000
        try {
          service.startWildBattle(
              session, characterId = characterId, listOf(ownedMon(25, 7)), ownedMon(504, 4))
          val open = sent.filterIsInstance<BattleOpenPacket>().single()

          // The player-character entity is the live session's character, not the template's.
          open.playerCharEntityId shouldBe characterId
          // The two mon entities are freshly allocated, distinct, and in the battle-mon space.
          open.playerMonEntityId shouldNotBe open.wildMonEntityId
          (open.playerMonEntityId and 0xFFFFL) shouldBe 0xC000L
          (open.wildMonEntityId and 0xFFFFL) shouldBe 0xC000L

          // The S2C event stream is keyed to the SAME ids the client learned from the open.
          val sides =
              Json.parseToJsonElement(
                  """[{"side":"player","active":[{"hpCurrent":30,"hpMax":41}],"benchCount":0},""" +
                      """{"side":"wild","active":[{"hpCurrent":18,"hpMax":18}],"benchCount":0}]""",
              )
          val turn = TurnResult(battleId = "b1", sides = sides, finished = false)
          val deltas = service.hpDeltas(session, turn)
          deltas.map { it.entityId } shouldBe listOf(open.playerMonEntityId, open.wildMonEntityId)
          deltas.map { it.currentHp } shouldBe listOf(30.toShort(), 18.toShort())
        } finally {
          client.close()
          server.close()
        }
      }

      test("each battle allocates its own fresh mon entity ids") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val service = BattleService(client, CaughtPokemonSink { _, _ -> })
        try {
          val sentA = mutableListOf<Any>()
          val sentB = mutableListOf<Any>()
          service.startWildBattle(
              fakeSession(sentA), characterId = 1L, listOf(ownedMon(25, 7)), ownedMon(504, 4))
          service.startWildBattle(
              fakeSession(sentB), characterId = 2L, listOf(ownedMon(25, 7)), ownedMon(506, 3))
          val openA = sentA.filterIsInstance<BattleOpenPacket>().single()
          val openB = sentB.filterIsInstance<BattleOpenPacket>().single()
          openA.wildMonEntityId shouldNotBe openB.wildMonEntityId
          openA.playerMonEntityId shouldNotBe openB.playerMonEntityId
        } finally {
          client.close()
          server.close()
        }
      }

      test("buildWildOpen maps species/level from domain mons and HP from the sidecar sides") {
        val service = BattleService(BattleSessionClient(port = 1), CaughtPokemonSink { _, _ -> })
        val sides =
            Json.parseToJsonElement(
                """[{"side":"player","active":[{"hpCurrent":12,"hpMax":40}],"benchCount":0},""" +
                    """{"side":"wild","active":[{"hpCurrent":7,"hpMax":22}],"benchCount":0}]""",
            )
        val turn = TurnResult(battleId = "b1", sides = sides, finished = false)
        val open = service.buildWildOpen(ownedMon(25, 9), ownedMon(504, 3), turn)
        open.playerSpecies shouldBe 25
        open.playerLevel shouldBe 9
        open.playerCurrentHp shouldBe 12
        open.wildSpecies shouldBe 504
        open.wildCurrentHp shouldBe 7
        open.wildMaxHp shouldBe 22
      }

      test("a successful catch persists the generated wild mon and frees the sidecar session") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val persisted = mutableListOf<Pair<Long, OwnedPokemon>>()
        val service = BattleService(client, CaughtPokemonSink { c, m -> persisted.add(c to m) })
        val session = fakeSession()
        val wild = ownedMon(504, 4)
        try {
          service.startWildBattle(session, characterId = 77L, listOf(ownedMon(25, 7)), wild)
          val result = service.onThrowBall(session, "master-ball")
          result!!.catchOutcome.success shouldBe true
          persisted shouldContain (77L to wild) // ORIGINAL domain mon, not a wire round-trip
          received shouldContain "end"
          received.indexOf("end") shouldBeGreaterThan received.indexOf("catch")
        } finally {
          client.close()
          server.close()
        }
      }

      test("onBattleAction routes a ball throw (actionKind=1) to a catch + persist") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val persisted = mutableListOf<Pair<Long, OwnedPokemon>>()
        val service = BattleService(client, CaughtPokemonSink { c, m -> persisted.add(c to m) })
        val session = fakeSession()
        val wild = ownedMon(504, 4)
        try {
          service.startWildBattle(session, characterId = 5L, listOf(ownedMon(25, 7)), wild)
          // actionKindId = 1 (use item / ball throw)
          val ballThrow = BattleActionSelectPacket(0, 1, 0, 0, 0L, 0.toByte())
          service.onBattleAction(session, ballThrow)
          received shouldContain "catch"
          persisted shouldContain (5L to wild)
        } finally {
          client.close()
          server.close()
        }
      }

      test("fleeing frees the sidecar session (end after run)") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val service = BattleService(client, CaughtPokemonSink { _, _ -> })
        val session = fakeSession()
        try {
          service.startWildBattle(
              session, characterId = 1L, listOf(ownedMon(25, 7)), ownedMon(504, 4))
          service.onLeave(session)
          received shouldContain "end"
          received.indexOf("end") shouldBeGreaterThan received.indexOf("run")
        } finally {
          client.close()
          server.close()
        }
      }
    })

/**
 * Minimal SessionContext for battle tests: used as the battle-map key and records packets passed to
 * [send] (backed by an EmbeddedChannel so a real ChannelFuture is returned).
 */
private fun fakeSession(sent: MutableList<Any> = mutableListOf()): SessionContext {
  val embedded = EmbeddedChannel()
  return object : SessionContext {
    override val side: Side
      get() = throw NotImplementedError()

    override val channel: Channel
      get() = embedded

    override val remoteAddress: SocketAddress
      get() = throw NotImplementedError()

    override val phase: SessionPhase
      get() = throw NotImplementedError()

    override val handshakeCompletedAt: Instant?
      get() = null

    override val attributes: SessionAttributes
      get() = throw NotImplementedError()

    override val diagnosticsCaptureEnabled: Boolean = false
    override val diagnosticsCaptureDir: String = "captures"

    override fun send(packet: Any): ChannelFuture {
      sent.add(packet)
      return embedded.newSucceededFuture()
    }

    override fun close(reason: () -> String) {}

    override fun onPhase(phase: SessionPhase, listener: () -> Unit) {}
  }
}
