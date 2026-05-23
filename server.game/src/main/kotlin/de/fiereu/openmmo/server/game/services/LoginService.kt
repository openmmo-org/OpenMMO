package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.protocols.game.packets.CharacterEntry
import de.fiereu.openmmo.protocols.game.packets.CharacterListPacket
import de.fiereu.openmmo.protocols.game.packets.ChatMessagePacket
import de.fiereu.openmmo.protocols.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.protocols.game.packets.JoinGamePacket
import de.fiereu.openmmo.protocols.game.packets.LoadEntityPacket
import de.fiereu.openmmo.protocols.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.protocols.game.packets.RenderScreenPacket
import de.fiereu.openmmo.protocols.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.protocols.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.protocols.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.protocols.game.packets.SelectedCharacterPacket
import de.fiereu.openmmo.protocols.game.packets.codecs.SkinSet
import de.fiereu.openmmo.server.game.protocol.game.ext.accept
import de.fiereu.openmmo.server.game.protocol.game.ext.buildAndRespond
import de.fiereu.openmmo.server.game.session.SessionManager
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.MapManager
import de.fiereu.openmmo.server.protocol.PacketEvent
import de.fiereu.openmmo.server.protocol.respond
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

class LoginService(
    private val mapLoadService: MapLoadService,
    private val npcService: NpcService,
    private val multiplayerService: MultiplayerService,
    private val packetSender: PacketSender,
) {

  fun onJoinGame(event: PacketEvent<JoinGamePacket>) {
    val join = event.packet
    log.info { "Player joined the game." }

    val authData = join.authData
    var userId = 0
    if (authData is de.fiereu.openmmo.protocols.game.packets.NewAuthData) {
      userId = authData.userId
      log.info { "User $userId joined with session key (${authData.sessionKey.size} bytes)" }
    }

    if (userId > 0) {
      SessionManager.createSession(event.ctx.channel(), userId)
      log.info { "Session created for user $userId" }
    }

    event.accept().withPlaytime(1337).withRewardPoints(420).withBalance(187).buildAndRespond(event)
  }

  fun onCreateCharacter(event: PacketEvent<CreateCharacterPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) {
      log.warn { "Create character from unknown session" }
      return
    }
    val name = event.packet.name
    log.info { "Creating character '$name' for userId=${session.userId}" }
    CharacterStore.createCharacter(session.userId, name)
    log.info { "Created character '$name'" }
    event.respond(buildCharacterList(session.userId))
  }

  fun onCharacterRequest(event: PacketEvent<RequestCharactersPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session != null) {
      event.respond(buildCharacterList(session.userId))
    } else {
      log.warn { "Character request from unauthenticated session" }
      event.respond(CharacterListPacket(emptyList()))
    }
  }

  private fun buildCharacterList(userId: Int): CharacterListPacket {
    val characters = CharacterStore.getCharactersByUser(userId)
    val entries =
        characters.map { stored ->
          CharacterEntry(
              characterInfo = stored.info,
              skinSet = SkinSet(),
              guildId = null,
              pokemon = stored.pokemon.take(1),
          )
        }
    return CharacterListPacket(entries)
  }

  fun onCharacterSelected(event: PacketEvent<SelectCharacterPacket>) {
    val charId = event.packet.characterId
    val stored = CharacterStore.getCharacter(charId)

    if (stored == null) {
      log.warn { "Character $charId not found" }
      return
    }

    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) {
      log.warn { "No session for channel" }
      return
    }

    SessionManager.setActiveCharacter(event.ctx.channel(), charId)
    log.info { "Player selected character '${stored.info.name}' (id=$charId)" }

    val info = stored.info
    val now = LocalDateTime.now()
    val updatedInfo = info.copy(lastLogin = now)
    CharacterStore.updateCharacter(updatedInfo)

    val containers =
        mapOf(
            PokemonContainer.PARTY to stored.pokemon,
            PokemonContainer.PC to stored.pcStorage,
            PokemonContainer.BATTLE_BOX_1 to emptyList<de.fiereu.openmmo.common.Pokemon>(),
            PokemonContainer.BATTLE_BOX_2 to emptyList<de.fiereu.openmmo.common.Pokemon>(),
            PokemonContainer.DAYCARE to emptyList<de.fiereu.openmmo.common.Pokemon>(),
        )
    for ((container, pokemon) in containers) {
      event.respond(
          PokemonContainerPacket(
              container = container,
              hasChange = true,
              delete = false,
              pokemon = pokemon,
          ))
    }

    sendRawPackets(event, info)
    preloadMapAndJoin(event, charId, session)
  }

  private fun sendRawPackets(
      event: PacketEvent<SelectCharacterPacket>,
      info: de.fiereu.openmmo.common.CharacterInfo
  ) {
    packetSender.sendRaw(event.ctx, 0x40u, bytesOf(0x01, 0x01, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x55u, bytesOf(0x00, 0x00, 0x00))

    listOf(PokemonContainer.UNKNOWN_13, PokemonContainer.UNKNOWN_14).forEach { container ->
      event.respond(
          PokemonContainerPacket(
              container = container,
              hasChange = true,
              delete = false,
              pokemon = emptyList(),
          ))
    }

    packetSender.sendRaw(event.ctx, 0x67u, bytesOf(0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x63u, bytesOf(0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x98u, bytesOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
    packetSender.sendRaw(
        event.ctx, 0x0Au, bytesOf(0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x29u, bytesOf(0x00, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x29u, hexToBytes("0101006F080100"))
    packetSender.sendRaw(event.ctx, 0x29u, bytesOf(0x02, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x29u, bytesOf(0x03, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x29u, bytesOf(0x04, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x29u, bytesOf(0x0A, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x29u, bytesOf(0x80, 0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x1Cu, bytesOf(0x00, 0x00))

    event.respond(SelectedCharacterPacket(info))

    packetSender.sendRaw(event.ctx, 0xF1u, bytesOf(0x05))
    packetSender.sendRaw(event.ctx, 0x4Au, bytesOf(0x00))
    packetSender.sendRaw(event.ctx, 0x4Du, bytesOf(0x18))

    event.respond(
        ChatMessagePacket(
            ChatType.GAME_NOTIFICATIONS,
            Language.EN,
            "Welcome to OpenMMO!",
            "",
        ))

    packetSender.sendRaw(
        event.ctx,
        0xF5u,
        bytesOf(
            0x77,
            0x1A,
            0x00,
            0x00,
            0x81,
            0x00,
            0x05,
            0x4A,
            0x00,
            0x6F,
            0x00,
            0x73,
            0x00,
            0x75,
            0x00,
            0x6B,
            0x00,
            0x65,
            0x00,
            0x00,
            0x00,
            0x10,
        ))

    packetSender.sendRaw(
        event.ctx,
        0x4Fu,
        hexToBytes(
            "B8009502029202019102029002008F02008C02008902008802028702008602008502008402008302008202008102008002027F02017E02027D02017B02017802037602037402037102026D02026C02016402026102015C02035602015102014F02014302013D02033B02033302023102022B02022902022102021602011202010E0202F10101F00103EE0100ED0100EC0102EB0100EA0100E90100E80101E70100E60100E50101E40100E30100E20100E10100E00100DF0103DE0102DB0103DA0102D90101D80101D50102D40101D10102D00102CE0102CD0101CC0103C60103C20102C00101BD0101BA0102B50101B40103B30103AE0102AD0102A80103A701029701038E01018B01028801018501038201008101018001007F01007E01007D01007C01007B01007A01007901007801017501016401035E01035601014E01034A01024401023F01033401022E01012901032301021E01011A0103170101100103040103010103FE0003FB0000FA0000F90000F80001F50001F40002F30001F20002ED0003E90002E80002E60001E30001E20003DD0003D60002D40001D30003D00003CF0002CD0002C70002C50002C40002C30002BA0002B20003AB0003A90002A000029700009600009500019200039100018F00038D00038700038600038200017900017100016E00016B00036500035E00015B00025200035000014C00034900024400024100033E00033B00033700033100032A00032600022400032200021F0002090003030002080600000F00000C00000B00000D00000700000800000E0000"))

    packetSender.sendRaw(event.ctx, 0xD3u, bytesOf(0x00))

    packetSender.sendRaw(
        event.ctx,
        0xFCu,
        hexToBytes(
            "10D6B660234D938A916F030CD1B64F9CDD0100040100007F0601000000000000000000000000000000621E0A"))

    packetSender.sendRaw(event.ctx, 0x59u, bytesOf(0x00, 0x00))
    packetSender.sendRaw(event.ctx, 0x59u, bytesOf(0x01, 0x00))

    packetSender.sendRaw(
        event.ctx,
        0x6Eu,
        bytesOf(
            0x05,
            0x92,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0xF5,
            0x00,
            0x00))

    packetSender.sendRaw(event.ctx, 0xB9u, bytesOf(0x00))
  }

  private fun preloadMapAndJoin(
      event: PacketEvent<SelectCharacterPacket>,
      charId: Long,
      session: de.fiereu.openmmo.server.game.session.Session,
  ) {
    val info = CharacterStore.getCharacter(charId)?.info ?: return

    val map = MapManager.getMap(info.positionRegionId, info.positionBankId, info.positionMapId)
    if (map != null) {
      event.respond(MapManager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true))
      mapLoadService.preloadConnectedMaps(event.ctx, map)
    } else {
      log.warn {
        "Map not found for position ${info.positionRegionId}:${info.positionBankId}:${info.positionMapId}"
      }
    }

    session.regionId = info.positionRegionId.toInt()
    session.bankId = info.positionBankId.toInt()
    session.mapId = info.positionMapId.toInt()
    session.x = info.positionX
    session.y = info.positionY

    multiplayerService.broadcastMessage(
        ChatMessagePacket(
            ChatType.GAME_NOTIFICATIONS,
            Language.EN,
            "Player ${info.name} joined the game.",
            "",
        ))
  }

  fun onRequestPlayer(event: PacketEvent<RequestPlayerPacket>) {
    log.info { "Received RequestPlayerPacket" }
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) {
      log.warn { "RequestPlayer from unknown session" }
      return
    }

    val charId = session.characterId
    if (charId == null) {
      log.warn { "RequestPlayer without active character" }
      return
    }
    val stored = CharacterStore.getCharacter(charId)
    if (stored == null) {
      log.warn { "RequestPlayer for unknown character $charId" }
      return
    }
    val info = stored.info

    log.info { "Sending LoadEntity + 0x90 for character '${info.name}'" }
    val facing = session.facingDirection
    val loadEntity = mapLoadService.createLoadEntity(info, facing)

    event.ctx.channel().writeAndFlush(loadEntity)
    mapLoadService.send90(event.ctx, loadEntity.entityId, loadEntity.skin)
    npcService.spawnNpcsForMap(event.ctx, info.positionBankId.toInt(), info.positionMapId.toInt())

    val bankId = info.positionBankId.toInt()
    val mapId = info.positionMapId.toInt()
    val regionId = info.positionRegionId.toInt()
    session.regionId = regionId
    session.bankId = bankId
    session.mapId = mapId
    session.x = info.positionX
    session.y = info.positionY

    val currentParty = CharacterStore.getCharacter(charId)?.pokemon ?: emptyList()
    val hasFollower = currentParty.isNotEmpty()
    val followerDexId = (currentParty.firstOrNull()?.dexId ?: 0).toShort()

    // Send LoadEntity of each existing player in this map to the new player
    val others = SessionManager.getOthersInMap(charId, regionId, bankId, mapId)
    for (other in others) {
      val otherStored = CharacterStore.getCharacter(other.characterId ?: continue) ?: continue
      val otherParty = otherStored.pokemon
      event.ctx
          .channel()
          .write(
              LoadEntityPacket(
                  entityId = other.characterId!!,
                  skin = SkinSet(),
                  name = otherStored.info.name,
                  regionId = other.regionId,
                  bankId = other.bankId,
                  mapId = other.mapId,
                  x = other.x.toInt(),
                  y = other.y.toInt(),
                  z = 0,
                  facing = other.facingDirection,
                  status = de.fiereu.openmmo.common.enums.EntityStatus.NONE,
                  hasFollower = otherParty.isNotEmpty(),
                  followerDexId = (otherParty.firstOrNull()?.dexId ?: 0).toShort(),
              ))
    }

    // Broadcast LoadEntity of new player to all existing players in this map
    val selfEntity =
        LoadEntityPacket(
            entityId = charId,
            skin = SkinSet(),
            name = info.name,
            regionId = regionId,
            bankId = bankId,
            mapId = mapId,
            x = info.positionX.toInt(),
            y = info.positionY.toInt(),
            z = 0,
            facing = facing,
            status = de.fiereu.openmmo.common.enums.EntityStatus.NONE,
            hasFollower = hasFollower,
            followerDexId = followerDexId,
        )
    for (other in others) {
      other.channel.writeAndFlush(selfEntity)
    }

    event.ctx.channel().writeAndFlush(RenderScreenPacket(true))
    log.info { "Player $charId spawned in bank=$bankId map=$mapId; ${others.size} others present" }
  }

  private fun bytesOf(vararg ints: Int) = ByteArray(ints.size) { ints[it].toByte() }

  private fun hexToBytes(hex: String): ByteArray {
    val hexStr = hex.replace(" ", "").replace("\n", "")
    return ByteArray(hexStr.length / 2) { hexStr.substring(it * 2, it * 2 + 2).toInt(16).toByte() }
  }
}
