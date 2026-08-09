package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket

fun notice(message: String): ChatMessagePacket =
    ChatMessagePacket(
        type = ChatType.GAME_NOTIFICATIONS,
        language = Language.EN,
        message = message,
        sender = "",
    )
