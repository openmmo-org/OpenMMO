package de.fiereu.openmmo.server.game.di

import dagger.BindsInstance
import dagger.Component
import de.fiereu.openmmo.server.game.GameServer
import de.fiereu.openmmo.server.game.config.GameServerConfig
import de.fiereu.openmmo.server.game.handler.GameAppHandler
import de.fiereu.openmmo.server.game.session.SessionRegistry
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Component(modules = [GameServerModule::class])
interface GameServerComponent {

  fun server(): GameServer

  fun handlerProvider(): Provider<GameAppHandler>

  fun sessionRegistry(): SessionRegistry

  @Component.Factory
  fun interface Factory {
    fun create(@BindsInstance config: GameServerConfig): GameServerComponent
  }
}
