package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.utils.hexToBytes
import de.fiereu.openmmo.common.utils.toHex
import de.fiereu.openmmo.net.game.packets.dialog.CreatureDataArg
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DialogActionPacketTest :
    FunSpec({
      // Every case is a real 0x21 dialog action from the NPC interaction capture.
      val captures =
          listOf(
              "0c2e0d00b52903e008bf86c4c01ab004000000",
              "006400000000ffffffffffffffff0000000000",
              "0e04f90600010ae008bf86c4c01ab004000000",
              "0d04f80600010ae008bf86c4c01ab004000001000992d00300",
          )

      test("round-trips every captured dialog action byte for byte") {
        captures.forEach { hex ->
          val packet = DialogActionPacketCodec.decodeBytes(hex.hexToBytes())
          DialogActionPacketCodec.encodeToBytes(packet).toHex() shouldBe hex
        }
      }

      test("decodes the fields of a dialog action carrying a creature arg") {
        val packet =
            DialogActionPacketCodec.decodeBytes(
                "0d04f80600010ae008bf86c4c01ab004000001000992d00300".hexToBytes())
        packet.flags shouldBe 0x0d.toByte()
        packet.actionType shouldBe 0x04.toByte()
        packet.textId shouldBe 0x010006f8
        packet.entityId shouldBe 0x1ac0c486bf08e00aL
        packet.contextValue shouldBe 0x04b0
        packet.messageArgs shouldBe listOf(CreatureDataArg(0x03d09209, emptyList()))
      }
    })
