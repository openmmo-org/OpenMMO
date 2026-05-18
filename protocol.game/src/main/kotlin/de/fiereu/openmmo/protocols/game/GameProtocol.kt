package de.fiereu.openmmo.protocols.game

import de.fiereu.openmmo.protocols.Protocol
import de.fiereu.openmmo.protocols.game.packets.CharacterListDeserializer
import de.fiereu.openmmo.protocols.game.packets.CharacterListSerializer
import de.fiereu.openmmo.protocols.game.packets.ChatMessageDeserialize
import de.fiereu.openmmo.protocols.game.packets.ChatMessageSerialize
import de.fiereu.openmmo.protocols.game.packets.CreateCharacterDeserializer
import de.fiereu.openmmo.protocols.game.packets.DialogChoiceDeserializer
import de.fiereu.openmmo.protocols.game.packets.DialogChoiceSerializer
import de.fiereu.openmmo.protocols.game.packets.DialogDataDeserializer
import de.fiereu.openmmo.protocols.game.packets.DialogDataSerializer
import de.fiereu.openmmo.protocols.game.packets.DialogOptionDeserializer
import de.fiereu.openmmo.protocols.game.packets.DialogOptionSerializer
import de.fiereu.openmmo.protocols.game.packets.DialogStateDeserializer
import de.fiereu.openmmo.protocols.game.packets.DialogStateSerializer
import de.fiereu.openmmo.protocols.game.packets.EntityInteractDeserializer
import de.fiereu.openmmo.protocols.game.packets.EntityInteractSerializer
import de.fiereu.openmmo.protocols.game.packets.EntityLeaveDeserializer
import de.fiereu.openmmo.protocols.game.packets.EntityLeaveSerializer
import de.fiereu.openmmo.protocols.game.packets.EntityMovePacketDeserializer
import de.fiereu.openmmo.protocols.game.packets.EntityMovePacketSerializer
import de.fiereu.openmmo.protocols.game.packets.FaceDirectionDeserializer
import de.fiereu.openmmo.protocols.game.packets.FaceDirectionSerializer
import de.fiereu.openmmo.protocols.game.packets.InteractiveResponseDeserializer
import de.fiereu.openmmo.protocols.game.packets.InteractiveSerializer
import de.fiereu.openmmo.protocols.game.packets.JoinPacketDeserializer
import de.fiereu.openmmo.protocols.game.packets.JoinPacketSerializer
import de.fiereu.openmmo.protocols.game.packets.JoinResponseDeserializer
import de.fiereu.openmmo.protocols.game.packets.JoinResponseSerializer
import de.fiereu.openmmo.protocols.game.packets.KeepAlivePacketDeserializer
import de.fiereu.openmmo.protocols.game.packets.KeepAlivePacketSerializer
import de.fiereu.openmmo.protocols.game.packets.LoadEntityDeserializer
import de.fiereu.openmmo.protocols.game.packets.LoadEntitySerializer
import de.fiereu.openmmo.protocols.game.packets.LoadMapPacketDeserializer
import de.fiereu.openmmo.protocols.game.packets.LoadMapPacketSerializer
import de.fiereu.openmmo.protocols.game.packets.MapTransitionAckDeserializer
import de.fiereu.openmmo.protocols.game.packets.MapTransitionAckSerializer
import de.fiereu.openmmo.protocols.game.packets.MapTransitionDeserializer
import de.fiereu.openmmo.protocols.game.packets.MapTransitionSerializer
import de.fiereu.openmmo.protocols.game.packets.MovementPacketDeserializer
import de.fiereu.openmmo.protocols.game.packets.MovementPacketSerializer
import de.fiereu.openmmo.protocols.game.packets.NpcAnimationDeserializer
import de.fiereu.openmmo.protocols.game.packets.NpcAnimationSerializer
import de.fiereu.openmmo.protocols.game.packets.NpcSpawnDeserializer
import de.fiereu.openmmo.protocols.game.packets.NpcSpawnSerializer
import de.fiereu.openmmo.protocols.game.packets.NpcUpdateDeserializer
import de.fiereu.openmmo.protocols.game.packets.NpcUpdateSerializer
import de.fiereu.openmmo.protocols.game.packets.NullPacketDeserializer
import de.fiereu.openmmo.protocols.game.packets.PokemonContainerDeserializer
import de.fiereu.openmmo.protocols.game.packets.PokemonContainerSerializer
import de.fiereu.openmmo.protocols.game.packets.RenderScreenDeserializer
import de.fiereu.openmmo.protocols.game.packets.RenderScreenSerializer
import de.fiereu.openmmo.protocols.game.packets.RequestCharactersDeserializer
import de.fiereu.openmmo.protocols.game.packets.RequestCharactersPacketSerializer
import de.fiereu.openmmo.protocols.game.packets.RequestPlayerDeserializePacket
import de.fiereu.openmmo.protocols.game.packets.RequestPlayerSerializer
import de.fiereu.openmmo.protocols.game.packets.SelectCharacterDeserializer
import de.fiereu.openmmo.protocols.game.packets.SelectCharacterSerializer
import de.fiereu.openmmo.protocols.game.packets.SelectedCharacterDeserializer
import de.fiereu.openmmo.protocols.game.packets.SelectedCharacterSerializer
import de.fiereu.openmmo.protocols.game.packets.TokenPayloadSerializer
import de.fiereu.openmmo.protocols.incomingPacket
import de.fiereu.openmmo.protocols.outgoingPacket

/**
 * Game Protocol implementation. This protocol handles in-game communication after the TLS handshake
 * is complete.
 */
abstract class GameProtocol : Protocol() {
  override val async: Boolean = true
  override val compressed: Boolean = true
}

class GameServerProtocol : GameProtocol() {
  init {
    // Game server: client sends 0x01 as first packet (client info / "JoinGame")
    incomingPacket(0x01u, JoinPacketDeserializer())
    outgoingPacket(0x01u, JoinResponseSerializer())

    incomingPacket(0x02u, RequestCharactersDeserializer())
    outgoingPacket(0x02u, CharacterListSerializer())

    incomingPacket(0x03u, CreateCharacterDeserializer())

    incomingPacket(0x04u, SelectCharacterDeserializer())
    outgoingPacket(0x04u, SelectedCharacterSerializer())

    incomingPacket(0x05u, RequestPlayerDeserializePacket())
    outgoingPacket(0x05u, LoadEntitySerializer())

    incomingPacket(0x06u, MovementPacketDeserializer()) // Client movement
    outgoingPacket(0xEAu, EntityMovePacketSerializer()) // Entity move broadcast

    incomingPacket(0x07u, FaceDirectionDeserializer()) // Face direction

    outgoingPacket(0x08u, EntityLeaveSerializer()) // Entity leave area

    incomingPacket(0x09u, ChatMessageDeserialize()) // Chat message
    outgoingPacket(0x09u, ChatMessageSerialize())

    outgoingPacket(0x10u, LoadMapPacketSerializer()) // Load map
    incomingPacket(0x10u, LoadMapPacketDeserializer())

    outgoingPacket(0x13u, PokemonContainerSerializer()) // Pokemon data
    incomingPacket(0x13u, PokemonContainerDeserializer())

    incomingPacket(0x20u, NullPacketDeserializer()) // Token exchange (heartbeat/token)
    outgoingPacket(0x20u, TokenPayloadSerializer())

    outgoingPacket(0xB4u, RenderScreenSerializer()) // Render screen
    incomingPacket(0xB4u, RenderScreenDeserializer())

    incomingPacket(0xC2u, KeepAlivePacketDeserializer()) // Keepalive echo
    outgoingPacket(0xC2u, KeepAlivePacketSerializer())

    incomingPacket(0x0Du, DialogDataDeserializer())
    outgoingPacket(0x0Du, DialogDataSerializer())

    incomingPacket(0x0Eu, DialogStateDeserializer())
    outgoingPacket(0x0Eu, DialogStateSerializer())

    outgoingPacket(0x11u, NpcUpdateSerializer())

    incomingPacket(0x12u, NpcSpawnDeserializer())
    outgoingPacket(0x12u, NpcSpawnSerializer())

    incomingPacket(0x1Bu, MapTransitionDeserializer())
    outgoingPacket(0x1Bu, MapTransitionSerializer())

    incomingPacket(0x21u, InteractiveResponseDeserializer())
    outgoingPacket(0x21u, InteractiveSerializer())

    incomingPacket(0x22u, EntityInteractDeserializer()) // Entity interaction start
    outgoingPacket(0x22u, EntityInteractSerializer())

    incomingPacket(0x25u, DialogChoiceDeserializer())
    outgoingPacket(0x25u, DialogChoiceSerializer())

    incomingPacket(0x26u, DialogOptionDeserializer())
    outgoingPacket(0x26u, DialogOptionSerializer())

    incomingPacket(0xB2u, NpcAnimationDeserializer())
    outgoingPacket(0xB2u, NpcAnimationSerializer())

    incomingPacket(0xB9u, MapTransitionAckDeserializer())
    outgoingPacket(0xB9u, MapTransitionAckSerializer())
  }
}

class GameClientProtocol : GameProtocol() {
  init {
    outgoingPacket(0x01u, JoinPacketSerializer()) // Client info / "JoinGame"
    incomingPacket(0x01u, JoinResponseDeserializer())

    outgoingPacket(0x02u, RequestCharactersPacketSerializer())
    incomingPacket(0x02u, CharacterListDeserializer())

    outgoingPacket(0x04u, SelectCharacterSerializer())
    incomingPacket(0x04u, SelectedCharacterDeserializer())

    outgoingPacket(0x05u, RequestPlayerSerializer())
    incomingPacket(0x05u, LoadEntityDeserializer())

    outgoingPacket(0x06u, MovementPacketSerializer()) // Client movement
    incomingPacket(0xEAu, EntityMovePacketDeserializer()) // Entity move broadcast

    outgoingPacket(0x07u, FaceDirectionSerializer()) // Face direction

    incomingPacket(0x08u, EntityLeaveDeserializer()) // Entity leave area

    outgoingPacket(0x09u, ChatMessageSerialize())
    incomingPacket(0x09u, ChatMessageDeserialize())

    outgoingPacket(0x10u, LoadMapPacketSerializer())
    incomingPacket(0x10u, LoadMapPacketDeserializer())

    outgoingPacket(0x13u, PokemonContainerSerializer())
    incomingPacket(0x13u, PokemonContainerDeserializer())

    outgoingPacket(0x20u, TokenPayloadSerializer())
    incomingPacket(0x20u, NullPacketDeserializer())

    outgoingPacket(0xB4u, RenderScreenSerializer())
    incomingPacket(0xB4u, RenderScreenDeserializer())

    outgoingPacket(0xC2u, KeepAlivePacketSerializer()) // Keepalive echo
    incomingPacket(0xC2u, KeepAlivePacketDeserializer())

    outgoingPacket(0x0Du, DialogDataSerializer())
    incomingPacket(0x0Du, DialogDataDeserializer())

    outgoingPacket(0x0Eu, DialogStateSerializer())
    incomingPacket(0x0Eu, DialogStateDeserializer())

    incomingPacket(0x11u, NpcUpdateDeserializer())

    outgoingPacket(0x12u, NpcSpawnSerializer())
    incomingPacket(0x12u, NpcSpawnDeserializer())

    outgoingPacket(0x1Bu, MapTransitionSerializer())
    incomingPacket(0x1Bu, MapTransitionDeserializer())

    outgoingPacket(0x21u, InteractiveSerializer())
    incomingPacket(0x21u, InteractiveResponseDeserializer())

    outgoingPacket(0x22u, EntityInteractSerializer())
    incomingPacket(0x22u, EntityInteractDeserializer())

    outgoingPacket(0x25u, DialogChoiceSerializer())
    incomingPacket(0x25u, DialogChoiceDeserializer())

    outgoingPacket(0x26u, DialogOptionSerializer())
    incomingPacket(0x26u, DialogOptionDeserializer())

    outgoingPacket(0xB2u, NpcAnimationSerializer())
    incomingPacket(0xB2u, NpcAnimationDeserializer())

    outgoingPacket(0xB9u, MapTransitionAckSerializer())
    incomingPacket(0xB9u, MapTransitionAckDeserializer())
  }
}
