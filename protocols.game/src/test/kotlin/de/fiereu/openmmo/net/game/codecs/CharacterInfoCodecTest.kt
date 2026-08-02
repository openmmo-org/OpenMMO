package de.fiereu.openmmo.net.game.codecs

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.CharacterInfo
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class CharacterInfoCodecTest :
    FunSpec({
      test("encodes the captured position block alignment and NDS global coordinates") {
        val timestamp = LocalDateTime.of(2024, 1, 2, 3, 4, 5)
        val info =
            CharacterInfo(
                id = 123,
                name = "Uno",
                userId = 2,
                rivalSex = 1,
                lastLogin = timestamp,
                createdAt = timestamp,
                money = 30000,
                permissions = 8,
                remainingSafariSteps = 0,
                remainingSafariBalls = 0,
                pcExtraSlots = 0,
                battleBoxExtraSlots = 0,
                templateAmount = 0,
                positionRegionId = 2,
                positionBankId = 133.toByte(),
                positionMapId = 1,
                positionX = 782,
                positionY = 749,
                repelLeft = 0,
                repelItemId = 0,
                lureLeft = 0,
                lureItemId = 0,
            )

        val bytes = CharacterInfoCodecShort.encodeToBytes(info)
        val tail = bytes.copyOfRange(bytes.size - 68, bytes.size)

        tail.copyOfRange(45, 56) shouldBe
            byteArrayOf(
                0x02,
                0xff.toByte(),
                0x02,
                0x02,
                0x85.toByte(),
                0x01,
                0x00,
                0x0e,
                0x03,
                0xed.toByte(),
                0x02,
            )
        CharacterInfoCodecShort.decodeBytes(bytes) shouldBe info
      }
    })
