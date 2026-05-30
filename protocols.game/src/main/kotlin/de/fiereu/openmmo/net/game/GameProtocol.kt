package de.fiereu.openmmo.net.game

import de.fiereu.network.Protocol
import de.fiereu.network.bidi
import de.fiereu.network.c2s
import de.fiereu.network.s2c
import de.fiereu.openmmo.net.game.packets.CharactersListPacket
import de.fiereu.openmmo.net.game.packets.CharactersListPacketCodec
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.ChatMessagePacketCodec
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacket
import de.fiereu.openmmo.net.game.packets.CreateCharacterPacketCodec
import de.fiereu.openmmo.net.game.packets.DialogChoicePacket
import de.fiereu.openmmo.net.game.packets.DialogChoicePacketCodec
import de.fiereu.openmmo.net.game.packets.DialogDataPacket
import de.fiereu.openmmo.net.game.packets.DialogDataPacketCodec
import de.fiereu.openmmo.net.game.packets.DialogOptionPacket
import de.fiereu.openmmo.net.game.packets.DialogOptionPacketCodec
import de.fiereu.openmmo.net.game.packets.DialogStatePacket
import de.fiereu.openmmo.net.game.packets.DialogStatePacketCodec
import de.fiereu.openmmo.net.game.packets.EntityInteractPacket
import de.fiereu.openmmo.net.game.packets.EntityInteractPacketCodec
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.EntityLeavePacketCodec
import de.fiereu.openmmo.net.game.packets.EntityMovePacket
import de.fiereu.openmmo.net.game.packets.EntityMovePacketCodec
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacketCodec
import de.fiereu.openmmo.net.game.packets.InteractivePacket
import de.fiereu.openmmo.net.game.packets.InteractivePacketCodec
import de.fiereu.openmmo.net.game.packets.InteractiveResponsePacket
import de.fiereu.openmmo.net.game.packets.InteractiveResponsePacketCodec
import de.fiereu.openmmo.net.game.packets.JoinPacket
import de.fiereu.openmmo.net.game.packets.JoinPacketCodec
import de.fiereu.openmmo.net.game.packets.JoinResponsePacket
import de.fiereu.openmmo.net.game.packets.JoinResponsePacketCodec
import de.fiereu.openmmo.net.game.packets.KeepAlivePacket
import de.fiereu.openmmo.net.game.packets.KeepAlivePacketCodec
import de.fiereu.openmmo.net.game.packets.LoadEntityPacket
import de.fiereu.openmmo.net.game.packets.LoadEntityPacketCodec
import de.fiereu.openmmo.net.game.packets.LoadMapPacket
import de.fiereu.openmmo.net.game.packets.LoadMapPacketCodec
import de.fiereu.openmmo.net.game.packets.MapTransitionAckPacket
import de.fiereu.openmmo.net.game.packets.MapTransitionAckPacketCodec
import de.fiereu.openmmo.net.game.packets.MapTransitionPacket
import de.fiereu.openmmo.net.game.packets.MapTransitionPacketCodec
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.net.game.packets.MovementPacketCodec
import de.fiereu.openmmo.net.game.packets.NpcAnimationPacket
import de.fiereu.openmmo.net.game.packets.NpcAnimationPacketCodec
import de.fiereu.openmmo.net.game.packets.NpcSpawnPacket
import de.fiereu.openmmo.net.game.packets.NpcSpawnPacketCodec
import de.fiereu.openmmo.net.game.packets.NpcUpdatePacket
import de.fiereu.openmmo.net.game.packets.NpcUpdatePacketCodec
import de.fiereu.openmmo.net.game.packets.NullPacket
import de.fiereu.openmmo.net.game.packets.NullPacketCodec
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacketCodec
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacketCodec
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacket
import de.fiereu.openmmo.net.game.packets.RequestCharactersPacketCodec
import de.fiereu.openmmo.net.game.packets.RequestPlayerPacket
import de.fiereu.openmmo.net.game.packets.RequestPlayerPacketCodec
import de.fiereu.openmmo.net.game.packets.SelectCharacterPacket
import de.fiereu.openmmo.net.game.packets.SelectCharacterPacketCodec
import de.fiereu.openmmo.net.game.packets.SelectedCharacterPacket
import de.fiereu.openmmo.net.game.packets.SelectedCharacterPacketCodec
import de.fiereu.openmmo.net.game.packets.TokenPayloadPacket
import de.fiereu.openmmo.net.game.packets.TokenPayloadPacketCodec

object GameProtocol : Protocol() {
  override val compressed: Boolean = true

  init {
    c2s<JoinPacket>(0x01u, JoinPacketCodec)
    s2c<JoinResponsePacket>(0x01u, JoinResponsePacketCodec)

    c2s<RequestCharactersPacket>(0x02u, RequestCharactersPacketCodec)
    s2c<CharactersListPacket>(0x02u, CharactersListPacketCodec)

    c2s<CreateCharacterPacket>(0x03u, CreateCharacterPacketCodec)

    c2s<SelectCharacterPacket>(0x04u, SelectCharacterPacketCodec)
    s2c<SelectedCharacterPacket>(0x04u, SelectedCharacterPacketCodec)

    c2s<RequestPlayerPacket>(0x05u, RequestPlayerPacketCodec)
    s2c<LoadEntityPacket>(0x05u, LoadEntityPacketCodec)

    c2s<MovementPacket>(0x06u, MovementPacketCodec)

    c2s<FaceDirectionPacket>(0x07u, FaceDirectionPacketCodec)

    s2c<EntityLeavePacket>(0x08u, EntityLeavePacketCodec)

    bidi<ChatMessagePacket>(0x09u, ChatMessagePacketCodec)

    bidi<DialogDataPacket>(0x0Du, DialogDataPacketCodec)
    bidi<DialogStatePacket>(0x0Eu, DialogStatePacketCodec)

    bidi<LoadMapPacket>(0x10u, LoadMapPacketCodec)

    s2c<NpcUpdatePacket>(0x11u, NpcUpdatePacketCodec)

    bidi<NpcSpawnPacket>(0x12u, NpcSpawnPacketCodec)

    bidi<PokemonContainerPacket>(0x13u, PokemonContainerPacketCodec)

    bidi<MapTransitionPacket>(0x1Bu, MapTransitionPacketCodec)

    c2s<NullPacket>(0x20u, NullPacketCodec)
    s2c<TokenPayloadPacket>(0x20u, TokenPayloadPacketCodec)

    s2c<InteractivePacket>(0x21u, InteractivePacketCodec)
    c2s<InteractiveResponsePacket>(0x21u, InteractiveResponsePacketCodec)

    bidi<EntityInteractPacket>(0x22u, EntityInteractPacketCodec)

    bidi<DialogChoicePacket>(0x25u, DialogChoicePacketCodec)
    bidi<DialogOptionPacket>(0x26u, DialogOptionPacketCodec)

    bidi<NpcAnimationPacket>(0xB2u, NpcAnimationPacketCodec)
    bidi<RenderScreenPacket>(0xB4u, RenderScreenPacketCodec)
    bidi<MapTransitionAckPacket>(0xB9u, MapTransitionAckPacketCodec)
    bidi<KeepAlivePacket>(0xC2u, KeepAlivePacketCodec)

    s2c<EntityMovePacket>(0xEAu, EntityMovePacketCodec)
  }
}
