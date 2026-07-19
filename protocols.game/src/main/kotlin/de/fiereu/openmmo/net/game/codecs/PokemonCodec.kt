package de.fiereu.openmmo.net.game.codecs

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.*

private fun seg(hex: String): ByteArray =
    ByteArray(hex.length / 2) { hex.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

private fun reserved(hex: String): Codec<Unit> =
    object : Codec<Unit> {
      private val bytes = seg(hex)

      override fun read(buf: ReadBuffer) {
        if (bytes.isNotEmpty()) buf.readBytes(ByteArray(bytes.size))
      }

      override fun write(buf: WriteBuffer, value: Unit) {
        buf.writeBytes(bytes)
      }
    }

// Fixed 4-byte U8 list at the start of trailer A, purpose unknown, always zero here.
private const val LIST4 = "00000000"
// Trailer A bytes between the EVs and the IV word, purpose unknown (includes the ff/03 markers).
private const val TAIL_A_REST = "00000100000000000000040502ffffffff0300"
// Trailer B long that follows the unknown byte, purpose unknown. Species-dependent, so the captured
// value is written back for now.
private const val TRAILER_B_LONG = 0x200000L
// Record end: unknown bytes closed by a U8 list count, which is zero (empty list) in every capture.
private const val TRAILER = "00ffff00"

private fun packRarity(p: Pokemon): Int =
    (if (p.isShiny) PokemonRarityFlag.SHINY.mask else 0) or
        (if (p.hasHiddenAbility) PokemonRarityFlag.HIDDEN_ABILITY.mask else 0) or
        (if (p.isAlpha) PokemonRarityFlag.ALPHA.mask else 0) or
        (if (p.isSecret) PokemonRarityFlag.SECRET_SHINY.mask else 0) or
        (if (p.isFatefulEncounter) PokemonRarityFlag.FATEFUL_ENCOUNTER.mask else 0) or
        (if (p.isRaidEncounter) PokemonRarityFlag.RAID_ENCOUNTER.mask else 0)

// EVs are six raw bytes in wire order hp, atk, def, spd, spAtk, spDef. Bypasses the checked stat
// setters, which reject a zero value.
private fun evsFromWire(hp: Int, atk: Int, def: Int, spd: Int, spAtk: Int, spDef: Int): EVs =
    EVs().apply {
      put(PokemonStat.HP, hp.toByte())
      put(PokemonStat.ATTACK, atk.toByte())
      put(PokemonStat.DEFENSE, def.toByte())
      put(PokemonStat.SPEED, spd.toByte())
      put(PokemonStat.SP_ATTACK, spAtk.toByte())
      put(PokemonStat.SP_DEFENSE, spDef.toByte())
    }

// IVs are a packed 30-bit word (six 5-bit stats). The top two bits carry unidentified flags and are
// dropped on write, so they stay zero for constructed monsters. Bypasses the checked IV setters,
// which reject a zero roll.
private fun ivsFromBits(bits: Int): IVs =
    IVs().apply {
      put(PokemonStat.HP, ((bits shr 0) and 31).toByte())
      put(PokemonStat.ATTACK, ((bits shr 5) and 31).toByte())
      put(PokemonStat.DEFENSE, ((bits shr 10) and 31).toByte())
      put(PokemonStat.SP_ATTACK, ((bits shr 15) and 31).toByte())
      put(PokemonStat.SP_DEFENSE, ((bits shr 20) and 31).toByte())
      put(PokemonStat.SPEED, ((bits shr 25) and 31).toByte())
    }

/**
 * The 148-byte party/PC monster record. The named fields are decoded from real captures. The
 * reserved segments hold species metadata and still-unlabelled structure kept as reference bytes,
 * so the layout round-trips exactly.
 */
object PokemonCodec : PacketCodec<Pokemon>() {
  override fun CodecScope<Pokemon>.body(): Pokemon {
    val id = field(S64LE, Pokemon::id)
    field(reserved("0000")) {}
    val ownerId = field(S64LE, Pokemon::ownerId)
    field(S64LE, Pokemon::ownerId)
    field(reserved("01")) {}
    val containerSlot = field(S16LE, Pokemon::containerSlot)
    val dexId = field(U16LE, Pokemon::dexId)
    val seed = field(S32LE, Pokemon::seed)
    field(S64LE, Pokemon::ownerId)
    val ot = field(Utf16LeNullTerminated, Pokemon::ot)
    val nickname = field(Utf16LeNullTerminated, Pokemon::nickname)
    field(reserved("0000")) {}
    val level = field(S8, Pokemon::level)
    val hp = field(S16LE, Pokemon::hp)
    field(reserved("0000")) {}
    val xp = field(S32LE, Pokemon::xp)
    field(reserved("003200")) {}
    val moveIds = List(4) { i -> field(S16LE) { it.moves[i].id } }
    val movePps = List(4) { i -> field(S8) { it.moves[i].pp } }
    field(reserved(LIST4)) {}
    val evHp = field(U8) { it.eVs.hp and 0xFF }
    val evAtk = field(U8) { it.eVs.atk and 0xFF }
    val evDef = field(U8) { it.eVs.def and 0xFF }
    val evSpd = field(U8) { it.eVs.spd and 0xFF }
    val evSpAtk = field(U8) { it.eVs.spAtk and 0xFF }
    val evSpDef = field(U8) { it.eVs.spDef and 0xFF }
    field(reserved(TAIL_A_REST)) {}
    val ivBits = field(S32LE) { it.iVs.compress() }
    field(U8) { 0 }
    field(S64LE) { TRAILER_B_LONG }
    val rarityBits = field(U16LE) { packRarity(it) }
    val caughtAt = field(TimestampLE, Pokemon::caughtAt)
    val isEgg = field(Bool, Pokemon::isEgg)
    field(reserved(TRAILER)) {}
    return Pokemon(
        id = id,
        ownerId = ownerId,
        container = PokemonContainer.PARTY,
        containerSlot = containerSlot,
        dexId = dexId,
        seed = seed,
        ot = ot,
        nickname = nickname,
        level = level,
        hp = hp,
        xp = xp,
        eVs = evsFromWire(evHp, evAtk, evDef, evSpd, evSpAtk, evSpDef),
        iVs = ivsFromBits(ivBits),
        moves = List(4) { PokemonMove(moveIds[it], movePps[it]) },
        isShiny = PokemonRarityFlag.SHINY.isSet(rarityBits),
        hasHiddenAbility = PokemonRarityFlag.HIDDEN_ABILITY.isSet(rarityBits),
        isAlpha = PokemonRarityFlag.ALPHA.isSet(rarityBits),
        isSecret = PokemonRarityFlag.SECRET_SHINY.isSet(rarityBits),
        isFatefulEncounter = PokemonRarityFlag.FATEFUL_ENCOUNTER.isSet(rarityBits),
        isRaidEncounter = PokemonRarityFlag.RAID_ENCOUNTER.isSet(rarityBits),
        caughtAt = caughtAt,
        isEgg = isEgg,
    )
  }
}
