package de.fiereu.openmmo.server.game.battle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Verifies BattleSessionClient's wire behavior against an in-JVM fake of the Node sidecar (one JSON
 * line in, one canned frame out) — no Node process required. Proves framing, (de)serialization,
 * the @SerialName("catch") mapping, and that the wild mon Kotlin passed in is echoed back as
 * caughtPokemon.
 */
class BattleSessionClientTest :
    FunSpec({
      fun startFakeSidecar(): ServerSocket {
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
                val result =
                    when (method) {
                      "create" ->
                          """{"battleId":"b1","turn":{"battleId":"b1","log":["|start"],"finished":false,"request":{"active":[]}}}"""
                      "catch" ->
                          """{"catch":{"success":true,"shakes":4,"caughtPokemon":{"uid":"w1","speciesId":19,"level":5,"nature":"hardy","ability":"guts","ivs":{"hp":1,"atk":1,"def":1,"spa":1,"spd":1,"spe":1},"evs":{"hp":0,"atk":0,"def":0,"spa":0,"spd":0,"spe":0},"moves":[]},"message":"Gotcha!"},"turn":{"battleId":"b1","log":["Gotcha!"],"finished":true,"winner":"player"}}"""
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

      test("create + catch round-trip over the socket") {
        val server = startFakeSidecar()
        val client = BattleSessionClient(host = "127.0.0.1", port = server.localPort)
        try {
          val wild =
              WirePokemon(
                  uid = "w1",
                  speciesId = 19,
                  level = 5,
                  nature = "hardy",
                  ability = "guts",
                  ivs = WireStats(1, 1, 1, 1, 1, 1),
                  evs = WireStats(0, 0, 0, 0, 0, 0),
                  moves = listOf(WireMove(33)),
              )
          val player = wild.copy(uid = "p1", speciesId = 25, ability = "static")

          val created = client.create(listOf(player), listOf(wild))
          created.battleId shouldBe "b1"
          created.turn.finished shouldBe false
          created.turn.request.shouldNotBeNull()

          val caught = client.attemptCatch("b1", "master-ball")
          caught.catchOutcome.success shouldBe true
          caught.catchOutcome.shakes shouldBe 4
          caught.catchOutcome.caughtPokemon.shouldNotBeNull().speciesId shouldBe 19
          caught.turn.finished shouldBe true
          caught.turn.winner shouldBe "player"
        } finally {
          client.close()
          server.close()
        }
      }
    })
