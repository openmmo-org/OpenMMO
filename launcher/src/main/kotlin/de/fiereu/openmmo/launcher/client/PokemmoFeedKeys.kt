package de.fiereu.openmmo.launcher.client

import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

// Only the first signs the live feeds. The second is here because the client trusts it too.
val POKEMMO_FEED_PUBLIC_KEYS =
    listOf(
        "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAyfYQx1kSfIVGdGzcHmVVP7cbyLsMXGdLhwMnx2AD1MYgU170iFN5gHT+U248rH10L6D1UMlZK1LfCsbPkdQOir3C+8Do212NONyNm/7+ZGeIwbpy+jxEQH8Jfn4JYY7+Sn4qg249yW7DSY+XKvTOcphoXRNzSQp8u6IVj03mIw7zDA0SqMMFtnCXVP3NRmtjK1SuVVFLltFctz1Pp7f9uqgqnFlgD2l8/THnddTRM5IR6O9pbOXu7My0+Jli6+4zJgw5gQvgivYPCeess9gWRqpw66VTpMJERJYA6AIbVierAbjGmtRETRsHUOGAgo54G0oxtXXEaTWXF6n6mdgSE2Ra8q7P23stsSWU3mDNQjXO0XOhtAKQCZfvICxmsH3ed5hm8bEC5yga8z8m0vyZ71fWzP4Q3g6B+o6oDsMX1nWbV2GEHci/6nwFofgOJkLINaZfUTivAIRuxECVwjTTa7ruRNgFlA2ciGUIIke2Ev2cYzyBA4LLARky2FZiEM0VAgMBAAE=",
        "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAyNb7iGEOL8/7hBkzvSm0edntPPO6/oaXlsl+1/OKukUifNnLlx+ApInkJyPy7PK6ds6R5C8liCvEEBD6G4/TRi8riG/8pIOCFAe/bjyBoXodzSJ2NRvArW6xVxax6Dpl5/tpsIBOSQDYH/BUXVZZM+DbIAbJhgvxK3r1eth7vO+kj3VLBVzJTrYpzEUz+cW+SxukOsDzHWxuUcogCEC3tzY0M3f/jMv8yrO/xVlWTzn7orrZtg0JfBMOs59NLLPKOlfB2Dtxg517Pbg2knpnNliKYE8vXwcYEJDb1jxgicqV4sYTNwu7MPSBEnYsPpBqbwbDIRI4Nrigqr+9D6Q7LPZjbI8JUZIJ1+pyYtXkSNOnnpbBPyT5WCMrviNDgocct+/PpzlYnwGRjGusqbR+6OTY9U2rptNETyK+0kmmNqxWCxAH93MqgiJk+WonkIaA5zkRlhpstlhIBwrQiYZWY6XgOMRvrdUom8FYLYKRIwRvoTRQG92gI8+WCL8+sjCpAgMBAAE=",
    )

fun pokemmoFeedPublicKeys(): List<PublicKey> {
  val factory = KeyFactory.getInstance("RSA")
  return POKEMMO_FEED_PUBLIC_KEYS.map {
    factory.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(it)))
  }
}
