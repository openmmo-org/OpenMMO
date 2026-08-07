package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.net.game.packets.gtl.GtlFilterKind
import de.fiereu.openmmo.net.game.packets.gtl.GtlListKind
import de.fiereu.openmmo.net.game.packets.gtl.GtlSearchPageRequestPacketCodec
import de.fiereu.openmmo.net.game.packets.gtl.GtlSpeciesFilter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GtlSearchPageRequestPacketTest :
    FunSpec({
      test("an unfiltered search carries no filters") {
        val buf = fixture("game/c2s/9b/own_listings_request_31914.bin")
        val request = GtlSearchPageRequestPacketCodec.read(buf)

        request.requestId shouldBe 3.toByte()
        request.listKind shouldBe GtlListKind.OWN_LISTINGS
        request.categoryId shouldBe 13.toByte()
        request.page shouldBe 0.toShort()
        request.filters shouldBe emptyList()
        buf.remaining() shouldBe 0
      }

      test("a search filtered to one species carries its dex id") {
        val buf = fixture("game/c2s/9b/one_species_filter_32710.bin")
        val request = GtlSearchPageRequestPacketCodec.read(buf)

        request.requestId shouldBe 4.toByte()
        request.filters shouldBe listOf(GtlSpeciesFilter(listOf<Short>(132)))
        buf.remaining() shouldBe 0
      }

      test("a search filtered to thirteen species carries all of them") {
        val buf = fixture("game/c2s/9b/thirteen_species_filter_32710.bin")
        val request = GtlSearchPageRequestPacketCodec.read(buf)

        request.listKind shouldBe GtlListKind.ITEM
        val species = request.filters.single() as GtlSpeciesFilter
        species.speciesIds.size shouldBe 13
        species.speciesIds.first() shouldBe 1118.toShort()
        buf.remaining() shouldBe 0
      }

      test("a filtered search carries one entry per filter") {
        val buf = fixture("game/c2s/9b/eleven_filters_32710.bin")
        val request = GtlSearchPageRequestPacketCodec.read(buf)

        request.filters.size shouldBe 11
        request.filters.first() shouldBe GtlSpeciesFilter(listOf<Short>(132))
        request.filters.map { it.kind } shouldBe
            listOf(
                GtlFilterKind.SPECIES,
                GtlFilterKind.UNKNOWN_13,
                GtlFilterKind.UNKNOWN_12,
                GtlFilterKind.NATURE,
                GtlFilterKind.GENDER,
                GtlFilterKind.IV,
                GtlFilterKind.IV,
                GtlFilterKind.IV,
                GtlFilterKind.IV,
                GtlFilterKind.IV,
                GtlFilterKind.IV,
            )
        buf.remaining() shouldBe 0
      }
    })
