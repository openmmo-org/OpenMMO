package buildsrc.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Property
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jooq.codegen.gradle.CodegenPluginExtension
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Property as JooqProperty
import org.jooq.meta.jaxb.Target

private const val MIGRATIONS_DIR = "src/main/resources/db/migration"

abstract class JooqDbExtension {
  abstract val packageName: Property<String>
}

/**
 * Generates jOOQ classes from the module's Flyway migrations without a live database and compiles
 * them in their own `jooq` source set. Also adds the runtime database dependencies.
 */
class JooqDbPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.pluginManager.apply("org.jooq.jooq-codegen-gradle")
    val ext = project.extensions.create<JooqDbExtension>("jooqDb")

    project.afterEvaluate {
      project.extensions.getByType<CodegenPluginExtension>().configuration {
        generator =
            Generator()
                .withName("org.jooq.codegen.KotlinGenerator")
                .withDatabase(
                    Database()
                        .withName("org.jooq.meta.extensions.ddl.DDLDatabase")
                        .withProperties(
                            JooqProperty().withKey("scripts").withValue("$MIGRATIONS_DIR/*.sql"),
                            JooqProperty().withKey("sort").withValue("flyway"),
                            JooqProperty().withKey("unqualifiedSchema").withValue("none"),
                            JooqProperty().withKey("defaultNameCase").withValue("lower"),
                        ))
                .withGenerate(
                    Generate()
                        .withKotlinNotNullRecordAttributes(true)
                        .withKotlinNotNullPojoAttributes(true)
                        .withPojos(false)
                        .withDaos(false))
                .withTarget(
                    Target()
                        .withPackageName(ext.packageName.get())
                        .withDirectory("build/generated-sources/jooq"))
      }
    }

    // The jOOQ plugin does not track the files behind the DDLDatabase "scripts" property.
    // Declare them as inputs so up-to-date checks and caching stay correct.
    project.tasks.named("jooqCodegen") {
      inputs
          .dir(MIGRATIONS_DIR)
          .withPropertyName("flywayMigrations")
          .withPathSensitivity(PathSensitivity.RELATIVE)
    }

    val sourceSets = project.extensions.getByType<SourceSetContainer>()
    val jooqSourceSet = sourceSets.create("jooq")
    project.extensions
        .getByType<KotlinJvmProjectExtension>()
        .sourceSets
        .getByName("jooq")
        .kotlin
        .srcDir(project.tasks.named("jooqCodegen"))

    // Realizing the codegen task adds its output to the main source set. Do it now and undo
    // the srcDir, so the generated classes only compile in the jooq source set.
    project.afterEvaluate {
      project.tasks.named("jooqCodegen").get()
      val mainJava = sourceSets.getByName("main").java
      mainJava.setSrcDirs(
          mainJava.srcDirs.filterNot {
            it.invariantSeparatorsPath.endsWith("generated-sources/jooq")
          })
    }

    listOf("main", "test").forEach { name ->
      sourceSets.getByName(name).apply {
        compileClasspath += jooqSourceSet.output
        runtimeClasspath += jooqSourceSet.output
      }
    }
    project.tasks.named<Jar>("jar") { from(jooqSourceSet.output) }

    val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")
    fun lib(alias: String) =
        libs.findLibrary(alias).orElseThrow { IllegalStateException("missing catalog entry $alias") }
    with(project.dependencies) {
      addProvider("jooqCodegen", lib("jooq-meta-extensions"))
      addProvider("jooqImplementation", lib("jooq"))
      addProvider("jooqImplementation", lib("jooq-kotlin"))
      addProvider("implementation", lib("jooq"))
      addProvider("implementation", lib("jooq-kotlin"))
      addProvider("implementation", lib("hikaricp"))
      addProvider("implementation", lib("flyway-core"))
      addProvider("runtimeOnly", lib("flyway-postgresql"))
      addProvider("runtimeOnly", lib("postgresql"))
      addProvider("testImplementation", lib("testcontainers-postgresql"))
      addProvider("testImplementation", lib("testcontainers-junit"))
      addProvider("testRuntimeOnly", lib("postgresql"))
    }
  }
}
