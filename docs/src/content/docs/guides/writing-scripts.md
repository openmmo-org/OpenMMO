---
title: Writing overworld scripts
description: Where the overworld scripts live, what one looks like, and how to change the dialog an npc or sign shows.
---

Every npc and sign in the overworld runs a **script**: a small piece of Kotlin
that decides what happens when the player presses A on it. Maps run scripts too,
on entry, with no button press involved.

This page is the starting point. It covers where the scripts are, what one looks
like, and how to change what an existing one does. From here:

- [Porting a decomp script](../porting-scripts/): filling in a `TODO` stub, the
  decomp command reference, cutscenes, and what to do about commands that have no
  equivalent yet.
- [The script system](../../concepts/scripts/): how a button press finds your
  Kotlin, and the runtime rules that follow from it.

## Where the scripts live

All scripts are in one folder, one file per map:

```
server.game/src/main/kotlin/de/fiereu/openmmo/server/game/script/generated/
  hoenn/LittlerootTown.kt
  kanto/PalletTown.kt
  ...
```

These files were bootstrapped once by a generator, but you edit them **by hand**
from now on. The generator is not part of the build, so a normal `gradlew build`
never touches your work. Do not run `gradlew :codegen:generateScriptStubs`
yourself: it deletes each region's package and re-emits it, wiping every port.

## What a script looks like

Here is a real file, `hoenn/LittlerootTown.kt`. Two kinds of entries show up:

```kotlin
// Done: shows one npc dialog box and closes.
internal object LittlerootTown_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LittlerootTown.BirchSpendsDaysInLab)
}

// Not done yet: the real script had branches, so it needs a hand port.
/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_ADVENTURE_STARTED, LittlerootTown_EventScript_GoodLuck
 * msgbox LittlerootTown_Text_IfYouGoInGrassPokemonWillJumpOut, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LittlerootTown_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LittlerootTown_EventScript_Twin")
}
```

Most scripts are still the second kind. Roughly 2500 of the 3650 generated
objects are `TODO` stubs carrying their original decomp body in a comment;
[Porting a decomp script](../porting-scripts/) is about turning those into
Kotlin.

Each object is keyed by its **decomp label** (`LittlerootTown_EventScript_Boy`).
The label comes from the map data, so it is never guessed.

## What a script can do

Everything goes through `ctx`, the `ScriptContext`:

| Call | What it does |
| --- | --- |
| `ctx.say(line)` | An npc box from the entity the player talked to. Waits. |
| `ctx.sign(line)` | A sign or object box, no speaker. Waits. |
| `ctx.isFlagSet(f)` / `ctx.setFlag(f)` / `ctx.clearFlag(f)` | Read and write a story flag. |
| `ctx.getVar(k)` / `ctx.setVar(k, n)` | Read and write a story var. |
| `ctx.moveNpc(localId, ...steps)` | Walk a map npc through a path. Waits for it to finish. |
| `ctx.moveSelf(...steps)` | Walk the player's own avatar. Waits. |
| `ctx.showNpc(localId)` | Reveal a normally hidden npc for a cutscene. |
| `ctx.setDynamicWarp(...)` | Point this player's `MAP_DYNAMIC` warp somewhere. |
| `ctx.entityId` | The npc that was talked to, or `-1` for a sign or map script. |

That is the whole api. Plenty of decomp commands have no equivalent in it yet,
choices, doors, giving items, trainer battles,
[which the porting guide lists](../porting-scripts/#commands-with-no-equivalent-yet).

The calls that say "waits" suspend until the player acts, so you can write boxes
top to bottom and they show one after another. They are also the *only* correct
way to wait: awaiting a packet directly inside a script locks up the connection,
for [the reason explained here](../../concepts/scripts/#why-scripts-are-coroutines).

Flag and var keys come from the generated `HoennFlags` / `HoennVars` objects (or
the Kanto pair). Always use the constant, never a raw string, because the real
keys are region namespaced. See
[Story flags and vars](../../concepts/story-state/).

## Tweak an existing script

Say the boy should show a different line. Just change the dialog value:

```kotlin
internal object LittlerootTown_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LittlerootTown.SomeOtherLine)
}
```

For more than one box:

```kotlin
internal object LittlerootTown_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(LittlerootTown.BirchSpendsDaysInLab)
    ctx.say(LittlerootTown.AndOneMoreThing)
  }
}
```

`line` is a value from the generated dialog enum for that map (like
`LittlerootTown.BirchSpendsDaysInLab`). Every text line the decomp had is already
in that enum, so type `LittlerootTown.` and let autocomplete show you the names.

## Keep the script in the map

At the bottom of every file is a map listing every script in it. **A script that
is not in this map cannot be found**, so if you add a brand new object, add it
here too:

```kotlin
internal val LittlerootTownScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_EventScript_Boy" to LittlerootTown_EventScript_Boy,
        "LittlerootTown_EventScript_Twin" to LittlerootTown_EventScript_Twin,
        // ...
    )
```

## Test it

Restart the game server and press A on the npc or sign in the client. If nothing
shows, check the server log. Two messages mean different things:

```
NPC entityIdx=3 script=LittlerootTown_EventScript_Boy has no wired dialog
```

The label is not in the map above, so the lookup returned nothing.

```
Script not ported yet
```

The label resolved fine, but the body is still a `TODO(...)` stub.

For a script whose whole point is a state change rather than a visible box, write
a test instead of squinting at the client,
[the pattern is here](../porting-scripts/#testing-a-port).
