package de.fiereu.openmmo.common

import de.fiereu.openmmo.common.enums.SkinSlot

data class Skin(val slot: SkinSlot, val type: UShort?, val color: UByte?)
