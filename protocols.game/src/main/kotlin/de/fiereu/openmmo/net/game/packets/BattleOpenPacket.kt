package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.WriteBuffer

/**
 * S2C `0x30` — **wild battle open / battle-init** (206B for a 1v1 wild battle).
 *
 * VALIDATED against `captures/2026-07-07-232143-first-manual.log` (two samples: a fled encounter
 * and the Patrat catch). NOTE: opcode `0x30` is **direction-specific** — C2S `0x30` is
 * `BattleActionPacket`; this is the previously-undefined **S2C** `0x30` that opens the battle.
 *
 * The packet is treated as a raw byte payload (round-trips byte-exact) with typed accessors for the
 * fields confirmed against real bytes. Many header/prelude/marker bytes are
 * fixed-but-not-yet-decoded (`0x66666666` markers, stat blocks); construction patches only the
 * validated fields onto a captured template, guaranteeing a structurally client-valid packet. Byte
 * layout: docs/BATTLE-PACKET-MAP.md.
 *
 * Validated field offsets (LE): player mon species@90 u16, level@92 u8, curHp@99 u16, maxHp@101
 * u16; wild mon species@161 u16, level@163 u8, curHp@170 u16, maxHp@172 u16.
 */
class BattleOpenPacket(val raw: ByteArray) {

  val playerSpecies: Int
    get() = u16le(90)

  val playerLevel: Int
    get() = raw[92].toInt() and 0xFF

  val playerCurrentHp: Int
    get() = u16le(99)

  val playerMaxHp: Int
    get() = u16le(101)

  val wildSpecies: Int
    get() = u16le(161)

  val wildLevel: Int
    get() = raw[163].toInt() and 0xFF

  val wildCurrentHp: Int
    get() = u16le(170)

  val wildMaxHp: Int
    get() = u16le(172)

  private fun u16le(off: Int): Int =
      (raw[off].toInt() and 0xFF) or ((raw[off + 1].toInt() and 0xFF) shl 8)

  override fun equals(other: Any?): Boolean =
      other is BattleOpenPacket && raw.contentEquals(other.raw)

  override fun hashCode(): Int = raw.contentHashCode()

  override fun toString(): String =
      "BattleOpenPacket(player=#$playerSpecies L$playerLevel $playerCurrentHp/$playerMaxHp, " +
          "wild=#$wildSpecies L$wildLevel $wildCurrentHp/$wildMaxHp)"

  companion object {
    /**
     * Captured 206B template (the Patrat-catch sample). Fixed regions (header, player name/entity,
     * stat blocks, markers) are preserved from real bytes; [wild] patches the validated mon fields.
     */
    private val TEMPLATE: ByteArray =
        hexToBytes(
            "02000000000000000000000000ff000000000016000000ff002000064f00740068006500" +
                "72004200610067000000000090c8afe045a519ff00005c0326fc0afc0f140200008000" +
                "140000000000010100000100c088dd554ca519f501070000000000000b001900000000" +
                "ff030143002100270037000000010000f5010700000000000003ff0000000066666666" +
                "01060000000000010100000100c00889ad78aa1af8010400000000000012001200000000" +
                "ff0300010000f8010400000000000003ff000000006666666600000000",
        )

    /**
     * Build a wild battle-open by patching the validated mon fields onto the captured template.
     *
     * TODO(session): parameterize player name + entity ids from the live session; TODO(capture):
     *   the stat/status regions are still template-derived — refine as more captures land.
     */
    fun wild(
        playerSpecies: Int,
        playerLevel: Int,
        playerCurrentHp: Int,
        playerMaxHp: Int,
        wildSpecies: Int,
        wildLevel: Int,
        wildCurrentHp: Int,
        wildMaxHp: Int,
    ): BattleOpenPacket {
      val b = TEMPLATE.copyOf()
      putU16le(b, 90, playerSpecies)
      b[92] = playerLevel.toByte()
      putU16le(b, 99, playerCurrentHp)
      putU16le(b, 101, playerMaxHp)
      putU16le(b, 161, wildSpecies)
      b[163] = wildLevel.toByte()
      putU16le(b, 170, wildCurrentHp)
      putU16le(b, 172, wildMaxHp)
      return BattleOpenPacket(b)
    }

    private fun putU16le(b: ByteArray, off: Int, v: Int) {
      b[off] = (v and 0xFF).toByte()
      b[off + 1] = ((v shr 8) and 0xFF).toByte()
    }

    private fun hexToBytes(hex: String): ByteArray =
        ByteArray(hex.length / 2) {
          ((hex[it * 2].digitToInt(16) shl 4) or hex[it * 2 + 1].digitToInt(16)).toByte()
        }
  }
}

/** Raw-faithful codec: reads all remaining bytes, writes them back byte-exact. */
object BattleOpenPacketCodec : PacketCodec<BattleOpenPacket>() {
  private val payload: Codec<ByteArray> =
      object : Codec<ByteArray> {
        override fun read(buf: ReadBuffer): ByteArray {
          val arr = ByteArray(buf.remaining())
          if (arr.isNotEmpty()) buf.readBytes(arr)
          return arr
        }

        override fun write(buf: WriteBuffer, value: ByteArray) {
          if (value.isNotEmpty()) buf.writeBytes(value)
        }
      }

  override fun CodecScope<BattleOpenPacket>.body(): BattleOpenPacket =
      BattleOpenPacket(field(payload) { it.raw })
}
