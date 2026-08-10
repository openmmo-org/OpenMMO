package de.fiereu.openmmo.server.login.config

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.typesafe.config.ConfigFactory
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ConfigLoaderTest :
    FunSpec({
      test("the shipped config carries the loopback endpoint") {
        val endpoint = ConfigLoader.load().gameServer

        endpoint.ipv4Address shouldBe IPv4Address.of("127.0.0.1")
        endpoint.port shouldBe 7777
        endpoint.localAddress shouldBe IPAddress.of("127.0.0.1")
        endpoint.localHostname shouldBe "localhost"
      }

      test("a public node address does not drag the local one off loopback") {
        val config =
            ConfigFactory.parseString("gameServer.ipv4Address = \"203.0.113.5\"")
                .withFallback(ConfigFactory.parseResources("application.conf"))
                .resolve()

        config.getString("gameServer.localAddress") shouldBe "127.0.0.1"
        config.getString("gameServer.localHostname") shouldBe "localhost"
      }

      test("no admin account is configured out of the box") {
        ConfigLoader.load().admin shouldBe null
      }

      test("the local address is still settable for anyone testing what the client does with it") {
        val config =
            ConfigFactory.parseString(
                    """
                    gameServer.ipv4Address = "203.0.113.5"
                    gameServer.localAddress = "10.0.0.2"
                    """)
                .withFallback(ConfigFactory.parseResources("application.conf"))
                .resolve()

        config.getString("gameServer.localAddress") shouldBe "10.0.0.2"
      }
    })
