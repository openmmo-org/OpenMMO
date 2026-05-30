package de.fiereu.network

import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

sealed interface SessionIdentity {
  data class ServerRoot(val rootPrivate: ECPrivateKey) : SessionIdentity

  data class ClientTrust(val rootPublic: ECPublicKey) : SessionIdentity
}
