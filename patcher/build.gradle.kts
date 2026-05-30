plugins {
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.common.keys")
}

group = "de.fiereu.openmmo"

version = "1.0.0"

dependencies {
  implementation(libs.bundles.bytebuddy)
  implementation(libs.bundles.crypto)
}

fun String.evalEnvVars(): String =
    replace(Regex("\\$\\{([^}]+)\\}")) { System.getenv(it.groupValues[1]) ?: it.value }

tasks.register("setLocalServer") {
  description = "Creates a configuration file for PokeMMO that sets the login server to 127.0.0.1."

  val pokemmoWorkingDir = (project.findProperty("pokemmo.workingDir") as String).evalEnvVars()
  val configFile = File("$pokemmoWorkingDir/config/openmmo.properties")

  doFirst {
    if (!configFile.exists()) {
      configFile.parentFile.mkdirs()
      configFile.createNewFile()
    }
  }

  doLast {
    var content = configFile.readText()
    fun createOrReplace(key: String, value: String): String =
        if (content.contains(key)) content.replace("$key=.*", "$key=$value")
        else "$content\n$key=$value"
    content = createOrReplace("client.misc.ignore_feed", "true")
    content = createOrReplace("loginserver.network.client.host", "127.0.0.1")
    configFile.writeText(content)
  }
}

tasks.register<JavaExec>("run") {
  group = "application"
  description = "Runs the PokeMMO client with the ByteBuddy patcher attached"
  dependsOn("setLocalServer", "copyPublicKeys")
  tasks.processResources.get().mustRunAfter("copyPublicKeys")

  val pokemmoMainClass = project.findProperty("pokemmo.mainClass") as String
  val pokemmoExecutable = (project.findProperty("pokemmo.executable") as String).evalEnvVars()
  val pokemmoWorkingDir = (project.findProperty("pokemmo.workingDir") as String).evalEnvVars()

  mainClass.set("de.fiereu.openmmo.patcher.Launcher")
  classpath(sourceSets.main.get().runtimeClasspath, files(pokemmoExecutable))
  systemProperty("openmmo.targetMain", pokemmoMainClass)
  jvmArgs("-XX:+EnableDynamicAgentLoading")
  workingDir = File(pokemmoWorkingDir)
}
