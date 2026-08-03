@import java.util.List
@import de.fiereu.openmmo.codegen.learnset.ParsedLearnset
@import de.fiereu.openmmo.codegen.learnset.RenderUtil

@param List<List<ParsedLearnset>> chunks
package de.fiereu.openmmo.pokemon.generated

import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.LevelUpMove

internal object GeneratedLearnsets {
  fun loadInto(reg: LearnsetRegistry) {
@for(int i = 0; i < chunks.size(); i++)
    registerChunk${i}(reg)
@endfor
  }

@for(int c = 0; c < chunks.size(); c++)
  private fun registerChunk${c}(reg: LearnsetRegistry) {
@for(ParsedLearnset l : chunks.get(c))
    ${RenderUtil.INSTANCE.learnset(l)}
@endfor
  }

@endfor
}
