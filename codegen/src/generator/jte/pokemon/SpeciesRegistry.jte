@import java.util.List
@import de.fiereu.openmmo.codegen.pokemon.ParsedSpecies
@import de.fiereu.openmmo.codegen.pokemon.RenderUtil

@param List<List<ParsedSpecies>> chunks
package de.fiereu.openmmo.pokemon.generated

import de.fiereu.openmmo.common.enums.Ability
import de.fiereu.openmmo.common.enums.BodyColor
import de.fiereu.openmmo.common.enums.EggGroup
import de.fiereu.openmmo.common.enums.GrowthRate
import de.fiereu.openmmo.common.enums.PokemonType
import de.fiereu.openmmo.pokemon.SpeciesDef
import de.fiereu.openmmo.pokemon.SpeciesRegistry

internal object GeneratedSpecies {
  fun loadInto(reg: SpeciesRegistry) {
@for(int i = 0; i < chunks.size(); i++)
    registerChunk${i}(reg)
@endfor
  }

@for(int c = 0; c < chunks.size(); c++)
  private fun registerChunk${c}(reg: SpeciesRegistry) {
@for(ParsedSpecies s : chunks.get(c))
    reg.register(${RenderUtil.INSTANCE.species(s)})
@endfor
  }

@endfor
}
