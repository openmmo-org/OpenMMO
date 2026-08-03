package de.fiereu.openmmo.server.game.storage

import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.UpdatableRecord
import org.jooq.impl.DSL

/** What one table has to write and drop to catch up with a newer version of the data. */
data class RowDelta<K, V>(val changed: Map<K, V>, val removed: Set<K>) {
  val isEmpty: Boolean
    get() = changed.isEmpty() && removed.isEmpty()
}

fun <K, V> rowDelta(before: Map<K, V>, after: Map<K, V>): RowDelta<K, V> =
    RowDelta(after.filter { (key, row) -> before[key] != row }, before.keys - after.keys)

fun <K> rowDelta(before: Set<K>, after: Set<K>): RowDelta<K, Unit> =
    RowDelta((after - before).associateWith {}, before - after)

/** Removed rows go first, so the changed ones can take over a freed unique slot. */
fun <K, V, R : UpdatableRecord<R>> DSLContext.writeDelta(
    table: Table<R>,
    delta: RowDelta<K, V>,
    match: (K) -> Condition,
    record: (K, V) -> R,
) {
  if (delta.removed.isNotEmpty()) {
    deleteFrom(table).where(DSL.or(delta.removed.map(match))).execute()
  }
  if (delta.changed.isEmpty()) return
  // resetTouchedOnNotNull() would drop a column set back to null, leaving it never cleared.
  val records = delta.changed.map { (key, row) -> record(key, row).apply { touched(true) } }
  // A table that is only a primary key has nothing to set on a conflict.
  val keyOnly = table.primaryKey?.fields?.size == table.fields().size
  if (keyOnly) batchInsert(records).execute() else batchMerge(records).execute()
}
