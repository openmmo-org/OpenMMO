package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.DialogChoicePacket
import de.fiereu.openmmo.net.game.packets.DialogStatePacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionPacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionResponsePacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class DialogService @Inject constructor() {

  /**
   * Opens a dialog line on the client and marks it as the active dialog. [textId] is a line id from
   * the generated dialog enums, [actionType] frames the box (3 for a sign, 4 for an npc or default
   * box) and [entityId] is the speaking npc or -1 when there is no on screen source. The flags byte
   * counts up per line so the client can match its reply back to the line it closed.
   */
  fun openDialog(
      ctx: SessionContext,
      state: PlayerState,
      textId: Int,
      actionType: Int,
      entityId: Long,
  ) {
    val seq = state.dialogSeqId
    state.dialogSeqId = seq + 1
    ctx.send(
        DialogActionPacket(
            flags = seq.toByte(),
            actionType = actionType.toByte(),
            textId = textId,
            entityId = entityId,
            contextValue = 0,
            messageArgs = emptyList(),
            detail = byteArrayOf(0),
        ))
    state.inDialog = true
    state.dialogNpcEntityId = entityId
  }

  fun onInteractive(event: PacketEvent<DialogActionResponsePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    if (state.inDialog) {
      ctx.send(DialogStatePacket(false))
      state.inDialog = false
      state.dialogNpcEntityId = 0
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
