package de.fiereu.openmmo.server.login.auth

import de.fiereu.openmmo.common.enums.LoginState
import de.fiereu.openmmo.server.login.testsupport.DockerAvailable
import io.kotest.core.annotation.EnabledIf
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import org.flywaydb.core.Flyway
import org.jooq.impl.DSL
import org.testcontainers.containers.PostgreSQLContainer

@EnabledIf(DockerAvailable::class)
class JooqUserStoreIT :
    FunSpec({
      val container = PostgreSQLContainer<Nothing>("postgres:18")
      lateinit var store: JooqUserStore

      beforeSpec {
        container.start()
        Flyway.configure()
            .dataSource(container.jdbcUrl, container.username, container.password)
            .locations("classpath:db/migration", "classpath:db/dev")
            .load()
            .migrate()
        val dsl = DSL.using(container.jdbcUrl, container.username, container.password)
        store = JooqUserStore(dsl, Dispatchers.IO)
      }

      afterSpec { container.stop() }

      test("the seed migration provides admin and test") {
        store.getUserId("admin") shouldBe 1
        store.getUserId("test") shouldBe 2
      }

      test("authenticate succeeds with the hashed password") {
        val result = store.authenticate("admin", sha1Hex("admin"))
        result.state shouldBe LoginState.AUTHED
        result.userId shouldBe 1
      }

      test("authenticate is case-insensitive on username") {
        store.authenticate("ADMIN", sha1Hex("admin")).state shouldBe LoginState.AUTHED
      }

      test("authenticate fails for a wrong password") {
        store.authenticate("admin", sha1Hex("nope")).state shouldBe LoginState.INVALID_PASSWORD
      }

      test("authenticate fails for an unknown user") {
        store.authenticate("nobody", sha1Hex("pw")).state shouldBe LoginState.INVALID_PASSWORD
      }

      test("addUser returns the generated id and getUserId finds it") {
        val id = store.addUser("Alice", "pw")
        store.getUserId("alice") shouldBe id
      }
    })
