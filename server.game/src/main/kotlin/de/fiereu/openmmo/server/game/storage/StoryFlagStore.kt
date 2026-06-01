package de.fiereu.openmmo.server.game.storage

import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

private val log = KotlinLogging.logger {}

@Singleton
class StoryFlagStore @Inject constructor(
    private val dataSource: DataSource,
) {
  fun getFlag(characterId: Long, flagName: String): Int {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt = c.prepareStatement("select flag_value from story_flag where character_id = ? and flag_name = ?")
      stmt.setLong(1, characterId)
      stmt.setString(2, flagName)
      stmt.executeQuery().use { rs ->
        if (rs.next()) return rs.getInt("flag_value") else return 0
      }
    }
  }

  fun setFlag(characterId: Long, flagName: String, value: Int) {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt =
          c.prepareStatement(
              """insert into story_flag (character_id, flag_name, flag_value)
                 values (?, ?, ?)
                 on conflict (character_id, flag_name)
                 do update set flag_value = excluded.flag_value""")
      stmt.setLong(1, characterId)
      stmt.setString(2, flagName)
      stmt.setInt(3, value)
      stmt.executeUpdate()
    }
  }

  fun checkFlag(characterId: Long, flagName: String, expected: Int): Boolean {
    return getFlag(characterId, flagName) == expected
  }

  fun clearFlags(characterId: Long) {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt = c.prepareStatement("delete from story_flag where character_id = ?")
      stmt.setLong(1, characterId)
      stmt.executeUpdate()
    }
  }
}
