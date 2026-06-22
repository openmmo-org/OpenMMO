package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.GuildMembershipPacket
import de.fiereu.openmmo.net.game.packets.GuildMembershipPacketCodec
import de.fiereu.openmmo.net.game.packets.GuildProfileData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GuildMembershipPacketTest :
    FunSpec({
      test("not in a guild encodes to a single flag byte") {
        val pkt = GuildMembershipPacket(inGuild = false, profile = null)
        GuildMembershipPacketCodec.encodeToBytes(pkt) shouldBe byteArrayOf(0)
        GuildMembershipPacketCodec.decodeBytes(byteArrayOf(0)) shouldBe pkt
      }

      test("in a guild roundtrips the full profile") {
        val pkt =
            GuildMembershipPacket(
                inGuild = true,
                profile =
                    GuildProfileData(
                        guildId = 0x1122334455660000L,
                        name = "Knights",
                        tag = "KNT",
                        foundedAt = 1_700_000_000,
                        message = "Your Team has been successfully created!",
                        updatedAt = 1_700_000_027,
                        value1 = 5,
                        value2 = 5,
                        value3 = 5,
                        value4 = 0,
                        value5 = 0,
                        unk1 = 0,
                        rankCount = 6,
                        unk2 = 0,
                        unk3 = 0,
                        flag = 0,
                    ),
            )
        GuildMembershipPacketCodec.decodeBytes(
            GuildMembershipPacketCodec.encodeToBytes(pkt)) shouldBe pkt
      }
    })
