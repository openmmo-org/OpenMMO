package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.enums.SkinSlot
import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.common.utils.toHex
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CreateCharacterPacketTest :
    FunSpec({
      test("decodes the captured female Hoenn character creation") {
        val bytes = fixture("game/c2s/03/female_hoenn.bin")

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

      test("splits a packed slot into a ten bit type and a six bit color") {
        val bytes = fixture("game/c2s/03/female_hoenn.bin")

        val packet = CreateCharacterPacketCodec.decodeBytes(bytes)

        val hair = packet.appearance[SkinSlot.HAIR]!!
        hair.type shouldBe 4.toUShort()
        hair.color shouldBe 24.toUByte()
        val leggings = packet.appearance[SkinSlot.LEGGINGS]!!
        leggings.type shouldBe 0.toUShort()
        leggings.color shouldBe 33.toUByte()
      }

      test("roundtrips the new male Hoenn character creation capture") {
        val bytes = fixture("game/c2s/03/male_hoenn_32710.bin")

        val packet = CreateCharacterPacketCodec.decodeBytes(bytes)

        packet.name shouldBe "MacherDer"
        packet.gender shouldBe 0
        packet.startingRegion shouldBe 1
        packet.appearance.regionSelectionIndex shouldBe 3
        CreateCharacterPacketCodec.encodeToBytes(packet).toHex() shouldBe bytes.toHex()
      }

      test("roundtrips the independent female Kanto character creation capture") {
        val bytes = fixture("game/c2s/03/female_kanto_32710.bin")

        val packet = CreateCharacterPacketCodec.decodeBytes(bytes)

        packet.name shouldBe "MacherRin"
        packet.gender shouldBe 1
        packet.startingRegion shouldBe 0
        packet.appearance.regionSelectionIndex shouldBe 0
        CreateCharacterPacketCodec.encodeToBytes(packet).toHex() shouldBe bytes.toHex()
      }
    })
