package de.fiereu.openmmo.items

import de.fiereu.openmmo.items.generated.Items
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class ItemRegistryTest :
    FunSpec({
      // Kotest shares the spec instance, so the tests that register have to work on their own
      // registry rather than a shared one.
      fun registry() = ItemRegistry()

      test("loads the generated catalogue") { registry().size() shouldBeGreaterThan 500 }

      test("resolves the ids the live client sends") {
        val items = registry()

        items.idOf(Items.POKE_BALL) shouldBe 5004
        items.idOf(Items.POTION) shouldBe 5017
        items.idOf(Items.ANTIDOTE) shouldBe 5018
        items.idOf(Items.PARLYZ_HEAL) shouldBe 5022
        items.idOf(Items.PARCEL) shouldBe 5459
      }

      test("carries the price the item table gives") {
        Items.POKE_BALL.price shouldBe 200
        Items.POTION.price shouldBe 300
        Items.ANTIDOTE.price shouldBe 100
        Items.PARLYZ_HEAL.price shouldBe 200
      }

      test("round trips every item through its ids") {
        val items = registry()

        for (item in items.all()) {
          for (id in items.idsOf(item)) {
            items.get(id) shouldBe item
          }
        }
      }

      test("answers nothing for an id no item claims") { registry().get(1).shouldBeNull() }

      test("an item is its identity, so a look-alike does not resolve") {
        val items = registry()

        items.idOrNull(ItemDef(Items.POTION.name, Items.POTION.price)).shouldBeNull()
      }

      test("refuses an id already claimed by another item") {
        val items = registry()

        shouldThrow<IllegalStateException> {
          items.register(ItemDef("Impostor", 0), items.idOf(Items.POTION))
        }
      }

      test("registering the same item twice merges its ids") {
        val items = registry()
        val item = ItemDef("Multi", 10)

        items.register(item, 90002)
        items.register(item, 90001, 90002)

        items.idsOf(item) shouldContainExactly listOf(90001, 90002)
        items.idOf(item) shouldBe 90001
      }

      test("throws by name for an item this build has no id for") {
        val thrown = shouldThrow<IllegalStateException> { registry().idOf(ItemDef("Teachy TV", 0)) }

        thrown.message shouldBe "Item 'Teachy TV' has no id in this build"
      }
    })
