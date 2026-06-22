package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class BattleListEventDetail(
    val listType: Byte,
    val value: Short,
)

data class BattleListEventPacket(
    val kind: Byte,
    val value: Short,
    val subKind: Byte,
    val detail: BattleListEventDetail?,
)

object BattleListEventPacketCodec : PacketCodec<BattleListEventPacket>() {
  override fun CodecScope<BattleListEventPacket>.body(): BattleListEventPacket {
    val kind = field(S8) { it.kind }
    val value = field(S16LE) { it.value }
    val subKind = field(S8) { it.subKind }
    val detail =
        if (subKind.toInt() == 4) {
          val listType = field(S8) { it.detail!!.listType }
          val detailValue = field(S16LE) { it.detail!!.value }
          BattleListEventDetail(listType, detailValue)
        } else null
    return BattleListEventPacket(kind, value, subKind, detail)
  }
}
