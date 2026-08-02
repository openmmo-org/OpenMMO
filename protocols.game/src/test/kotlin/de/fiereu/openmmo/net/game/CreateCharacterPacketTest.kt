package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.common.enums.SkinSlot
import de.fiereu.openmmo.common.utils.hexToBytes
import de.fiereu.openmmo.common.utils.toHex
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CreateCharacterPacketTest :
    FunSpec({
      test("decodes the captured female Hoenn character creation") {
        val bytes =
            "4c0061006e0061006e0061005400650073007400540077006f0000000101014c0304600f00030800680084"
                .hexToBytes()

        val packet = CreateCharacterPacketCodec.decodeBytes(bytes)

        packet.name shouldBe "LananaTestTwo"
        packet.gender shouldBe 1
        packet.startingRegion shouldBe 1
        packet.appearance.keys shouldBe
            setOf(
                SkinSlot.HAIR,
                SkinSlot.EYES,
                SkinSlot.TOP,
                SkinSlot.FOOTWEAR,
                SkinSlot.LEGGINGS,
            )
        CreateCharacterPacketCodec.encodeToBytes(packet).toHex() shouldBe bytes.toHex()
      }

      test("roundtrips the new male Hoenn character creation capture") {
        val bytes =
            "4d006100630068006500720044006500720000000001034c031e280a00025400700020".hexToBytes()

        val packet = CreateCharacterPacketCodec.decodeBytes(bytes)

        packet.name shouldBe "MacherDer"
        packet.gender shouldBe 0
        packet.startingRegion shouldBe 1
        packet.appearance.regionSelectionIndex shouldBe 3
        CreateCharacterPacketCodec.encodeToBytes(packet).toHex() shouldBe bytes.toHex()
      }

      test("roundtrips the independent female Kanto character creation capture") {
        val bytes =
            "4d0061006300680065007200520069006e0000000100004c030bb80e0003180030029c".hexToBytes()

        val packet = CreateCharacterPacketCodec.decodeBytes(bytes)

        packet.name shouldBe "MacherRin"
        packet.gender shouldBe 1
        packet.startingRegion shouldBe 0
        packet.appearance.regionSelectionIndex shouldBe 0
        CreateCharacterPacketCodec.encodeToBytes(packet).toHex() shouldBe bytes.toHex()
      }
    })
