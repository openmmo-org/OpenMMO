package de.fiereu.openmmo.server.game.domain

enum class StatKey {
  HP,
  ATK,
  DEF,
  SPA,
  SPD,
  SPE
}

data class StatBlock(
    val hp: Int,
    val atk: Int,
    val def: Int,
    val spa: Int,
    val spd: Int,
    val spe: Int,
) {
  operator fun get(stat: StatKey): Int =
      when (stat) {
        StatKey.HP -> hp
        StatKey.ATK -> atk
        StatKey.DEF -> def
        StatKey.SPA -> spa
        StatKey.SPD -> spd
        StatKey.SPE -> spe
      }

  fun with(stat: StatKey, value: Int): StatBlock =
      when (stat) {
        StatKey.HP -> copy(hp = value)
        StatKey.ATK -> copy(atk = value)
        StatKey.DEF -> copy(def = value)
        StatKey.SPA -> copy(spa = value)
        StatKey.SPD -> copy(spd = value)
        StatKey.SPE -> copy(spe = value)
      }

  companion object {
    val ZERO = StatBlock(0, 0, 0, 0, 0, 0)
  }
}

typealias IVs = StatBlock

enum class Nature {
  HARDY,
  LONELY,
  BRAVE,
  ADAMANT,
  NAUGHTY,
  BOLD,
  DOCILE,
  RELAXED,
  IMPISH,
  LAX,
  TIMID,
  HASTY,
  SERIOUS,
  JOLLY,
  NAIVE,
  MODEST,
  MILD,
  QUIET,
  BASHFUL,
  RASH,
  CALM,
  GENTLE,
  SASSY,
  CAREFUL,
  QUIRKY
}

enum class Gender {
  MALE,
  FEMALE,
  GENDERLESS
}

enum class EggGroup {
  MONSTER,
  WATER1,
  WATER2,
  WATER3,
  BUG,
  FLYING,
  FIELD,
  FAIRY,
  GRASS,
  HUMANSHAPE,
  MINERAL,
  AMORPHOUS,
  DITTO,
  DRAGON,
  UNDISCOVERED
}

enum class GrowthRate {
  ERRATIC,
  FAST,
  MEDIUM_FAST,
  MEDIUM_SLOW,
  SLOW,
  FLUCTUATING
}
