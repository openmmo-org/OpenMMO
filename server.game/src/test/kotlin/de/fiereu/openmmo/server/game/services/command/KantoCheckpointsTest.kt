package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private val LAB_CHECKPOINTS = setOf("starter-choice", "rival-battle", "pokedex")

class KantoCheckpointsTest :
    FunSpec({
      test("every checkpoint stands on a walkable tile of a map we have") {
        val maps = MapManager()

        KANTO_CHECKPOINTS.forEach { checkpoint ->
          val map =
              maps.getMap(
                  checkpoint.region.wireValue,
                  checkpoint.bankId.toByte(),
                  checkpoint.mapId.toByte(),
              )
          withClue(checkpoint.name) {
            map shouldNotBe null
            val tile = map!!.tileAt(checkpoint.x, checkpoint.y)
            tile shouldNotBe null
            tile!!.blocksMovement() shouldBe false
          }
        }
      }

      test("names are unique and lower case, so /story can match on them") {
        KANTO_CHECKPOINTS.map { it.name } shouldBe KANTO_CHECKPOINTS.map { it.name }.distinct()
        KANTO_CHECKPOINTS.all { it.name == it.name.lowercase() } shouldBe true
      }

      test("lab checkpoints clear the flag that would hide Oak") {
        KANTO_CHECKPOINTS.filter { it.name in LAB_CHECKPOINTS }
            .forEach {
              withClue(it.name) {
                it.storyFlags.contains(KantoFlags.FLAG_HIDE_OAK_IN_HIS_LAB) shouldBe false
              }
            }
      }

      test("a checkpoint the rival battle depends on carries a party matching its starter var") {
        val rivalBattle = KANTO_CHECKPOINTS.single { it.name == "rival-battle" }

        rivalBattle.storyVars[KantoVars.VAR_STARTER_MON] shouldBe 0
        rivalBattle.party.single().dexId shouldBe 1
      }

      test("every checkpoint past the starter carries the party its scenes need") {
        val needParty = setOf("rival-battle", "oaks-parcel", "pokedex")

        KANTO_CHECKPOINTS.filter { it.name in needParty }
            .forEach { withClue(it.name) { it.party.isEmpty() shouldBe false } }
      }
    })
