package de.fiereu.openmmo.server.game.protocol.game

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.protocols.Protocol
import de.fiereu.openmmo.protocols.game.packets.CharacterEntry
import de.fiereu.openmmo.protocols.game.packets.CharacterListPacket
import de.fiereu.openmmo.protocols.game.packets.ChatMessagePacket
import de.fiereu.openmmo.protocols.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.protocols.game.packets.DialogChoicePacket
import de.fiereu.openmmo.protocols.game.packets.DialogStatePacket
import de.fiereu.openmmo.protocols.game.packets.EntityInteractPacket
import de.fiereu.openmmo.protocols.game.packets.EntityLeavePacket
import de.fiereu.openmmo.protocols.game.packets.EntityMovePacket
import de.fiereu.openmmo.protocols.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.protocols.game.packets.InteractiveResponsePacket
import de.fiereu.openmmo.protocols.game.packets.JoinGamePacket
import de.fiereu.openmmo.protocols.game.packets.KeepAlivePacket
import de.fiereu.openmmo.protocols.game.packets.LoadEntityPacket
import de.fiereu.openmmo.protocols.game.packets.MapData
import de.fiereu.openmmo.protocols.game.packets.MapTransitionAckPacket
import de.fiereu.openmmo.protocols.game.packets.MapTransitionPacket
import de.fiereu.openmmo.protocols.game.packets.MovementPacket
import de.fiereu.openmmo.protocols.game.packets.NewAuthData
import de.fiereu.openmmo.protocols.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.protocols.game.packets.RenderScreenPacket
import de.fiereu.openmmo.protocols.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.protocols.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.protocols.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.protocols.game.packets.SelectedCharacterPacket
import de.fiereu.openmmo.protocols.game.packets.codecs.SkinSet
import de.fiereu.openmmo.server.config.ServerConfig
import de.fiereu.openmmo.server.game.protocol.game.ext.accept
import de.fiereu.openmmo.server.game.protocol.game.ext.buildAndRespond
import de.fiereu.openmmo.server.game.session.ScriptPage
import de.fiereu.openmmo.server.game.session.SessionManager
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.MapManager
import de.fiereu.openmmo.server.game.world.WarpTile
import de.fiereu.openmmo.server.netty.handlers.ProtocolHandler
import de.fiereu.openmmo.server.protocol.PacketEvent
import de.fiereu.openmmo.server.protocol.respond
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val log = KotlinLogging.logger {}

class GameProtocolHandler(
    protocol: Protocol,
    serverConfig: ServerConfig,
    private val coroutineScope: CoroutineScope,
) : ProtocolHandler(protocol, serverConfig) {

  private val sequenceCounter = java.util.concurrent.atomic.AtomicInteger(0)
  private val npcEntityIdCounter = java.util.concurrent.atomic.AtomicLong(0x1A69000000000000L)
  private val npcEntityIds = mutableMapOf<String, Long>() // "bankId:mapId:entityIdx" -> entityId

  override fun onActive(ctx: ChannelHandlerContext) {
    log.info { "Client ${ctx.channel().remoteAddress()} connected to game server." }
  }

  override fun channelInactive(ctx: ChannelHandlerContext) {
    val session = SessionManager.getSessionByChannel(ctx.channel())
    if (session != null) {
      log.info { "Player ${session.characterId} disconnected." }

      session.characterId?.let { charId ->
        broadcastExcept(ctx.channel(), EntityLeavePacket(charId))
      }

      broadcastMessage(
          ChatMessagePacket(
              de.fiereu.openmmo.common.enums.ChatType.GAME_NOTIFICATIONS,
              de.fiereu.openmmo.common.enums.Language.EN,
              "A player left the game.",
              ""))
      SessionManager.removeSession(ctx.channel())
    }
    super.channelInactive(ctx)
  }

  @Suppress("unchecked_cast")
  override fun onPacketReceived(event: PacketEvent<*>) {
    coroutineScope.launch {
      when (event.packet) {
        is JoinGamePacket -> onJoinGame(event as PacketEvent<JoinGamePacket>)
        is CreateCharacterPacket -> onCreateCharacter(event as PacketEvent<CreateCharacterPacket>)
        is RequestCharactersPacket ->
            onCharacterRequest(event as PacketEvent<RequestCharactersPacket>)
        is SelectCharacterPacket -> onCharacterSelected(event as PacketEvent<SelectCharacterPacket>)
        is RequestPlayerPacket -> onRequestPlayerPacket(event as PacketEvent<RequestPlayerPacket>)
        is MovementPacket -> onMovement(event as PacketEvent<MovementPacket>)
        is FaceDirectionPacket -> onFaceDirection(event as PacketEvent<FaceDirectionPacket>)
        is KeepAlivePacket -> onKeepAlive(event as PacketEvent<KeepAlivePacket>)
        is EntityInteractPacket -> onEntityInteract(event as PacketEvent<EntityInteractPacket>)
        is InteractiveResponsePacket ->
            onInteractive(event as PacketEvent<InteractiveResponsePacket>)
        is DialogChoicePacket -> onDialogChoice(event as PacketEvent<DialogChoicePacket>)
        is ChatMessagePacket -> onChatMessage(event as PacketEvent<ChatMessagePacket>)
        else -> log.warn { "Unhandled game packet type: ${event.packet::class.simpleName}" }
      }
    }
  }

  fun onJoinGame(event: PacketEvent<JoinGamePacket>) {
    val join = event.packet
    log.info { "Player joined the game." }

    val authData = join.authData
    var userId = 0
    if (authData is NewAuthData) {
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
    sendCharacterList(event.ctx, session.userId, event::respond)
  }

  fun onCharacterRequest(event: PacketEvent<RequestCharactersPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session != null) {
      sendCharacterList(event.ctx, session.userId, event::respond)
    } else {
      log.warn { "Character request from unauthenticated session" }
      event.respond(CharacterListPacket(emptyList()))
    }
  }

  private inline fun sendCharacterList(
      ctx: io.netty.channel.ChannelHandlerContext,
      userId: Int,
      respond: (Any) -> Unit,
  ) {
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
    respond(CharacterListPacket(entries))
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
    val now = java.time.LocalDateTime.now()
    val updatedInfo = info.copy(lastLogin = now)
    CharacterStore.updateCharacter(updatedInfo)

    // Real server order (from packets.db):
    // 1. 5 PokemonContainer (PARTY, PC, BATTLE_BOX_1, BATTLE_BOX_2, DAYCARE)
    log.info {
      "Sending PokemonContainer packets (party=${stored.pokemon.size}, pc=${stored.pcStorage.size})"
    }

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
              container = container, hasChange = true, delete = false, pokemon = pokemon))
    }

    // 2. 0x40 (4 bytes)
    sendRaw(event.ctx, 0x40u, bytesOf(0x01, 0x01, 0x00, 0x00))
    // 3. 0x55 (3 bytes)
    sendRaw(event.ctx, 0x55u, bytesOf(0x00, 0x00, 0x00))

    // 4. 2 more PokemonContainer (UNKNOWN_13, UNKNOWN_14)
    log.info { "Sending last 2 PokemonContainer packets" }
    listOf(PokemonContainer.UNKNOWN_13, PokemonContainer.UNKNOWN_14).forEach { container ->
      event.respond(
          PokemonContainerPacket(
              container = container, hasChange = true, delete = false, pokemon = emptyList()))
    }

    // 5-11. Packets required before SelectedCharacter (from real server capture)
    sendRaw(event.ctx, 0x67u, bytesOf(0x00, 0x00))
    sendRaw(event.ctx, 0x63u, bytesOf(0x00, 0x00))
    sendRaw(event.ctx, 0x98u, bytesOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
    sendRaw(event.ctx, 0x0Au, bytesOf(0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
    sendRaw(event.ctx, 0x29u, bytesOf(0x00, 0x00, 0x00))
    sendRaw(event.ctx, 0x29u, hexToBytes("0101006F080100"))
    sendRaw(event.ctx, 0x29u, bytesOf(0x02, 0x00, 0x00))
    sendRaw(event.ctx, 0x29u, bytesOf(0x03, 0x00, 0x00))
    sendRaw(event.ctx, 0x29u, bytesOf(0x04, 0x00, 0x00))
    sendRaw(event.ctx, 0x29u, bytesOf(0x0A, 0x00, 0x00))
    sendRaw(event.ctx, 0x29u, bytesOf(0x80, 0x00, 0x00))
    sendRaw(event.ctx, 0x1Cu, bytesOf(0x00, 0x00))

    // 12. SelectedCharacter (0x04)
    log.info { "Sending SelectedCharacterPacket" }
    event.respond(SelectedCharacterPacket(info))

    // Raw packets before ChatMessage (from real server capture)
    sendRaw(event.ctx, 0xF1u, bytesOf(0x05))
    sendRaw(event.ctx, 0x4Au, bytesOf(0x00))
    sendRaw(event.ctx, 0x4Du, bytesOf(0x18))

    event.respond(
        ChatMessagePacket(
            de.fiereu.openmmo.common.enums.ChatType.GAME_NOTIFICATIONS,
            de.fiereu.openmmo.common.enums.Language.EN,
            "Welcome to OpenMMO!",
            ""))

    // 0xF5 packet (map-adjacent setup)
    sendRaw(
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
            0x10))

    // 0x4F (pokedex/starter data)
    sendRaw(
        event.ctx,
        0x4Fu,
        hexToBytes(
            "B8009502029202019102029002008F02008C02008902008802028702008602008502008402008302008202008102008002027F02017E02027D02017B02017802037602037402037102026D02026C02016402026102015C02035602015102014F02014302013D02033B02033302023102022B02022902022102021602011202010E0202F10101F00103EE0100ED0100EC0102EB0100EA0100E90100E80101E70100E60100E50101E40100E30100E20100E10100E00100DF0103DE0102DB0103DA0102D90101D80101D50102D40101D10102D00102CE0102CD0101CC0103C60103C20102C00101BD0101BA0102B50101B40103B30103AE0102AD0102A80103A701029701038E01018B01028801018501038201008101018001007F01007E01007D01007C01007B01007A01007901007801017501016401035E01035601014E01034A01024401023F01033401022E01012901032301021E01011A0103170101100103040103010103FE0003FB0000FA0000F90000F80001F50001F40002F30001F20002ED0003E90002E80002E60001E30001E20003DD0003D60002D40001D30003D00003CF0002CD0002C70002C50002C40002C30002BA0002B20003AB0003A90002A000029700009600009500019200039100018F00038D00038700038600038200017900017100016E00016B00036500035E00015B00025200035000014C00034900024400024100033E00033B00033700033100032A00032600022400032200021F0002090003030002080600000F00000C00000B00000D00000700000800000E0000"))

    sendRaw(event.ctx, 0xD3u, bytesOf(0x00))

    // 0xFC packet
    sendRaw(
        event.ctx,
        0xFCu,
        hexToBytes(
            "10D6B660234D938A916F030CD1B64F9CDD0100040100007F0601000000000000000000000000000000621E0A"))

    sendRaw(event.ctx, 0x59u, bytesOf(0x00, 0x00))
    sendRaw(event.ctx, 0x59u, bytesOf(0x01, 0x00))

    // 0x6E packet
    sendRaw(
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

    sendRaw(event.ctx, 0xB9u, bytesOf(0x00))

    val map = MapManager.getMap(info.positionRegionId, info.positionBankId, info.positionMapId)
    if (map != null) {
      log.info {
        "Sending LoadMap for ${info.positionRegionId}:${info.positionBankId}:${info.positionMapId}"
      }
      // Login uses both flags for initial map load (interior start)
      event.respond(MapManager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true))
      // Preload connected maps
      for (conn in map.connections) {
        val connectedMap = MapManager.getMap(1, conn.targetBank.toByte(), conn.targetMap.toByte())
        if (connectedMap != null) {
          event.ctx
              .channel()
              .writeAndFlush(
                  MapManager.createLoadMapPacket(
                      connectedMap, reloadPlayer = false, deleteCache = true))
          log.info { "Preloaded connected map ${conn.targetBank}:${conn.targetMap}" }
        }
      }
    } else {
      log.warn {
        "Map not found for position ${info.positionRegionId}:${info.positionBankId}:${info.positionMapId}"
      }
    }

    // Track player position in session for multiplayer
    session.regionId = info.positionRegionId.toInt()
    session.bankId = info.positionBankId.toInt()
    session.mapId = info.positionMapId.toInt()
    session.x = info.positionX
    session.y = info.positionY

    broadcastMessage(
        ChatMessagePacket(
            de.fiereu.openmmo.common.enums.ChatType.GAME_NOTIFICATIONS,
            de.fiereu.openmmo.common.enums.Language.EN,
            "Player ${stored.info.name} joined the game.",
            ""))
  }

  fun onRequestPlayerPacket(event: PacketEvent<RequestPlayerPacket>) {
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
    val loadEntity = createLoadEntity(info, facing)
    // Send LoadEntity + 0x90 first (RenderScreen(true) must come last)
    event.ctx.channel().writeAndFlush(loadEntity)
    send90(event.ctx, loadEntity.entityId, loadEntity.skin)
    spawnNpcsForMap(event.ctx, info.positionBankId.toInt(), info.positionMapId.toInt())

    // Multiplayer: broadcast LoadEntity of self to other players in same map,
    // and send LoadEntity of existing players to this player
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
                  facing = other.facingDirection,
                  status = de.fiereu.openmmo.common.enums.EntityStatus.NONE,
                  hasFollower = otherParty.isNotEmpty(),
                  followerDexId = (otherParty.firstOrNull()?.dexId ?: 0).toShort()))
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
            facing = facing,
            status = de.fiereu.openmmo.common.enums.EntityStatus.NONE,
            hasFollower = hasFollower,
            followerDexId = followerDexId)
    for (other in others) {
      other.channel.writeAndFlush(selfEntity)
    }
    // RenderScreen(true) must be the last packet — real server sends it after all entities/NPCs
    event.ctx.channel().writeAndFlush(RenderScreenPacket(true))
    log.info { "Player $charId spawned in bank=$bankId map=$mapId; ${others.size} others present" }
  }

  private fun scriptParams(script: String): List<ScriptPage>? {
    if (script == "0x0") return null
    // Known scripts from packet captures — each entry is a list of dialog pages
    val known =
        mapOf<String, List<ScriptPage>>(
            "LittlerootTown_ProfessorBirchsLab_EventScript_Birch" to
                listOf(ScriptPage(type = 0x04, unk1 = 0xAA74, unk2 = 0x1F10, unk3 = 0x0708)),
            "LittlerootTown_ProfessorBirchsLab_EventScript_Aide" to
                listOf(ScriptPage(type = 0x04, unk1 = 0xA6CE, unk2 = 0x1F10, unk3 = 0x04B0)),
            "PlayersHouse_1F_EventScript_Mom" to
                listOf(
                    ScriptPage(type = 0x04, unk1 = 0x087D, unk2 = 0x1F10, unk3 = 0x04B0),
                    ScriptPage(type = 0x04, unk1 = 0x7D5C, unk2 = 0x1F10, unk3 = 0x02BC),
                ),
            "RivalsHouse_1F_EventScript_RivalMom" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x8CE3, unk2 = 0x1E10, unk3 = 0x02BC)),
            "RivalsHouse_1F_EventScript_RivalSibling" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x8B25, unk2 = 0x1E10, unk3 = 0x0708)),
            "LittlerootTown_EventScript_Twin" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x6292, unk2 = 0x1F10, unk3 = 0x04B0)),
            "LittlerootTown_EventScript_Boy" to
                listOf(ScriptPage(type = 0x04, unk1 = 0x938D, unk2 = 0x1F10, unk3 = 0x04B0)),
        )
    return known[script]
  }

  private fun bytesOf(vararg ints: Int) = ByteArray(ints.size) { ints[it].toByte() }

  private fun sendInteractive(
      ctx: ChannelHandlerContext,
      id: Int,
      entityId: Long,
      type: Int = 0x04,
      unk1: Int = 0xAA74,
      unk2: Int = 0x1F10,
      unk3: Int = 0x0708,
  ) {
    val buf = Unpooled.buffer(19)
    buf.writeByte(id)
    buf.writeByte(type)
    buf.writeShortLE(unk1)
    buf.writeShortLE(unk2)
    buf.writeLongLE(entityId)
    buf.writeShortLE(unk3)
    buf.writeShortLE(0)
    buf.writeByte(0)
    val raw = ByteArray(buf.readableBytes())
    buf.readBytes(raw)
    buf.release()
    sendRaw(ctx, 0x21u, raw)
  }

  private fun hexToBytes(hex: String): ByteArray {
    val hexStr = hex.replace(" ", "").replace("\n", "")
    return ByteArray(hexStr.length / 2) { hexStr.substring(it * 2, it * 2 + 2).toInt(16).toByte() }
  }

  private fun createLoadEntity(
      info: de.fiereu.openmmo.common.CharacterInfo,
      facing: Direction = Direction.DOWN,
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
        facing = facing,
        status = de.fiereu.openmmo.common.enums.EntityStatus.NONE,
        hasFollower = false,
        followerDexId = 0,
    )
  }

  private fun warpCharacter(ctx: ChannelHandlerContext, charId: Long, warp: WarpTile) {
    val session = SessionManager.getSessionByCharacterId(charId)
    if (session != null) {
      session.justWarped = true
    }
    val stored = CharacterStore.getCharacter(charId) ?: return

    val newInfo =
        stored.info.copy(
            positionRegionId = warp.targetRegionId,
            positionBankId = warp.targetBankId,
            positionMapId = warp.targetMapId,
            positionX = warp.targetX.toShort(),
            positionY = warp.targetY.toShort(),
        )
    CharacterStore.updateCharacter(newInfo)

    // Multiplayer: broadcast EntityLeave to old-map players before position update
    val oldRegionId = session?.regionId
    val oldBankId = session?.bankId
    val oldMapId = session?.mapId
    if (oldBankId != null && oldMapId != null) {
      val oldMapPlayers =
          SessionManager.getOthersInMap(charId, oldRegionId ?: 1, oldBankId, oldMapId)
      for (other in oldMapPlayers) {
        other.channel.writeAndFlush(EntityLeavePacket(charId))
      }
    }

    // Update session position for multiplayer tracking
    if (session != null) {
      session.regionId = warp.targetRegionId.toInt()
      session.bankId = warp.targetBankId.toInt()
      session.mapId = warp.targetMapId.toInt()
      session.x = warp.targetX.toShort()
      session.y = warp.targetY.toShort()
    }

    // Real server warp sequence (from packets.db):
    // MapTransition → RenderScreen(false) → LoadEntity(0x05) → MapTransitionAck → LoadMap(×N) →
    // [client sends RequestPlayer(0x05)] → LoadEntity + 0x90 + NpcSpawn + others' EntityInfo +
    // RenderScreen(true)
    ctx.channel().writeAndFlush(MapTransitionPacket())
    ctx.channel().writeAndFlush(RenderScreenPacket(false))
    ctx.channel().writeAndFlush(createLoadEntity(newInfo))
    ctx.channel().writeAndFlush(MapTransitionAckPacket(0))

    val map = MapManager.getMap(warp.targetRegionId, warp.targetBankId, warp.targetMapId)
    if (map != null) {
      // Interior maps (no connections): flags=0x03 (both). Exterior: flags=0x01 (deleteCache only)
      val isInterior = map.connections.isEmpty()
      ctx.channel()
          .writeAndFlush(
              MapManager.createLoadMapPacket(map, reloadPlayer = isInterior, deleteCache = true))
      // Preload connected maps — real server sends these AFTER MapTransitionAck with
      // flags=02
      // (reloadPlayer=true, deleteCache=false)
      for (conn in map.connections) {
        val connectedMap = MapManager.getMap(1, conn.targetBank.toByte(), conn.targetMap.toByte())
        if (connectedMap != null) {
          ctx.channel()
              .writeAndFlush(
                  MapManager.createLoadMapPacket(
                      connectedMap, reloadPlayer = true, deleteCache = false))
        }
      }
    } else {
      log.warn {
        "Map not found for warp target ${warp.targetRegionId}:${warp.targetBankId}:${warp.targetMapId}"
      }
    }

    log.info { "Player $charId warped to bank=${warp.targetBankId} map=${warp.targetMapId}" }
  }

  private fun edgeTransition(
      ctx: ChannelHandlerContext,
      charId: Long,
      connection: MapData.GbaConnection,
      targetX: Byte,
      targetY: Byte,
      direction: Direction
  ) {
    val targetBank = connection.targetBank.toByte()
    val targetMap = connection.targetMap.toByte()
    val map = MapManager.getMap(1, targetBank, targetMap) ?: return
    // Update session + store to new map and position
    val session = SessionManager.getSessionByCharacterId(charId)
    session?.bankId = targetBank.toInt()
    session?.mapId = targetMap.toInt()
    session?.x = targetX.toShort()
    session?.y = targetY.toShort()
    CharacterStore.updatePosition(charId, targetX.toShort(), targetY.toShort())
    // Real server sends LoadMap (flags=00) during edge transition (capture ID 108)
    ctx.channel()
        .writeAndFlush(
            MapManager.createLoadMapPacket(map, reloadPlayer = false, deleteCache = false))
    // Preload connected maps of the target map (deep preload — real server sends these
    // at ID 108)
    for (conn in map.connections) {
      val nextMap = MapManager.getMap(1, conn.targetBank.toByte(), conn.targetMap.toByte())
      if (nextMap != null) {
        ctx.channel()
            .writeAndFlush(
                MapManager.createLoadMapPacket(nextMap, reloadPlayer = false, deleteCache = false))
      }
    }
    // Spawn NPCs of the new map
    spawnNpcsForMap(ctx, targetBank.toInt(), targetMap.toInt())
    // Send EntityMove to the moving player (movement confirmation) and broadcast to
    // others
    val seq = sequenceCounter.incrementAndGet().toByte()
    val movePkt =
        EntityMovePacket(
            entityId = charId, x = targetX, y = targetY, direction = direction, seq = seq)
    ctx.channel().writeAndFlush(movePkt)
    broadcastExcept(ctx.channel(), movePkt)
    log.info { "Player $charId edge-transitioned to bank=$targetBank map=$targetMap" }
  }

  private fun sendEntityWith90(ctx: ChannelHandlerContext, loadEntity: LoadEntityPacket) {
    ctx.channel().writeAndFlush(loadEntity)
    send90(ctx, loadEntity.entityId, loadEntity.skin)
    ctx.channel().writeAndFlush(RenderScreenPacket(true))
  }

  private fun send90(ctx: ChannelHandlerContext, entityId: Long, skin: SkinSet) {
    val raw90Buf = io.netty.buffer.Unpooled.buffer()
    raw90Buf.writeShortLE(0x9000)
    raw90Buf.writeLongLE(entityId)
    raw90Buf.writeByte(0x01)
    raw90Buf.writeByte(0x00)
    var mask = 0
    for (slot in de.fiereu.openmmo.common.enums.SkinSlot.entries) {
      val s = skin[slot]
      if (s != null && (s.type != null || s.color != null)) {
        mask = mask or (1 shl slot.ordinal)
      }
    }
    raw90Buf.writeShortLE(mask)
    for (slot in de.fiereu.openmmo.common.enums.SkinSlot.entries) {
      if ((mask and (1 shl slot.ordinal)) != 0) {
        val s = skin[slot]
        val type = s?.type ?: 0x3FFFu
        val color = s?.color ?: 0x3Fu
        val compressed = (type.toInt() and 0x3FFF) or ((color.toInt() and 0x3F) shl 10)
        raw90Buf.writeShortLE(compressed)
      }
    }
    raw90Buf.writeByte(0x01)
    val raw90 = ByteArray(raw90Buf.readableBytes())
    raw90Buf.readBytes(raw90)
    raw90Buf.release()
    sendRaw(ctx, 0x90u, raw90)
  }

  private fun spawnNpcsForMap(ctx: ChannelHandlerContext, bankId: Int, mapId: Int) {
    val map = MapManager.getMap(1, bankId.toByte(), mapId.toByte())
    if (map == null) return
    val entityIds = mutableListOf<Long>()
    for (npc in map.npcs) {
      val key = "$bankId:$mapId:${npc.entityIdx}"
      val entityId = npcEntityIds.getOrPut(key) { npcEntityIdCounter.incrementAndGet() }
      val unk3 = ((npc.movementType and 0xFF) shl 8) or 0x02
      val unk4 =
          if (npc.movementType in 1..6 || (npc.movementType in 25..52)) {
            ((npc.movementRangeX and 0xFF) shl 8) or (npc.movementRangeY and 0xFF)
          } else {
            0
          }
      val spawnPacket =
          de.fiereu.openmmo.protocols.game.packets.NpcSpawnPacket(
              entityId = entityId,
              unk1 = 1,
              unk2 = npc.graphicsId,
              unk3 = unk3,
              unk4 = unk4,
              regionId = 1,
              bankId = bankId,
              mapId = mapId,
              x = npc.x,
              y = npc.y,
              facing = npc.facing,
              unk5 = 2,
              unk6 = 8)
      ctx.channel().write(spawnPacket)
      entityIds.add(entityId)
    }
    ctx.channel().flush()
  }

  fun onMovement(event: PacketEvent<MovementPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return

    val charId = session.characterId ?: return
    val msg = event.packet

    log.info { "Movement: char=$charId, x=${msg.x}, y=${msg.y}, dir=${msg.direction}" }
    session.facingDirection = msg.direction

    // Store previous position for detecting wall bumps
    val prevStored = CharacterStore.getCharacter(charId)
    val prevX = prevStored?.info?.positionX?.toInt()
    val prevY = prevStored?.info?.positionY?.toInt()
    val isWallBump = prevX == msg.x && prevY == msg.y

    // Check for warps and edge transitions BEFORE updating position / broadcasting EntityMove.
    // Real server sends LoadMap *before* EntityMove for edge transitions.
    val stored = CharacterStore.getCharacter(charId)
    if (stored != null) {
      val currentMap =
          MapManager.getMap(
              stored.info.positionRegionId, stored.info.positionBankId, stored.info.positionMapId)
      if (currentMap != null) {
        // Skip warp check for the first movement after a warp
        if (session.justWarped) {
          session.justWarped = false
        } else {
          // Direct warp: player stepped onto a warp tile
          val warp =
              currentMap.warps.find { w ->
                w.x == msg.x &&
                    w.y == msg.y &&
                    (w.facingDirection == null || w.facingDirection == msg.direction)
              }
          if (warp != null) {
            log.info {
              "DIRECT WARP at (${msg.x}, ${msg.y}) facing ${msg.direction} → ${warp.targetRegionId}:${warp.targetBankId}:${warp.targetMapId} (${warp.targetX}, ${warp.targetY})"
            }
            warpCharacter(event.ctx, charId, warp)
            return
          }

          // Adjacent warp: player is standing in front of a door tile, facing it
          val adjX =
              msg.x +
                  when (msg.direction) {
                    Direction.RIGHT -> 1
                    Direction.LEFT -> -1
                    else -> 0
                  }
          val adjY =
              msg.y +
                  when (msg.direction) {
                    Direction.UP -> -1
                    Direction.DOWN -> 1
                    else -> 0
                  }
          val adjWarp =
              currentMap.warps.find { w ->
                w.x == adjX &&
                    w.y == adjY &&
                    (w.facingDirection == null || w.facingDirection == msg.direction)
              }
          if (adjWarp != null) {
            log.info {
              "ADJACENT WARP at (${msg.x}, ${msg.y}) facing ${msg.direction} → tile ($adjX, $adjY) → ${adjWarp.targetRegionId}:${adjWarp.targetBankId}:${adjWarp.targetMapId} (${adjWarp.targetX}, ${adjWarp.targetY})"
            }
            warpCharacter(event.ctx, charId, adjWarp)
            return
          }
        }

        // Map edge transition: client pre-computes position on connected map.
        // Detect by checking for an impossible single-step distance from the
        // previous position, then use prevPos to identify the edge.
        if (prevX != null && prevY != null && !isWallBump) {
          val dx = kotlin.math.abs(msg.x - prevX)
          val dy = kotlin.math.abs(msg.y - prevY)
          if (dx > 1 || dy > 1) {
            val gbaDirection =
                when {
                  prevY == 0 -> 2 // North
                  prevY == currentMap.height - 1 -> 1 // South
                  prevX == 0 -> 3 // West
                  prevX == currentMap.width - 1 -> 4 // East
                  else -> null
                }
            if (gbaDirection != null) {
              val connection = currentMap.connections.find { it.direction == gbaDirection }
              if (connection != null) {
                val targetMap =
                    MapManager.getMap(
                        1, connection.targetBank.toByte(), connection.targetMap.toByte())
                if (targetMap != null) {
                  val targetX =
                      when (gbaDirection) {
                        3 -> targetMap.width - 1
                        4 -> 0
                        else -> msg.x.coerceIn(0, targetMap.width - 1)
                      }
                  val targetY =
                      when (gbaDirection) {
                        1 -> 0
                        2 -> targetMap.height - 1
                        else -> msg.y.coerceIn(0, targetMap.height - 1)
                      }
                  log.info {
                    "MAP TRANSITION prev=($prevX, $prevY) dir=${msg.direction} → bank=${connection.targetBank} map=${connection.targetMap} ($targetX, $targetY)"
                  }
                  edgeTransition(
                      event.ctx,
                      charId,
                      connection,
                      targetX.toByte(),
                      targetY.toByte(),
                      msg.direction)
                  return
                }
              }
            }
          }
        }
      }
    }

    // No transition: normal movement update + EntityMove broadcast
    CharacterStore.updatePosition(charId, msg.x.toShort(), msg.y.toShort())
    session.x = msg.x.toShort()
    session.y = msg.y.toShort()
    if (!isWallBump) {
      val seq = sequenceCounter.incrementAndGet().toByte()
      val movePkt =
          EntityMovePacket(
              entityId = charId,
              x = msg.x.toByte(),
              y = msg.y.toByte(),
              direction = msg.direction,
              seq = seq)
      // Send EntityMove to the moving player (movement confirmation) and broadcast to
      // others
      event.ctx.channel().writeAndFlush(movePkt)
      broadcastExcept(event.ctx.channel(), movePkt)
    }
  }

  fun onFaceDirection(event: PacketEvent<FaceDirectionPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return

    val charId = session.characterId ?: return
    val msg = event.packet
    session.facingDirection = msg.direction

    val seq = sequenceCounter.incrementAndGet().toByte()
    val stored = CharacterStore.getCharacter(charId) ?: return
    val movePkt =
        EntityMovePacket(
            entityId = charId,
            x = stored.info.positionX.toByte(),
            y = stored.info.positionY.toByte(),
            direction = msg.direction,
            seq = seq)
    event.ctx.channel().writeAndFlush(movePkt)
    broadcastExcept(event.ctx.channel(), movePkt)
  }

  fun onEntityInteract(event: PacketEvent<EntityInteractPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return
    val charId = session.characterId ?: return

    val npcEntityId = event.packet.entityId
    val stored = CharacterStore.getCharacter(charId) ?: return
    val currentMap =
        MapManager.getMap(
            stored.info.positionRegionId, stored.info.positionBankId, stored.info.positionMapId)
    if (currentMap == null) return

    // Find which NPC this entityId belongs to
    val bankId = stored.info.positionBankId.toInt()
    val mapId = stored.info.positionMapId.toInt()
    for (npc in currentMap.npcs) {
      val key = "$bankId:$mapId:${npc.entityIdx}"
      val expectedId = npcEntityIds[key]
      if (expectedId == npcEntityId) {
        log.info {
          "Entity interaction: NPC entityIdx=${npc.entityIdx} entityId=$npcEntityId script=${npc.script}"
        }

        if (npc.script != "0x0") {
          val params = scriptParams(npc.script)
          if (params != null) {
            log.info {
              "NPC entityIdx=${npc.entityIdx} script=${npc.script} — starting dialog (${params.size} pages)"
            }
            session.inDialog = true
            session.dialogNpcEntityId = npcEntityId
            session.dialogPages = params
            session.dialogPageIndex = 0
            val seqId = session.dialogSeqId++
            val page = params[0]
            // Exact sequence from real server GBA capture (packets.db rows 65-67):
            // 1. DialogState(01) = open dialog window
            event.ctx.channel().write(DialogStatePacket(true))
            // 2. 0x07 entity interaction confirmation [entityId:8][0xFF:1]
            val confirmBuf = io.netty.buffer.Unpooled.buffer(9)
            confirmBuf.writeLongLE(npcEntityId)
            confirmBuf.writeByte(0xFF)
            val confirmBytes = ByteArray(9)
            confirmBuf.readBytes(confirmBytes)
            confirmBuf.release()
            sendRaw(event.ctx, 0x07u, confirmBytes)
            // 3. Interactive (0x21) with script params — sets script context for client
            sendInteractive(
                event.ctx, seqId, npcEntityId, page.type, page.unk1, page.unk2, page.unk3)
            event.ctx.channel().flush()
          } else {
            log.info { "NPC entityIdx=${npc.entityIdx} script=${npc.script} — no known params" }
          }
        } else {
          log.info { "NPC entityIdx=${npc.entityIdx} has no script (0x0) — no interaction" }
        }
        return
      }
    }

    // No NPC found — check for bg_event at tile player is facing
    val facingX =
        when (session.facingDirection) {
          Direction.RIGHT -> stored.info.positionX.toInt() + 1
          Direction.LEFT -> stored.info.positionX.toInt() - 1
          else -> stored.info.positionX.toInt()
        }
    val facingY =
        when (session.facingDirection) {
          Direction.UP -> stored.info.positionY.toInt() - 1
          Direction.DOWN -> stored.info.positionY.toInt() + 1
          else -> stored.info.positionY.toInt()
        }
    val facingDirOk: (String) -> Boolean = { dir ->
      dir == "BG_EVENT_PLAYER_FACING_ANY" ||
          (dir == "BG_EVENT_PLAYER_FACING_NORTH" && session.facingDirection == Direction.UP) ||
          (dir == "BG_EVENT_PLAYER_FACING_SOUTH" && session.facingDirection == Direction.DOWN) ||
          (dir == "BG_EVENT_PLAYER_FACING_WEST" && session.facingDirection == Direction.LEFT) ||
          (dir == "BG_EVENT_PLAYER_FACING_EAST" && session.facingDirection == Direction.RIGHT)
    }
    val bgEvent =
        currentMap.bgEvents.find { it.x == facingX && it.y == facingY && facingDirOk(it.facingDir) }
    if (bgEvent != null) {
      val params = scriptParams(bgEvent.script)
      if (params != null) {
        log.info {
          "BG event script=${bgEvent.script} at ($facingX,$facingY) — starting dialog (${params.size} pages)"
        }
        session.inDialog = true
        session.dialogNpcEntityId = npcEntityId
        session.dialogPages = params
        session.dialogPageIndex = 0
        val seqId = session.dialogSeqId++
        val page = params[0]
        event.ctx.channel().write(DialogStatePacket(true))
        val confirmBuf = io.netty.buffer.Unpooled.buffer(9)
        confirmBuf.writeLongLE(npcEntityId)
        confirmBuf.writeByte(0xFF)
        val confirmBytes = ByteArray(9)
        confirmBuf.readBytes(confirmBytes)
        confirmBuf.release()
        sendRaw(event.ctx, 0x07u, confirmBytes)
        sendInteractive(event.ctx, seqId, npcEntityId, page.type, page.unk1, page.unk2, page.unk3)
        event.ctx.channel().flush()
      } else {
        log.info {
          "BG event interaction: script=${bgEvent.script} at ($facingX,$facingY) — no known params"
        }
      }
      return
    }

    log.info { "Entity interaction for entity $npcEntityId — not found on current map" }
  }

  fun onInteractive(event: PacketEvent<InteractiveResponsePacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return
    val charId = session.characterId ?: return

    if (session.inDialog) {
      val respId = event.packet.id
      log.info {
        "Interactive response: id=$respId for char $charId (page ${session.dialogPageIndex + 1}/${session.dialogPages.size})"
      }
      val nextPageIndex = session.dialogPageIndex + 1
      if (nextPageIndex < session.dialogPages.size) {
        // More pages — send next Interactive
        val seqId = session.dialogSeqId++
        val page = session.dialogPages[nextPageIndex]
        session.dialogPageIndex = nextPageIndex
        sendInteractive(
            event.ctx, seqId, session.dialogNpcEntityId, page.type, page.unk1, page.unk2, page.unk3)
        event.ctx.channel().flush()
      } else {
        // No more pages — close dialog
        event.ctx.channel().writeAndFlush(DialogStatePacket(false))
        session.inDialog = false
        session.dialogNpcEntityId = 0
        session.dialogPages = emptyList()
        session.dialogPageIndex = 0
      }
    }
  }

  fun onDialogChoice(event: PacketEvent<DialogChoicePacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return
    log.info { "Dialog choice received: unk1=${event.packet.unk1}, unk2=${event.packet.unk2}" }
    // Close dialog after choice
    if (session.inDialog) {
      event.ctx.channel().writeAndFlush(DialogStatePacket(false))
      session.inDialog = false
      session.dialogNpcEntityId = 0
    }
  }

  fun onKeepAlive(event: PacketEvent<KeepAlivePacket>) {
    event.respond(event.packet)
  }

  fun onChatMessage(event: PacketEvent<ChatMessagePacket>) {
    val msg = event.packet
    val sender = SessionManager.getSessionByChannel(event.ctx.channel())

    if (sender == null) {
      log.warn { "Chat message from unknown sender" }
      return
    }

    val charId = sender.characterId ?: return
    val stored = CharacterStore.getCharacter(charId)
    val senderName = stored?.info?.name ?: "Unknown"

    log.info { "Chat [${msg.type}] $senderName: ${msg.message}" }

    broadcastMessage(
        ChatMessagePacket(
            type = msg.type, language = msg.language, message = msg.message, sender = senderName))
  }

  private fun broadcastMessage(packet: Any) {
    for (characterId in SessionManager.getOnlineCharacterIds()) {
      val s = SessionManager.getSessionByCharacterId(characterId) ?: continue
      if (s.channel.isActive) {
        s.channel.writeAndFlush(packet)
      }
    }
  }

  private fun broadcastExcept(channel: io.netty.channel.Channel, packet: Any) {
    for (characterId in SessionManager.getOnlineCharacterIds()) {
      val s = SessionManager.getSessionByCharacterId(characterId) ?: continue
      if (s.channel.isActive && s.channel != channel) {
        s.channel.writeAndFlush(packet)
      }
    }
  }
}
