package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.protocols.game.packets.DialogChoicePacket
import de.fiereu.openmmo.protocols.game.packets.DialogStatePacket
import de.fiereu.openmmo.protocols.game.packets.InteractiveResponsePacket
import de.fiereu.openmmo.server.game.session.ScriptPage
import de.fiereu.openmmo.server.game.session.SessionManager
import de.fiereu.openmmo.server.protocol.PacketEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext

private val log = KotlinLogging.logger {}

class DialogService(
    private val packetSender: PacketSender,
) {

  fun scriptParams(script: String): List<ScriptPage>? {
    if (script == "0x0") return null
    val known =
        mapOf<String, List<ScriptPage>>(
            "LittlerootTown_ProfessorBirchsLab_EventScript_Birch" to
                listOf(ScriptPage(type = 0x04, unk1 = 0xAA74, unk2 = 0x1F10, unk3 = 0x0708)),
            "LittlerootTown_ProfessorBirchsLab_EventScript_Aide" to
                listOf(ScriptPage(type = 0x04, unk1 = 0xA6CE, unk2 = 0x1F10, unk3 = 0x04B0)),
            "PlayersHouse_1F_EventScript_Mom" to
                listOf(
                    ScriptPage(type = 0x04, unk1 = 0x087D, unk2 = 0x1F10, unk3 = 0x04B0),
                    ScriptPage(type = 0x04, unk1 = 0x7D5C, unk2 = 0x1F10, unk3 = 0x02BC),
                ),
            "RivalsHouse_1F_EventScript_RivalMom" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x8CE3, unk2 = 0x1E10, unk3 = 0x02BC)),
            "RivalsHouse_1F_EventScript_RivalSibling" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x8B25, unk2 = 0x1E10, unk3 = 0x0708)),
            "LittlerootTown_EventScript_Twin" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x6292, unk2 = 0x1F10, unk3 = 0x04B0)),
            "LittlerootTown_EventScript_Boy" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x938D, unk2 = 0x1F10, unk3 = 0x04B0)),
        )
    return known[script]
  }

  fun sendInteractive(
      ctx: ChannelHandlerContext,
      id: Int,
      entityId: Long,
      type: Int = 0x04,
      unk1: Int = 0xAA74,
      unk2: Int = 0x1F10,
      unk3: Int = 0x0708,
  ) {
    val buf = Unpooled.buffer(19)
    buf.writeByte(id)
    buf.writeByte(type)
    buf.writeShortLE(unk1)
    buf.writeShortLE(unk2)
    buf.writeLongLE(entityId)
    buf.writeShortLE(unk3)
    buf.writeShortLE(0)
    buf.writeByte(0)
    val raw = ByteArray(buf.readableBytes())
    buf.readBytes(raw)
    buf.release()
    packetSender.sendRaw(ctx, 0x21u, raw)
  }

  fun onInteractive(event: PacketEvent<InteractiveResponsePacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return
    val charId = session.characterId ?: return

    if (session.inDialog) {
      val respId = event.packet.id
      log.info {
        "Interactive response: id=$respId for char $charId (page ${session.dialogPageIndex + 1}/${session.dialogPages.size})"
      }
      val nextPageIndex = session.dialogPageIndex + 1
      if (nextPageIndex < session.dialogPages.size) {
        val seqId = session.dialogSeqId++
        val page = session.dialogPages[nextPageIndex]
        session.dialogPageIndex = nextPageIndex
        sendInteractive(
            event.ctx,
            seqId,
            session.dialogNpcEntityId,
            page.type,
            page.unk1,
            page.unk2,
            page.unk3,
        )
        event.ctx.channel().flush()
      } else {
        event.ctx.channel().writeAndFlush(DialogStatePacket(false))
        session.inDialog = false
        session.dialogNpcEntityId = 0
        session.dialogPages = emptyList()
        session.dialogPageIndex = 0
      }
    }
  }

  fun onDialogChoice(event: PacketEvent<DialogChoicePacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return
    log.info { "Dialog choice received: unk1=${event.packet.unk1}, unk2=${event.packet.unk2}" }
    if (session.inDialog) {
      event.ctx.channel().writeAndFlush(DialogStatePacket(false))
      session.inDialog = false
      session.dialogNpcEntityId = 0
    }
  }
}
