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

  /**
   * The player **character** entity id (8-byte LE @47) — the overworld character the party belongs
   * to. Session-sourced (= the logged-in character's entity id) so a live battle references the
   * same entity the client already knows from the overworld, not the captured sample's character.
   */
  val playerCharEntityId: Long
    get() = u64le(47)

  /** The player **active-mon** entity id (8-byte LE @82); every S2C event must reuse this id. */
  val playerMonEntityId: Long
    get() = u64le(82)

  /** The **wild-mon** entity id (8-byte LE @153); every S2C event must reuse this id. */
  val wildMonEntityId: Long
    get() = u64le(153)

  private fun u16le(off: Int): Int =
      (raw[off].toInt() and 0xFF) or ((raw[off + 1].toInt() and 0xFF) shl 8)

  private fun u64le(off: Int): Long {
    var v = 0L
    for (i in 0 until 8) v = v or ((raw[off + i].toLong() and 0xFF) shl (8 * i))
    return v
  }

  override fun equals(other: Any?): Boolean =
      other is BattleOpenPacket && raw.contentEquals(other.raw)

  override fun hashCode(): Int = raw.contentHashCode()

  override fun toString(): String =
      "BattleOpenPacket(player=#$playerSpecies L$playerLevel $playerCurrentHp/$playerMaxHp, " +
          "wild=#$wildSpecies L$wildLevel $wildCurrentHp/$wildMaxHp)"

  companion object {
    /**
     * Entity ids baked into the captured template (capture #1: `captures/2026-07-07-232143`). Used
     * as [wild] defaults so omitting the ids reproduces the captured bytes byte-exact (unit tests);
     * a live battle overrides them with session-assigned ids.
     */
    const val TEMPLATE_PLAYER_CHAR_ENTITY_ID = 0x19a545e0afc89000L
    const val TEMPLATE_PLAYER_MON_ENTITY_ID = 0x19a54c55dd88c000L
    const val TEMPLATE_WILD_MON_ENTITY_ID = 0x1aaa78ad8908c000L

    // Byte offsets of the three 8-byte LE entity ids inside the 206B template.
    private const val OFF_PLAYER_CHAR_ENTITY = 47
    private const val OFF_PLAYER_MON_ENTITY = 82
    private const val OFF_WILD_MON_ENTITY = 153

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
     * Build a wild battle-open by patching the validated mon fields + the three per-battle entity
     * ids onto the captured template. The entity ids default to the captured template's ids (so a
     * plain `wild(species…)` call is byte-exact vs the capture); a live battle passes
     * session-assigned ids — the SAME ids that must key every subsequent S2C event packet.
     *
     * TODO(capture): the stat/status regions are still template-derived — refine as more captures
     *   land; player name is still template-derived pending a session-name source.
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
        playerCharEntityId: Long = TEMPLATE_PLAYER_CHAR_ENTITY_ID,
        playerMonEntityId: Long = TEMPLATE_PLAYER_MON_ENTITY_ID,
        wildMonEntityId: Long = TEMPLATE_WILD_MON_ENTITY_ID,
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
      putU64le(b, OFF_PLAYER_CHAR_ENTITY, playerCharEntityId)
      putU64le(b, OFF_PLAYER_MON_ENTITY, playerMonEntityId)
      putU64le(b, OFF_WILD_MON_ENTITY, wildMonEntityId)
      return BattleOpenPacket(b)
    }

    private fun putU16le(b: ByteArray, off: Int, v: Int) {
      b[off] = (v and 0xFF).toByte()
      b[off + 1] = ((v shr 8) and 0xFF).toByte()
    }

    private fun putU64le(b: ByteArray, off: Int, v: Long) {
      for (i in 0 until 8) b[off + i] = ((v shr (8 * i)) and 0xFF).toByte()
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
