package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ListWindowRow(
    val rowType: Byte,
    val valueA: Short,
    val valueB: Short,
    val valueC: Short,
    val valueD: Short,
    val valueE: Short,
    val label: String,
    val valueF: Short,
)

data class ListWindowPagePacket(
    val windowId: Int,
    val firstPage: Boolean,
    val lastPage: Boolean,
    val headerA: Byte,
    val headerB: Byte,
    val headerC: Byte,
    val rows: List<ListWindowRow>,
)

private val ListWindowRowCodec: Codec<ListWindowRow> =
    object : PacketCodec<ListWindowRow>() {
      override fun CodecScope<ListWindowRow>.body(): ListWindowRow {
        val rowType = field(S8) { it.rowType }
        val valueA = field(S16LE) { it.valueA }
        val valueB = field(S16LE) { it.valueB }
        val valueC = field(S16LE) { it.valueC }
        val valueD = field(S16LE) { it.valueD }
        val valueE = field(S16LE) { it.valueE }
        val label = field(Utf16LeNullTerminated) { it.label }
        val valueF = field(S16LE) { it.valueF }
        return ListWindowRow(rowType, valueA, valueB, valueC, valueD, valueE, label, valueF)
      }
    }

object ListWindowPagePacketCodec : PacketCodec<ListWindowPagePacket>() {
  override fun CodecScope<ListWindowPagePacket>.body(): ListWindowPagePacket {
    val windowId = field(S32LE) { it.windowId }
    val firstPage = field(U8) { if (it.firstPage) 1 else 0 } == 1
    val lastPage = field(U8) { if (it.lastPage) 1 else 0 } == 1
    val headerA = field(S8) { it.headerA }
    val headerB = field(S8) { it.headerB }
    val headerC = field(S8) { it.headerC }
    val rows = field(ListWindowRowCodec.listPrefixed(U16LE)) { it.rows }
    return ListWindowPagePacket(windowId, firstPage, lastPage, headerA, headerB, headerC, rows)
  }
}
