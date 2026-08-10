package de.fiereu.openmmo.server.login

import de.fiereu.openmmo.server.login.config.ConfigLoader
import de.fiereu.openmmo.server.login.di.DaggerLoginServerComponent
import kotlinx.coroutines.runBlocking

fun main() {
  val config = ConfigLoader.load()
  val component = DaggerLoginServerComponent.factory().create(config)
  component.databaseBootstrap().migrate()
  runBlocking { component.adminAccountBootstrap().ensureAdmin() }
  component.server().start()
}
