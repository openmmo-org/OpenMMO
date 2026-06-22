package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TournamentMatchup(
    val value: Short,
    val type: Byte,
    val entityId: Long,
    val moveRefA: Short?,
    val moveRefB: Short?,
)

data class TournamentMatchupsPacket(
    val matchups: List<TournamentMatchup>,
)

private object TournamentMatchupCodec : PacketCodec<TournamentMatchup>() {
  override fun CodecScope<TournamentMatchup>.body(): TournamentMatchup {
    val value = field(S16LE, TournamentMatchup::value)
    val type = field(S8, TournamentMatchup::type)
    val entityId = field(S64LE, TournamentMatchup::entityId)
    if (type.toInt() != 0) {
      val moveRefA = field(S16LE) { it.moveRefA!! }
      val moveRefB = field(S16LE) { it.moveRefB!! }
      return TournamentMatchup(value, type, entityId, moveRefA, moveRefB)
    }
    return TournamentMatchup(value, type, entityId, null, null)
  }
}

private val TournamentMatchupListPrefixedU16: Codec<List<TournamentMatchup>> =
    object : Codec<List<TournamentMatchup>> {
      override fun read(buf: ReadBuffer): List<TournamentMatchup> {
        val n = U16LE.read(buf)
        return List(n) { TournamentMatchupCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<TournamentMatchup>) {
        U16LE.write(buf, value.size)
        value.forEach { TournamentMatchupCodec.write(buf, it) }
      }
    }

object TournamentMatchupsPacketCodec : PacketCodec<TournamentMatchupsPacket>() {
  override fun CodecScope<TournamentMatchupsPacket>.body(): TournamentMatchupsPacket {
    val matchups = field(TournamentMatchupListPrefixedU16, TournamentMatchupsPacket::matchups)
    return TournamentMatchupsPacket(matchups)
  }
}
