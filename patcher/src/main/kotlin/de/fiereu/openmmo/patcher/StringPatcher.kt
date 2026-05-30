package de.fiereu.openmmo.patcher

import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes

class StringPatcher(classVisitor: ClassVisitor, private val patches: List<Patch>) :
    ClassVisitor(Opcodes.ASM9, classVisitor) {

  data class Patch(
      val name: String = "UnknownPatch",
      val original: String,
      val replacement: String,
  )

  private fun applyPatches(value: String): String {
    val matching = patches.filter { it.original == value }
    if (matching.isEmpty()) return value
    var newValue = value
    matching.forEach { patch ->
      Launcher.log {
        "Applying patch: ${patch.name} - replacing '$value' with '${patch.replacement}'"
      }
      newValue = patch.replacement
    }
    return newValue
  }

  override fun visitField(
      access: Int,
      name: String,
      descriptor: String,
      signature: String?,
      value: Any?,
  ): FieldVisitor? =
      if (value is String)
          super.visitField(access, name, descriptor, signature, applyPatches(value))
      else super.visitField(access, name, descriptor, signature, value)

  override fun visitMethod(
      access: Int,
      name: String,
      descriptor: String,
      signature: String?,
      exceptions: Array<out String>?,
  ): MethodVisitor =
      object :
          MethodVisitor(
              Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
        override fun visitLdcInsn(value: Any?) {
          if (value is String) super.visitLdcInsn(applyPatches(value))
          else super.visitLdcInsn(value)
        }
      }
}
