package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.config.GameServerConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val DEV_USER_ID = 1

/**
 * One character per region for local testing, built through [CharacterStore.createCharacter] so
 * their start state cannot drift from what a real new character gets.
 */
@Singleton
class DevCharacterSeeder
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val config: GameServerConfig,
) {

  suspend fun seed() {
    if (!config.db.seedDev) return
    val existing = characterStore.getCharactersByUser(DEV_USER_ID)
    for (region in Region.entries) {
      val name = region.name.lowercase().replaceFirstChar { it.uppercase() }
      if (existing.any { it.info.name == name }) continue
      val created = characterStore.createCharacter(DEV_USER_ID, name, CharacterGender.MALE, region)
      characterStore.updateCharacter(
          created.info.copy(
              permissions = created.info.permissions or CharacterPermissions.DEVELOPER))
      characterStore.flushCharacterAsync(created.info.id)
      log.info { "Seeded dev character '$name' for $region" }
    }
  }
}
