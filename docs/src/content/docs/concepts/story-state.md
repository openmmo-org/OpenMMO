---
title: Story flags and vars
description: How a character's story progression is stored, why the keys are opaque strings, and where the constants come from.
---

Story progression is two things: **flags**, which are booleans, and **vars**,
which are named integers. A flag records that something happened
(`FLAG_VISITED_LITTLEROOT_TOWN`), a var records where in a multi step sequence a
player is (`VAR_LITTLEROOT_INTRO_STATE`). Between them they are what makes an npc
say something different on the second visit.

## The api

Scripts touch story state through `ScriptContext`, which forwards to
`StoryService` (`server.game/.../services/StoryService.kt`) with the acting
player's character id filled in:

```kotlin
ctx.isFlagSet(HoennFlags.FLAG_ADVENTURE_STARTED)   // Boolean
ctx.setFlag(HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN)
ctx.clearFlag(HoennFlags.FLAG_SYS_POKEMON_GET)

ctx.getVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE)   // Int
ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 3)
```

Two defaults matter, and they are chosen to match how the decomp scripts already
think:

- A flag that was never set reads as **false**.
- A var that was never set reads as **0**.

So a fresh character needs no seeding: every flag is unset and every var is 0,
which is exactly the start-of-game state the decomp assumes. `setVar(key, 0)`
does not store a zero row, it removes the key, keeping the two representations of
"never set" identical.

## Why the keys are opaque strings

`StoryService` takes a `String` key and does nothing with it but use it as a map
key. It does not know what a flag *means*, does not know GBA flag numbering, and
does not import anything from the decomp side. The database schema
(`V2__create_story_state.sql`) is the same:

```sql
CREATE TABLE character_flags (
  character_id BIGINT       NOT NULL REFERENCES characters (id) ON DELETE CASCADE,
  flag_key     VARCHAR(128) NOT NULL,
  PRIMARY KEY (character_id, flag_key)
);
```

A row in `character_flags` means the flag is set; no row means unset.
`character_vars` adds an `INT` value column.

That is deliberate. The GBA decomp is the content source *today*, but it is not
supposed to be the only one. Keeping the keys opaque means a hand authored
questline or a non GBA game can drive the same store through the same api without
the persistence layer learning anything about Pokémon Emerald. The decomp is an
adapter on top, not the model.

## Where the constants come from

You never type a raw key string. Each region gets a generated pair of constant
objects, `HoennFlags`/`HoennVars` and `KantoFlags`/`KantoVars`, in
`de.fiereu.openmmo.story.generated.<region>`.

They are produced by the `story` generator
(`codegen/.../codegen/story/`), which reads `include/constants/flags.h` and
`include/constants/vars.h` from each decomp and keeps only the names. The numeric
values are the client's business and are not needed to key the store. Unlike the
script stubs, this generator **is** wired into the build as `generateStory`, so
the constants regenerate on compile and are not committed.

Each constant's value is the name **namespaced by region**:

```kotlin
object HoennFlags {
  const val FLAG_ADVENTURE_STARTED = "hoenn/FLAG_ADVENTURE_STARTED"
  // ...
}
```

The namespace is what keeps Hoenn's and Kanto's overlapping flag names from
colliding in one character's store. This is also why you should always reference
the constant rather than writing the string: `"FLAG_ADVENTURE_STARTED"` and
`HoennFlags.FLAG_ADVENTURE_STARTED` are different keys, and the first one will
silently never match.

## Persistence

Reads and writes go to `CharacterStore`, the in-memory cache that fronts the
database. Every mutation marks the character dirty and the periodic flusher
writes it back after a debounce, so a script does not pay for a database round
trip mid-conversation.

Writes copy the collection instead of mutating in place, so the flusher never
snapshots a half-updated set. Setting a flag that is already set, or a var to the
value it already has, is a no-op and does not dirty the character.

## Reading story state outside a script

`MapEntryScripts` reads vars directly to pick which `ON_FRAME` script to run on
map entry, comparing `storyService.getVar(charId, it.varKey)` against each
entry's value. Any other service can do the same: inject `StoryService` and pass
the character id. `ScriptContext` is only a convenience wrapper for the common
case where the acting character is obvious.
