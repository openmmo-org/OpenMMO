package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.net.game.packets.BagInventoryPacket
import de.fiereu.openmmo.net.game.packets.BagItemEntry
import de.fiereu.openmmo.net.game.packets.BagOpenRequestPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val bagLog = KotlinLogging.logger {}

@Singleton
class BagService
@Inject
constructor(
    private val characterStore: CharacterStore,
) {
  fun onBagOpen(event: PacketEvent<BagOpenRequestPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    val charId = state?.characterId
    if (charId == null) {
      bagLog.warn { "Bag open request without selected character" }
      return
    }

    val stored = characterStore.getCharacter(charId)
    if (stored == null) {
      bagLog.warn { "Bag open request for unknown character $charId" }
      return
    }

    // v31914's real bag-open response covers every container the client renders, container 0x0001
    // first (golden capture 2026-07-07-232143-first-manual.log, S2C 0x70 burst). We never sent
    // 0x0001 at all, which crashed bag-render client-side (null item lookup) -- the client expects
    // *a* response for that container even with nothing in it. Container 0x0001's populated-entry
    // shape is larger and still undecoded, but an empty container needs no entries to encode, so
    // send it empty until a follow-up capture with real data lands.
    val entries = itemEntries(stored.items)
    ctx.send(BagInventoryPacket(CONTAINER_DEFERRED_LARGE, emptyList()))
    ctx.send(BagInventoryPacket(CONTAINER_MAIN, entries))
    ctx.send(BagInventoryPacket(CONTAINER_SMALL, entries.take(MAX_SMALL_ENTRIES)))
    bagLog.info { "Sending BagInventory for character $charId" }
  }

  private fun itemEntries(items: Map<Int, Int>): List<BagItemEntry> =
      items.entries
          .sortedBy { it.key }
          .take(MAX_MAIN_ENTRIES)
          .mapIndexed { index, (itemId, quantity) ->
            BagItemEntry(
                slot = index,
                categoryFlags = CATEGORY_FLAGS_ITEM,
                subType = SUBTYPE_NORMAL_ITEM,
                itemId = itemId.coerceIn(0, 0xffff),
                quantity = quantity.coerceAtLeast(0),
                maxStack = quantity.coerceAtLeast(0),
                flag1 = 1,
                flag2 = 0,
                entityId = 0,
                unknownA = 0,
                slotDuplicate = index,
                state = 0x0001,
            )
          }

  companion object {
    const val CONTAINER_MAIN: Int = 0x0000
    const val CONTAINER_SMALL: Int = 0x0100
    const val CONTAINER_DEFERRED_LARGE: Int = 0x0001
    const val MAX_MAIN_ENTRIES: Int = 100
    const val MAX_SMALL_ENTRIES: Int = 8
    private const val CATEGORY_FLAGS_ITEM: Int = 1
    private const val SUBTYPE_NORMAL_ITEM: Int = 0x02
  }
}
