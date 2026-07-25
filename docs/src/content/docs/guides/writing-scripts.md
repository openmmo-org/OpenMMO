---
title: Writing overworld scripts
description: How to fill in missing npc and sign behaviour and tweak the dialog an existing script shows.
---

Every npc and sign in the overworld runs a **script**. A script decides what
happens when the player presses A on it: which dialog box to show, what to ask,
what to give. This guide shows how to fill in a script that is not done yet, and
how to change what an existing one does.

## How it fits together

```
player presses A
        │
        v
InteractionService     finds the entity/tile and its script label
        │  looks up the label in
        v
ScriptRegistry         label -> Script  (from GeneratedScripts.byLabel)
        │  runs
        v
Script.run(ctx)        your code, shows dialog through ctx
```

Each script is a small object keyed by its **decomp label** (like
`LittlerootTown_EventScript_Boy`). The label comes from the map data, so we never
guess it. When the label has no script wired, the player just gets no dialog and
the server logs it.

## Where the scripts live

All scripts are in one folder, one file per map:

```
server.game/src/main/kotlin/de/fiereu/openmmo/server/game/script/generated/
  hoenn/LittlerootTown.kt
  kanto/PalletTown.kt
  ...
```

These files were made once by a generator, but you edit them **by hand** from now
on. The generator does not run on a normal build, so your changes are safe. (See
[the warning below](#never-re-run-the-generator-over-your-work).)

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

At the bottom of every file is a map that lists every script in it. **Every
script must be in this map** or the game cannot find it:

```kotlin
internal val LittlerootTownScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_EventScript_Boy" to LittlerootTown_EventScript_Boy,
        "LittlerootTown_EventScript_Twin" to LittlerootTown_EventScript_Twin,
        // ...
    )
```

Use `ctx.say(line)` for an npc box and `ctx.sign(line)` for a sign or object.
Both **wait** for the player to close the box before the next line runs, so you
can just write boxes top to bottom and they show one after another.

`line` is a value from the generated dialog enum for that map (like
`LittlerootTown.BirchSpendsDaysInLab`). Every text line the decomp had is already
in that enum, so type `LittlerootTown.` and let autocomplete show you the names.

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

## Port a missing script

A `TODO("port ...")` script has its original decomp in the comment right above
it. Read that, then replace the `TODO(...)` with real code.

Start simple. This decomp:

```
lock
faceplayer
msgbox LittlerootTown_Text_IfYouGoInGrassPokemonWillJumpOut, MSGBOX_DEFAULT
release
end
```

is just one npc box. Ignore the `lock` / `faceplayer` / `release` / `end` lines,
they are engine plumbing. The real content is the one `msgbox`:

```kotlin
internal object LittlerootTown_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LittlerootTown.IfYouGoInGrassPokemonWillJumpOut)
}
```

For a branching script, the decomp uses `goto_if_set` / `goto_if_eq` to jump to
other labels based on flags or variables. Port the branch with normal Kotlin
`if`. Flags and variables are not wired up yet, so for now pick the branch that
fits a fresh save, and leave a `// TODO` for the condition:

```kotlin
internal object LittlerootTown_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) {
    // TODO check FLAG_ADVENTURE_STARTED once flags exist
    ctx.say(LittlerootTown.IfYouGoInGrassPokemonWillJumpOut)
  }
}
```

## Rules to remember

### Never re-run the generator over your work

The generator overwrites these files. It does **not** run on a normal build, so
you are safe day to day. Do not run `gradlew :codegen:generateScriptStubs`
after you start porting, or it wipes your ports.

### Keep the script in the map

If you add a brand new script object, add it to the map at the bottom of the file
too. If it is not in the map, the label lookup returns null and nothing shows.

### Do not wait on packets yourself

`ctx.say` and `ctx.sign` are the only correct way to wait for the player. They run
on a separate scope so the connection keeps reading packets while the box is open.
If you `await` a packet directly inside a script, the reply never arrives and the
connection locks up.

## Test it

Restart the game server and press A on the npc or sign in the client. If nothing
shows, check the server log. A missing wiring prints something like:

```
NPC entityIdx=3 script=LittlerootTown_EventScript_Boy has no wired dialog
```

which means the label is not in the map.
