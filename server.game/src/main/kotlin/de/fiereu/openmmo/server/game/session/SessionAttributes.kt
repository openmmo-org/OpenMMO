package de.fiereu.openmmo.server.game.session

import de.fiereu.network.SessionAttribute
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionResponsePacket
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope

val PLAYER_STATE = SessionAttribute.of<PlayerState>("playerState")

/** Completed when the player advances or closes the dialog box a script is currently waiting on. */
val PENDING_DIALOG = SessionAttribute.of<CompletableDeferred<Unit>>("pendingDialog")

/** Completes with the selected dialog value. */
val PENDING_DIALOG_RESPONSE =
    SessionAttribute.of<CompletableDeferred<DialogActionResponsePacket>>("pendingDialogResponse")

/** Completed when the client has asked for its player again, which ends a map transition. */
val PENDING_MAP_LOAD = SessionAttribute.of<CompletableDeferred<Unit>>("pendingMapLoad")

/** Scope the connection's scripts run in. Cancelled on disconnect so a waiting script unwinds. */
val SCRIPT_SCOPE = SessionAttribute.of<CoroutineScope>("scriptScope")
