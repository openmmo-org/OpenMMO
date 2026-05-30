package de.fiereu.openmmo.server.game

import de.fiereu.openmmo.server.game.config.ConfigLoader
import de.fiereu.openmmo.server.game.di.DaggerGameServerComponent

fun main() {
  val config = ConfigLoader.load()
  val component = DaggerGameServerComponent.factory().create(config)
  component.server().start()
}
