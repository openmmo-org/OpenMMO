@import java.util.List
@import de.fiereu.openmmo.codegen.typechart.ParsedMatchup

@param List<ParsedMatchup> matchups
package de.fiereu.openmmo.typechart.generated

import de.fiereu.openmmo.common.enums.PokemonType
import de.fiereu.openmmo.typechart.TypeChart

internal object GeneratedTypeChart {
  fun loadInto(chart: TypeChart) {
@for(ParsedMatchup m : matchups)
    chart.register(${m.getAttacker()}, ${m.getDefender()}, ${m.getMultiplier()})
@endfor
  }
}
