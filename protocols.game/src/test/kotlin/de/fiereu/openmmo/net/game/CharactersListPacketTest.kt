package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.U8
import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.codecs.CharacterInfoCodecShort
import de.fiereu.openmmo.net.game.codecs.DefaultSkinSetCodec
import de.fiereu.openmmo.net.game.codecs.PokemonCodec
import de.fiereu.openmmo.net.game.codecs.SkinSetCodecNoLeading
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CharactersListPacketTest :
    FunSpec({
      test("a captured client 32710 character list is consumed to the last byte") {
        val buf = fixtureBuffer("game/s2c/02/character_list_32710.bin")
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
        // A SocialListEntryAdd payload, which is a bare monster record.
        val buf = fixtureBuffer("game/s2c/14/monster_record_32710.bin")
        val mon = PokemonCodec.read(buf)

        mon.dexId shouldBe 4
        mon.ot shouldBe "MachRR"
        mon.level shouldBe 5.toByte()
        mon.hp shouldBe 19.toShort()
        mon.moves.map { it.id } shouldBe listOf<Short>(10, 45, 33, 52)
        buf.remaining() shouldBe 0
      }
    })
