package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class TournamentTeleportAcceptPacket

object TournamentTeleportAcceptPacketCodec : PacketCodec<TournamentTeleportAcceptPacket>() {
  override fun CodecScope<TournamentTeleportAcceptPacket>.body(): TournamentTeleportAcceptPacket {
    return TournamentTeleportAcceptPacket()
  }
}
