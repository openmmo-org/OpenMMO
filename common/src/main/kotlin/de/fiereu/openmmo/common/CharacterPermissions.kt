package de.fiereu.openmmo.common

object CharacterPermissions {
  // The codec sends only the low byte, so bits above 0xFF never reach the client.
  const val DEVELOPER = 0x100
}

fun CharacterInfo.hasPermission(permission: Int): Boolean = permissions and permission == permission
