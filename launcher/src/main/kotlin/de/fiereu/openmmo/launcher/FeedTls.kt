package de.fiereu.openmmo.launcher

import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x509.Extension
import org.bouncycastle.asn1.x509.GeneralName
import org.bouncycastle.asn1.x509.GeneralNames
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder

private const val ALIAS = "openmmo-feed"
private const val VALID_DAYS = 365L

/** Key material for the loopback HTTPS feed server and the trust store the client is given. */
object FeedTls {

  val password: CharArray = "openmmo".toCharArray()

  /** A fresh key store holding a self signed certificate for 127.0.0.1. */
  fun keyStore(): KeyStore {
    val pair = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }.generateKeyPair()
    val now = Instant.now()
    val name = X500Name("CN=127.0.0.1")
    val certificate =
        JcaX509v3CertificateBuilder(
                name,
                BigInteger.valueOf(now.toEpochMilli()),
                Date.from(now.minus(1, ChronoUnit.DAYS)),
                Date.from(now.plus(VALID_DAYS, ChronoUnit.DAYS)),
                name,
                pair.public,
            )
            // Without a matching address the client rejects the certificate for an IP url.
            .addExtension(
                Extension.subjectAlternativeName,
                false,
                GeneralNames(GeneralName(GeneralName.iPAddress, LOOPBACK)),
            )
            .let { JcaContentSignerBuilder("SHA256withRSA").build(pair.private).let(it::build) }
            .let { JcaX509CertificateConverter().getCertificate(it) }

    return KeyStore.getInstance("PKCS12").apply {
      load(null, password)
      setKeyEntry(ALIAS, pair.private, password, arrayOf<Certificate>(certificate))
    }
  }

  fun certificate(keyStore: KeyStore): X509Certificate =
      keyStore.getCertificate(ALIAS) as X509Certificate

  /**
   * Writes a trust store holding the client's usual roots plus [certificate] to [target].
   *
   * The client validates the feed over TLS, so it needs our certificate. Keeping the original roots
   * means everything else it talks to still validates.
   */
  fun writeTrustStore(certificate: X509Certificate, installDir: Path, target: Path): Path {
    val store = KeyStore.getInstance("PKCS12").apply { load(null, password) }
    baseRoots(installDir)?.let { base ->
      base.aliases().asSequence().filter(base::isCertificateEntry).forEach {
        store.setCertificateEntry(it, base.getCertificate(it))
      }
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
