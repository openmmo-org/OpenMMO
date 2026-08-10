package de.fiereu.openmmo.items

import de.fiereu.openmmo.items.generated.GeneratedItems
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRegistry @Inject constructor() {

  private val byId = ConcurrentHashMap<Int, ItemDef>()
  private val idsByItem = ConcurrentHashMap<ItemDef, List<Int>>()

  init {
    GeneratedItems.loadInto(this)
  }

  fun register(item: ItemDef, vararg ids: Int) {
    for (id in ids) {
      val previous = byId.put(id, item)
      // Generated data, so a clash is a codegen bug rather than bad input.
      check(previous == null || previous === item) {
        "Item id $id is claimed by both ${previous?.name} and ${item.name}"
      }
    }
    idsByItem.merge(item, ids.toList()) { old, new -> (old + new).distinct().sorted() }
  }

  fun get(id: Int): ItemDef? = byId[id]

  fun idsOf(item: ItemDef): List<Int> = idsByItem[item].orEmpty()

  /** An item with several ids is named by its lowest. */
  fun idOf(item: ItemDef): Int =
      idsOf(item).firstOrNull() ?: error("Item '${item.name}' has no id in this build")

  fun idOrNull(item: ItemDef): Int? = idsOf(item).firstOrNull()

  fun all(): Collection<ItemDef> = idsByItem.keys

  fun size(): Int = idsByItem.size
}
