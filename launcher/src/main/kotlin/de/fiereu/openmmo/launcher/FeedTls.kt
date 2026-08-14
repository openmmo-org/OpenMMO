package de.fiereu.openmmo.launcher

import de.fiereu.openmmo.launcher.client.POKEMMO_MIRRORS
import java.math.BigInteger
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x509.BasicConstraints
import org.bouncycastle.asn1.x509.ExtendedKeyUsage
import org.bouncycastle.asn1.x509.Extension
import org.bouncycastle.asn1.x509.GeneralName
import org.bouncycastle.asn1.x509.GeneralNames
import org.bouncycastle.asn1.x509.KeyPurposeId
import org.bouncycastle.asn1.x509.KeyUsage
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder

private const val ALIAS = "openmmo-feed"
private const val VALID_DAYS = 365L
private const val CAPTURED_AUTHORITY_NAME = "C=US,O=Amazon,CN=Amazon Root CA 1"

/** Key material for the loopback HTTPS feed server and the trust store the client is given. */
object FeedTls {

  val password: CharArray = "openmmo".toCharArray()

  /** A fresh key store for loopback and the feed names tunneled to it. */
  fun keyStore(): KeyStore {
    val keys = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }
    val authorityPair = keys.generateKeyPair()
    val serverPair = keys.generateKeyPair()
    val now = Instant.now()
    // The client captures its roots into the native image. The manifest substitutes this root's
    // public key with ours, so the generated authority must retain the captured root's identity.
    val authorityName = X500Name(CAPTURED_AUTHORITY_NAME)
    val authority =
        JcaX509v3CertificateBuilder(
                authorityName,
                BigInteger.valueOf(now.toEpochMilli()),
                Date.from(now.minus(1, ChronoUnit.DAYS)),
                Date.from(now.plus(VALID_DAYS, ChronoUnit.DAYS)),
                authorityName,
                authorityPair.public,
            )
            .addExtension(Extension.basicConstraints, true, BasicConstraints(true))
            .addExtension(
                Extension.keyUsage,
                true,
                KeyUsage(KeyUsage.keyCertSign or KeyUsage.cRLSign),
            )
            .let {
              JcaContentSignerBuilder("SHA256withRSA").build(authorityPair.private).let(it::build)
            }
            .let { JcaX509CertificateConverter().getCertificate(it) }

    val serverName = X500Name("CN=127.0.0.1")
    val server =
        JcaX509v3CertificateBuilder(
                authorityName,
                BigInteger.valueOf(now.toEpochMilli() + 1),
                Date.from(now.minus(1, ChronoUnit.DAYS)),
                Date.from(now.plus(VALID_DAYS, ChronoUnit.DAYS)),
                serverName,
                serverPair.public,
            )
            // The proxy preserves the mirror's TLS hostname while tunneling it to loopback.
            .addExtension(
                Extension.subjectAlternativeName,
                false,
                GeneralNames(
                    arrayOf(GeneralName(GeneralName.iPAddress, LOOPBACK)) +
                        POKEMMO_MIRRORS.map { GeneralName(GeneralName.dNSName, URI(it).host) }),
            )
            .addExtension(Extension.basicConstraints, true, BasicConstraints(false))
            .addExtension(
                Extension.keyUsage,
                true,
                KeyUsage(KeyUsage.digitalSignature or KeyUsage.keyEncipherment),
            )
            .addExtension(
                Extension.extendedKeyUsage,
                false,
                ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth),
            )
            .let {
              JcaContentSignerBuilder("SHA256withRSA").build(authorityPair.private).let(it::build)
            }
            .let { JcaX509CertificateConverter().getCertificate(it) }

    return KeyStore.getInstance("PKCS12").apply {
      load(null, password)
      setKeyEntry(ALIAS, serverPair.private, password, arrayOf<Certificate>(server, authority))
    }
  }

  fun certificate(keyStore: KeyStore): X509Certificate =
      keyStore.getCertificateChain(ALIAS).last() as X509Certificate

  fun trustAnchor(store: Path): X509Certificate =
      KeyStore.getInstance("PKCS12").run {
        Files.newInputStream(store).use { load(it, password) }
        getCertificate(ALIAS) as X509Certificate
      }

  fun sslContext(store: Path): SSLContext {
    val keys = KeyStore.getInstance("PKCS12")
    Files.newInputStream(store).use { keys.load(it, password) }
    val managers = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    managers.init(keys)
    return SSLContext.getInstance("TLS").apply {
      init(null, managers.trustManagers, SecureRandom())
    }
  }

  /**
   * Writes a trust store holding the client's usual roots plus [certificate] to [target].
   *
   * The client validates the feed over TLS, so it needs our certificate. Keeping the original roots
   * means everything else it talks to still validates.
   */
  fun writeTrustStore(certificate: X509Certificate, installDir: Path, target: Path): Path {
    val store = KeyStore.getInstance("PKCS12").apply { load(null, password) }
    baseRoots(installDir)?.let { base ->
      base
          .aliases()
          .asSequence()
          .filter(base::isCertificateEntry)
          .filter {
            (base.getCertificate(it) as? X509Certificate)?.subjectX500Principal !=
                certificate.subjectX500Principal
          }
          .forEach { store.setCertificateEntry(it, base.getCertificate(it)) }
    }
    store.setCertificateEntry(ALIAS, certificate)
    Files.newOutputStream(target).use { store.store(it, password) }
    return target
  }

  private fun baseRoots(installDir: Path): KeyStore? {
    val cacerts =
        listOf(
                installDir.resolve("jre/lib/security/cacerts"),
                Path.of(System.getProperty("java.home"), "lib", "security", "cacerts"),
            )
            .firstOrNull { Files.isRegularFile(it) } ?: return null
    val defaultPassword = "changeit".toCharArray()
    for (type in listOf("PKCS12", "JKS")) {
      runCatching {
        val store = KeyStore.getInstance(type)
        Files.newInputStream(cacerts).use { store.load(it, defaultPassword) }
        return store
      }
    }
    return null
  }
}
