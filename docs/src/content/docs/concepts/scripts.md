---
title: The script system
description: How an npc, sign, or map entry turns into running Kotlin, and why scripts are coroutines.
---

Almost everything the overworld does when a player presses A is a **script**: a
small piece of Kotlin keyed by a decomp script label. This page explains how a
button press finds that Kotlin, what runs it, and the rules that fall out of the
way it runs.

If you just want to write or fix one, read
[Writing overworld scripts](../../guides/writing-scripts/). This page is the
model behind it.

## The pieces

Everything lives in
`server.game/src/main/kotlin/de/fiereu/openmmo/server/game/script/`:

- `Script.kt`: a `fun interface` with one method, `suspend fun run(ctx: ScriptContext)`.
- `ScriptContext.kt`: everything a script is allowed to do to the player.
- `ScriptRegistry.kt`: decomp label -> `Script`.
- `ScriptRunner.kt`: launches a script and owns the dialog lock.
- `MovementStep.kt`: the small movement vocabulary cutscenes use.
- `generated/`: one file per map holding the script objects themselves.

The heavy lifting a script asks for is delegated to three services:
`DialogService` (boxes), `StoryService` (flags and vars, see
[Story flags and vars](../story-state/)), and `ScriptMovementService` (scripted
walking).

## From button press to Kotlin

```
  player presses A
        │
        v
 InteractionService    finds the npc or bg event, reads its script label
        │              looks the label up in
        v
   ScriptRegistry      label -> Script  (GeneratedScripts.byLabel)
        │              hands it to
        v
   ScriptRunner        claims the dialog, launches a coroutine
        │              runs
        v
 Script.run(ctx)       your code, talks to the player through ctx
```

### Labels come from the map data

A script is never found by guessing. `MapDef` carries the decomp label straight
through from the pret map json: `NpcDef.script` for an npc, `BgEventDef.script`
for a sign, `MapDef.onTransitionScript` and `MapFrameScript.script` for map
entry. `ScriptRegistry.forLabel` looks that string up and returns `null` when
nothing is wired, which is the normal case today. Nothing happens and the server
logs it:

```
NPC entityIdx=3 script=LittlerootTown_EventScript_Boy has no wired dialog
```

`GeneratedScripts.byLabel` is the one big map, built by `putAll`ing each map's
`...Scripts` map. It is emitted in chunks of 40 maps across `chunk0()`, `chunk1()`
and so on, so no single method ends up enormous.

## The three triggers

### An npc

`InteractionService.onEntityInteract` handles `EntityInteractPacket`. It resolves
the player's current map, walks `map.npcs` looking for the one whose entity id
matches the packet, and runs its script with that entity id. The script's
`ctx.entityId` is that npc, so `ctx.say` makes the box point at it.

### A sign or a piece of furniture

`InteractionService.onTileInteract` handles `TileInteractPacket`. There is no
entity, so the server computes the tile the player faces from their position and
`facingDirection`, then looks for a bg event on it. The bg event's own
`facingDir` has to agree: `BG_EVENT_PLAYER_FACING_ANY` always matches, the
`NORTH`/`SOUTH`/`WEST`/`EAST` variants only match a player facing that way.
Scripts triggered this way get `entityId = -1`.

### Entering a map

`MapScriptService.onMapEnter` runs on every entry, however the player got there:
login, a warp, or walking across a map connection. `MapEntryScripts.onEntry`
builds the list and `MapScriptService` runs it in order:

1. `map.onTransitionScript`, unconditionally.
2. The **first** entry in `map.onFrameScripts` whose story var currently equals
   its value, that is `storyService.getVar(charId, it.varKey) == it.value`.

Both go through `ScriptRunner.runAll` on one coroutine, so an on-transition
script and the cutscene it enables share a single dialog lock instead of racing.

A script that warps is the exception: `ctx.warp` runs the destination map's
entry scripts inline on the coroutine it is already on, the way the decomp's
warp continues into the destination's scripts, rather than starting a new one.
The conditional `ON_WARP` table is not wired up yet.

## Why scripts are coroutines

A script that shows two boxes has to *wait* in the middle:

```kotlin
ctx.say(LittlerootTown.OurNewHomeLetsGoInside)  // suspends here
ctx.say(LittlerootTown.SomethingElse)
```

Underneath, `DialogService.showAndWait` parks a `CompletableDeferred` in the
session attribute `PENDING_DIALOG`, sends the box, and awaits. The thing that
completes that deferred is the player's `DialogActionResponsePacket`, arriving
through the connection's normal packet handling.

So the wait can only finish if packet handling keeps running while the script is
parked. That is the whole reason `ScriptRunner` launches on a separate scope
(`SCRIPT_SCOPE` on the session, a `SupervisorJob` on `Dispatchers.Default`)
rather than running inside the packet handler. **If a script blocks or awaits a
packet directly, the reply it is waiting for never gets read and the connection
locks up.** Use `ctx.say`, `ctx.sign`, `ctx.moveNpc` and `ctx.moveSelf`, never a
raw await.

## One script at a time

`ScriptRunner` sets `state.inDialog = true` *before* launching the coroutine, and
every trigger checks `state.inDialog` and bails if it is already set. That
combination is what stops a player from mashing A and starting two scripts on top
of each other, or from walking into a map cutscene while an npc conversation is
open. `DialogService.close` in the runner's `finally` clears it again, so the
lock is released even when a script throws.

## How a script can end

`ScriptRunner` catches three things, deliberately differently:

| Outcome | What happens |
| --- | --- |
| `CancellationException` | Rethrown. Cancellation must not be swallowed. |
| `NotImplementedError` | Logged at info as "Script not ported yet". This is the `TODO("port ...")` stub firing, and it is expected, not a bug. |
| Any other `Exception` | Logged at error with the stack trace. |

In all three cases the `finally` closes the dialog, so a broken script leaves the
player able to move rather than stuck in a box.

## Scripts are per player

Nothing a script does is broadcast. `ScriptMovementService` sends its movement
packets to the acting player's session only, so a cutscene where an npc walks
across the room plays for the player who triggered it and is invisible to anyone
else standing on that map. That is the right model for story content, where two
players on the same map can legitimately be at different points in the story.

The one thing that does escape the session is story state: flags and vars are
written through to the character in the database. See
[Story flags and vars](../story-state/).

## Where the script objects come from

The files under `script/generated/` were bootstrapped once from the decomp by
`gradlew :codegen:generateScriptStubs` and are **committed source that you edit
by hand**. The task is deliberately not part of the build. Re-running it deletes
each region package and re-emits it, so it destroys every hand written port. See
[Porting a decomp script](../../guides/porting-scripts/) for the workflow.
