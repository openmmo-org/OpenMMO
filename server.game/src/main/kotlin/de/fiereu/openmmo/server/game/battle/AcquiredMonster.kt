package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.battle.Experience
import de.fiereu.openmmo.pokemon.SpeciesDef

/** Tells the client about a monster the player just obtained. */
fun acquiredMonsterDelta(mon: Pokemon, species: SpeciesDef): BattleEntityDeltaPacket {
  val stats = StatCalculator.computeAll(species, mon)
  // The real server also sends a happiness here, which we do not model.
  return BattleEntityDeltaPacket(
      entityId = mon.id,
      experience = Experience(mon.level, mon.xp),
      statValues = stats.asWireList(),
      currentHp = mon.hp,
      faintFlag = 0,
  )
}
