package de.fiereu.openmmo.server.game.storage

import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

const val CHARACTER_ID_TAG = 0x9000L
const val MONSTER_ID_TAG = 0xC000L

/**
 * Creates entity ids without a database roundtrip, safe to call from any thread.
 *
 * An id is a 47-bit head shifted left 16 with the kind tag in the low bits. The head combines 39
 * bits of unix millis with an 8-bit counter that starts at a random offset, so ids are unique
 * within a process as long as fewer than 256 are created in the same millisecond. The sign bit
 * stays clear. The wire protocol depends on the low-16 tag, so keep it intact.
 */
@Singleton
class EntityIdService @Inject constructor() {

  private val counter = AtomicLong(ThreadLocalRandom.current().nextLong(256))

  fun newCharacterId(): Long = newId(CHARACTER_ID_TAG)

  fun newMonsterId(): Long = newId(MONSTER_ID_TAG)

  private fun newId(tag: Long): Long {
    val millis = System.currentTimeMillis() and 0x7F_FFFF_FFFFL
    val head = (millis shl 8) or (counter.getAndIncrement() and 0xFF)
    return (head shl 16) or tag
  }
}
