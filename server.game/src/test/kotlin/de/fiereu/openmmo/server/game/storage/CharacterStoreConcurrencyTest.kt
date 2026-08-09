package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private const val WRITES = 200

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterStoreConcurrencyTest :
    FunSpec({
      test("concurrent writers do not drop each other's update") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val baseline = store.getCharacter(charId)!!.storyFlags.size
          val pool = Executors.newFixedThreadPool(2)
          val start = CountDownLatch(1)

          // Both threads add their own flags, so a lost update loses a flag and shows in the count.
          val writers =
              (0 until 2).map { thread ->
                pool.submit {
                  start.await()
                  repeat(WRITES) { store.setStoryFlag(charId, "kanto/FLAG_${thread}_$it") }
                }
              }
          start.countDown()
          writers.forEach { it.get() }
          pool.shutdown()

          store.getCharacter(charId)!!.storyFlags.size shouldBe baseline + 2 * WRITES
        }
      }

      test("a write for a character that is not cached is refused rather than lost silently") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          store.addItem(404, itemId = 1, amount = 1) shouldBe false
        }
      }
    })
