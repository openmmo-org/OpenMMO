package de.fiereu.openmmo.server.game.services

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BagService @Inject constructor() {
  /**
   * TODO: main bag/inventory packet is not identified in the validated login+walk capture. Do not
   *   guess an opcode. Wire this only after a fresh bag-open capture validates the C2S request and
   *   S2C item-stack carrier.
   */
  fun bagInventoryPacketPendingCapture(): Nothing =
      error("Bag/inventory opcode is pending fresh capture; do not call this stub.")
}
