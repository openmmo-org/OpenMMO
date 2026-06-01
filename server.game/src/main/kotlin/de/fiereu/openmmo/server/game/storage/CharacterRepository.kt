package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class CharacterRepository @Inject constructor(
    private val dataSource: DataSource,
) {
  fun create(
      userId: Int,
      name: String,
      gender: Byte = 0,
      cosmetics: ByteArray? = null,
  ): CharacterInfo {
    val now = LocalDateTime.now()
    val rivalSex = (1 - gender).toByte()

    val conn = dataSource.connection
    val id = conn.use { c ->
      val stmt =
          c.prepareStatement(
              """insert into "character" (user_id, name, rival_sex, gender, cosmetics, created_at, last_login, permissions)
                 values (?, ?, ?, ?, ?, ?, ?, 8)""",
              java.sql.Statement.RETURN_GENERATED_KEYS)
      stmt.setInt(1, userId)
      stmt.setString(2, name)
      stmt.setShort(3, rivalSex.toShort())
      stmt.setShort(4, gender.toShort())
      if (cosmetics != null) stmt.setBytes(5, cosmetics)
      else stmt.setNull(5, java.sql.Types.BINARY)
      stmt.setTimestamp(6, Timestamp.valueOf(now))
      stmt.setTimestamp(7, Timestamp.valueOf(now))
      stmt.executeUpdate()
      val keys = stmt.generatedKeys
      keys.next()
      keys.getLong(1)
    }

    return CharacterInfo(
        id = id,
        name = name,
        namePrefix = "",
        userId = userId,
        rivalSex = rivalSex,
        lastLogin = now,
        createdAt = now,
        money = 3000,
        permissions = 8,
        remainingSafariSteps = 0,
        remainingSafariBalls = 0,
        pcExtraSlots = 0,
        battleBoxExtraSlots = 0,
        templateAmount = 0,
        positionRegionId = 1,
        positionBankId = 51,
        positionMapId = 3,
        positionX = 4,
        positionY = 4,
        repelLeft = 0,
        repelItemId = 0,
        lureLeft = 0,
        lureItemId = 0,
    )
  }

  fun getById(id: Long): CharacterInfo? {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt = c.prepareStatement("""select * from "character" where id = ?""")
      stmt.setLong(1, id)
      stmt.executeQuery().use { rs ->
        if (rs.next()) return rowToInfo(rs) else return null
      }
    }
  }

  fun getByUserId(userId: Int): List<CharacterInfo> {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt = c.prepareStatement("""select * from "character" where user_id = ? order by created_at""")
      stmt.setInt(1, userId)
      stmt.executeQuery().use { rs ->
        val result = mutableListOf<CharacterInfo>()
        while (rs.next()) result.add(rowToInfo(rs))
        return result
      }
    }
  }

  fun update(characterId: Long, info: CharacterInfo) {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt =
          c.prepareStatement(
              """update "character" set name = ?, name_prefix = ?, money = ?, position_region_id = ?,
                 position_bank_id = ?, position_map_id = ?, position_x = ?, position_y = ?,
                 repel_left = ?, repel_item_id = ?, lure_left = ?, lure_item_id = ?
                 where id = ?""")
      stmt.setString(1, info.name)
      stmt.setString(2, info.namePrefix)
      stmt.setInt(3, info.money)
      stmt.setShort(4, info.positionRegionId.toShort())
      stmt.setShort(5, info.positionBankId.toShort())
      stmt.setShort(6, info.positionMapId.toShort())
      stmt.setShort(7, info.positionX)
      stmt.setShort(8, info.positionY)
      stmt.setShort(9, info.repelLeft)
      stmt.setShort(10, info.repelItemId)
      stmt.setShort(11, info.lureLeft)
      stmt.setShort(12, info.lureItemId)
      stmt.setLong(13, characterId)
      stmt.executeUpdate()
    }
  }

  fun updatePosition(
      characterId: Long,
      x: Short,
      y: Short,
      bankId: Byte? = null,
      mapId: Byte? = null,
  ) {
    val conn = dataSource.connection
    conn.use { c ->
      val current = getById(characterId) ?: return
      val stmt =
          c.prepareStatement(
              """update "character" set position_x = ?, position_y = ?,
                 position_bank_id = ?, position_map_id = ? where id = ?""")
      stmt.setShort(1, x)
      stmt.setShort(2, y)
      stmt.setShort(3, (bankId ?: current.positionBankId).toShort())
      stmt.setShort(4, (mapId ?: current.positionMapId).toShort())
      stmt.setLong(5, characterId)
      stmt.executeUpdate()
    }
  }

  fun updateLastLogin(characterId: Long) {
    val conn = dataSource.connection
    conn.use { c ->
      val stmt = c.prepareStatement("""update "character" set last_login = ? where id = ?""")
      stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()))
      stmt.setLong(2, characterId)
      stmt.executeUpdate()
    }
  }

  private fun rowToInfo(rs: ResultSet): CharacterInfo {
    return CharacterInfo(
        id = rs.getLong("id"),
        name = rs.getString("name") ?: "",
        namePrefix = rs.getString("name_prefix") ?: "",
        userId = rs.getInt("user_id"),
        rivalSex = rs.getShort("rival_sex").toByte(),
        lastLogin = rs.getTimestamp("last_login")?.toLocalDateTime() ?: LocalDateTime.now(),
        createdAt = rs.getTimestamp("created_at")?.toLocalDateTime() ?: LocalDateTime.now(),
        money = rs.getInt("money"),
        permissions = rs.getInt("permissions"),
        remainingSafariSteps = rs.getShort("remaining_safari_steps"),
        remainingSafariBalls = rs.getShort("remaining_safari_balls").toByte(),
        pcExtraSlots = rs.getShort("pc_extra_slots").toByte(),
        battleBoxExtraSlots = rs.getShort("battle_box_extra_slots").toByte(),
        templateAmount = rs.getShort("template_amount").toByte(),
        positionRegionId = rs.getShort("position_region_id").toByte(),
        positionBankId = rs.getShort("position_bank_id").toByte(),
        positionMapId = rs.getShort("position_map_id").toByte(),
        positionX = rs.getShort("position_x"),
        positionY = rs.getShort("position_y"),
        repelLeft = rs.getShort("repel_left"),
        repelItemId = rs.getShort("repel_item_id"),
        lureLeft = rs.getShort("lure_left"),
        lureItemId = rs.getShort("lure_item_id"),
    )
  }
}
