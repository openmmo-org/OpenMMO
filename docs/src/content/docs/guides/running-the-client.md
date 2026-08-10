---
title: Running the game client
description: How the launcher downloads a PokeMMO client, patches it, and points it at an OpenMMO server.
---

The launcher gets a PokeMMO client from the official servers, patches it, and
starts it. It works in its own folder and never touches a PokeMMO install you
already have.

## Start it

Run the servers first, then:

```bash
./gradlew :launcher:dev
```

This does two things. It serves a feed on `https://127.0.0.1:20443`, and it opens
the launcher window. Press Play, and the client starts.

Leave the command running while you play. The client reads the feed the whole
time it is open, so stopping it too early breaks the game. The launcher itself
closes as soon as the client is up, which is normal.

## What ends up where

Everything lives under `%LOCALAPPDATA%/OpenMMO`, or the XDG equivalent on Linux
and macOS.

| Folder | What is in it |
|--------|---------------|
| `client` | Exactly what PokeMMO publishes, checked against their hashes. |
| `runtime` | The patched copy the game runs from. Rebuilt on every launch. |

The two are kept apart so a broken file can always be repaired from a known good
copy. Files no patch touches are hard linked, so the second folder costs almost
no disk.

The client writes its own settings and ROM paths into `runtime`, and those are
left alone when the folder is rebuilt.

## Why the client trusts us

The client will not start without a feed, and it checks that feed against a key
built into it. The launcher patches our key over theirs, so the client accepts a
feed we signed.

In development that feed comes from the server `:launcher:dev` runs. A released
launcher reads the published one instead:

```bash
./gradlew :launcher:createDistributable -Popenmmo.feedOrigin=https://feed.openmmo.dev
```

## Adding a patch

Patches live in `launcher/manifests/manifest-<revision>.toml`, one file per
client version. A patch that no longer matches fails the launch and names
itself, so a client update never corrupts anything quietly.

```toml
[[patches]]
type = "binary_string"
name = "SupportUrl"
target = "@client"
find = "https://support.pokemmo.com"
replace = "https://support.openmmo.dev"
```

`target = "@client"` means the game binary, whatever it is called on this
platform.

**A replacement must be exactly as long as the text it replaces.** The client
stores the length of every string separately, so a longer one cannot fit and a
shorter one leaves rubbish behind. For a url you can pad the end with `?xxx`,
which servers ignore.

Two other patch types exist. `strings` adds lines to `data/strings/strings_*.xml`
using ids from 1000000000 up, which is above anything PokeMMO uses. `file` writes
a whole file from an asset next to the manifest.
