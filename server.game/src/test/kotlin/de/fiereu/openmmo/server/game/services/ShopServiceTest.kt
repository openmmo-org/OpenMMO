package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.ItemDef
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.net.game.packets.ExchangeItemRequestPacket
import de.fiereu.openmmo.net.game.packets.LocalCharacterDeltaPacket
import de.fiereu.openmmo.net.game.packets.ShopCatalogPacket
import de.fiereu.openmmo.net.game.packets.ShopSellRequestPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSideAddPokemonPacket
import de.fiereu.openmmo.server.game.session.OPEN_SHOP
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private val SHELF = listOf(Items.POKE_BALL, Items.POTION, Items.ANTIDOTE, Items.PARLYZ_HEAL)
private const val CLERK_ENTITY = 4711L

private class ShopFixture(scope: CoroutineScope) {
  val items = ItemRegistry()
  val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), scope)
  val service = ShopService(store, items)

  suspend fun shopper(): Pair<FakeSession, Long> {
    val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
    val session = FakeSession(created.info.id)
    service.open(session, CLERK_ENTITY, SHELF)
    session.sent.clear()
    return session to created.info.id
  }

  suspend fun buy(session: FakeSession, id: Int, quantity: Int) {
    service.onBuy(
        PacketEvent(ExchangeItemRequestPacket(id.toShort(), quantity.toShort(), 0), session))
  }

  suspend fun sell(session: FakeSession, id: Int, quantity: Int) {
    service.onSell(
        PacketEvent(
            ShopSellRequestPacket((id.toLong() shl 16) or 0x5000, quantity.toShort()), session))
  }

  suspend fun money(charId: Long): Int = store.getCharacter(charId)!!.info.money

  suspend fun held(charId: Long, id: Int): Int = store.getCharacter(charId)!!.items[id] ?: 0
}

@OptIn(ExperimentalCoroutinesApi::class)
class ShopServiceTest :
    FunSpec({
      test("the shelf carries the ids and prices the client expects") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val created = fx.store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          val session = FakeSession(created.info.id)

          fx.service.open(session, CLERK_ENTITY, SHELF)

          val catalog = session.sent.filterIsInstance<ShopCatalogPacket>().single()
          catalog.npcEntityId shouldBe CLERK_ENTITY
          catalog.items.map { it.itemId.toInt() to it.price } shouldContainExactly
              listOf(5004 to 200, 5017 to 300, 5018 to 100, 5022 to 200)
        }
      }

      test("an item with no id is left off the shelf rather than failing the open") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val created = fx.store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          val session = FakeSession(created.info.id)

          fx.service.open(session, CLERK_ENTITY, listOf(Items.POTION, ItemDef("Teachy TV", 100)))

          session.sent.filterIsInstance<ShopCatalogPacket>().single().items.map {
            it.itemId.toInt()
          } shouldContainExactly listOf(5017)
        }
      }

      test("buying resolves the id the client sends") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()

          fx.buy(session, 5017, 3)

          fx.held(charId, 5017) shouldBe 3
          fx.money(charId) shouldBe 30000 - 900
          session.sent.filterIsInstance<LocalCharacterDeltaPacket>().single().money shouldBe 29100
        }
      }

      test("the bought stack is announced on its own, so the open window can recount it") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, _) = fx.shopper()

          fx.buy(session, 5004, 5)

          val update = session.sent.filterIsInstance<BattleSideAddPokemonPacket>().single()
          update.pokemon.frontSpriteId shouldBe 5004.toShort()
          update.pokemon.backSpriteId shouldBe 5.toShort()
        }
      }

      test("refuses an item this clerk does not sell") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()

          fx.buy(session, fx.items.idOf(Items.MASTER_BALL), 1)

          fx.money(charId) shouldBe 30000
          session.sent.shouldBeEmpty()
        }
      }

      test("refuses a buy the player cannot pay for") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()

          fx.buy(session, 5017, 200)

          fx.held(charId, 5017) shouldBe 0
          fx.money(charId) shouldBe 30000
        }
      }

      test("refuses a non-positive quantity") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()

          fx.buy(session, 5017, 0)
          fx.buy(session, 5017, -2)

          fx.money(charId) shouldBe 30000
        }
      }

      test("selling pays half and reads the item out of the bag stack id") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()
          fx.buy(session, 5017, 4)
          session.sent.clear()

          fx.sell(session, 5017, 2)

          fx.held(charId, 5017) shouldBe 2
          fx.money(charId) shouldBe 30000 - 1200 + 300
          session.sent
              .filterIsInstance<BattleSideAddPokemonPacket>()
              .single()
              .pokemon
              .backSpriteId shouldBe 2.toShort()
        }
      }

      test("refuses to sell more than the bag holds") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()
          fx.buy(session, 5017, 1)

          fx.sell(session, 5017, 2)

          fx.held(charId, 5017) shouldBe 1
          fx.money(charId) shouldBe 30000 - 300
        }
      }

      test("ignores a sell with no shop open") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()
          fx.buy(session, 5017, 1)
          session.attributes.remove(OPEN_SHOP)
          session.sent.clear()

          fx.sell(session, 5017, 1)

          fx.held(charId, 5017) shouldBe 1
          session.sent.shouldBeEmpty()
        }
      }

      test("refuses to buy from a shelf the player has walked away from") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()
          session.state().mapId += 1

          fx.buy(session, 5017, 1)

          fx.held(charId, 5017) shouldBe 0
          fx.money(charId) shouldBe 30000
          session.attributes[OPEN_SHOP].shouldBeNull()
        }
      }

      test("refuses to sell a key item, rather than deleting it for nothing") {
        runTest {
          val fx = ShopFixture(backgroundScope)
          val (session, charId) = fx.shopper()
          val townMap = fx.items.idOf(Items.TOWN_MAP)
          fx.store.addItem(charId, townMap, 1)
          session.sent.clear()

          fx.sell(session, townMap, 1)

          fx.held(charId, townMap) shouldBe 1
          fx.money(charId) shouldBe 30000
          session.sent.shouldBeEmpty()
        }
      }
    })
