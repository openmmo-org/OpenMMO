package de.fiereu.openmmo.server.login.di

import dagger.BindsInstance
import dagger.Component
import de.fiereu.openmmo.server.login.LoginServer
import de.fiereu.openmmo.server.login.config.LoginServerConfig
import de.fiereu.openmmo.server.login.db.DatabaseBootstrap
import de.fiereu.openmmo.server.login.handler.LoginAppHandler
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginServerModule::class])
interface LoginServerComponent {

  fun server(): LoginServer

  fun handlerProvider(): Provider<LoginAppHandler>

  fun databaseBootstrap(): DatabaseBootstrap

  @Component.Factory
  fun interface Factory {
    fun create(@BindsInstance config: LoginServerConfig): LoginServerComponent
  }
}
