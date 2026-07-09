package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.net.game.packets.ChatMessagePacketCodec
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacketCodec
import de.fiereu.openmmo.net.game.packets.DialogStatePacket
import de.fiereu.openmmo.net.game.packets.DialogStatePacketCodec
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.EntityLeavePacketCodec
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacketCodec
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.net.game.packets.MovementPacketCodec
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GameCodecRoundtripTest :
    FunSpec({
      test("RenderScreenPacket roundtrip") {
        RenderScreenPacketCodec.encodeToBytes(RenderScreenPacket(true)) shouldBe byteArrayOf(1)
        RenderScreenPacketCodec.decodeBytes(byteArrayOf(0)) shouldBe RenderScreenPacket(false)
      }

      test("ChatMessagePacket decodes golden S2C 0x09 type and message only") {
        val golden =
            hexToBytes(
                "10570065006c0063006f006d006500200074006f00200050006f006b0065004d004d004f002100200045006e006a006f007900200079006f0075007200200073007400610079002e000000")

        val decoded = ChatMessagePacketCodec.decodeBytes(golden)
        val encoded = ChatMessagePacketCodec.encodeToBytes(decoded)

        decoded.type shouldBe ChatType.SYSTEM_ANNOUNCEMENTS
        decoded.message shouldBe "Welcome to PokeMMO! Enjoy your stay."
        decoded.language shouldBe null
        decoded.sender shouldBe null
        encoded shouldBe golden
      }

      test("EntityLeavePacket roundtrip") {
        val pkt = EntityLeavePacket(entityId = 0x0102030405060708L)
        EntityLeavePacketCodec.decodeBytes(EntityLeavePacketCodec.encodeToBytes(pkt)) shouldBe pkt
      }

      test("FaceDirectionPacket roundtrip") {
        val pkt = FaceDirectionPacket(direction = Direction.UP)
        FaceDirectionPacketCodec.decodeBytes(FaceDirectionPacketCodec.encodeToBytes(pkt)) shouldBe
            pkt
      }

      test("MovementPacket roundtrip") {
        val pkt = MovementPacket(x = 100, y = 250, direction = Direction.DOWN)
        MovementPacketCodec.decodeBytes(MovementPacketCodec.encodeToBytes(pkt)) shouldBe pkt
      }

      test("CreateCharacterPacket consumes gender and trailing cosmetics bytes") {
        val packet =
            CreateCharacterPacket("NewGuy", gender = 1, cosmetics = ByteArray(17) { it.toByte() })
        CreateCharacterPacketCodec.decodeBytes(
            CreateCharacterPacketCodec.encodeToBytes(packet)) shouldBe packet
      }

      test("DialogStatePacket roundtrip") {
        DialogStatePacketCodec.encodeToBytes(DialogStatePacket(true)) shouldBe byteArrayOf(1)
        DialogStatePacketCodec.decodeBytes(byteArrayOf(0)) shouldBe DialogStatePacket(false)
      }
    })

private fun hexToBytes(hex: String): ByteArray =
    hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
