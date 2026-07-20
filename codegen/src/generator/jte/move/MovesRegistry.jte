@import java.util.List
@import de.fiereu.openmmo.codegen.move.ParsedMove
@import de.fiereu.openmmo.codegen.move.RenderUtil

@param List<List<ParsedMove>> chunks
package de.fiereu.openmmo.moves.generated

import de.fiereu.openmmo.common.enums.MoveEffect
import de.fiereu.openmmo.common.enums.MoveFlag
import de.fiereu.openmmo.common.enums.MoveTarget
import de.fiereu.openmmo.common.enums.PokemonType
import de.fiereu.openmmo.moves.MoveDef
import de.fiereu.openmmo.moves.MoveRegistry

internal object GeneratedMoves {
  fun loadInto(reg: MoveRegistry) {
@for(int i = 0; i < chunks.size(); i++)
    registerChunk${i}(reg)
@endfor
  }

@for(int c = 0; c < chunks.size(); c++)
  private fun registerChunk${c}(reg: MoveRegistry) {
@for(ParsedMove m : chunks.get(c))
    reg.register(${RenderUtil.INSTANCE.move(m)})
@endfor
  }

@endfor
}
