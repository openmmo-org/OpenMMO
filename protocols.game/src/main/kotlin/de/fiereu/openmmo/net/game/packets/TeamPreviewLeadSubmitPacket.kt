package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class RibbonEntry(
    val ribbonId: Byte,
    val value: Byte,
)

private val RibbonEntryCodec: Codec<RibbonEntry> =
    object : PacketCodec<RibbonEntry>() {
      override fun CodecScope<RibbonEntry>.body(): RibbonEntry {
        val ribbonId = field(S8) { it.ribbonId }
        val value = field(S8) { it.value }
        return RibbonEntry(ribbonId, value)
      }
    }

data class TeamPreviewAppearance(
    val kind: Byte,
    val shinyTypeId: Byte?,
    val form: Byte?,
    val moveIds: List<Short>?,
    val ribbons: List<RibbonEntry>?,
    val simpleForm: Byte?,
)

private val AppearanceCodec: Codec<TeamPreviewAppearance> =
    object : PacketCodec<TeamPreviewAppearance>() {
      override fun CodecScope<TeamPreviewAppearance>.body(): TeamPreviewAppearance {
        val kind = field(S8) { it.kind }
        if (kind.toInt() == 1) {
          val shinyTypeId = field(S8) { it.shinyTypeId ?: -1 }
          val form = field(S8) { it.form ?: 0 }
          val moveIds = field(S16LE.listPrefixed(U8)) { it.moveIds ?: emptyList() }
          val ribbons = field(RibbonEntryCodec.listPrefixed(U8)) { it.ribbons ?: emptyList() }
          return TeamPreviewAppearance(kind, shinyTypeId, form, moveIds, ribbons, null)
        } else if (kind.toInt() == 6) {
          val simpleForm = field(S8) { it.simpleForm ?: 0 }
          return TeamPreviewAppearance(kind, null, null, null, null, simpleForm)
        }
        return TeamPreviewAppearance(kind, null, null, null, null, null)
      }
    }

data class TeamPreviewLeadSubmitPacket(
    val pokemonEntityId: Long,
    val battleSlot: Byte,
    val appearance: TeamPreviewAppearance?,
    val teamSide: Short,
    val reserved: Byte,
)

object TeamPreviewLeadSubmitPacketCodec : PacketCodec<TeamPreviewLeadSubmitPacket>() {
  override fun CodecScope<TeamPreviewLeadSubmitPacket>.body(): TeamPreviewLeadSubmitPacket {
    val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
    val battleSlot = field(S8) { it.battleSlot }
    val present = field(U8) { if (it.appearance != null) 1 else 0 }
    val appearance = if (present != 0) field(AppearanceCodec) { it.appearance!! } else null
    val teamSide = field(S16LE) { it.teamSide }
    val reserved = field(S8) { it.reserved }
    return TeamPreviewLeadSubmitPacket(
        pokemonEntityId,
        battleSlot,
        appearance,
        teamSide,
        reserved,
    )
  }
}
