package de.fiereu.openmmo.server.game.storage

import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

data class NpcScriptRow(
    val scriptName: String,
    val scriptType: Int,
    val data: String,
)

private val log = KotlinLogging.logger {}

@Singleton
class NpcSpawnRepository @Inject constructor(
    private val dataSource: DataSource,
) {
  fun getScript(scriptName: String): NpcScriptRow? {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt = c.prepareStatement("select * from npc_script where script_name = ?")
      stmt.setString(1, scriptName)
      stmt.executeQuery().use { rs ->
        if (rs.next()) {
          return NpcScriptRow(
              scriptName = rs.getString("script_name"),
              scriptType = rs.getInt("script_type"),
              data = rs.getString("data") ?: "{}",
          )
        }
      }
    }
    return null
  }
}
