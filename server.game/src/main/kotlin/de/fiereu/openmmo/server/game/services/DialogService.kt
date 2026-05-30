package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.DialogChoicePacket
import de.fiereu.openmmo.net.game.packets.DialogStatePacket
import de.fiereu.openmmo.net.game.packets.InteractivePacket
import de.fiereu.openmmo.net.game.packets.InteractiveResponsePacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.ScriptPage
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class DialogService @Inject constructor() {

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
      ctx: SessionContext,
      id: Int,
      entityId: Long,
      type: Int = 0x04,
      unk1: Int = 0xAA74,
      unk2: Int = 0x1F10,
      unk3: Int = 0x0708,
  ) {
    ctx.send(
        InteractivePacket(
            id = id,
            type = type,
            unk1 = unk1,
            unk2 = unk2,
            targetEntityId = entityId,
            unk3 = unk3,
            unk4 = 0,
        ))
  }

  fun onInteractive(event: PacketEvent<InteractiveResponsePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return

    if (state.inDialog) {
      val respId = event.packet.id
      log.info {
        "Interactive response: id=$respId for char $charId (page ${state.dialogPageIndex + 1}/${state.dialogPages.size})"
      }
      val nextPageIndex = state.dialogPageIndex + 1
      if (nextPageIndex < state.dialogPages.size) {
        val seqId = state.dialogSeqId++
        val page = state.dialogPages[nextPageIndex]
        state.dialogPageIndex = nextPageIndex
        sendInteractive(
            ctx,
            seqId,
            state.dialogNpcEntityId,
            page.type,
            page.unk1,
            page.unk2,
            page.unk3,
        )
      } else {
        ctx.send(DialogStatePacket(false))
        state.inDialog = false
        state.dialogNpcEntityId = 0
        state.dialogPages = emptyList()
        state.dialogPageIndex = 0
      }
    }
  }

  fun onDialogChoice(event: PacketEvent<DialogChoicePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    log.info { "Dialog choice received: unk1=${event.packet.unk1}, unk2=${event.packet.unk2}" }
    if (state.inDialog) {
      ctx.send(DialogStatePacket(false))
      state.inDialog = false
      state.dialogNpcEntityId = 0
    }
  }
}
