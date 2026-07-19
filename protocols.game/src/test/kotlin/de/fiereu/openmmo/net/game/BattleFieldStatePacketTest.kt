package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.Base64

// A two-monster party wild encounter (Snivy active, Patrat benched), scrubbed of capture data. The
// player side carries a per-species stat and move block and a per-slot trailer, all reproduced by
// the codec.
private const val CAPTURED_TWO_PARTY =
    "AgAAAAAAAAAAAAAAAP8AAAAAABYAAAD/ACAABlQAZQBzAHQAAAAAAJABAAAAAAD/AAJMAxqsDwADgAGkAAQAAAAAAAECAAABAMABAAAAAADvAQYAAAAAAAAWABYAAAAA/wMBQQAhACsAAAAAAAABAQDAAgAAAAAA+AECAAABAAAADgAOAAAAAP8DATIAIQAAAAAAAAABAADvAQYAAAAAAAAD/wAAAABmZmZmAQYAAAAAAAEBAAABAMADAAAAAAD4AQIAAAEAAAAOAA4AAAAA/wMAAQAA+AECAAAAAAEAA/8AAAAAZmZmZgAAAAA="

class BattleFieldStatePacketTest :
    FunSpec({
      test("round-trips a two-party wild field state byte for byte") {
        val bytes = Base64.getDecoder().decode(CAPTURED_TWO_PARTY)
        val decoded = BattleFieldStatePacketCodec.decodeBytes(bytes)
        decoded.playerName shouldBe "Test"
        decoded.playerParty.size shouldBe 2
        decoded.playerParty[0].species shouldBe 495.toShort()
        decoded.playerParty[1].species shouldBe 504.toShort()
        decoded.wildMon.species shouldBe 504.toShort()
        BattleFieldStatePacketCodec.encodeToBytes(decoded) shouldBe bytes
      }
    })
