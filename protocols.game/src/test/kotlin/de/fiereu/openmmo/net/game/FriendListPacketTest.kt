package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.FriendListEntry
import de.fiereu.openmmo.net.game.packets.FriendListPacket
import de.fiereu.openmmo.net.game.packets.FriendListPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class FriendListPacketTest :
    FunSpec({
      test("roundtrips entries with mixed online state and variable-length names") {
        val pkt =
            FriendListPacket(
                mode = 0,
                entries =
                    listOf(
                        FriendListEntry(
                            player = 0x0102030405069000L,
                            friendsSince = 1_600_000_000,
                            online = false,
                            name = "Alpha",
                            unk = 0,
                            lastSeen = 1_700_000_000,
                            appearance = listOf(1, 2, 3, 4, 5),
                        ),
                        FriendListEntry(
                            player = 0xA7L,
                            friendsSince = 1_650_000_000,
                            online = true,
                            name = "BravoBravoBravo",
                            unk = 0,
                            lastSeen = 1_710_000_000,
                            appearance = listOf(0, 0, 0, 0, 0),
                        ),
                    ),
            )
        val decoded = FriendListPacketCodec.decodeBytes(FriendListPacketCodec.encodeToBytes(pkt))
        decoded shouldBe pkt
        decoded.entries.map { it.name } shouldBe listOf("Alpha", "BravoBravoBravo")
        decoded.entries.map { it.online } shouldBe listOf(false, true)
      }
    })
