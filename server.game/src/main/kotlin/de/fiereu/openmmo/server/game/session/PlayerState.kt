package de.fiereu.openmmo.server.game.session

import de.fiereu.openmmo.common.enums.Direction

data class ScriptPage(
    val type: Int = 0x04,
    val unk1: Int,
    val unk2: Int,
    val unk3: Int,
)

data class PlayerState(
    val userId: Int,
    var characterId: Long? = null,
    var justWarped: Boolean = false,
    var facingDirection: Direction = Direction.DOWN,
    var inDialog: Boolean = false,
    var dialogNpcEntityId: Long = 0,
    var dialogSeqId: Int = 0,
    var dialogPages: List<ScriptPage> = emptyList(),
    var dialogPageIndex: Int = 0,
    var regionId: Int = 1,
    var bankId: Int = 51,
    var mapId: Int = 3,
    var x: Short = 4,
    var y: Short = 2,
)
