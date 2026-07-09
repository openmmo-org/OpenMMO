package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.EntityStatus
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.codecs.SkinSet
import de.fiereu.openmmo.net.game.codecs.opaqueSkinSet
import de.fiereu.openmmo.net.game.packets.CharacterEntry
import de.fiereu.openmmo.net.game.packets.CharactersListPacket
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.JoinPacket
import de.fiereu.openmmo.net.game.packets.JoinResponsePacket
import de.fiereu.openmmo.net.game.packets.LoadEntityPacket
import de.fiereu.openmmo.net.game.packets.LocalPlayerStatePacket
import de.fiereu.openmmo.net.game.packets.NewAuthData
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.net.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.net.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.net.game.packets.SelectedCharacterPacket
import de.fiereu.openmmo.server.game.domain.OwnedPokemon
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

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
) {

  fun onJoinGame(event: PacketEvent<JoinPacket>) {
    val ctx = event.session
    val join = event.packet
    log.info { "Player joined the game." }

    val authData = join.authData
    var userId = 0
    if (authData is NewAuthData) {
      userId = authData.userId
      log.info { "User $userId joined with session key (${authData.sessionKey.size} bytes)" }
    }

    if (userId > 0) {
      ctx.attributes[PLAYER_STATE] = PlayerState(userId = userId)
      sessionRegistry.register(ctx)
      log.info { "Session created for user $userId" }
    }

    ctx.send(JoinResponsePacket.acceptNow(playtime = 1337, rewardPoints = 420, balance = 187))
  }

  fun onCreateCharacter(event: PacketEvent<CreateCharacterPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "Create character from unknown session" }
      return
    }
    val packet = event.packet
    val name = packet.name
    log.info { "Creating character '$name' for userId=${state.userId}" }
    val stored = characterStore.createCharacter(state.userId, name, packet.gender, packet.cosmetics)
    ctx.send(buildCharacterList(state.userId))
    // The real client's create-character flow doesn't return to character-select on its own --
    // it waits to be dropped straight into the world, same as picking an existing character does.
    // Sending only the refreshed list here left the client parked on the New Character screen
    // forever (validated 2026-07-09: the S2C list update went out, no exception, client never
    // sent a follow-up select).
    selectAndSpawnCharacter(ctx, state, stored.info.id)
  }

  fun onCharacterRequest(event: PacketEvent<RequestCharactersPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    if (state != null) {
      ctx.send(buildCharacterList(state.userId))
    } else {
      log.warn { "Character request from unauthenticated session" }
      ctx.send(CharactersListPacket(emptyList()))
    }
  }

  private fun skinSetFromCosmetics(info: CharacterInfo): SkinSet =
      if (info.cosmetics.isNotEmpty()) opaqueSkinSet(info.cosmetics) else SkinSet()

  private fun buildCharacterList(userId: Int): CharactersListPacket {
    val characters = characterStore.getCharactersByUser(userId)
    val entries =
        characters.map { stored ->
          CharacterEntry(
              characterInfo = stored.info,
              skinSet = skinSetFromCosmetics(stored.info),
              guildId = null,
              pokemon = PartyPokemonMapper.toWireParty(stored.pokemon).take(1),
          )
        }
    return CharactersListPacket(entries)
  }

  fun onCharacterSelected(event: PacketEvent<SelectCharacterPacket>) {
    val ctx = event.session
    val charId = event.packet.characterId
    if (characterStore.getCharacter(charId) == null) {
      log.warn { "Character $charId not found" }
      return
    }
    val state = ctx.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "No session for channel" }
      return
    }
    selectAndSpawnCharacter(ctx, state, charId)
  }

  /**
   * Binds the session to [charId] and drives it into the world: party/PC containers, player state,
   * the selected-character ack, a welcome chat line, then the map load. Shared by character SELECT
   * and by CREATE, which auto-selects the character it just made (the real client's create flow
   * expects to land in-world, not back at character-select).
   */
  private fun selectAndSpawnCharacter(ctx: SessionContext, state: PlayerState, charId: Long) {
    val stored = characterStore.getCharacter(charId) ?: return

    state.characterId = charId
    sessionRegistry.bindCharacter(ctx, charId)
    log.info { "Player selected character '${stored.info.name}' (id=$charId)" }

    val info = stored.info
    val now = LocalDateTime.now()
    val updatedInfo = info.copy(lastLogin = now)
    characterStore.updateCharacter(updatedInfo)

    val containers =
        mapOf(
            PokemonContainer.PARTY to PartyPokemonMapper.toWireParty(stored.pokemon),
            PokemonContainer.PC to
                PartyPokemonMapper.toWireContainer(stored.pcStorage, PokemonContainer.PC),
            PokemonContainer.BATTLE_BOX_1 to emptyList(),
            PokemonContainer.BATTLE_BOX_2 to emptyList(),
            PokemonContainer.DAYCARE to emptyList(),
        )
    for ((container, pokemon) in containers) {
      ctx.send(
          PokemonContainerPacket(
              container = container,
              hasChange = true,
              delete = false,
              pokemon = pokemon,
          ))
    }

    listOf(PokemonContainer.UNKNOWN_13, PokemonContainer.UNKNOWN_14).forEach { container ->
      ctx.send(
          PokemonContainerPacket(
              container = container,
              hasChange = true,
              delete = false,
              pokemon = emptyList(),
          ))
    }

    ctx.send(buildLocalPlayerState(info, stored.pokemon))
    ctx.send(SelectedCharacterPacket(info))
    ctx.send(
        ChatMessagePacket(
            ChatType.GAME_NOTIFICATIONS,
            Language.EN,
            "Welcome to OpenMMO!",
            "",
        ))

    preloadMapAndJoin(ctx, state, info)
  }

  private fun buildLocalPlayerState(
      info: CharacterInfo,
      party: List<OwnedPokemon>,
  ): LocalPlayerStatePacket {
    // Validated login capture has empty partyDex/partyForms here even when 0x13 carries one party
    // member. Keep 0xF3 focused on player state; 0x13 is the party source of truth.
    val caught = party.map { it.speciesId.toShort() }.distinct()
    return LocalPlayerStatePacket(
        region = info.positionRegionId,
        mapId = info.positionMapId.toShort(),
        moveSpeed = 0.05f,
        x = info.positionX,
        y = info.positionY,
        z = 0,
        money = info.money,
        gender = info.rivalSex,
        skinTone = 30000.toShort(),
        hairColor = 30000.toShort(),
        playtime = 0.0,
        flags = 27,
        partyDex = emptyList(),
        partyForms = emptyList(),
        pokedexSeen = caught,
        pokedexCaught = caught,
        badges = emptyList(),
        variables = emptyList(),
    )
  }

  private fun preloadMapAndJoin(
      ctx: SessionContext,
      state: PlayerState,
      info: CharacterInfo,
  ) {
    val map = mapManager.getMap(info.positionRegionId, info.positionBankId, info.positionMapId)
    if (map != null) {
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
    val stored = characterStore.getCharacter(charId)
    if (stored == null) {
      log.warn { "RequestPlayer for unknown character $charId" }
      return
    }
    val info = stored.info

    log.info { "Sending LoadEntity for character '${info.name}'" }
    val facing = state.facingDirection
    val currentParty = characterStore.getParty(charId)
    val hasFollower = currentParty.isNotEmpty()
    val followerDexId = (currentParty.firstOrNull()?.speciesId ?: 0).toShort()
    val loadEntity =
        mapLoadService.createLoadEntity(
            info,
            facing,
            hasFollower = hasFollower,
            followerDexId = followerDexId,
        )
    ctx.send(loadEntity)

    npcService.spawnNpcsForMap(ctx, info.positionBankId.toInt(), info.positionMapId.toInt())

    val bankId = info.positionBankId.toInt()
    val mapId = info.positionMapId.toInt()
    val regionId = info.positionRegionId.toInt()
    state.regionId = regionId
    state.bankId = bankId
    state.mapId = mapId
    state.x = info.positionX
    state.y = info.positionY

    ctx.send(buildLocalPlayerState(info, currentParty))

    val others = sessionRegistry.getOthersInMap(charId, regionId, bankId, mapId)
    for (other in others) {
      val otherState = other.attributes[PLAYER_STATE] ?: continue
      val otherCharId = otherState.characterId ?: continue
      val otherStored = characterStore.getCharacter(otherCharId) ?: continue
      val otherParty = otherStored.pokemon
      ctx.send(
          LoadEntityPacket(
              entityId = otherCharId,
              skin = skinSetFromCosmetics(otherStored.info),
              name = otherStored.info.name,
              regionId = otherState.regionId,
              bankId = otherState.bankId,
              mapId = otherState.mapId,
              x = otherState.x.toInt(),
              y = otherState.y.toInt(),
              z = 0,
              facing = otherState.facingDirection,
              status = EntityStatus.NONE,
              hasFollower = otherParty.isNotEmpty(),
              followerDexId = (otherParty.firstOrNull()?.speciesId ?: 0).toShort(),
          ))
    }

    val selfEntity =
        LoadEntityPacket(
            entityId = charId,
            skin = skinSetFromCosmetics(info),
            name = info.name,
            regionId = regionId,
            bankId = bankId,
            mapId = mapId,
            x = info.positionX.toInt(),
            y = info.positionY.toInt(),
            z = 0,
            facing = facing,
            status = EntityStatus.NONE,
            hasFollower = hasFollower,
            followerDexId = followerDexId,
        )
    for (other in others) {
      other.send(selfEntity)
    }

    ctx.send(RenderScreenPacket(true))

    socialService.sendFriendList(ctx)

    log.info { "Player $charId spawned in bank=$bankId map=$mapId; ${others.size} others present" }
  }
}
