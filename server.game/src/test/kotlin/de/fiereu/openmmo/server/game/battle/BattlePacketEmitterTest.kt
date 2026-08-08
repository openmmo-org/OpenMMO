package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEventBody
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.typechart.TypeChart
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

private const val BULBASAUR = 1
private const val TACKLE: Short = 33

private val species = SpeciesRegistry()

private fun mon(entityId: Long): BattleMonState {
  val def = species.get(BULBASAUR)!!
  val source =
      Pokemon(
          id = entityId,
          ownerId = 2L,
          container = PokemonContainer.PARTY,
          containerSlot = 0,
          dexId = BULBASAUR,
          seed = 0,
          ot = "Ash",
          nickname = "",
          level = 10,
          hp = Short.MAX_VALUE,
          xp = 0,
          eVs = EVs(),
          iVs = IVs(),
          moves = listOf(PokemonMove(TACKLE, 35)),
          isShiny = false,
          hasHiddenAbility = false,
          isAlpha = false,
          isSecret = false,
          isFatefulEncounter = false,
          isRaidEncounter = false,
          caughtAt = LocalDateTime.now(),
      )
  return BattleMonState(entityId, def, 0, source, StatCalculator.computeAll(def, source))
}

class BattlePacketEmitterTest :
    FunSpec({
      // Capture 0x33 e6169246 is a missed Rock Tomb: one target, move word 1, no events under it.
      test("a miss sends the target on its own with no events") {
        val session = FakeSession(1L)
        val interest = InterestManager()
        val emitter = BattlePacketEmitter(interest)
        val attacker = mon(10L)
        val defender = mon(20L)
        val battle =
            BattleInstance(1L, 1L, session, listOf(attacker), listOf(defender), BattleRng())
        interest.join(session, battle.key)

        emitter.sendEvents(
            battle,
            listOf(
                BattleEvent.MoveUsed(attacker.entityId, TACKLE, 0, 34),
                BattleEvent.MoveMissed(attacker.entityId, TACKLE),
            ),
        )

        val target =
            session.sent.filterIsInstance<BattleEntityMoveEventPacket>().single().targets.single()
        target.entityId shouldBe defender.entityId
        target.targetMove shouldBe 1.toShort()
        target.subEvents.shouldBeEmpty()
      }

      // Capture 0x33 bf4d168c is a super effective Rock Tomb, outcome word 0x0220.
      test("effectiveness rides in the outcome word next to the hit bit") {
        val session = FakeSession(1L)
        val interest = InterestManager()
        val emitter = BattlePacketEmitter(interest)
        val attacker = mon(10L)
        val defender = mon(20L)
        val battle =
            BattleInstance(1L, 1L, session, listOf(attacker), listOf(defender), BattleRng())
        interest.join(session, battle.key)

        emitter.sendEvents(
            battle,
            listOf(
                BattleEvent.MoveUsed(attacker.entityId, TACKLE, 0, 34),
                BattleEvent.DamageDealt(defender.entityId, 21, false, TypeChart.NEUTRAL * 2),
            ),
        )

        val target =
            session.sent.filterIsInstance<BattleEntityMoveEventPacket>().single().targets.single()
        target.targetMove shouldBe 0x0220.toShort()
        target.subEvents.single().body shouldBe BattleEventBody.HpUpdate(21)
      }

      // Capture 0x33 bf4d168c carries both, an hp update and a speed drop, under one target.
      test("a move's secondary stat drop rides under the damage it dealt") {
        val session = FakeSession(1L)
        val interest = InterestManager()
        val emitter = BattlePacketEmitter(interest)
        val attacker = mon(10L)
        val defender = mon(20L)
        val battle =
            BattleInstance(1L, 1L, session, listOf(attacker), listOf(defender), BattleRng())
        interest.join(session, battle.key)

        emitter.sendEvents(
            battle,
            listOf(
                BattleEvent.MoveUsed(attacker.entityId, TACKLE, 0, 34),
                BattleEvent.DamageDealt(defender.entityId, 21, false, TypeChart.NEUTRAL * 2),
                BattleEvent.StageChanged(defender.entityId, BattleStat.SPEED, -1, 0, -1, false),
            ),
        )

        val target =
            session.sent.filterIsInstance<BattleEntityMoveEventPacket>().single().targets.single()
        target.targetMove shouldBe 0x0220.toShort()
        target.subEvents.map { it.body } shouldBe
            listOf(BattleEventBody.HpUpdate(21), BattleEventBody.StatChange(3, -1))
      }

      // Capture 0x33 6c1b3e8a is a Growl: outcome word 0, one stat event under it.
      test("a stat change rides under an outcome word of its own") {
        val session = FakeSession(1L)
        val interest = InterestManager()
        val emitter = BattlePacketEmitter(interest)
        val attacker = mon(10L)
        val defender = mon(20L)
        val battle =
            BattleInstance(1L, 1L, session, listOf(attacker), listOf(defender), BattleRng())
        interest.join(session, battle.key)

        emitter.sendEvents(
            battle,
            listOf(
                BattleEvent.MoveUsed(attacker.entityId, TACKLE, 0, 34),
                BattleEvent.StageChanged(defender.entityId, BattleStat.ACCURACY, -1, 0, -1, false),
            ),
        )

        val target =
            session.sent.filterIsInstance<BattleEntityMoveEventPacket>().single().targets.single()
        target.targetMove shouldBe 0.toShort()
        target.subEvents.single().body shouldBe BattleEventBody.StatChange(6, -1)
      }
    })
