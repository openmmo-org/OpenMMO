package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
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

/**
 * Regression coverage for the Codex-Review finding: BattleService must free the sidecar session
 * (`end`) after TERMINAL actions (successful catch, flee), not only after move/switch outcomes —
 * otherwise the sidecar leaks BattleSessions.
 */
class BattleServiceTest :
    FunSpec({
      // Fake sidecar that records the methods it receives and returns canned frames.
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
                          """{"battleId":"b1","turn":{"battleId":"b1","log":[],"finished":false}}"""
                      "catch" ->
                          """{"catch":{"success":true,"shakes":4,"caughtPokemon":{"uid":"w1","speciesId":19,"level":5,"nature":"hardy","ability":"guts","ivs":{"hp":1,"atk":1,"def":1,"spa":1,"spd":1,"spe":1},"evs":{"hp":0,"atk":0,"def":0,"spa":0,"spd":0,"spe":0},"moves":[]},"message":"Gotcha!"},"turn":{"battleId":"b1","log":[],"finished":true,"winner":"player"}}"""
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

      fun mon() =
          WirePokemon(
              uid = "p1",
              speciesId = 25,
              level = 5,
              nature = "hardy",
              ability = "static",
              ivs = WireStats(1, 1, 1, 1, 1, 1),
              evs = WireStats(0, 0, 0, 0, 0, 0),
              moves = listOf(WireMove(33)),
          )

      test("a successful catch frees the sidecar session (end after catch)") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val service = BattleService(client)
        val session = fakeSession()
        try {
          service.startWildBattle(session, listOf(mon()), listOf(mon()))
          val result = service.onThrowBall(session, "master-ball")
          result!!.catchOutcome.success shouldBe true
          received shouldContain "end"
          received.indexOf("end") shouldBeGreaterThan received.indexOf("catch")
        } finally {
          client.close()
          server.close()
        }
      }

      test("fleeing frees the sidecar session (end after run)") {
        val received = Collections.synchronizedList(mutableListOf<String>())
        val server = startFakeSidecar(received)
        val client = BattleSessionClient(port = server.localPort)
        val service = BattleService(client)
        val session = fakeSession()
        try {
          service.startWildBattle(session, listOf(mon()), listOf(mon()))
          service.onLeave(session)
          received shouldContain "end"
          received.indexOf("end") shouldBeGreaterThan received.indexOf("run")
        } finally {
          client.close()
          server.close()
        }
      }
    })

/** Minimal SessionContext used only as a battle-map key (no members are accessed). */
private fun fakeSession(): SessionContext =
    object : SessionContext {
      override val side: Side
        get() = throw NotImplementedError()

      override val channel: Channel
        get() = throw NotImplementedError()

      override val remoteAddress: SocketAddress
        get() = throw NotImplementedError()

      override val phase: SessionPhase
        get() = throw NotImplementedError()

      override val handshakeCompletedAt: Instant?
        get() = null

      override val attributes: SessionAttributes
        get() = throw NotImplementedError()

      override fun send(packet: Any): ChannelFuture = throw NotImplementedError()

      override fun close(reason: () -> String) {}

      override fun onPhase(phase: SessionPhase, listener: () -> Unit) {}
    }
