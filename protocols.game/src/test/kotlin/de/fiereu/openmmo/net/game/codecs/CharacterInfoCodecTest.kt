package de.fiereu.openmmo.net.game.codecs

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.net.game.packets.CharactersListPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class CharacterInfoCodecTest :
    FunSpec({
      test("CharactersListPacket decodes golden v31914 0x02 capture") {
        val packet =
            CharactersListPacketCodec.decodeBytes(
                hexToBytes(
                    "010090c8afe045a5194f007400680065007200420061006700000000005d933c010013944d6aea78426900000000006e6d0000f802000000000000000020005b003a03000002860100028601000000000000000000000002ff02028501000e03ed02000000000000ff0000000000005c0326fc0afc0f140200008000140000000100c088dd554ca51900000090c8afe045a5190090c8afe045a519010000f501aef0421a0090c8afe045a5194f0074006800650072004200610067000000000000000614000000de000000003b0021002700000000001f1e00000000000000000000010100010000000000000000040502ffffffff0300efbdf71e0100002000000000000000877f42690000ffff00"))

        packet.characters.size shouldBe 1
        packet.characters.first().characterInfo.name shouldBe "OtherBag"
        packet.characters.first().characterInfo.money shouldBe 760
        packet.characters.first().pokemon.size shouldBe 1
        packet.characters.first().pokemon.first().dexId shouldBe 501
      }

      test("CharacterInfoCodecShort writes v31914 109-byte layout placeholders") {
        val bytes = CharacterInfoCodecShort.encodeToBytes(sampleCharacterInfo())

        bytes.size shouldBe 109
        le32(bytes, 41) shouldBe 0
        bytes[45] shouldBe 0.toByte()
        le32(bytes, 46) shouldBe 28014
        bytes[62] shouldBe 0x5b.toByte()
        le32(bytes, 63) shouldBe 211456
        bytes.copyOfRange(67, 75) shouldBe
            byteArrayOf(0x00, 0x02, 0x86.toByte(), 0x01, 0x00, 0x02, 0x86.toByte(), 0x01)
        bytes[86] shouldBe 2.toByte()
        bytes[87] shouldBe (-1).toByte()
        bytes[91] shouldBe 1.toByte()
        bytes[96] shouldBe 2.toByte()
      }
    })

private fun sampleCharacterInfo(): CharacterInfo =
    CharacterInfo(
        id = 0x19a545e0afc89000L,
        name = "OtherBag",
        namePrefix = "",
        userId = 20747101,
        rivalSex = 0,
        lastLogin = LocalDateTime.ofEpochSecond(1783469075, 0, java.time.ZoneOffset.UTC),
        createdAt = LocalDateTime.ofEpochSecond(1765964010, 0, java.time.ZoneOffset.UTC),
        money = 760,
        permissions = 32,
        remainingSafariSteps = 0,
        remainingSafariBalls = 0,
        pcExtraSlots = 0,
        battleBoxExtraSlots = 0,
        templateAmount = 0,
        positionRegionId = 2,
        positionBankId = 2,
        positionMapId = (-123).toByte(),
        positionX = 0x0e00,
        positionY = (-4859).toShort(),
        repelLeft = 0,
        repelItemId = 0,
        lureLeft = 0,
        lureItemId = 255,
    )

private fun hexToBytes(hex: String): ByteArray =
    hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()

private fun le32(bytes: ByteArray, offset: Int): Int =
    (bytes[offset].toInt() and 0xff) or
        ((bytes[offset + 1].toInt() and 0xff) shl 8) or
        ((bytes[offset + 2].toInt() and 0xff) shl 16) or
        ((bytes[offset + 3].toInt() and 0xff) shl 24)
