---
title: The InterestManager
description: How the server decides which players receive a packet.
---

When a player does something, other players need to hear about it. A player
walking across Slateport should show up on the screens of the players standing
there, and nobody else. Sending every packet to every online player would be
wasteful and leak things players should not see.

The `InterestManager` solves this. It lives in
`server.game/src/main/kotlin/de/fiereu/openmmo/server/game/world/interest/`.

## The idea

Players are grouped into **interest groups**. A group is identified by an
`InterestKey`, a small value like "this map" or "this guild". A session can be
in several groups at the same time: its current map, its guild, and a running
battle all at once.

The manager itself is intentionally dumb. It only knows three things:

- `join(ctx, key)` / `leave(ctx, key)`: put a session into a group or take it out.
- `members(key)`: who is in a group right now.
- `broadcast(key, packet, exclude)`: send a packet to everyone in a group,
  usually excluding the sender.

It does not know what a map, a guild, or a battle is. What a group *means* is
decided by the service that uses it.

## Movement, as an example

Overworld presence is built on top of the manager by `PresenceService`
(`server.game/.../services/PresenceService.kt`). Each map is one interest
group (`MapInterestKey`). When a player spawns, `PresenceService.enter` joins
them to their map's group and exchanges entity snapshots with the players
already there. When a `MovementPacket` comes in, `MovementService` calls
`presenceService.broadcastMove(ctx, movePkt)`, which fans the move out to that
map's group only. Warping or walking off a map edge calls `refresh`, which
despawns the player from the old group and spawns them into the new one.

## When to use it

Use the `InterestManager` whenever one player's action should reach a
*specific set* of other players:

- guild chat and guild notifications
- battle updates to the participants and spectators
- trade windows, parties, and similar shared sessions

Do not use it for truly global traffic (server announcements, global chat).
That is what `MultiplayerService` is for.

## How to use it

1. Pick or add a key. `InterestKey` is a sealed interface in `InterestKey.kt`.
   `MapInterestKey`, `GuildInterestKey`, and `BattleInterestKey` already exist.
   Add a new data class variant for a new kind of group.
2. Inject the `InterestManager` (it is a Dagger `@Singleton`) into your service.
3. Call `join` when a player enters your group (logs in, joins the guild,
   enters the battle) and `leave` when they exit. `leaveAll(ctx)` removes a
   session from every group on disconnect.
4. Call `broadcast(key, packet, exclude = sender)` to notify the group.

```kotlin
// Guild chat, sketched:
interestManager.join(ctx, GuildInterestKey(guildId))          // on login
interestManager.broadcast(GuildInterestKey(guildId), chatPkt) // on message
```

If your feature needs more than membership, for example spawn and despawn
packets when players come and go, follow the `PresenceService` pattern: build
a small service on top of the manager and put the semantics there.
