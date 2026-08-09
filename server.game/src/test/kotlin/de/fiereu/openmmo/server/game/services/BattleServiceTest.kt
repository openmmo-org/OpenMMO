package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.EntityMovePpPacket
import de.fiereu.openmmo.net.game.packets.EntityPresencePacket
import de.fiereu.openmmo.net.game.packets.MapLoadedAckPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleBulkStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleQueuedEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSidePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTileMapPacket
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleRewards
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.trainer.TrainerDef
import de.fiereu.openmmo.trainer.TrainerMon
import de.fiereu.openmmo.trainer.TrainerRegistry
import de.fiereu.openmmo.typechart.TypeChart
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

private const val TACKLE: Short = 33
private const val RATTATA = 19

private fun bulbasaur(ownerId: Long, level: Byte, hp: Short): Pokemon =
    Pokemon(
        id = EntityIdService().newMonsterId(),
        ownerId = ownerId,
        container = PokemonContainer.PARTY,
        containerSlot = 0,
        dexId = 1,
        seed = 0,
        ot = "Ash",
        nickname = "",
        level = level,
        hp = hp,
        xp = 0,
        eVs = EVs(),
        iVs = IVs(),
        moves =
            listOf(
                PokemonMove(TACKLE, 35), PokemonMove(0, 0), PokemonMove(0, 0), PokemonMove(0, 0)),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.now(),
    )

private class Fixture(scope: CoroutineScope) {
  val repo = FakeCharacterRepository()
  val store = CharacterStore(repo, EntityIdService(), scope)
  val interestManager = InterestManager()
  val registry = BattleRegistry()
  val service =
      BattleService(
          characterStore = store,
          battles = registry,
          engine = TurnEngine(MoveRegistry(), TypeChart()),
          wildMons =
              WildMonFactory(
                  SpeciesRegistry(), MoveRegistry(), LearnsetRegistry(), EntityIdService()),
          emitter = BattlePacketEmitter(interestManager),
          rewards = BattleRewards(),
          moveLearner = MoveLearner(LearnsetRegistry(), MoveRegistry()),
          interestManager = interestManager,
          speciesRegistry = SpeciesRegistry(),
          moveRegistry = MoveRegistry(),
          trainers = TrainerRegistry(),
      )

  suspend fun playerWithParty(level: Byte = 50, hp: Short = 999): Pair<FakeSession, Long> {
    val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
    store.addPokemon(created.info.id, bulbasaur(created.info.id, level, hp))
    return FakeSession(created.info.id) to created.info.id
  }
}

private fun FakeSession.startBattle(service: BattleService, dexId: Int = 19, level: Int = 2) {
  service.startWildBattle(this, dexId, level)
}

private suspend fun FakeSession.act(
    service: BattleService,
    action: BattleAction,
    value: Short = 0,
) {
  service.onBattleAction(PacketEvent(BattleActionSelectPacket(0, action, value, 0L, 0), this))
}

private fun FakeSession.finishBattleTransition(service: BattleService) {
  service.onClientReady(PacketEvent(MapLoadedAckPacket(), this))
}

@OptIn(ExperimentalCoroutinesApi::class)
class BattleServiceTest :
    FunSpec({
      test("the start sequence arrives in the proven order") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, _) = fx.playerWithParty()

          session.startBattle(fx.service)

          val types = session.sent.map { it::class }
          types shouldBe
              listOf(
                  EntityPresencePacket::class,
                  BattleSidePacket::class,
                  BattleFieldStatePacket::class,
                  BattleTileMapPacket::class,
                  BattleQueuedEventPacket::class,
              )
          // Bulbasaur base hp 45 at level 50 with empty IVs and EVs.
          val field = session.sent.filterIsInstance<BattleFieldStatePacket>().single()
          field.playerParty.single().maxHp shouldBe 105.toShort()
          field.opponentParty.single().level shouldBe 2.toByte()
          field.opponentParty.single().currentHp shouldBe field.opponentParty.single().maxHp
        }
      }

      test("an unknown species aborts with a chat notice") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, charId) = fx.playerWithParty()

          session.startBattle(fx.service, dexId = 9999, level = 5)

          fx.registry.byChar(charId).shouldBeNull()
          session.sent.none { it is BattleFieldStatePacket }.shouldBeTrue()
        }
      }

      test("a session joined to the battle key receives the broadcasts") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, charId) = fx.playerWithParty()
          session.startBattle(fx.service)
          val battle = fx.registry.byChar(charId).shouldNotBeNull()

          val spectator = FakeSession(200L)
          fx.interestManager.join(spectator, battle.key)

          session.act(fx.service, BattleAction.MOVE, TACKLE)

          spectator.sent.shouldNotBeEmpty()
        }
      }

      test("a move resolves the turn and re-prompts while both sides stand") {
        runTest {
          val fx = Fixture(backgroundScope)
          // A bulky matchup, so neither side can faint inside one turn.
          val (session, charId) = fx.playerWithParty(level = 30, hp = 999)
          session.startBattle(fx.service, dexId = 143, level = 10)
          session.sent.clear()

          session.act(fx.service, BattleAction.MOVE, TACKLE)

          fx.registry.byChar(charId).shouldNotBeNull()
          session.sent.filterIsInstance<EntityMovePpPacket>().shouldNotBeEmpty()
          session.sent.filterIsInstance<BattleQueuedEventPacket>().shouldNotBeEmpty()
        }
      }

      test("fleeing waits for the overworld before clearing the battle") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, charId) = fx.playerWithParty()
          session.startBattle(fx.service)

          session.act(fx.service, BattleAction.RUN)

          fx.registry.byChar(charId).shouldNotBeNull()
          fx.service.onClientReady(PacketEvent(MapLoadedAckPacket(byteArrayOf(1, 2, 3)), session))
          fx.registry.byChar(charId).shouldNotBeNull()
          session.finishBattleTransition(fx.service)
          fx.registry.byChar(charId).shouldBeNull()
          session.sent.filterIsInstance<BattleBulkStatePacket>().shouldNotBeEmpty()
          session.startBattle(fx.service)
          fx.registry.byChar(charId).shouldNotBeNull()
        }
      }

      test("victory persists the party hp and pp through the store") {
        runTest {
          val fx = Fixture(this)
          val (session, charId) = fx.playerWithParty()
          session.startBattle(fx.service)

          var rounds = 0
          while (fx.registry.byChar(charId)?.pendingResult == null && rounds < 10) {
            session.act(fx.service, BattleAction.MOVE, TACKLE)
            rounds += 1
          }
          fx.registry.byChar(charId).shouldNotBeNull()
          session.finishBattleTransition(fx.service)
          fx.registry.byChar(charId).shouldBeNull()
          advanceUntilIdle()

          // The level 2 Rattata yields its base experience scaled by level over seven.
          session.sent
              .filterIsInstance<BattleEntityDeltaPacket>()
              .any { it.experience != null }
              .shouldBeTrue()
          val saved = fx.repo.saved[charId].shouldNotBeNull()
          saved.pokemon.single().moves[0].pp shouldBe (35 - rounds).toByte()
          saved.pokemon.single().xp shouldBe 57 * 2 / 7
        }
      }

      test("a trainer sends out its next monster instead of losing when one faints") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, charId) = fx.playerWithParty()
          val terry =
              TrainerDef(
                  id = 1,
                  name = "TERRY",
                  trainerClass = 0,
                  doubleBattle = false,
                  prizeRate = 5,
                  party =
                      listOf(
                          TrainerMon(RATTATA, 2, 0, 0, emptyList()),
                          TrainerMon(RATTATA, 2, 0, 0, emptyList()),
                      ),
              )
          // The battle never ends in this test, so its await must not hold the test scope open.
          backgroundScope.launch { fx.service.startTrainerBattle(session, terry) }
          runCurrent()

          val battle = fx.registry.byChar(charId).shouldNotBeNull()
          battle.opponent.size shouldBe 2

          val field = session.sent.filterIsInstance<BattleFieldStatePacket>().single()
          field.opponentParty[0].revealed.shouldBeTrue()
          field.opponentParty[1].revealed shouldBe false

          var rounds = 0
          while (battle.opponentSlot == 0 && rounds < 10) {
            session.act(fx.service, BattleAction.MOVE, TACKLE)
            rounds += 1
          }

          battle.opponentSlot shouldBe 1
          battle.opponent[0].fainted.shouldBeTrue()
          battle.pendingResult.shouldBeNull()
          session.sent.filterIsInstance<BattleSwitchInPacket>().last().side shouldBe 1.toByte()

          // The lead is paid for as it faints, not held back until the whole team is beaten.
          val xpBefore = battle.activeMon().source.xp
          xpBefore shouldBeGreaterThan 0
          session.sent
              .filterIsInstance<BattleEntityDeltaPacket>()
              .any { it.experience != null }
              .shouldBeTrue()

          rounds = 0
          while (battle.pendingResult == null && rounds < 10) {
            session.act(fx.service, BattleAction.MOVE, TACKLE)
            rounds += 1
          }

          // The second monster pays on top of the first, rather than replacing it.
          battle.activeMon().source.xp shouldBeGreaterThan xpBefore
        }
      }

      test("a disconnect persists and removes the battle") {
        runTest {
          val fx = Fixture(this)
          val (session, charId) = fx.playerWithParty()
          session.startBattle(fx.service)
          session.act(fx.service, BattleAction.MOVE, TACKLE)

          fx.service.onDisconnect(session)

          fx.registry.byChar(charId).shouldBeNull()
          advanceUntilIdle()
          fx.repo.saved[charId].shouldNotBeNull()
        }
      }
    })
