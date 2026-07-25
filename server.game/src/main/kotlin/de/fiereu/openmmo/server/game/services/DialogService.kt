package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.net.game.packets.DialogChoicePacket
import de.fiereu.openmmo.net.game.packets.DialogStatePacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionResponsePacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class DialogService @Inject constructor() {

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
