package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.protocols.game.packets.LoadEntityPacket
import de.fiereu.openmmo.protocols.game.packets.MapData
import de.fiereu.openmmo.protocols.game.packets.codecs.SkinSet
import de.fiereu.openmmo.server.game.world.MapDef
import de.fiereu.openmmo.server.game.world.MapManager
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext

private val log = KotlinLogging.logger {}

class MapLoadService(private val packetSender: PacketSender) {

  fun createLoadEntity(
      info: CharacterInfo,
      facing: Direction = Direction.DOWN,
      z: Int = 0,
  ): LoadEntityPacket {
    return LoadEntityPacket(
        entityId = info.id,
        skin = SkinSet(),
        name = info.name,
        regionId = info.positionRegionId.toInt(),
        bankId = info.positionBankId.toInt(),
        mapId = info.positionMapId.toInt(),
        x = info.positionX.toInt(),
        y = info.positionY.toInt(),
        z = z,
        facing = facing,
        status = de.fiereu.openmmo.common.enums.EntityStatus.NONE,
        hasFollower = false,
        followerDexId = 0,
    )
  }

  fun send90(ctx: ChannelHandlerContext, entityId: Long, skin: SkinSet) {
    val raw90Buf = Unpooled.buffer()
    raw90Buf.writeLongLE(entityId)
    raw90Buf.writeByte(0x01)
    raw90Buf.writeByte(0x00)
    raw90Buf.writeByte(0x4C)
    raw90Buf.writeByte(0x03)
    raw90Buf.writeBytes(
        byteArrayOf(
            0x13,
            0xA8.toByte(),
            0x0A,
            0x00,
            0x03,
            0x8C.toByte(),
            0x01,
            0x38.toByte(),
            0x02,
            0x4C,
            0x01))
    val raw90 = ByteArray(raw90Buf.readableBytes())
    raw90Buf.readBytes(raw90)
    raw90Buf.release()
    log.info {
      ">> TX 0x90 payload (${raw90.size}b): ${raw90.joinToString("") { "%02X".format(it) }}"
    }
    packetSender.sendRaw(ctx, 0x90u, raw90)
  }

  fun preloadConnectedMaps(
      ctx: ChannelHandlerContext,
      map: MapDef,
      depth: Int = 2,
      reloadPlayer: Boolean = false,
      flush: Boolean = true,
  ) {
    val loaded = mutableSetOf<String>()
    loaded.add("${map.bankId}:${map.mapId}")
    fun preload(connections: List<MapData.GbaConnection>, remaining: Int) {
      if (remaining <= 0) return
      for (conn in connections) {
        val key = "${conn.targetBank}:${conn.targetMap}"
        if (!loaded.add(key)) continue
        val connected = MapManager.getMap(1, conn.targetBank.toByte(), conn.targetMap.toByte())
        if (connected != null) {
          ctx.channel()
              .write(
                  MapManager.createLoadMapPacket(
                      connected,
                      reloadPlayer = reloadPlayer,
                      deleteCache = false,
                  ))
          preload(connected.connections, remaining - 1)
        }
      }
    }
    preload(map.connections, depth)
    if (flush) {
      ctx.channel().flush()
    }
  }
}
