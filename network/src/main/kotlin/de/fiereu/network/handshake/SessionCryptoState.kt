package de.fiereu.network.handshake

import de.fiereu.network.checksum.Checksum
import de.fiereu.network.checksum.ChecksumFactory
import de.fiereu.network.cipher.AesCtrSessionCipher
import de.fiereu.network.cipher.SessionCipher
import java.security.PrivateKey
import java.security.PublicKey

internal class SessionCryptoState(
    val cipher: SessionCipher,
    val incomingChecksum: Checksum,
    val outgoingChecksum: Checksum,
) {
  companion object {
    fun derive(
        localPrivate: PrivateKey,
        remotePublic: PublicKey,
        role: AesCtrSessionCipher.Role,
        checksumSize: Int,
    ): SessionCryptoState {
      val secret = AesCtrSessionCipher.ecdh(localPrivate, remotePublic)
      val cipher = AesCtrSessionCipher.derive(secret, role)
      val (outSeed, inSeed) = AesCtrSessionCipher.directionalSeeds(secret, role)
      return SessionCryptoState(
          cipher = cipher,
          incomingChecksum = ChecksumFactory.create(checksumSize, inSeed),
          outgoingChecksum = ChecksumFactory.create(checksumSize, outSeed),
      )
    }
  }
}
