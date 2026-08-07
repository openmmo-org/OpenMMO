package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.common.utils.toHex
import de.fiereu.openmmo.net.game.packets.dialog.CreatureDataArg
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionPacketCodec
import de.fiereu.openmmo.net.game.packets.dialog.TextPokemonSpeciesArg
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DialogActionPacketTest :
    FunSpec({
      val captures =
          listOf(
              "flags_0c_type_2e",
              "flags_00_type_64",
              "flags_0e_type_04",
              "creature_arg",
              "species_arg",
          )

      test("round-trips every captured dialog action byte for byte") {
        captures.forEach { name ->
          val bytes = fixture("game/s2c/21/$name.bin")
          val packet = DialogActionPacketCodec.decodeBytes(bytes)
          DialogActionPacketCodec.encodeToBytes(packet).toHex() shouldBe bytes.toHex()
        }
      }

      test("decodes the fields of a dialog action carrying a creature arg") {
        val packet = DialogActionPacketCodec.decodeBytes(fixture("game/s2c/21/creature_arg.bin"))
        packet.flags shouldBe 0x0d.toByte()
        packet.actionType shouldBe 0x04.toByte()
        packet.textId shouldBe 0x010006f8
        packet.entityId shouldBe 0x1ac0c486bf08e00aL
        packet.contextValue shouldBe 0x04b0
        packet.messageArgs shouldBe listOf(CreatureDataArg(0x03d09209, emptyList()))
      }

      test("species-name argument uses the captured party slot variable and species order") {
        val bytes = fixture("game/s2c/21/species_arg.bin")
        val packet = DialogActionPacketCodec.decodeBytes(bytes)

        packet.messageArgs shouldBe listOf(TextPokemonSpeciesArg(1, 1, 255))
        DialogActionPacketCodec.encodeToBytes(packet) shouldBe bytes
      }
    })
