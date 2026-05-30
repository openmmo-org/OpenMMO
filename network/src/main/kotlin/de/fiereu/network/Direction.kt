package de.fiereu.network

enum class Direction {
  C2S,
  S2C;

  fun isIncomingFor(side: Side): Boolean =
      when (this) {
        C2S -> side == Side.SERVER
        S2C -> side == Side.CLIENT
      }

  fun isOutgoingFor(side: Side): Boolean = !isIncomingFor(side)
}
