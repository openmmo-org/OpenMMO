---
title: Porting a decomp script
description: How to turn a decomp script body into Kotlin, which commands have an equivalent, and what to do about the ones that do not.
---

Most overworld scripts are not ported yet. Of roughly 3650 generated script
objects, about 2500 are still `TODO("port ...")` stubs carrying their original
decomp body in a comment. This guide is the reference for turning one of those
bodies into Kotlin.

For the basics of editing a script file at all, read
[Writing overworld scripts](../writing-scripts/) first. For how the runtime
works, see [The script system](../../concepts/scripts/).

## The workflow

1. Find a stub. `grep -rn 'TODO("port' server.game/src/main/kotlin/.../script/generated/`
   or just open the map you care about.
2. Read the decomp body in the KDoc above it. That comment is the source of
   truth; you do not need the decomp checked out to port most scripts.
3. Translate it with the table below.
4. Delete the `TODO(...)`, keep the KDoc if anything is still missing, and say
   what is missing.
5. Build and run it in the client.

Pick small scripts first. A `lock` / `faceplayer` / `msgbox` / `release` / `end`
body is one line of Kotlin; a body with `special`, `trainerbattle` or door
animations is not portable yet at all.

## Command reference

### Framing you can drop

These do not change what the player sees, because the runtime already handles the
equivalent. Ignore them:

`lock`, `lockall`, `faceplayer`, `release`, `releaseall`, `closemessage`,
`waitmessage`, `waitbuttonpress`, `end`, `return`.

The dialog lock is taken by `ScriptRunner` before your script starts and released
in its `finally`, which covers `lock`/`release`. `end` and `return` are just the
end of your function.

### Commands with a direct equivalent

| Decomp | Kotlin |
| --- | --- |
| `msgbox TEXT, MSGBOX_SIGN` | `ctx.sign(Map.Text)` |
| `msgbox TEXT, MSGBOX_DEFAULT` (and other types) | `ctx.say(Map.Text)` |
| `setflag FLAG_X` | `ctx.setFlag(HoennFlags.FLAG_X)` |
| `clearflag FLAG_X` | `ctx.clearFlag(HoennFlags.FLAG_X)` |
| `goto_if_set FLAG_X, Label` | `if (ctx.isFlagSet(HoennFlags.FLAG_X)) { ... }` |
| `goto_if_unset FLAG_X, Label` | `if (!ctx.isFlagSet(HoennFlags.FLAG_X)) { ... }` |
| `setvar VAR_X, n` | `ctx.setVar(HoennVars.VAR_X, n)` |
| `goto_if_eq VAR_X, n, Label` | `if (ctx.getVar(HoennVars.VAR_X) == n) { ... }` |
| `goto_if_ne VAR_X, n, Label` | `if (ctx.getVar(HoennVars.VAR_X) != n) { ... }` |
| `applymovement id, M` + `waitmovement 0` | `ctx.moveNpc(LOCALID_X, WALK_UP, ...)` |
| `applymovement OBJ_EVENT_ID_PLAYER, M` + `waitmovement 0` | `ctx.moveSelf(WALK_UP, ...)` |
| `addobject id` | `ctx.showNpc(LOCALID_X)` |
| `setdynamicwarp ...` | `ctx.setDynamicWarp(region, bank, map, x, y, facing)` |

`ctx.say` and `ctx.sign` both **wait** for the player to close the box, so
consecutive calls show consecutive boxes with no extra plumbing. `moveNpc` and
`moveSelf` likewise cover `applymovement` *and* its following `waitmovement`, so
drop the `waitmovement` line.

Flag and var constants live in the generated `HoennFlags` / `HoennVars` (or
`KantoFlags` / `KantoVars`) objects. Always use the constant, never a raw string,
because the real keys are region namespaced. See
[Story flags and vars](../../concepts/story-state/).

### `goto` and `call` between scripts

Every script in a file is an `object`, so a jump to another label in the same map
is a plain call:

```kotlin
// call LittlerootTown_EventScript_GiveRunningShoes
LittlerootTown_EventScript_GiveRunningShoes.run(ctx)
```

`call` runs the other script and comes back, which is exactly what that line
does. `goto` never comes back, so port it as the same call placed **last** in the
branch, with nothing after it:

```kotlin
// goto_if_set FLAG_ADVENTURE_STARTED, LittlerootTown_EventScript_GoodLuck
if (ctx.isFlagSet(HoennFlags.FLAG_ADVENTURE_STARTED)) {
  return LittlerootTown_EventScript_GoodLuck.run(ctx)
}
```

If the target label lives in another map's file it will not be visible, since the
generated objects are `internal` and per file. Either port the shared behaviour
inline or move it somewhere both can see.

### Movement steps

`MovementStep` is the whole vocabulary, deliberately small and game agnostic:
`WALK_UP` / `WALK_DOWN` / `WALK_LEFT` / `WALK_RIGHT`, `FACE_UP` / `FACE_DOWN` /
`FACE_LEFT` / `FACE_RIGHT`, and `SET_INVISIBLE` for an npc stepping into a door.

The decomp's named movement templates (`Common_Movement_WalkInPlaceFasterUp` and
friends) expand to a list of these. Faster and slower walk variants all collapse
to the plain walk step; the animation speed is not modelled.

The npc id is the decomp **local id**, which is the npc's `entityIdx` in the map's
object events. Give it a name so the script reads like the original:

```kotlin
// Decomp local id of mom in this map's object events (LOCALID_LITTLEROOT_MOM).
private const val LOCALID_MOM = 3
```

## Commands with no equivalent yet

There is no verb for these. Do not fake them:

- **Choices.** `MSGBOX_YESNO`, `multichoice` and anything reading `VAR_RESULT`
  from a prompt. `DialogService` can see a choice packet come back, but
  `ScriptContext` has no way to ask a question and branch on the answer.
- **Doors.** `opendoor`, `closedoor`, `waitdooranim`.
- **Warping from a script.** `warp`, `warpsilent`, `warphole`. Only
  `setDynamicWarp`, which changes where an existing `MAP_DYNAMIC` warp tile
  sends the player, exists.
- **Hiding things.** `hideobjectat`, `hideplayer`/`showplayer`. `showNpc` exists,
  its inverse does not, beyond ending a movement with `SET_INVISIBLE`.
- **Items, money, party.** `giveitem`, `takeitem`, `checkitemspace`,
  `givemon`, `addvar` on money, and so on.
- **Battles.** `trainerbattle` in all its forms.
- **Player identity.** `checkplayergender`, and the `VAR_RESULT` branch that
  usually follows it.
- **Presentation.** `playse`, `playfanfare`, `waitfanfare`, `fadescreen`,
  `delay`, and `special` calls into engine C code generally.

When a script needs one of these, port the part you can and leave a scoped `TODO`
comment naming what is missing, the way the Littleroot intro does:

```kotlin
ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 3)
// TODO Finish the Littleroot intro cutscene
//  Port the rest of GoInsideWithMom: the player jumping off the truck, the door
//  open/close animations, the player following mom into the house, and the
//  warpsilent inside. These need door, hideplayer, warpsilent and sound-effect
//  script verbs.
```

A named `TODO` comment is much better than a `TODO(...)` stub: the script now does
something useful, and the next person knows exactly what is left. Prefer leaving
the branch a fresh save would take when a condition cannot be evaluated yet.

## Testing a port

Scripts are plain objects with one suspend method, so a test just builds a
context and calls `run`. `MapTransitionScriptTest` is the pattern to copy:

```kotlin
val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
val charId = store.createCharacter(1, "Ash").info.id
val story = StoryService(store)
val session = FakeSession(characterId = charId)
val state = session.attributes[PLAYER_STATE]!!
val mapManager = MapManager()
val movement = ScriptMovementService(mapManager, NpcService(mapManager), store)
val ctx = ScriptContext(session, state, entityId = -1, DialogService(), story, movement)

LittlerootTown_OnTransition.run(ctx)

story.isFlagSet(charId, HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN) shouldBe true
```

This is worth doing for scripts whose point is a state change, since those are
the ones you cannot see by looking at a dialog box. A script that only shows text
is usually faster to check in the client.

## Never re-run the generator

`gradlew :codegen:generateScriptStubs` **deletes each region's generated package
and re-emits it from scratch**. Every hand written port in it is gone. The task
is not part of the build for exactly this reason, so a normal `gradlew build`
never touches your work, but do not run it by hand once porting has started.
