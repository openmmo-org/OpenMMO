package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.enums.Direction
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

      test("DialogStatePacket roundtrip") {
        DialogStatePacketCodec.encodeToBytes(DialogStatePacket(true)) shouldBe byteArrayOf(1)
        DialogStatePacketCodec.decodeBytes(byteArrayOf(0)) shouldBe DialogStatePacket(false)
      }
    })
