package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE

data class CurrencyBalancePacket(
    val primaryBalance: Int,
    val secondaryBalance: Int,
)

object CurrencyBalancePacketCodec : PacketCodec<CurrencyBalancePacket>() {
  override fun CodecScope<CurrencyBalancePacket>.body(): CurrencyBalancePacket {
    val primaryBalance = field(S32LE) { it.primaryBalance }
    val secondaryBalance = field(S32LE) { it.secondaryBalance }
    return CurrencyBalancePacket(primaryBalance, secondaryBalance)
  }
}
