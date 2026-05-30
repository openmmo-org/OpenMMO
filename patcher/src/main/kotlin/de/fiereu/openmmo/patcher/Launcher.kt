package de.fiereu.openmmo.patcher

import java.io.StringReader
import kotlin.io.encoding.Base64
import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.AsmVisitorWrapper
import net.bytebuddy.description.field.FieldDescription
import net.bytebuddy.description.field.FieldList
import net.bytebuddy.description.method.MethodList
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.implementation.Implementation
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.matcher.ElementMatchers.any
import net.bytebuddy.matcher.ElementMatchers.nameStartsWith
import net.bytebuddy.pool.TypePool
import org.bouncycastle.util.io.pem.PemReader

private const val POKEMMO_PUBKEY_GAME =
    "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEtqx2myJz3ftlYWgd7cbNqf2t208itQMY7ouPNBDpQetbi7eXbEDxDDZy4Q9fMnI6mF5/D0qMdRd40SRXf0OS7Q=="
private const val POKEMMO_PUBKEY_CHAT =
    "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEh4Vqgnd+8Fqebu0H40v+FgwhE6RwgAYxJMihb8mJmcHDy8r/rPz3kLHH1oabyKIRUa5Y2cK0TsxZky+mp7DKWA=="

private const val TARGET_MAIN_PROPERTY = "openmmo.targetMain"

object Launcher {

  fun log(message: () -> String) {
    println("[OpenMMO Patcher] ${message()}")
  }

  @JvmStatic
  fun main(args: Array<String>) {
    val targetMain =
        System.getProperty(TARGET_MAIN_PROPERTY)
            ?: error("Missing -D$TARGET_MAIN_PROPERTY=<fully.qualified.MainClass>")

    log { "Installing ByteBuddy agent on the running JVM" }
    val instrumentation = ByteBuddyAgent.install()

    val patches =
        listOf(
            StringPatcher.Patch(
                "GamePubKeyPatch", POKEMMO_PUBKEY_GAME, loadPublicKey("/game.public.pem")),
            StringPatcher.Patch(
                "ChatPubKeyPatch", POKEMMO_PUBKEY_CHAT, loadPublicKey("/chat.public.pem")),
        )
    patches.forEach { p -> log { "Loaded patch: ${p.name} - for '${p.original}'" } }

    AgentBuilder.Default()
        .ignore(
            nameStartsWith<TypeDescription>("java.")
                .or(nameStartsWith("javax."))
                .or(nameStartsWith("sun."))
                .or(nameStartsWith("jdk."))
                .or(nameStartsWith("com.sun."))
                .or(nameStartsWith("net.bytebuddy."))
                .or(nameStartsWith("org.bouncycastle."))
                .or(nameStartsWith("kotlin."))
                .or(nameStartsWith("kotlinx."))
                .or(nameStartsWith("de.fiereu.openmmo.patcher.")))
        .type(any<TypeDescription>())
        .transform(
            AgentBuilder.Transformer { builder, _, _, _, _ ->
              builder.visit(StringPatcherWrapper(patches))
            })
        .installOn(instrumentation)

    log { "Invoking $targetMain.main(${args.toList()})" }
    Class.forName(targetMain).getMethod("main", Array<String>::class.java).invoke(null, args)
  }

  private fun loadPublicKey(path: String): String {
    val stream =
        Launcher::class.java.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Public key not found at path: $path")
    return stream.use {
      val pem = it.readBytes().decodeToString()
      val obj = PemReader(StringReader(pem)).readPemObject()
      Base64.Default.encode(obj.content)
    }
  }
}

private class StringPatcherWrapper(private val patches: List<StringPatcher.Patch>) :
    AsmVisitorWrapper {

  override fun mergeWriter(flags: Int): Int = flags

  override fun mergeReader(flags: Int): Int = flags

  override fun wrap(
      instrumentedType: TypeDescription,
      classVisitor: ClassVisitor,
      implementationContext: Implementation.Context,
      typePool: TypePool,
      fields: FieldList<FieldDescription.InDefinedShape>,
      methods: MethodList<*>,
      writerFlags: Int,
      readerFlags: Int,
  ): ClassVisitor = StringPatcher(classVisitor, patches)
}
