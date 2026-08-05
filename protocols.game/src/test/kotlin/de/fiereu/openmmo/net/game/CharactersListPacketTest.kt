package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.ByteArrayReadBuffer
import de.fiereu.bytecodec.U8
import de.fiereu.openmmo.common.utils.hexToBytes
import de.fiereu.openmmo.net.game.codecs.CharacterInfoCodecShort
import de.fiereu.openmmo.net.game.codecs.DefaultSkinSetCodec
import de.fiereu.openmmo.net.game.codecs.PokemonCodec
import de.fiereu.openmmo.net.game.codecs.SkinSetCodecNoLeading
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/** A captured character list. Only the second character has a party, and the third one after it. */
private const val CAPTURED_LIST_32710 =
    "03009008849CEFC41A4D0061006300680065007200520069006E0000000000DA02220101280A696A0C0A696A00000000" +
        "001900000000000000000000000000200001000000000000000000FFFFFFFF0000000000000000000000000200040100" +
        "06000600020000000000FF0000000000004C030BB80E0003180030029C00000000009088C41C56091861007300740072" +
        "0069006400650066006F007500720000000000DA02220100EFCD646A36A69C670000000000B05303007C010000000000" +
        "00000020005C005C030000028D0100028D01000000000000000000000002FF02028E010009000D00000100000000FF00" +
        "00000000024C031AAC0F00038001A400040000000100C008522BEDAF1A0000009088C41C560918009088C41C56091801" +
        "0000EF01BA707DDB009088C41C560918610073007400720069006400650066006F007500720000000000000007180000" +
        "002801000000430021002B0016000000231E19000000000000000000000500000100000000000000040502FFFFFFFF03" +
        "00EFBDF71E00000020000000000000008686536A0000FFFF00000090882BE6F6C01A4D00610063006800650072004400" +
        "6500720000000000DA022201002CF9646AEFF8646A00000000003B0000000000000000000000000020003D0002000000" +
        "0133000201330002000000000000000000000100020133010003000200020100000000FF0000000000034C031E280A00" +
        "02540070002000000000"

class CharactersListPacketTest :
    FunSpec({
      test("a captured client 32710 character list is consumed to the last byte") {
        val bytes = CAPTURED_LIST_32710.hexToBytes()
        bytes.size shouldBe 538

        val buf = ByteArrayReadBuffer(bytes)
        val names = mutableListOf<String>()
        val parties = mutableListOf<Int>()
        repeat(U8.read(buf)) {
          names += CharacterInfoCodecShort.read(buf).name
          DefaultSkinSetCodec.read(buf)
          SkinSetCodecNoLeading.read(buf)
          val hasGuild = buf.readByte().toInt() != 0
          hasGuild shouldBe false
          val party = U8.read(buf)
          parties += party
          repeat(party) { PokemonCodec.read(buf) }
        }

        names shouldBe listOf("MacherRin", "astridefour", "MacherDer")
        parties shouldBe listOf(0, 1, 0)
        buf.remaining() shouldBe 0
      }

      test("a captured client 32710 monster record is consumed to the last byte") {
        // One SocialListEntryAdd payload, which is a bare monster record.
        val bytes =
            ("00C0885FCC11CB1A00000090484E2A11CB1A0090484E2A11CB1A0100000400184204670090484E2A11CB1A4D00610063" +
                    "006800520052000000000000000513000000870000000032000A002D0021003400232823190000000000000000000000" +
                    "000000000000000000580500FFFFFFFF0300EFBDF71E0000002000000000000000EB516F6A0000FFFF0000")
                .hexToBytes()
        bytes.size shouldBe 139

        val buf = ByteArrayReadBuffer(bytes)
        val mon = PokemonCodec.read(buf)

        mon.dexId shouldBe 4
        mon.ot shouldBe "MachRR"
        mon.level shouldBe 5.toByte()
        mon.hp shouldBe 19.toShort()
        mon.moves.map { it.id } shouldBe listOf<Short>(10, 45, 33, 52)
        buf.remaining() shouldBe 0
      }
    })
