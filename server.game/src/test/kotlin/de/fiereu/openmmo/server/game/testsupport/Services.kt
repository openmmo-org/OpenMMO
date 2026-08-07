package de.fiereu.openmmo.server.game.testsupport

import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleRewards
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.script.ScriptRegistry
import de.fiereu.openmmo.server.game.script.ScriptRunner
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.EncounterService
import de.fiereu.openmmo.server.game.services.MapLoadService
import de.fiereu.openmmo.server.game.services.MapScriptService
import de.fiereu.openmmo.server.game.services.MovementService
import de.fiereu.openmmo.server.game.services.NpcService
import de.fiereu.openmmo.server.game.services.PresenceService
import de.fiereu.openmmo.server.game.services.ScriptMovementService
import de.fiereu.openmmo.server.game.services.ScriptWarpService
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.services.WarpService
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.server.game.world.interest.PassThroughInterestPolicy
import de.fiereu.openmmo.typechart.TypeChart

/**
 * A real [MovementService] with every collaborator built for real, since none of them are worth
 * faking. Pass a [mapManager] that already holds the maps the test walks on.
 */
fun movementService(
    store: CharacterStore,
    mapManager: MapManager = MapManager(),
): MovementService {
  val mapLoad = MapLoadService(mapManager)
  val interest = InterestManager()
  val presence = PresenceService(interest, PassThroughInterestPolicy(), mapLoad, store)
  val npcs = NpcService(mapManager, store)
  val story = StoryService(store)
  val species = SpeciesRegistry()
  val moves = MoveRegistry()
  val wildMons = WildMonFactory(species, moves, LearnsetRegistry(), EntityIdService())
  val battles =
      BattleService(
          characterStore = store,
          battles = BattleRegistry(),
          engine = TurnEngine(moves, TypeChart()),
          wildMons = wildMons,
          emitter = BattlePacketEmitter(interest),
          rewards = BattleRewards(),
          moveLearner = MoveLearner(LearnsetRegistry(), moves),
          interestManager = interest,
          speciesRegistry = species,
          moveRegistry = moves,
      )
  val registry = ScriptRegistry(emptyMap())
  val runner =
      ScriptRunner(
          DialogService(),
          story,
          ScriptMovementService(mapManager, npcs, store),
          ScriptWarpService(mapManager, mapLoad, store, presence),
          StoryPlayerService(store, wildMons, species, moves),
          battles,
          store,
      )
  return MovementService(
      WarpService(mapLoad, mapManager, store, presence),
      mapLoad,
      npcs,
      presence,
      mapManager,
      store,
      EncounterService(store, battles),
      MapScriptService(registry, runner, story),
  )
}
