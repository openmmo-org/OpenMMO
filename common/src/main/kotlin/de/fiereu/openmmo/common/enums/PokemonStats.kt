package de.fiereu.openmmo.common.enums

import java.util.*

enum class PokemonStat {
  HP,
  ATTACK,
  DEFENSE,
  SP_ATTACK,
  SP_DEFENSE,
  SPEED
}

open class PokemonStats(private val individualCap: Int, private val totalCap: Int) :
    EnumMap<PokemonStat, Byte>(PokemonStat.entries.associateWith { 0.toByte() }) {

  val total
    get() = hp + atk + def + spAtk + spDef + spd

  private fun checkedSet(stat: PokemonStat, value: Int) {
    if (value < 0) error("Value can't be negative")
    if (value > individualCap) error("Value exceeds individual capacity")
    if (total - (get(stat)!!.toInt() and 0xFF) + value > totalCap)
        error("Total value exceeds capacity")
    set(stat, value.toByte())
  }

  var hp
    get() = get(PokemonStat.HP)!!.toInt() and 0xFF
    set(value) = checkedSet(PokemonStat.HP, value)

  var atk
    get() = get(PokemonStat.ATTACK)!!.toInt() and 0xFF
    set(value) = checkedSet(PokemonStat.ATTACK, value)

  var def
    get() = get(PokemonStat.DEFENSE)!!.toInt() and 0xFF
    set(value) = checkedSet(PokemonStat.DEFENSE, value)

  var spAtk
    get() = get(PokemonStat.SP_ATTACK)!!.toInt() and 0xFF
    set(value) = checkedSet(PokemonStat.SP_ATTACK, value)

  var spDef
    get() = get(PokemonStat.SP_DEFENSE)!!.toInt() and 0xFF
    set(value) = checkedSet(PokemonStat.SP_DEFENSE, value)

  var spd
    get() = get(PokemonStat.SPEED)!!.toInt() and 0xFF
    set(value) = checkedSet(PokemonStat.SPEED, value)
}

class EVs : PokemonStats(252, 510)

class IVs : PokemonStats(31, 186)

fun IVs.compress(): Int =
    (((hp and 31) shl 0) or
        ((atk and 31) shl 5) or
        ((def and 31) shl 10) or
        ((spAtk and 31) shl 15) or
        ((spDef and 31) shl 20) or
        ((spd and 31) shl 25))

fun decompressIVs(value: Int): IVs =
    IVs().apply {
      hp = (value shr 0) and 31
      atk = (value shr 5) and 31
      def = (value shr 10) and 31
      spAtk = (value shr 15) and 31
      spDef = (value shr 20) and 31
      spd = (value shr 25) and 31
    }
