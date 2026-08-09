package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.auth.SessionTokenVerifier
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.codecs.SkinSet
import de.fiereu.openmmo.net.game.packets.CharacterEntry
import de.fiereu.openmmo.net.game.packets.CharactersListPacket
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.DeleteCharacterPacket
import de.fiereu.openmmo.net.game.packets.DeleteCharacterResultPacket
import de.fiereu.openmmo.net.game.packets.JoinPacket
import de.fiereu.openmmo.net.game.packets.JoinResponsePacket
import de.fiereu.openmmo.net.game.packets.MenuPagePayloadPacket
import de.fiereu.openmmo.net.game.packets.NewAuthData
import de.fiereu.openmmo.net.game.packets.ObjectiveProgressBulkPacket
import de.fiereu.openmmo.net.game.packets.PokedexSpeciesResetPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.net.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.net.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.net.game.packets.SelectedCharacterPacket
import de.fiereu.openmmo.net.game.packets.ViewScalePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleRatingBulkPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleStateBytePacket
import de.fiereu.openmmo.server.game.session.PENDING_MAP_LOAD
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val DELETE_SUCCESS = 0
private const val DELETE_REJECTED = 1

@Singleton
class LoginService
@Inject
constructor(
    private val mapLoadService: MapLoadService,
    private val npcService: NpcService,
    private val multiplayerService: MultiplayerService,
    private val socialService: SocialService,
    private val sessionRegistry: SessionRegistry,
    private val mapManager: MapManager,
    private val characterStore: CharacterStore,
    private val presenceService: PresenceService,
    private val mapScriptService: MapScriptService,
    private val tokenVerifier: SessionTokenVerifier,
    private val worldStateService: WorldStateService,
) {

  fun onJoinGame(event: PacketEvent<JoinPacket>) {
    val ctx = event.session
    val authData = event.packet.authData

    if (authData !is NewAuthData) {
      log.warn { "Rejected join with unsupported auth data ${authData::class.simpleName}" }
      rejectJoin(ctx)
      return
    }

    val token = tokenVerifier.verify(authData.sessionKey)
    if (token == null) {
      log.warn {
        "Rejected join for claimed userId=${authData.userId}, " +
            "session token invalid or expired (${authData.sessionKey.size} bytes)"
      }
      rejectJoin(ctx)
      return
    }

    // The claim in the packet is the client's, the token is the login server's. Compare before
    // narrowing, so a value that does not fit an Int cannot match by truncation.
    if (token.userId != authData.userId.toLong() || token.userId <= 0) {
      log.warn { "Join claimed userId=${authData.userId} but its token says ${token.userId}" }
      rejectJoin(ctx)
      return
    }
    val userId = token.userId.toInt()

    ctx.attributes[PLAYER_STATE] = PlayerState(userId = userId)
    sessionRegistry.register(ctx)
    log.info { "Session created for user $userId" }

    ctx.send(JoinResponsePacket.acceptNow(playtime = 1337, rewardPoints = 420, balance = 187))
  }

  // Closing is what keeps a refused peer from going on to send packets the handlers would
  // otherwise answer without a PlayerState. Close only once the refusal itself has been written.
  private fun rejectJoin(ctx: SessionContext) {
    ctx.send(JoinResponsePacket.reject()).addListener { ctx.close { "join rejected" } }
  }

  suspend fun onCreateCharacter(event: PacketEvent<CreateCharacterPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "Create character from unknown session" }
      return
    }
    val name = event.packet.name.trim()
    if (name.isEmpty() || name.length > 32) {
      log.warn { "Rejected character name '${event.packet.name}' for userId=${state.userId}" }
      ctx.send(buildCharacterList(state.userId))
      return
    }
    log.info { "Creating character '$name' for userId=${state.userId}" }
    val gender = CharacterGender.byWireValue(event.packet.gender)
    val startingRegion = Region.byWireValue(event.packet.startingRegion)
    if (gender == null || startingRegion == null) {
      log.warn {
        "Rejected character options gender=${event.packet.gender} " +
            "region=${event.packet.startingRegion} for userId=${state.userId}"
      }
      ctx.send(buildCharacterList(state.userId))
      return
    }
    val appearance = event.packet.appearance
    characterStore.createCharacter(
        state.userId,
        name,
        gender,
        startingRegion,
        skins = appearance.toMap(),
        skinRegionSelectionIndex = appearance.regionSelectionIndex,
    )
    ctx.send(buildCharacterList(state.userId))
  }

  suspend fun onCharacterRequest(event: PacketEvent<RequestCharactersPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    if (state != null) {
      ctx.send(buildCharacterList(state.userId))
    } else {
      log.warn { "Character request from unauthenticated session" }
      ctx.send(CharactersListPacket(emptyList()))
    }
  }

  suspend fun onDeleteCharacter(event: PacketEvent<DeleteCharacterPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    val userId = state?.userId
    val characterId = event.packet.characterId
    val deleted =
        userId != null &&
            state.characterId == null &&
            characterStore.deleteCharacter(userId, characterId)
    if (deleted) {
      log.info { "Deleted character $characterId for userId=$userId" }
    } else {
      log.warn { "Rejected character deletion id=$characterId from ${ctx.remoteAddress}" }
    }
    ctx.send(
        DeleteCharacterResultPacket(if (deleted) DELETE_SUCCESS else DELETE_REJECTED, characterId))
  }

  private suspend fun buildCharacterList(userId: Int): CharactersListPacket {
    val characters = characterStore.getCharactersByUser(userId)
    val entries =
        characters.map { stored ->
          CharacterEntry(
              characterInfo = stored.info,
              skinSet = SkinSet(stored.info.skinRegionSelectionIndex, stored.skins),
              guildId = null,
              // Character selection shows the complete party.
              pokemon = stored.pokemon,
          )
        }
    return CharactersListPacket(entries)
  }

  suspend fun onCharacterSelected(event: PacketEvent<SelectCharacterPacket>) {
    val ctx = event.session
    val charId = event.packet.characterId
    val state = ctx.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "No session for channel" }
      return
    }
    val stored = characterStore.getOrLoadCharacter(charId)
    if (stored == null) {
      log.warn { "Character $charId not found" }
      return
    }
    if (stored.info.userId != state.userId) {
      log.warn { "User ${state.userId} tried to select character $charId owned by another account" }
      return
    }

    state.characterId = charId
    sessionRegistry.bindCharacter(ctx, charId)
    log.info { "Player selected character '${stored.info.name}' (id=$charId)" }

    worldStateService.send(ctx, stored)

    val info = stored.info
    val now = LocalDateTime.now()
    val updatedInfo = info.copy(lastLogin = now)
    characterStore.updateCharacter(updatedInfo)

    ctx.send(SelectedCharacterPacket(info))
    sendJoinState(ctx)
    ctx.send(
        ChatMessagePacket(
            ChatType.GAME_NOTIFICATIONS,
            Language.EN,
            "Welcome to OpenMMO!",
            "",
        ))

    preloadMapAndJoin(ctx, state, info)
  }

  // Small state packets the real server sends in the join flow. The battle-state byte and menu
  // payloads initialise state the battle bag reads, so without them opening the bag crashes.
  private fun sendJoinState(ctx: SessionContext) {
    ctx.send(ViewScalePacket(viewScale = 5))
    ctx.send(BattleRatingBulkPacket(emptyList()))
    ctx.send(BattleStateBytePacket(state = 0x19))
    ctx.send(PokedexSpeciesResetPacket(emptyList()))
    ctx.send(ObjectiveProgressBulkPacket(emptyList()))
    ctx.send(MenuPagePayloadPacket(menuType = 0, page = null))
    ctx.send(MenuPagePayloadPacket(menuType = 1, page = null))
  }

  private fun preloadMapAndJoin(
      ctx: SessionContext,
      state: PlayerState,
      info: CharacterInfo,
  ) {
    val map = mapManager.getMap(info.positionRegionId, info.positionBankId, info.positionMapId)
    if (map != null) {
      log.info {
        "Placing '${info.name}' at ${info.positionRegionId}:${info.positionBankId}:" +
            "${info.positionMapId} (${info.positionX}, ${info.positionY})"
      }
      mapLoadService.resetClientCache(ctx, map)
      ctx.send(mapManager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true))
      mapLoadService.preloadConnectedMaps(ctx, map)
    } else {
      log.warn {
        "Map not found for position ${info.positionRegionId}:${info.positionBankId}:${info.positionMapId}"
      }
    }

    state.regionId = info.positionRegionId.toInt()
    state.bankId = info.positionBankId.toInt()
    state.mapId = info.positionMapId.toInt()
    state.x = info.positionX
    state.y = info.positionY
    state.facingDirection = info.positionFacing
    // Elevation is left alone on purpose. Spawning the player on the tile's own elevation makes
    // the client refuse every step, and a warp has already set the one value that works.

    multiplayerService.broadcastMessage(
        ChatMessagePacket(
            ChatType.GAME_NOTIFICATIONS,
            Language.EN,
            "Player ${info.name} joined the game.",
            "",
        ))
  }

  fun onRequestPlayer(event: PacketEvent<RequestPlayerPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "RequestPlayer from unknown session" }
      return
    }
    val charId = state.characterId
    if (charId == null) {
      log.warn { "RequestPlayer without active character" }
      return
    }
    // The client asks for its player once a map transition is done, so the warp ends here. The
    // waiter is only cleared at the end, so a throw in between leaves the deadline to rescue it.
    state.justWarped = false
    val pendingLoad = ctx.attributes[PENDING_MAP_LOAD]

    val stored = characterStore.getCharacter(charId)
    if (stored == null) {
      log.warn { "RequestPlayer for unknown character $charId" }
      return
    }
    val info = stored.info

    log.info { "Sending LoadEntity for character '${info.name}'" }
    val facing = state.facingDirection
    val loadEntity =
        mapLoadService.createLoadEntity(
            info, facing, state.elevation, party = stored.pokemon, skins = stored.skins)
    ctx.send(loadEntity)

    npcService.spawnNpcsForMap(
        ctx,
        info.positionBankId.toInt(),
        info.positionMapId.toInt(),
        info.positionRegionId.toInt(),
    )

    val bankId = info.positionBankId.toInt()
    val mapId = info.positionMapId.toInt()
    val regionId = info.positionRegionId.toInt()
    state.regionId = regionId
    state.bankId = bankId
    state.mapId = mapId
    state.x = info.positionX
    state.y = info.positionY

    // The client dropped its entities with the map cache, so always re-exchange snapshots.
    presenceService.enter(ctx)

    ctx.send(RenderScreenPacket(true))

    // An entry script may fade back out, so it runs after the fade this arrival owns.
    mapManager.getMap(info.positionRegionId, info.positionBankId, info.positionMapId)?.let { map ->
      mapScriptService.onMapEnter(ctx, state, map)
    }

    socialService.sendFriendList(ctx)
    if (ctx.attributes[PENDING_MAP_LOAD] === pendingLoad) ctx.attributes.remove(PENDING_MAP_LOAD)
    pendingLoad?.complete(Unit)

    log.info { "Player $charId spawned in bank=$bankId map=$mapId" }
  }
}
