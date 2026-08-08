@import java.util.List
@import de.fiereu.openmmo.codegen.trainer.ParsedTrainer
@import de.fiereu.openmmo.codegen.trainer.RenderUtil

@param String pkg
@param String objectName
@param String region
@param List<List<ParsedTrainer>> chunks
package ${pkg}

import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.trainer.TrainerDef
import de.fiereu.openmmo.trainer.TrainerMon
import de.fiereu.openmmo.trainer.TrainerRegistry

internal object ${objectName} {
  fun loadInto(reg: TrainerRegistry) {
@for(int i = 0; i < chunks.size(); i++)
    registerChunk${i}(reg)
@endfor
  }

@for(int c = 0; c < chunks.size(); c++)
  private fun registerChunk${c}(reg: TrainerRegistry) {
@for(ParsedTrainer t : chunks.get(c))
    ${RenderUtil.INSTANCE.trainer(region, t)}
@endfor
  }

@endfor
}
