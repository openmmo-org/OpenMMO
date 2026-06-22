package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.AddFriendPacket
import de.fiereu.openmmo.net.game.packets.BlockPlayerPacket
import de.fiereu.openmmo.net.game.packets.CancelSocialInteractionPacket
import de.fiereu.openmmo.net.game.packets.FriendListEntry
import de.fiereu.openmmo.net.game.packets.FriendListPacket
import de.fiereu.openmmo.net.game.packets.FriendProfileRequestPacket
import de.fiereu.openmmo.net.game.packets.PartyMemberJoinPacket
import de.fiereu.openmmo.net.game.packets.PartyMemberLeavePacket
import de.fiereu.openmmo.net.game.packets.RemoveFriendPacket
import de.fiereu.openmmo.net.game.packets.RequestSocialProfilePacket
import de.fiereu.openmmo.net.game.packets.UnblockPlayerPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.SocialStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class SocialService
@Inject
constructor(
    private val socialStore: SocialStore,
    private val sessionRegistry: SessionRegistry,
    private val characterStore: CharacterStore,
) {

  fun sendFriendList(ctx: SessionContext) {
    val state = ctx.attributes[PLAYER_STATE] ?: return
    ctx.send(buildFriendList(state.userId))
  }

  fun onAddFriend(event: PacketEvent<AddFriendPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val name = event.packet.username
    socialStore.addFriend(state.userId, name)
    log.info { "AddFriend user=${state.userId} name='$name'" }
    ctx.send(buildFriendList(state.userId))
  }

  fun onRemoveFriend(event: PacketEvent<RemoveFriendPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val name = event.packet.username
    val removed = socialStore.removeFriend(state.userId, name)
    log.info { "RemoveFriend user=${state.userId} name='$name' removed=$removed" }
    ctx.send(buildFriendList(state.userId))
  }

  fun onBlockPlayer(event: PacketEvent<BlockPlayerPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val packet = event.packet
    socialStore.block(state.userId, packet.username)
    log.info {
      "BlockPlayer user=${state.userId} name='${packet.username}' reason='${packet.reason}'"
    }
    ctx.send(
        PartyMemberJoinPacket(
            player = syntheticId(packet.username),
            name = packet.username,
            secondaryName = packet.reason,
            value = 0,
        ))
  }

  fun onUnblockPlayer(event: PacketEvent<UnblockPlayerPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val name = event.packet.username
    val removed = socialStore.unblock(state.userId, name)
    log.info { "UnblockPlayer user=${state.userId} name='$name' removed=$removed" }
    ctx.send(PartyMemberLeavePacket(memberId = syntheticId(name)))
  }

  fun onFriendProfileRequest(event: PacketEvent<FriendProfileRequestPacket>) {
    log.info { "FriendProfileRequest targetEntityId=${event.packet.targetEntityId}" }
  }

  fun onRequestSocialProfile(event: PacketEvent<RequestSocialProfilePacket>) {
    log.info { "RequestSocialProfile targetId=${event.packet.targetId}" }
  }

  fun onCancelSocialInteraction(event: PacketEvent<CancelSocialInteractionPacket>) {
    log.info { "CancelSocialInteraction from ${event.session.remoteAddress}" }
  }

  private fun buildFriendList(userId: Int): FriendListPacket {
    val entries =
        socialStore.getFriends(userId).map { name ->
          FriendListEntry(
              player = syntheticId(name),
              friendsSince = 0,
              online = isOnlineByName(name),
              name = name,
              unk = 0,
              lastSeen = 0,
              appearance = List(5) { 0 },
          )
        }
    return FriendListPacket(mode = 0, entries = entries)
  }

  private fun isOnlineByName(name: String): Boolean =
      sessionRegistry.onlineCharacterIds().any { id ->
        characterStore.getCharacter(id)?.info?.name == name
      }

  private fun syntheticId(name: String): Long = (name.hashCode().toLong() shl 16) or 0x9000L
}
