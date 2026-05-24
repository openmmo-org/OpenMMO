package de.fiereu.bytecodec

import de.fiereu.bytecodec.test.assertBytesRoundtrip
import de.fiereu.bytecodec.test.assertValueRoundtrip
import de.fiereu.bytecodec.test.encodeToBytes
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private data class GameServer(
    val id: Int,
    val name: String,
    val currentPlayers: Int,
    val maxPlayers: Int,
    val joinable: Boolean,
)

private object GameServerCodec : PacketCodec<GameServer>() {
  override fun CodecScope<GameServer>.body() =
      GameServer(
          id = field(U8, GameServer::id),
          name = field(Utf16LeNullTerminated, GameServer::name),
          currentPlayers = field(U16LE, GameServer::currentPlayers),
          maxPlayers = field(U16LE, GameServer::maxPlayers),
          joinable = field(Bool, GameServer::joinable),
      )
}

private data class LoadEntityPacket(
    val entityId: Long,
    val name: String,
    val gateA: ByteArray?,
    val gateB: Int?,
) {
  override fun equals(other: Any?): Boolean =
      other is LoadEntityPacket &&
          entityId == other.entityId &&
          name == other.name &&
          (gateA?.contentEquals(other.gateA) ?: (other.gateA == null)) &&
          gateB == other.gateB

  override fun hashCode(): Int = entityId.hashCode()
}

private object LoadEntityCodec : PacketCodec<LoadEntityPacket>() {
  override fun CodecScope<LoadEntityPacket>.body(): LoadEntityPacket {
    val entityId = field(S64BE, LoadEntityPacket::entityId)
    reserved(0)
    val name = field(Utf16LeNullTerminated, LoadEntityPacket::name)
    val flags =
        field(U16LE) {
          var f = 0
          if (it.gateA != null) f = f or 0x01
          if (it.gateB != null) f = f or 0x02
          f
        }
    val gateA = if (flags and 0x01 != 0) field(unknownBytes(2)) { it.gateA!! } else null
    val gateB = if (flags and 0x02 != 0) field(U32LE) { it.gateB!!.toLong() }.toInt() else null
    return LoadEntityPacket(entityId, name, gateA, gateB)
  }
}

class PacketCodecTest :
    FunSpec({
      test("simple PacketCodec roundtrip") {
        val pkt =
            GameServer(
                id = 1, name = "alpha", currentPlayers = 10, maxPlayers = 100, joinable = true)
        GameServerCodec.assertValueRoundtrip(pkt)
      }

      test("byte-level layout of GameServer") {
        val bytes = GameServerCodec.encodeToBytes(GameServer(7, "ab", 3, 9, false))
        bytes shouldBe
            byteArrayOf(
                0x07,
                0x61,
                0x00,
                0x62,
                0x00,
                0x00,
                0x00,
                0x03,
                0x00,
                0x09,
                0x00,
                0x00,
            )
      }

      test("complex packet with reserved, flags, optional sections") {
        LoadEntityCodec.assertValueRoundtrip(
            LoadEntityPacket(42L, "world", gateA = null, gateB = null),
        )
        LoadEntityCodec.assertValueRoundtrip(
            LoadEntityPacket(
                entityId = 0x0102030405060708L,
                name = "x",
                gateA = byteArrayOf(0xAA.toByte(), 0xBB.toByte()),
                gateB = 0x11223344,
            ),
        )
      }

      test("structural reserved bytes survive bytes roundtrip") {
        val pkt = LoadEntityPacket(1L, "y", null, null)
        val bytes = LoadEntityCodec.encodeToBytes(pkt)
        LoadEntityCodec.assertBytesRoundtrip(bytes)
      }
    })
