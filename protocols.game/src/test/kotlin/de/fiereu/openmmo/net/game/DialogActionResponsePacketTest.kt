package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionResponsePacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionResponsePacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DialogActionResponsePacketTest :
    FunSpec({
      // The client replies to a 0x21 dialog action with the action id and a response byte. This is
      // the "0c 00" the capture sends back after the "0c" dialog action.
      test("round-trips the captured dialog reply") {
        val bytes = byteArrayOf(0x0c, 0x00)
        val packet = DialogActionResponsePacketCodec.decodeBytes(bytes)
        packet shouldBe DialogActionResponsePacket(id = 0x0c, unk = 0)
        DialogActionResponsePacketCodec.encodeToBytes(packet) shouldBe bytes
      }
    })
