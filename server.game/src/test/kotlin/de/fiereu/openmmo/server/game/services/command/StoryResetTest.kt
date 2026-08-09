package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleRewards
import de.fiereu.openmmo.server.game.battle.BattleRng
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.MapLoadService
import de.fiereu.openmmo.server.game.services.PresenceService
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.services.WarpService
import de.fiereu.openmmo.server.game.services.WorldStateService
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.storage.NewGameStarts
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.server.game.world.interest.PassThroughInterestPolicy
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars
import de.fiereu.openmmo.trainer.TrainerRegistry
import de.fiereu.openmmo.typechart.TypeChart
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class StoryResetTest :
    FunSpec({
      fun storyCommand(store: CharacterStore): StoryCommand {
        val mapManager = MapManager()
        val mapLoad = MapLoadService(mapManager)
        val interest = InterestManager()
        val species = SpeciesRegistry()
        val moves = MoveRegistry()
        return StoryCommand(
            characterStore = store,
            worldStateService = WorldStateService(),
            storyPlayerService =
                StoryPlayerService(
                    store,
                    WildMonFactory(species, moves, LearnsetRegistry(), EntityIdService()),
                    species,
                    moves,
                ),
            warpService =
                WarpService(
                    mapLoad,
                    mapManager,
                    store,
                    PresenceService(interest, PassThroughInterestPolicy(), mapLoad, store),
                ),
            battleService =
                BattleService(
                    characterStore = store,
                    battles = BattleRegistry(),
                    engine = TurnEngine(moves, TypeChart()),
                    wildMons =
                        WildMonFactory(species, moves, LearnsetRegistry(), EntityIdService()),
                    emitter = BattlePacketEmitter(interest),
                    rewards = BattleRewards(),
                    moveLearner = MoveLearner(LearnsetRegistry(), moves),
                    interestManager = interest,
                    speciesRegistry = species,
                    moveRegistry = moves,
                    trainers = TrainerRegistry(),
                ),
        )
      }

      suspend fun developer(store: CharacterStore): Long {
        val created = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO)
        store.updateCharacter(
            created.info.copy(
                permissions = created.info.permissions or CharacterPermissions.DEVELOPER))
        return created.info.id
      }

      test("reset puts the character back on its region's new game story state") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = developer(store)
          store.setStoryFlag(charId, KantoFlags.FLAG_SYS_POKEMON_GET)
          store.setStoryVar(charId, KantoVars.VAR_STARTER_MON, 2)
          store.addItem(charId, itemId = 4, amount = 5)
          val session = FakeSession(characterId = charId)
          val service = ChatCommandService(store, setOf(storyCommand(store)))

          service.tryHandle(session, "/story reset") shouldBe true

          val after = store.getCharacter(charId)!!
          after.storyFlags shouldBe NewGameStarts.forRegion(Region.KANTO, female = false).storyFlags
          after.storyVars shouldBe emptyMap()
          after.items shouldBe emptyMap()
          session.sent.filterIsInstance<ChatMessagePacket>().last().message shouldContain "Reset"
        }
      }

      test("reset empties the party and the pc") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = developer(store)
          val species = SpeciesRegistry()
          val factory =
              WildMonFactory(species, MoveRegistry(), LearnsetRegistry(), EntityIdService())
          store.addPokemon(charId, factory.create(1, 5, BattleRng(seed = 1))!!)
          // A loaded character keeps boxed monsters here, so addPokemon would test the wrong shape.
          store
              .getCharacter(charId)!!
              .pcStorage
              .add(
                  factory
                      .create(4, 5, BattleRng(seed = 2))!!
                      .copy(container = PokemonContainer.PC, containerSlot = 0))
          val session = FakeSession(characterId = charId)
          val service = ChatCommandService(store, setOf(storyCommand(store)))

          service.tryHandle(session, "/story reset") shouldBe true

          val after = store.getCharacter(charId)!!
          after.pokemon shouldBe emptyList()
          after.pcStorage shouldBe emptyList()
        }
      }

      test("reset is refused while a dialog is open") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = developer(store)
          store.setStoryFlag(charId, KantoFlags.FLAG_SYS_POKEMON_GET)
          val session = FakeSession(characterId = charId)
          session.state().inDialog = true
          val service = ChatCommandService(store, setOf(storyCommand(store)))

          service.tryHandle(session, "/story reset") shouldBe true

          store.getCharacter(charId)!!.storyFlags shouldContain KantoFlags.FLAG_SYS_POKEMON_GET
          session.sent.filterIsInstance<ChatMessagePacket>().last().message shouldContain "Finish"
        }
      }
    })
