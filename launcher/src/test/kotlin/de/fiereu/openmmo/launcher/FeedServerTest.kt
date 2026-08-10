package de.fiereu.openmmo.launcher

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.StringReader
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.KeyFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.Signature
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import org.bouncycastle.util.io.pem.PemReader

private fun pem(path: String): ByteArray =
    FeedServerTest::class.java.getResourceAsStream(path)!!.use {
      PemReader(StringReader(it.readBytes().decodeToString())).readPemObject().content
    }

class FeedServerTest :
    FunSpec({
      test("serves a signed feed pointing at the local login server") {
        val keyStore = FeedTls.keyStore()
        val signingKey =
            KeyFactory.getInstance("RSA")
                .generatePrivate(PKCS8EncodedKeySpec(pem("/feed.private.pem")))
        val server = FeedServer(signingKey, keyStore)
        server.publish(32710)
        server.start()

        try {
          val client = HttpClient.newBuilder().sslContext(trusting(server.certificate())).build()
          val base = "https://$LOOPBACK:${server.port}"

          val feed = client.send(get("$base/$MAIN_NAME"), HttpResponse.BodyHandlers.ofByteArray())
          feed.statusCode() shouldBe 200
          val xml = feed.body().decodeToString()
          xml shouldContain "<ip>127.0.0.1</ip>"
          xml shouldContain "<port>2106</port>"
          xml shouldContain "<revision>32710</revision>"

          val signature =
              client.send(get("$base/$SIGNATURE_NAME"), HttpResponse.BodyHandlers.ofByteArray())
          signature.statusCode() shouldBe 200
          verified(feed.body(), signature.body()) shouldBe true

          val news = client.send(get("$base/$NEWS_NAME"), HttpResponse.BodyHandlers.ofByteArray())
          news.statusCode() shouldBe 200
          news.body().decodeToString() shouldContain "<rss"
        } finally {
          server.stop()
        }
      }
    })

private fun get(url: String): HttpRequest = HttpRequest.newBuilder(URI(url)).GET().build()

private fun trusting(certificate: X509Certificate): SSLContext {
  val store = KeyStore.getInstance("PKCS12").apply { load(null, FeedTls.password) }
  store.setCertificateEntry("feed", certificate)
  val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
  factory.init(store)
  return SSLContext.getInstance("TLSv1.3").apply {
    init(null, factory.trustManagers, SecureRandom())
  }
}

private fun verified(body: ByteArray, signature: ByteArray): Boolean {
  val publicKey =
      KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(pem("/feed.public.pem")))
  return Signature.getInstance("SHA256withRSA").run {
    initVerify(publicKey)
    update(body)
    verify(signature)
  }
}
