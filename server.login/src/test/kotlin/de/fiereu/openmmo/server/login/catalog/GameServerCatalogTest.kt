package de.fiereu.openmmo.server.login.catalog

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.github.maltalex.ineter.base.IPv6Address
import de.fiereu.openmmo.server.login.config.GameServerEndpointConfig
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GameServerCatalogTest :
    FunSpec({
      test("an unconfigured server is only reachable from the machine it runs on") {
        val entry = GameServerCatalog(GameServerEndpointConfig()).find(0x00u)!!

        entry.node.iPv4Address shouldBe IPv4Address.of("127.0.0.1")
        entry.node.iPv6Address shouldBe IPv6Address.of("::1")
        entry.node.port shouldBe 7777u
        entry.localAddress shouldBe IPAddress.of("127.0.0.1")
        entry.localHostname shouldBe "localhost"
      }

      test("the advertised endpoint is what the config says") {
        val endpoint =
            GameServerEndpointConfig(
                ipv4Address = IPv4Address.of("203.0.113.5"),
                ipv6Address = IPv6Address.of("2001:db8::1"),
                port = 7000,
                localHostname = "game.example.test",
            )

        val entry = GameServerCatalog(endpoint).find(0x00u)!!

        entry.node.iPv4Address shouldBe IPv4Address.of("203.0.113.5")
        entry.node.iPv6Address shouldBe IPv6Address.of("2001:db8::1")
        entry.node.port shouldBe 7000u
        entry.localHostname shouldBe "game.example.test"
      }

      test("a public node address leaves the local pair on loopback, as retail sends it") {
        val endpoint = GameServerEndpointConfig(ipv4Address = IPv4Address.of("203.0.113.5"))

        endpoint.localAddress shouldBe IPAddress.of("127.0.0.1")
        endpoint.localHostname shouldBe "localhost"
      }

      test("a port off the wire's range is refused rather than truncated") {
        shouldThrow<IllegalArgumentException> { GameServerEndpointConfig(port = 70000) }
        shouldThrow<IllegalArgumentException> { GameServerEndpointConfig(port = 0) }
      }

      test("a blank hostname is refused") {
        shouldThrow<IllegalArgumentException> { GameServerEndpointConfig(localHostname = " ") }
      }

      test("an unknown server id has no entry") {
        GameServerCatalog(GameServerEndpointConfig()).find(0x7Fu) shouldBe null
      }
    })
