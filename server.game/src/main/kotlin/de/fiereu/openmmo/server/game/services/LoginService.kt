package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.EntityStatus
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.codecs.SkinSet
import de.fiereu.openmmo.net.game.packets.CharacterEntry
import de.fiereu.openmmo.net.game.packets.CharactersListPacket
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.JoinPacket
import de.fiereu.openmmo.net.game.packets.JoinResponsePacket
import de.fiereu.openmmo.net.game.packets.LoadEntityPacket
import de.fiereu.openmmo.net.game.packets.LocalPlayerStatePacket
import de.fiereu.openmmo.net.game.packets.MenuPagePayloadPacket
import de.fiereu.openmmo.net.game.packets.NewAuthData
import de.fiereu.openmmo.net.game.packets.ObjectiveProgressBulkPacket
import de.fiereu.openmmo.net.game.packets.PokedexSpeciesResetPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.net.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.net.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.net.game.packets.SelectedCharacterPacket
import de.fiereu.openmmo.net.game.packets.ViewScalePacket
import de.fiereu.openmmo.net.game.packets.WorldFlagTableResetPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleRatingBulkPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleStateBytePacket
import de.fiereu.openmmo.net.game.packets.battle.ItemStack
import de.fiereu.openmmo.net.game.packets.battle.itemStacksPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private fun hexToBytes(hex: String): ByteArray =
    ByteArray(hex.length / 2) { hex.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

// The world-flag table the client loads on join. Groups one to three are zlib streams that each
// decompress to a 67-byte flag block, the fourth is empty. The client reads real flag state while
// building the follower and party, so empty groups leave a lookup null and crash it.
private val WORLD_FLAG_GROUPS =
    listOf(
            "789c637060a00434a8b0320000133900ea",
            "789c637060a00434303032000012c500c2",
            "789c637060a00434303032000012c500c2",
            "",
        )
        .map(::hexToBytes)

private const val POKE_BALL_ITEM_ID: Short = 5004
private const val POKE_BALL_OBJECT_ID = 0x0000000000015000L
private const val POKE_BALL_QUANTITY: Short = 10
private val STARTING_BAG =
    listOf(ItemStack(POKE_BALL_OBJECT_ID, POKE_BALL_ITEM_ID, POKE_BALL_QUANTITY))

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
    val name = event.packet.name
    log.info { "Creating character '$name' for userId=${state.userId}" }
    characterStore.createCharacter(state.userId, name)
    ctx.send(buildCharacterList(state.userId))
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

  private fun buildCharacterList(userId: Int): CharactersListPacket {
    val characters = characterStore.getCharactersByUser(userId)
    val entries =
        characters.map { stored ->
          CharacterEntry(
              characterInfo = stored.info,
              skinSet = SkinSet(),
              guildId = null,
              pokemon = stored.pokemon.take(1),
          )
        }
    return CharactersListPacket(entries)
  }

  fun onCharacterSelected(event: PacketEvent<SelectCharacterPacket>) {
    val ctx = event.session
    val charId = event.packet.characterId
    val stored = characterStore.getCharacter(charId)
    if (stored == null) {
      log.warn { "Character $charId not found" }
      return
    }
    val state = ctx.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "No session for channel" }
      return
    }

    state.characterId = charId
    sessionRegistry.bindCharacter(ctx, charId)
    log.info { "Player selected character '${stored.info.name}' (id=$charId)" }

    // Initialise the client world-flag table before any monster or follower is built. Without it
    // the table stays null and the client crashes constructing a party monster that reads a flag.
    ctx.send(WorldFlagTableResetPacket(WORLD_FLAG_GROUPS))
    ctx.send(buildLocalPlayerState(stored.info, stored.pokemon.map { it.dexId.toShort() }))

    val info = stored.info
    val now = LocalDateTime.now()
    val updatedInfo = info.copy(lastLogin = now)
    characterStore.updateCharacter(updatedInfo)

    val containers =
        mapOf(
            PokemonContainer.PARTY to stored.pokemon,
            PokemonContainer.PC to stored.pcStorage,
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

    // The bag item stacks (opcode 0x40). The real server sends these during join, interleaved with
    // the container packets, so the client has the items before entering the world.
    ctx.send(itemStacksPacket(STARTING_BAG))

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

  // The full local-player state snapshot the client loads on login. Missing it leaves player state
  // uninitialised and the client crashes reading it (for example when opening the battle bag).
  private fun buildLocalPlayerState(
      info: CharacterInfo,
      partyDex: List<Short>
  ): LocalPlayerStatePacket =
      LocalPlayerStatePacket(
          region = info.positionRegionId,
          mapId = info.positionMapId.toShort(),
          moveSpeed = 0.05f,
          x = info.positionX,
          y = info.positionY,
          z = 0,
          money = info.money,
          gender = 0,
          skinTone = 0,
          hairColor = 0,
          playtime = 0.0,
          flags = 0,
          partyDex = partyDex,
          partyForms = partyDex.map { 0.toByte() },
          pokedexSeen = emptyList(),
          pokedexCaught = emptyList(),
          badges = emptyList(),
          variables = emptyList(),
      )

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
    val loadEntity = mapLoadService.createLoadEntity(info, facing)
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

    val currentParty = characterStore.getCharacter(charId)?.pokemon ?: emptyList()
    val hasFollower = currentParty.isNotEmpty()
    val followerDexId = (currentParty.firstOrNull()?.dexId ?: 0).toShort()

    val others = sessionRegistry.getOthersInMap(charId, regionId, bankId, mapId)
    for (other in others) {
      val otherState = other.attributes[PLAYER_STATE] ?: continue
      val otherCharId = otherState.characterId ?: continue
      val otherStored = characterStore.getCharacter(otherCharId) ?: continue
      val otherParty: List<Pokemon> = otherStored.pokemon
      ctx.send(
          LoadEntityPacket(
              entityId = otherCharId,
              skin = SkinSet(),
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
              followerDexId = (otherParty.firstOrNull()?.dexId ?: 0).toShort(),
          ))
    }

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
