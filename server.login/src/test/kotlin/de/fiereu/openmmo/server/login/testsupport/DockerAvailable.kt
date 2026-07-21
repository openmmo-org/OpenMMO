package de.fiereu.openmmo.server.login.testsupport

import io.kotest.core.annotation.Condition
import io.kotest.core.spec.Spec
import kotlin.reflect.KClass
import org.testcontainers.DockerClientFactory

/** Gates Testcontainers specs so machines without Docker skip them instead of failing. */
class DockerAvailable : Condition {
  override fun evaluate(kclass: KClass<out Spec>): Boolean =
      runCatching { DockerClientFactory.instance().isDockerAvailable() }.getOrDefault(false)
}
