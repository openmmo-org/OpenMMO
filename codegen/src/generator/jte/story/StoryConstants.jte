@import java.util.List
@import de.fiereu.openmmo.codegen.story.StoryConstant

@param String pkg
@param String objectName
@param String namespace
@param List<StoryConstant> constants
@param List<List<StoryConstant>> idChunks
@param List<StoryConstant> initialFlags
@param List<StoryConstant> maleIntroFlags
@param List<StoryConstant> femaleIntroFlags
package ${pkg}

/**
 * Story keys generated from the GBA decomp. Each value is the region namespaced key a script hands
 * to ScriptContext (setFlag/isFlagSet, setVar/getVar). Generated, do not edit by hand.
 */
object ${objectName} {
@for(StoryConstant constant : constants)
  const val ${constant.getName()} = "${namespace}/${constant.getName()}"
@endfor

@if(!initialFlags.isEmpty())
  /** Flags set by the source game's new-game map reset. */
  val initiallySet: Set<String> =
      setOf(
@for(StoryConstant constant : initialFlags)
          ${constant.getName()},
@endfor
      )

  /** Additional flags established in the moving truck for the male player path. */
  val maleIntro: Set<String> =
      setOf(
@for(StoryConstant constant : maleIntroFlags)
          ${constant.getName()},
@endfor
      )

  /** Additional flags established in the moving truck for the female player path. */
  val femaleIntro: Set<String> =
      setOf(
@for(StoryConstant constant : femaleIntroFlags)
          ${constant.getName()},
@endfor
      )

@endif
  /** Numeric id assigned by the source game's constants header. */
  fun numericId(key: String): Int? {
@for(int index = 0; index < idChunks.size(); index++)
    numericId${index}(key)?.let { return it }
@endfor
    return null
  }

@for(int index = 0; index < idChunks.size(); index++)
  private fun numericId${index}(key: String): Int? =
      when (key) {
@for(StoryConstant constant : idChunks.get(index))
        ${constant.getName()} -> ${constant.getNumericId()}
@endfor
        else -> null
      }

@endfor
}
