package buildsrc.convention

import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

abstract class JteCodegenSpec @Inject constructor(private val specName: String) : Named {
  override fun getName(): String = specName

  abstract val mainClass: Property<String>
  abstract val templatesSubdir: Property<String>
  abstract val extraArgs: ListProperty<String>
  abstract val inputDirs: ConfigurableFileCollection
}

abstract class JteCodegenExtension @Inject constructor(objects: ObjectFactory) {
  val generators: NamedDomainObjectContainer<JteCodegenSpec> =
      objects.domainObjectContainer(JteCodegenSpec::class.java)

  fun register(name: String, action: Action<JteCodegenSpec>) = generators.register(name, action)
}

class JteCodegenPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val generator = project.extensions.getByType<SourceSetContainer>().create("generator")
    val ext = project.extensions.create<JteCodegenExtension>("jteCodegen")
    val templatesBase = project.layout.projectDirectory.dir("src/generator/jte")

    project.afterEvaluate {
      ext.generators.forEach { spec ->
        val templatesDir = templatesBase.dir(spec.templatesSubdir.getOrElse(spec.name))
        val outDir = project.layout.buildDirectory.dir("generated/source/${spec.name}/kotlin")
        val jteCacheDir = project.layout.buildDirectory.dir("jte-classes/${spec.name}")

        val generate =
            project.tasks.register<JavaExec>(
                "generate${spec.name.replaceFirstChar { it.uppercase() }}") {
                  group = "openmmo"
                  description = "Render ${spec.name} sources from the GBA decomp data via JTE"
                  dependsOn(project.tasks.named("${generator.name}Classes"))
                  spec.inputDirs.forEach { inputs.dir(it) }
                  inputs.dir(templatesDir)
                  outputs.dir(outDir)
                  classpath = generator.runtimeClasspath
                  mainClass.set(spec.mainClass)
                  setArgs(
                      buildList {
                        add(outDir.get().asFile.absolutePath)
                        add(templatesDir.asFile.absolutePath)
                        add(jteCacheDir.get().asFile.absolutePath)
                        addAll(spec.extraArgs.get())
                      })
                }

        project.extensions
            .getByType<KotlinJvmProjectExtension>()
            .sourceSets
            .getByName("main")
            .kotlin
            .srcDir(generate)
      }
    }
  }
}
