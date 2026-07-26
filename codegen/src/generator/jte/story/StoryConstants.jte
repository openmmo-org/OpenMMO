@import java.util.List

@param String pkg
@param String objectName
@param String namespace
@param List<String> names
package ${pkg}

/**
 * Story keys generated from the GBA decomp. Each value is the region namespaced key a script hands
 * to ScriptContext (setFlag/isFlagSet, setVar/getVar). Generated, do not edit by hand.
 */
object ${objectName} {
@for(String name : names)
  const val ${name} = "${namespace}/${name}"
@endfor
}
