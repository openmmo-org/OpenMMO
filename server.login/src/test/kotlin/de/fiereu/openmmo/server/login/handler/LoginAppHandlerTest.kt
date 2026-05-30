package de.fiereu.openmmo.server.login.handler

import de.fiereu.openmmo.net.login.packets.JoinGameServerPacket
import de.fiereu.openmmo.net.login.packets.LoginRequestPacket
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacket
import de.fiereu.openmmo.server.login.config.LoginServerConfig
import de.fiereu.openmmo.server.login.di.DaggerLoginServerComponent
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LoginAppHandlerTest :
    FunSpec({
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
    })
