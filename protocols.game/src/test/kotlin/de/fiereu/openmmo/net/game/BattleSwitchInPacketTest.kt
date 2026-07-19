package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInMon
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private fun ByteArray.hex(): String = joinToString("") { "%02x".format(it) }

class BattleSwitchInPacketTest :
    FunSpec({
      // Switching back to an already-active monster sends only the 21-byte active detail.
      // Reproduced
      // byte for byte from a real capture (Snivy, slot 0, previous slot 1).
      test("encodes a return switch-in as just the active detail") {
        val packet =
            BattleSwitchInPacket(
                newSlot = 0,
                oldSlot = 1,
                mon = BattleSwitchInMon(entityId = 0, species = 495, hp = 0),
                fullBlock = false,
            )
        val bytes = BattleSwitchInPacketCodec.encodeToBytes(packet)
        bytes.hex() shouldBe "000000010000ef010600000000000003ff0000000066666666"
      }

      // A benched monster coming out carries its full block then its active detail.
      test("round-trips a full-block switch-in") {
        val packet =
            BattleSwitchInPacket(
                newSlot = 1,
                oldSlot = 0,
                mon = BattleSwitchInMon(entityId = 0x000000000003C000L, species = 504, hp = 14),
                fullBlock = true,
            )
        val bytes = BattleSwitchInPacketCodec.encodeToBytes(packet)
        bytes.size shouldBe 65
        val decoded = BattleSwitchInPacketCodec.decodeBytes(bytes)
        decoded.newSlot shouldBe 1
        decoded.oldSlot shouldBe 0
        decoded.fullBlock shouldBe true
        decoded.mon.species shouldBe 504.toShort()
        decoded.mon.hp shouldBe 14.toShort()
      }
    })
