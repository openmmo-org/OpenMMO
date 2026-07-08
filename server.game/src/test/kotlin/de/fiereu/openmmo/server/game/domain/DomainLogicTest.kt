package de.fiereu.openmmo.server.game.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import kotlin.math.abs

class DomainLogicTest :
    FunSpec({
      test("shiny roller matches TS behavior and exposes deterministic PID path") {
        ShinyRoller.rollShiny(512) { 0.0 }.shouldBeTrue()
        ShinyRoller.rollShiny(512) { 0.5 }.shouldBeFalse()
        ShinyRoller.shinyRateWithCharm(512, 256, true) shouldBe 256
        ShinyRoller.shinyRateWithCharm(512, null, true) shouldBe 512
        ShinyRoller.isPidShiny(0, 0, 0u, threshold = 8).shouldBeTrue()
        ShinyRoller.isPidShiny(1, 2, 0x0003_0008u, threshold = 8).shouldBeFalse()
        ShinyRoller.randomU32 { 0.5 } shouldBe 0x8000_0000u
        ShinyRoller.isPidSelectedShiny(123456789u, 512) shouldBe
            ShinyRoller.isPidSelectedShiny(123456789u, 512)
      }

      test("catch calculator matches TS formulas") {
        val input =
            CatchAttemptInput(
                maxHp = 100,
                currentHp = 50,
                speciesCatchRate = 45,
                ballBonus = 1.0,
                status = CatchStatus.NONE)
        CatchCalculator.modifiedCatchRate(input) shouldBe 30
        CatchCalculator.modifiedCatchRate(input.copy(status = CatchStatus.SLEEP)) shouldBe 60

        val guaranteed = CatchCalculator.attemptCatch(input.copy(rateModifier = 255.0))
        guaranteed.caught.shouldBeTrue()
        guaranteed.shakes shouldBe 4

        val failFirstShake = CatchCalculator.attemptCatch(input.copy(rng = { 1.0 }))
        failFirstShake.caught.shouldBeFalse()
        failFirstShake.shakes shouldBe 0
        abs(CatchCalculator.shakeProbability(30) - 0.5856507).shouldBeLessThan(0.0001)
      }

      test("exp calculator matches public growth curves") {
        ExpCalculator.expForLevel(GrowthRate.MEDIUM_FAST, 5) shouldBe 125
        ExpCalculator.expForLevel(GrowthRate.FAST, 5) shouldBe 100
        ExpCalculator.expForLevel(GrowthRate.SLOW, 5) shouldBe 156
        ExpCalculator.levelForExp(GrowthRate.MEDIUM_FAST, 124) shouldBe 4
        ExpCalculator.expToNextLevel(GrowthRate.MEDIUM_FAST, 4, 34) shouldBe 91
        shouldThrow<IllegalArgumentException> { ExpCalculator.expForLevel(GrowthRate.FAST, 0) }
        ExpCalculator.calculateExpGain(
            ExpGainInput(
                defeatedBaseExp = 64,
                defeatedLevel = 5,
                victorLevel = 5,
                participated = 1,
                trainerBattle = true,
                serverMultiplier = 2.0),
        ) shouldBe 384
      }

      test("evolution checker handles main triggers") {
        val mon =
            sampleMon(gender = Gender.MALE, level = 15, friendship = 221, heldItem = "Metal Coat")
        val species =
            Species(
                id = 1,
                name = "sample",
                evolutions =
                    listOf(
                        EvolutionEdge(2, EvolutionMethod.Level(16)),
                        EvolutionEdge(3, EvolutionMethod.Item("fire-stone")),
                        EvolutionEdge(4, EvolutionMethod.Trade("metal-coat")),
                        EvolutionEdge(5, EvolutionMethod.Friendship(TimeOfDay.DAY)),
                        EvolutionEdge(6, EvolutionMethod.Move("ancient power")),
                        EvolutionEdge(7, EvolutionMethod.Location("moss-rock")),
                    ),
            )
        EvolutionChecker.checkEvolution(
                mon, species, EvolutionContext(EvolutionTrigger.LEVEL_UP, newLevel = 16))
            .toSpecies shouldBe 2
        EvolutionChecker.checkEvolution(
                mon, species, EvolutionContext(EvolutionTrigger.ITEM, item = "Fire Stone"))
            .toSpecies shouldBe 3
        EvolutionChecker.checkEvolution(mon, species, EvolutionContext(EvolutionTrigger.TRADE))
            .toSpecies shouldBe 4
        EvolutionChecker.checkEvolution(
                mon,
                species,
                EvolutionContext(EvolutionTrigger.FRIENDSHIP, timeOfDay = TimeOfDay.DAY))
            .toSpecies shouldBe 5
        EvolutionChecker.checkEvolution(
                mon,
                species,
                EvolutionContext(
                    EvolutionTrigger.MOVE_LEARNED, knownMoves = listOf("Ancient_Power")))
            .toSpecies shouldBe 6
        EvolutionChecker.checkEvolution(
                mon,
                species,
                EvolutionContext(EvolutionTrigger.LOCATION_CHECK, mapId = "moss-rock"))
            .toSpecies shouldBe 7
      }

      test("breeding rules mirror TS edge cases and inheritance") {
        val bulba = Species(1, "bulbasaur", eggGroups = listOf(EggGroup.MONSTER, EggGroup.GRASS))
        val char = Species(4, "charmander", eggGroups = listOf(EggGroup.MONSTER, EggGroup.DRAGON))
        val ditto = Species(132, "ditto", eggGroups = listOf(EggGroup.DITTO))
        val baby = Species(172, "pichu", eggGroups = listOf(EggGroup.UNDISCOVERED))
        BreedingRules.canBreed(parent(Gender.FEMALE, bulba), parent(Gender.MALE, char))
            .canBreed
            .shouldBeTrue()
        BreedingRules.canBreed(parent(Gender.FEMALE, bulba), parent(Gender.MALE, char))
            .eggSpeciesId shouldBe 1
        BreedingRules.canBreed(parent(Gender.MALE, ditto), parent(Gender.FEMALE, ditto))
            .canBreed
            .shouldBeFalse()
        BreedingRules.canBreed(parent(Gender.MALE, baby), parent(Gender.FEMALE, bulba))
            .canBreed
            .shouldBeFalse()

        val mother =
            parent(
                Gender.FEMALE,
                bulba,
                sampleMon(
                    Gender.FEMALE,
                    nature = Nature.MODEST,
                    heldItem = "Everstone",
                    ivs = IVs(31, 30, 29, 28, 27, 26)))
        val father =
            parent(
                Gender.MALE,
                char,
                sampleMon(Gender.MALE, nature = Nature.ADAMANT, ivs = IVs(1, 2, 3, 4, 5, 6)))
        val rolls = mutableListOf(0.0, 0.0, 0.0, 0.0, 0.4, 0.1, 0.6, 0.2, 0.4, 0.3, 0.6, 0.4, 0.4)
        val egg = BreedingRules.createEggDraft(mother, father, rng = { rolls.removeAt(0) })
        egg.speciesId shouldBe 1
        egg.nature shouldBe Nature.MODEST
        egg.inheritedFrom.size shouldBe 3
        egg.eggSteps shouldBe 5120
        BreedingRules.hatchFriendship(999) shouldBe 255
      }

      test(
          "encounter generator uses weighted slots, table source, and deterministic PID shiny path") {
            val species =
                mapOf(
                    16 to Species(16, "pidgey", abilities = listOf("keen-eye"), genderRatio = 0.5),
                    19 to
                        Species(
                            19,
                            "rattata",
                            abilities = listOf("run-away", "guts"),
                            genderRatio = 0.5),
                )
            val table =
                EncounterTable(
                    "route-1",
                    EncounterMethod.GRASS,
                    listOf(EncounterSlot(16, 2, 4, 10), EncounterSlot(19, 3, 5, 90)))
            val source = EncounterTableSource { mapId, method ->
              if (mapId == "route-1" && method == EncounterMethod.GRASS) listOf(table)
              else emptyList()
            }
            val rolls =
                mutableListOf(0.2, 0.5, 0.25, 0.10, 0.75, 0.10, 0.20, 0.30, 0.40, 0.50, 0.60)
            val draft =
                EncounterGenerator.generateEncounter(
                    mapId = "route-1",
                    method = EncounterMethod.GRASS,
                    tableSource = source,
                    speciesById = species,
                    config = EncounterConfig(shinyRate = 512, shinyCharmRate = 256),
                    rng = { rolls.removeAt(0) },
                )
            draft!!.speciesId shouldBe 19
            draft.level shouldBe 4
            draft.ability shouldBe "run-away"
            draft.gender shouldBe Gender.FEMALE
            draft.ivs.hp.shouldBeGreaterThanOrEqual(0)
            draft.shiny shouldBe ShinyRoller.isPidSelectedShiny(draft.pid, 512, 256, false, false)
            EncounterGenerator.generateEncounter(
                "unknown", EncounterMethod.GRASS, source, species, EncounterConfig(512)) shouldBe
                null
          }
    })

private fun parent(
    gender: Gender,
    species: Species,
    mon: OwnedPokemon = sampleMon(gender)
): BreedingParent = BreedingParent(mon.copy(speciesId = species.id, gender = gender), species)

private fun sampleMon(
    gender: Gender = Gender.MALE,
    level: Int = 5,
    friendship: Int = 70,
    nature: Nature = Nature.HARDY,
    heldItem: String? = null,
    ivs: IVs = IVs(10, 10, 10, 10, 10, 10),
): OwnedPokemon =
    OwnedPokemon(
        uid = "mon-$gender-$level-$nature",
        speciesId = 1,
        level = level,
        exp = 0,
        nature = nature,
        ability = "overgrow",
        gender = gender,
        shiny = false,
        ivs = ivs,
        friendship = friendship,
        heldItem = heldItem,
        otWallet = "wallet",
        otName = "trainer",
        pid = 1u,
        metLevel = level,
        metLocation = "pallet-town",
    )
